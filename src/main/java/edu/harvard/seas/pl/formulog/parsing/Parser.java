package edu.harvard.seas.pl.formulog.parsing;

import java.io.FileNotFoundException;

/*-
 * #%L
 * Formulog
 * %%
 * Copyright (C) 2018 - 2020 President and Fellows of Harvard College
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import edu.harvard.seas.pl.formulog.Configuration;
import edu.harvard.seas.pl.formulog.ast.BasicProgram;
import edu.harvard.seas.pl.formulog.ast.Term;
import edu.harvard.seas.pl.formulog.parsing.generated.FormulogLexer;
import edu.harvard.seas.pl.formulog.parsing.generated.FormulogParser;
import edu.harvard.seas.pl.formulog.parsing.generated.FormulogParser.ProgContext;
import edu.harvard.seas.pl.formulog.symbols.RelationSymbol;
import edu.harvard.seas.pl.formulog.util.Pair;

public class Parser {

	private FormulogParser getParser(Reader r, boolean isTsv) throws ParseException {
		try {
			CharStream chars = CharStreams.fromReader(r);
			FormulogLexer lexer = new FormulogLexer(chars);
			TokenStream tokens = isTsv ? new BufferedTokenStream(lexer) : new CommonTokenStream(lexer);
			return new FormulogParser(tokens);
		} catch (IOException e) {
			throw new ParseException(e.getMessage());
		}
	}

	public BasicProgram parse(Reader r) throws ParseException {
		return parse(r, Collections.emptyList());
	}

	public BasicProgram parse(Reader r, List<Path> inputDirs) throws ParseException {
		try {
			FormulogParser parser = getParser(r, false);
			ParsingContext pc = new ParsingContext();
			ProgContext progCtx = parser.prog();
			Pair<BasicProgram, Set<RelationSymbol>> p = new TopLevelParser(pc).parse(progCtx);
			BasicProgram prog = p.fst();
			loadExternalEdbs(pc, prog, p.snd(), inputDirs);
			return prog;
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	private void loadExternalEdbs(ParsingContext pc, BasicProgram prog, Set<RelationSymbol> rels, List<Path> inputDirs)
			throws ParseException {
		if (rels.isEmpty()) {
			return;
		}
		ExecutorService exec = Executors.newFixedThreadPool(Configuration.parallelism);
		List<Future<?>> tasks = new ArrayList<>();
		for (Path inputDir : inputDirs) {
			for (RelationSymbol sym : rels) {
				tasks.add(exec.submit(new Runnable() {

					@Override
					public void run() {
						try {
							readEdbFromFile(pc, sym, inputDir, prog.getFacts(sym));
						} catch (ParseException e) {
							throw new RuntimeException(e);
						}
					}

				}));
			}
		}
		exec.shutdown();
		try {
			for (Future<?> task : tasks) {
				task.get();
			}
		} catch (InterruptedException | ExecutionException e) {
			throw new ParseException(e);
		}
	}

	private void readEdbFromFile(ParsingContext pc, RelationSymbol sym, Path inputDir, Set<Term[]> acc) throws ParseException {
		Path path = inputDir.resolve(sym.toString() + ".tsv");
		try (FileReader fr = new FileReader(path.toFile())) {
			FormulogParser parser = getParser(fr, true);
			FactFileParser fpp = new FactFileParser(pc);
			fpp.loadFacts(parser.tsvFile(), sym.getArity(), acc);
		} catch (FileNotFoundException e) {
			throw new ParseException("Could not find external fact file: " + path);
		} catch (Exception e) {
			throw new ParseException("Exception when extracting facts from " + path + ": " + e.getMessage());
		}
	}

}
