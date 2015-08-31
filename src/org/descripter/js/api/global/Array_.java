
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
import org.descripter.js.api.core.CArray;
import org.descripter.js.api.core.CObject;

/**
 * <p>Emulates the global Array function in JavaScript.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public final class Array_ extends Function<Core>
{
	/**
	 * <p>Creates the singleton of the global {@link Function} object of this type.</p>
	 * @param core The {@link Core} global script context
	 * @since Descripter 1.0
	 */
	public static final void create(Core core) {
		if (core.get(core._Array) == null) {
			core.put(core._Array, new Array_(core));
		}
	}

	private Array_(Core core) {
		super(core);
		CObject prototype = prototype();
		prototype.put(core._concat, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CArray)this_()).concat(arguments());
					}
				};
			}
		});
		prototype.put(core._join, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CArray)this_()).join(toString(arguments().get(0)));
					}
				};
			}
		});
		prototype.put(core._pop, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CArray)this_()).pop();
					}
				};
			}
		});
		prototype.put(core._push, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CArray)this_()).push(arguments());
					}
				};
			}
		});
		prototype.put(core._reverse, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CArray)this_()).reverse();
					}
				};
			}
		});
		prototype.put(core._shift, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CArray)this_()).shift();
					}
				};
			}
		});
		prototype.put(core._slice, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CArray)this_()).slice(arguments());
					}
				};
			}
		});
		prototype.put(core._sort, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CArray)this_()).sort((Function<?>)arguments().get(0));
					}
				};
			}
		});
		prototype.put(core._splice, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CArray)this_()).splice(arguments());
					}
				};
			}
		});
		prototype.put(core._unshift, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CArray)this_()).unshift(arguments());
					}
				};
			}
		});
	}

	/**
	 * <p>Allocates a new object with this {@link Function}.</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @return The newly allocated object
	 * @since Descripter 1.0
	 */
	@Override
	public <S extends Script<?>> CArray alloc(S script, Object ...args) {
		CArray o = new CArray(this);
//		Function<?> f = function();
//		f.evaluate();
		return o;
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
				return new CArray(Array_.this);
			}
		};
	}
}
