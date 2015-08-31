
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
 * <p>Emulates the global contexts of JavaScript.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public abstract class Global<W extends Core> extends Script<W>
{
	/**
	 * <p>Constructs a global script context of this type.</p>
	 * @param with The containing {@link Core} context.
	 * @since Descripter 1.0
	 */
	protected Global(W with) {
		super(with);
		if (with != null) {
			put(with._this, this);
		}
	}

	/**
	 * <p>Returns a string representation of the current object.</p>
	 * @return The string representation of the current object
	 * @since Descripter 1.0
	 */
	@Override
	public String toString() {
		return "[Global object]";
	}

	/**
	 * <p>Gets the engine core of this global script context.</p>
	 * @return The engine core of this global script context
	 * @since Descripter 1.0
	 */
	@Override
	public W core() {
		return with;
	}

	/**
	 * <p>Creates a global service with the specified {@link java.lang.reflect.Method} of this global script context.</p>
	 * @param method The name of a {@link java.lang.reflect.Method} of this global script context
	 * @since Descripter 1.0
	 */
	protected final Key globalize(String method) {
		try {
			Key k = key(method);
			put(k, getClass().getMethod(
					method,
					new Class<?>[]{Script.class, Object[].class}
			));
			return k;
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
}
