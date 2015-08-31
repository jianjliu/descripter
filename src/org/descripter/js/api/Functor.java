
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

import org.descripter.js.api.core.CArray;

/**
 * <p>Emulates JavaScript function definitions.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public abstract class Functor<W extends Script<?>> extends Script<W>
{
	/**
	 * <p>Constructs a script context of this type.</p>
	 * @param function The containing {@link Function} object.
	 * @since Descripter 1.0
	 */
	protected Functor(Function<W> function) {
		super(function);
	}

	/**
	 * <p>Gets the <tt>arguments</tt> member of the {@link Functor} context.</p>
	 * @return The <tt>arguments</tt> member of the {@link Functor} context
	 * @since Descripter 1.0
	 */
	public final CArray arguments() {
		return (CArray)get(core()._arguments);
	}

	/**
	 * <p>Gets the <tt>caller</tt> member of the {@link Functor} context.</p>
	 * @return The <tt>caller</tt> member of the {@link Functor} context
	 * @since Descripter 1.0
	 */
	public final Function<?> caller() {
		return (Function<?>)get(core()._caller);
	}

	/**
	 * <p>Gets the <tt>this</tt> member of the {@link Functor} context.</p>
	 * @return The <tt>this</tt> member of the {@link Functor} context
	 * @since Descripter 1.0
	 */
	public final Objective<?> this_() {
		return object(get(core()._this));
	}

	/**
	 * <p>Executes the script context of this type.</p>
	 * <p>This method executes this {@link Functor}</p>
	 * @since Descripter 1.0
	 */
	@Override
	public final void run() {
		function();
	}

	/**
	 * <p>An abstract method emulating a function definition.</p>
	 * <p>Subclasses need to concretely implement this method.</p>
	 * @return The return result of the invocation of this {@link Functor}
	 * @since Descripter 1.0
	 */
	public abstract Object function();
}
