
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

import java.util.Set;

/**
 * <p>An abstract class of {@link Script} {@link Context}s to emulate the for-in statements in JavaScript.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public abstract class For<W extends Script<?>> extends Script<W>
{
	private final Objective<?> base;
	private final Var var;

	/**
	 * <p>Constructs a script context of this type.</p>
	 * @param with The containing script context.
	 * @param base The base object to loop in
	 * @param key The {@link Key} of the looping variable
	 * @since Descripter 1.0
	 */
	protected For(W with, Object base, Key key) {
		super(with);
		this.base = object(base);
		var = my(key);
	}

	/**
	 * <p>Constructs a script context of this type.</p>
	 * @param with The containing script context.
	 * @param base The base object to loop in
	 * @param var A new looping {@link Var}
	 * @since Descripter 1.0
	 */
	protected For(W with, Object base, Var var) {
		super(with);
		this.base = object(base);
		this.var = var;
	}

	/**
	 * <p>An abstract method to be executed on each loop.</p>
	 * <p>Subclasses need to concretely implement this method.</p>
	 * @since Descripter 1.0
	 */
	protected abstract void each();

	/**
	 * <p>Executes the script context of this type.</p>
	 * @since Descripter 1.0
	 */
	@Override
	public final void run() {
		if (base != null) {
			Set<Key> keys = base.keys();
			for (Key k : keys) {
				var.assign(k);
				each();
			}
		}
	}
}
