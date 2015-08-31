
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

package org.descripter.js.api.global;

import org.descripter.js.api.Core;
import org.descripter.js.api.Function;
import org.descripter.js.api.Functor;
import org.descripter.js.api.Script;
import org.descripter.js.api.core.CBoolean;

/**
 * <p>Emulates the global Boolean function in JavaScript.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Boolean_ extends Function<Core>
{
	/**
	 * <p>Creates the singleton of the global {@link Function} object of this type.</p>
	 * @param core The {@link Core} global script context
	 * @since Descripter 1.0
	 */
	public static final void create(Core core) {
		if (core.get(core._Boolean) == null) {
			core.put(core._Boolean, new Boolean_(core));
		}
	}

	private Boolean_(Core core) {
		super(core);
	}

	/**
	 * <p>Allocates a new object with this {@link Function}.</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @return The newly allocated object
	 * @since Descripter 1.0
	 */
	@Override
	public <S extends Script<?>> CBoolean alloc(S script, Object ...args) {
		return new CBoolean(this, args != null & args.length > 0 && bool(args[0]));
	}

	/**
	 * <p>Gets the {@link Functor} of the {@link Function} object.</p>
	 * <p>This is a concrete implementation of the super abstract method.</p>
	 * @return The {@link Functor} of the {@link Function} object
	 * @since Descripter 1.0
	 */
	@Override
	protected Functor<Core> functor() {
		return new Functor<Core>(this) {
			@Override
			public Object function() {
				return new CBoolean(Boolean_.this, bool(arguments().get(0)));
			}
		};
	}
}
