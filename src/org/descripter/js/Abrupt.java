
/*
 *  Descripter 1.0 - Java Script Engines
 *  Copyright (C) 2010-2015  Jianjun Liu (J.J.Liu)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.descripter.js;

/**
 * <p>An abstract base class for interpreting exceptions.</p>
 *
 * @see Interpreter
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public abstract class Abrupt extends RuntimeException
{
	private static final long serialVersionUID = -1952897482867528618L;

	/**
	 * <p>A stored value.</p>
	 * @since Descripter 1.0
	 */
	public final Object value;

	/**
	 * <p>Constructs a runtime exception of this type.</p>
	 * @param value A value to store.
	 * @since Descripter 1.0
	 */
	protected Abrupt(Object value) {
		this.value = value;
	}

	/**
	 * <p>Constructs a runtime exception of this type.</p>
	 * @since Descripter 1.0
	 */
	protected Abrupt() {
		this(null);
	}

	/**
	 * <p>Tries to throw the runtime exception.</p>
	 * @param label A label value.
	 * @since Descripter 1.0
	 */
	public final void tryThrow(Object label) {
		if (value != null && !value.equals(label)) {
			throw this;
		}
	}
}
