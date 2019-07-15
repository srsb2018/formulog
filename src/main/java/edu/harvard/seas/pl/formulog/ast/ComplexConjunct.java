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

import edu.harvard.seas.pl.formulog.ast.ComplexConjuncts.ComplexConjunctExnVisitor;
import edu.harvard.seas.pl.formulog.ast.ComplexConjuncts.ComplexConjunctVisitor;
import edu.harvard.seas.pl.formulog.symbols.RelationSymbol;
import edu.harvard.seas.pl.formulog.unification.Substitution;

public interface ComplexConjunct<R extends RelationSymbol> extends Conjunct<R> {

	ComplexConjunct<R> applySubstitution(Substitution subst);

	boolean isNegated();
	
	<I, O> O accept(ComplexConjunctVisitor<R, I, O> visitor, I input);
	
	<I, O, E extends Throwable> O accept(ComplexConjunctExnVisitor<R, I, O, E> visitor, I input) throws E;
	
}