
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

package org.descripter.js.api.core;

import org.descripter.js.api.Core;
import org.descripter.js.api.Function;
import org.descripter.js.api.Objective;

/**
 * <p>Emulates JavaScript Object objects.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class CObject extends Objective<CObject>
{
	/**
	 * <p>Emulates the <tt>null</tt> object in JavaScript.</p>
	 * @since Descripter 1.0
	 */
	public final static CObject _null = new CObject(null) {
		@Override
		public String toString() {
			return "null";
		}
	};

	/**
	 * <p>The <tt>constructor</tt> function of the current object.</p>
	 * @since Descripter 1.0
	 */
	public final Function<?> constructor;

	/**
	 * <p>Constructs an {@link Objective} context of this type.</p>
	 * @param constructor The constructor {@link Function} object.
	 * @since Descripter 1.0
	 */
	public CObject(Function<?> constructor) {
		super(constructor != null ? constructor.prototype() : null);
		this.constructor = constructor;
		if (constructor != null) {
			put(constructor.core()._constructor, constructor);
		}
	}

	/**
	 * <p>Gets the engine {@link Core} of this {@link Objective} context.</p>
	 * @return The engine {@link Core} of this {@link Objective} context
	 * @since Descripter 1.0
	 */
	public Core core() {
		return constructor.core();
	}

	/**
	 * <p>Returns a string representation of the current object.</p>
	 * @return The string representation of the current object
	 * @since Descripter 1.0
	 */
	@Override
	public String toString() {
		return "[Object object]";
	}

	/**
	 * <p>Converts the current object instance to a string, localized as appropriate 
	 * for the current locale.</p>
	 * @return The string representation of the current object, localized as 
	 * appropriate for the current locale
	 * @since Descripter 1.0
	 */
	public String toLocaleString() {
		return toString();
	}

	/**
	 * <p>Returns the primitive value associated with this object, if there is one. </p>
	 * @return The primitive value associated with this object, if there is one, or this object itself.
	 * @since Descripter 1.0
	 */
	public Object valueOf() {
		return this;
	}
}
