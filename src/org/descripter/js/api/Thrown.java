
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

package org.descripter.js.api;

/**
 * <p>Wraps values to emulate JavaScript throwables.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public final class Thrown extends RuntimeException implements Value
{
	private static final long serialVersionUID = 1762152044381702373L;

	private final Object o;

	/**
	 * <p>Constructs a throwable.</p>
	 * @param o The object value to wrap
	 * @since Descripter 1.0
	 */
	public Thrown(Object o) {
		this.o = o;
	}

	/**
	 * <p>Evaluates the {@link Thrown} {@link Value}.</p>
	 * @return The evaluation of the wrapped object value
	 * @since Descripter 1.0
	 */
	@Override
	public Object evaluate() {
		return Objective.evaluate(o);
	}
}
