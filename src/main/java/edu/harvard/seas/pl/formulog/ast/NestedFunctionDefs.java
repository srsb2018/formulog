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

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import edu.harvard.seas.pl.formulog.ast.Exprs.ExprVisitor;
import edu.harvard.seas.pl.formulog.ast.Exprs.ExprVisitorExn;
import edu.harvard.seas.pl.formulog.eval.EvaluationException;
import edu.harvard.seas.pl.formulog.unification.Substitution;

public class NestedFunctionDefs implements Expr {
	
	private final Set<NestedFunctionDef> defs;
	
	private NestedFunctionDefs(Set<NestedFunctionDef> defs) {
		this.defs = Collections.unmodifiableSet(defs);
	}
	
	public static NestedFunctionDefs make(Set<NestedFunctionDef> defs) {
		return new NestedFunctionDefs(defs);
	}
	
	public Set<NestedFunctionDef> getDefs() {
		return defs;
	}

	@Override
	public boolean isGround() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Term applySubstitution(Substitution s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Term normalize(Substitution s) throws EvaluationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void varSet(Set<Var> acc) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateVarCounts(Map<Var, Integer> counts) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <I, O> O accept(ExprVisitor<I, O> visitor, I in) {
		return visitor.visit(this, in);
	}

	@Override
	public <I, O, E extends Throwable> O accept(ExprVisitorExn<I, O, E> visitor, I in) throws E {
		return visitor.visit(this, in);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((defs == null) ? 0 : defs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NestedFunctionDefs other = (NestedFunctionDefs) obj;
		if (defs == null) {
			if (other.defs != null)
				return false;
		} else if (!defs.equals(other.defs))
			return false;
		return true;
	}

}