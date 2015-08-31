
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

import java.util.HashMap;

/**
 * <p>Represents a cache of cases emulating the <tt>case</tt> clauses of JavaScript <tt>switch</tt> statements.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public final class Cases extends Script<Script<?>>
{
	private final HashMap<Object, Integer> map = new HashMap<Object, Integer>();
	private int next = 0;

	/**
	 * <p>Constructs a script context of this type.</p>
	 * @param with The containing script context.
	 * @since Descripter 1.0
	 */
	public Cases(Script<?> with) {
		super(with);
	}

	/**
	 * <p>Adds a key to the current <tt>Cases</tt> cache.</p>
	 * @return The current <tt>Cases</tt> cache itself.
	 * @since Descripter 1.0
	 */
	public final Cases add(Object o) {
		o = evaluate(o);
		if (!map.containsKey(o)) {
			map.put(o, next++);
		}
		return this;
	}

	/**
	 * <p>Returns the index of a key by searching its reference in the current <tt>Cases</tt> 
	 * cache.</p>
	 * @return The index of a key in the current <tt>Cases</tt> cache.
	 * @since Descripter 1.0
	 */
	public final int indexOf(Object o) {
		o = evaluate(o);
		return map.containsKey(o) ? map.get(o) : -1;
	}

	/**
	 * <p>Executes the current script context.</p>
	 * <p>This method simply throws an {@link UnsupportedOperationException}.</p>
	 * @since Descripter 1.0
	 */
	@Override
	public final void run() {
		throw new UnsupportedOperationException();
	}
}
