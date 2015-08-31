
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
 * <p>Emulates JavaScript with statements.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public abstract class With<W extends Script<?>> extends Script<W>
{
	private final Objective<?> base;

	/**
	 * <p>Constructs a script context of this type.</p>
	 * @param with The containing script context.
	 * @param o The base object
	 * @since Descripter 1.0
	 */
	public With(W with, Object o) {
		super(with);
		base = with.object(o);
	}

	/**
	 * <p>Returns the value associated with the specified index.</p>
	 * @param i An index to lookup
	 * @return The value associated with the specified index or <tt>null</tt> for none.
	 * @since Descripter 1.0
	 */
	@Override
	public final Object get(Integer i) {
		Object o = base.get(i);
		return o != null ? o : super.get(i);
	}

	/**
	 * <p>Sets the value associated with the specified index.</p>
	 * @param i An index to set the value
	 * @param v The value to set
	 * @throws RuntimeException if the base context is read-only.
	 * @since Descripter 1.0
	 */
	@Override
	public final void put(Integer i, Object v) {
		base.set(i, evaluate(v));
	}
}
