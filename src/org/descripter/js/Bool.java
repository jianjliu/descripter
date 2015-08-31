
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
 * <p>A wrapper class for booleans.</p>
 * <p>This class is useful for descripters.</p>
 * 
 * @see Descripter
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Bool
{
	private final Object o;

	/**
	 * <p>Constructs a wrapper of this type.</p>
	 * @param o A value to wrap
	 * @since Descripter 1.0
	 */
	public Bool(Object o) {
		this.o = o;
	}

	/**
	 * <p>Returns the string representation of the wrapped value.</p>
	 * @return The string representation of the wrapped value.
	 * @since Descripter 1.0
	 */
	@Override
	public String toString() {
		return o.toString();
	}
}
