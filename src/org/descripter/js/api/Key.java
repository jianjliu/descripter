
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

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * <p>Emulates the object properties of JavaScript.</p>
 * <p>This class is only used in the emulation of for-in statement.</p>
 * 
 * @see For
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public final class Key implements Value
{
	private static int next = -1;
	private final static Map<String, Key> map = new WeakHashMap<String, Key>();
	private final static Map<Key, Key> set = new WeakHashMap<Key, Key>();

	/**
	 * <p>Finds or creates a {@link Key} associated with the specified string name.</p>
	 * <p>Note that a {@link Key} does not strongly reference any objects except that it has a unique hash code.</p>
	 * @param cntx The context invoking this method.
	 * @param name A string name
	 * @return The found or created {@link Key}
	 * @since Descripter 1.0
	 */
	public static synchronized final Key get(Context<?, ?, ?> cntx, String name) {
		Key k = map.get(name);
		if (k == null) {
			try {
				int i = Integer.decode(name);
				if (i >= 0) {
					return new Key(null, name, i);
				}
			} catch (NumberFormatException nfe) {
			}
			k = new Key(cntx, name, next--);
			map.put(name, k);
			set.put(k, k);
		}
		return k;
	}

	/**
	 * <p>Checks if there is already a {@link Key} associated with the specified string name.</p>
	 * <p>Note that a {@link Key} does not strongly reference any objects except that it has a unique hash code.</p>
	 * @param name A string name
	 * @return <tt>true</tt> if there is already a {@link Key} associated with the specified string name; 
	 * <tt>false</tt>, otherwise.
	 * @since Descripter 1.0
	 */
	public static synchronized final boolean has(String name) {
		return map.containsKey(name);
	}

	/**
	 * <p>Finds a {@link Key} that has the specified hash code.</p>
	 * <p>Note that a {@link Key} does not strongly reference any objects except that it has a unique hash code.</p>
	 * @param hash The integer hash code
	 * @return The found {@link Key} or <tt>null</tt> for none.
	 * @since Descripter 1.0
	 */
	public static synchronized final Key get(int hash) {
		return set.get(new Key(hash));
	}

	private final Reference<Context<?, ?, ?>> cntx;
	private final Reference<String> name;
	private final int hash;

	private Key(int hash) {
		this(null, null, hash);
	}

	private Key(Context<?, ?, ?> cntx, String name, int hash) {
		this.cntx = new WeakReference<Context<?, ?, ?>>(cntx);
		this.name = new WeakReference<String>(name);
		this.hash = hash;
	}

	/**
	 * <p>Returns the hash code of this {@link Key}.</p>
	 * <p>Note that a {@link Key} does not strongly reference any objects except that it has a unique hash code.</p>
	 * @return The hash code of this {@link Key}.
	 * @since Descripter 1.0
	 */
	@Override
	public int hashCode() {
		return hash;
	}

	/**
	 * <p>Checks if this {@link Key} is equal to another object.</p>
	 * <p>Two {@link Key}s equal each other if and only if their hash codes are equal.</p>
	 * @param o Another object
	 * @return <tt>true</tt> if this {@link Key} is equal to the specified object.
	 * @since Descripter 1.0
	 */
	@Override
	public boolean equals(Object o) {
		return o instanceof Key && ((Key)o).hash == hash;
	}

	/**
	 * <p>Returns the context associated with this {@link Key}.</p>
	 * <p>Note that a {@link Key} does not strongly reference any objects except that it has a unique hash code.</p>
	 * @return The context associated with this {@link Key} or <tt>null</tt> for none.
	 * @since Descripter 1.0
	 */
	public final Context<?, ?, ?> context() {
		return cntx.get();
	}

	/**
	 * <p>Returns the string name associated with this {@link Key}.</p>
	 * <p>Note that a {@link Key} does not strongly reference any objects except that it has a unique hash code.</p>
	 * @return The string name associated with this {@link Key} or <tt>null</tt> for none.
	 * @since Descripter 1.0
	 */
	@Override
	public String evaluate() {
		return name.get();
	}

	/**
	 * <p>Returns the string name associated with this {@link Key}.</p>
	 * <p>Note that a {@link Key} does not strongly reference any objects except that it has a unique hash code.</p>
	 * @return The string name associated with this {@link Key} or <tt>null</tt> for none.
	 * @since Descripter 1.0
	 */
	@Override
	public String toString() {
		return name.get();
	}
}
