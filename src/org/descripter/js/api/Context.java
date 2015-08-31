
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
import java.util.HashSet;
import java.util.Set;

/**
 * <p>A base class for representing various related contexts in JavaScript.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Context<S, T, W extends Context<S, T, ?>>
{
	private final HashMap<S, T> map = new HashMap<S, T>();
	private boolean readOnly = false;
	/**
	 * <p>The container of the current context.</p>
	 * @since Descripter 1.0
	 */
	public final W with;

	/**
	 * <p>Constructs a context of this type.</p>
	 * @param with The containing context.
	 * @since Descripter 1.0
	 */
	public Context(W with) {
		this.with = with;
	}

	/**
	 * <p>Tells if the current context is read-only.</p>
	 * @return <tt>true</tt> if the current context is read-only; <tt>false</tt>, otherwise.
	 * @since Descripter 1.0
	 */
	public boolean readOnly() {
		return readOnly;
	}

	/**
	 * <p>Sets the current context to <tt>readOnly</tt>.</p>
	 * @param readOnly <tt>true</tt> to set the current context read-only; <tt>false</tt>, otherwise.
	 * @since Descripter 1.0
	 */
	public void readOnly(boolean readOnly) {
		if (this.readOnly ^ readOnly) {
			this.readOnly = readOnly;
			for (T t : map.values()) {
				if (t instanceof Context) {
					((Context<?, ?, ?>)t).readOnly(readOnly);
				}
			}
		}
	}

	/**
	 * <p>Finds the most inner context that owns the specified key.</p>
	 * @param key A key to lookup
	 * @return The most inner context that owns the specified key or <tt>null</tt> if no such containers.
	 * @since Descripter 1.0
	 */
	public Context<S, T, ?> in(S key) {
		if (map.containsKey(key) || with == null) {
			return this;
		} else {
			return with.in(key);
		}
	}

	/**
	 * <p>Tells if a specified context is one of the containers of the current one.</p>
	 * @param ctx A context to test
	 * @return <tt>true</tt> if <tt>ctx</tt> contains the current context; <tt>false</tt>, otherwise.
	 * @since Descripter 1.0
	 */
	public boolean with(Context<S, T, ?> ctx) {
		return with != null && (with == ctx || with.with(ctx));
	}

	/**
	 * <p>Tells if the current context owns the specified key.</p>
	 * @param key A key to test
	 * @return <tt>true</tt> if the current context owns the <tt>key</tt>; <tt>false</tt>, otherwise.
	 * @since Descripter 1.0
	 */
	public boolean owns(S key) {
		return map.containsKey(key) && map.get(key) != null;
	}

	/**
	 * <p>Tells if the specified key is visible in the current context.</p>
	 * @param key A key to test
	 * @return <tt>true</tt> if the <tt>key</tt> is visible in the current context; <tt>false</tt>, otherwise.
	 * @since Descripter 1.0
	 */
	public boolean has(S key) {
		return map.containsKey(key) || with != null && with.has(key);
	}

	/**
	 * <p>Returns the {@link Set} of keys that are visible to the current context.</p>
	 * @return The {@link Set} of keys that are visible to the current context.
	 * @since Descripter 1.0
	 */
	public Set<S> keys() {
		Set<S> keys = new HashSet<S>(map.keySet());
		if (with != null) {
			keys.addAll(with.keys());
		}
		return keys;
	}

	/**
	 * <p>Returns the value associated with the specified key.</p>
	 * @param key A key to lookup
	 * @return The value associated with the specified key or <tt>null</tt> for none.
	 * @since Descripter 1.0
	 */
	public T get(S key) {
		if (!map.containsKey(key) && with != null) {
			return with.get(key);
		}
		return map.get(key);
	}

	/**
	 * <p>Updates the value associated with the specified key if it is visible.</p>
	 * <p>Note that this method does nothing if the key is not visible.</p>
	 * @param key A key to update
	 * @throws RuntimeException if the current context is read-only.
	 * @since Descripter 1.0
	 */
	public void update(S key, T val) {
		if (map.containsKey(key)) {
			put(key, val);
		} else if (with != null) {
			with.update(key, val);
		}
	}

	/**
	 * <p>Hides the specified key from the current context if it is visible.</p>
	 * <p>Note that this method does nothing if the key is not visible.</p>
	 * @param key A key to remove
	 * @throws RuntimeException if the current context is read-only.
	 * @since Descripter 1.0
	 */
	public void hide(S key) {
		if (has(key)) {
			put(key, null);
		}
	}

	/**
	 * <p>Sets the value associated with the specified key.</p>
	 * @param key A key to set
	 * @param val The value to set the value
	 * @throws RuntimeException if the current context is read-only.
	 * @since Descripter 1.0
	 */
	public void put(S key, T val) {
		if (readOnly) {
			throw new RuntimeException();
		}
		map.put(key, val);
	}
}
