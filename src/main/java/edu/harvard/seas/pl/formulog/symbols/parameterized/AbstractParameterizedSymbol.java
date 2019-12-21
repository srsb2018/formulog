package edu.harvard.seas.pl.formulog.symbols.parameterized;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractParameterizedSymbol<B extends SymbolBase> implements ParameterizedSymbol {

	private final B base;
	private final List<ParamElt> args;

	public AbstractParameterizedSymbol(B base, List<ParamElt> args) {
		this.base = base;
		this.args = new ArrayList<>(args);
		checkArgs();
	}

	private void checkArgs() {
		if (args.size() != base.getNumParams()) {
			throw new IllegalArgumentException("Wrong number of parameters for symbol " + base);
		}
		int i = 0;
		Iterator<ParamKind> kinds = base.getParamKinds().iterator();
		for (ParamElt arg : args) {
			ParamKind paramType = kinds.next();
			if (!arg.matchesParamKind(paramType)) {
				throw new IllegalArgumentException("Parameter kind mismatch for symbol " + base + ": parameter "
						+ (i + 1) + " should be of kind " + paramType);
			}
			i++;
		}
	}

	@Override
	public int getArity() {
		return base.getArity();
	}

	@Override
	public String getName() {
		return base.getName();
	}

	@Override
	public B getBase() {
		return base;
	}

	@Override
	public List<ParamElt> getArgs() {
		return args;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((args == null) ? 0 : args.hashCode());
		result = prime * result + ((base == null) ? 0 : base.hashCode());
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
		@SuppressWarnings("rawtypes")
		AbstractParameterizedSymbol other = (AbstractParameterizedSymbol) obj;
		if (args == null) {
			if (other.args != null)
				return false;
		} else if (!args.equals(other.args))
			return false;
		if (base == null) {
			if (other.base != null)
				return false;
		} else if (!base.equals(other.base))
			return false;
		return true;
	}

}
