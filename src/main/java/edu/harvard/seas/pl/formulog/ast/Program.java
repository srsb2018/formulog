package edu.harvard.seas.pl.formulog.ast;

/*-
 * #%L
 * FormuLog
 * %%
 * Copyright (C) 2018 - 2019 President and Fellows of Harvard College
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

import java.util.Set;

import edu.harvard.seas.pl.formulog.ast.Atoms.Atom;
import edu.harvard.seas.pl.formulog.ast.Atoms.NormalAtom;
import edu.harvard.seas.pl.formulog.ast.functions.FunctionDef;
import edu.harvard.seas.pl.formulog.symbols.Symbol;
import edu.harvard.seas.pl.formulog.symbols.SymbolManager;

public interface Program {

	Set<Symbol> getFunctionSymbols();
	
	Set<Symbol> getFactSymbols();
	
	Set<Symbol> getRuleSymbols();
	
	FunctionDef getDef(Symbol sym);
	
	Set<Atom> getFacts(Symbol sym);

	Set<Rule> getRules(Symbol sym);
	
	SymbolManager getSymbolManager();
	
	RelationProperties getRelationProperties(Symbol sym);
	
	boolean hasQuery();
	
	NormalAtom getQuery();
	
	FunctionCallFactory getFunctionCallFactory();

}
