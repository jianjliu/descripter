
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
 * <p>An exception class used to interpret JavaScript continue statements.</p>
 *
 * @see Interpreter
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Continue extends Abrupt
{
	private static final long serialVersionUID = -4472272990543761448L;

	/**
	 * <p>Constructs a runtime exception of this type.</p>
	 * @since Descripter 1.0
	 */
	public Continue() {
	}

	/**
	 * <p>Constructs a runtime exception of this type.</p>
	 * @param label A label value to store.
	 * @since Descripter 1.0
	 */
	public Continue(Object label) {
		super(label);
	}
}
