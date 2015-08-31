
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
import org.descripter.js.api.core.CObject;
import org.descripter.js.api.core.CRegExp;
import org.descripter.js.api.core.CString;

/**
 * <p>Emulates the global String function in JavaScript.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class String_ extends Function<Core>
{
	/**
	 * <p>Creates the singleton of the global {@link Function} object of this type.</p>
	 * @param core The {@link Core} global script context
	 * @since Descripter 1.0
	 */
	public static final void create(Core core) {
		if (core.get(core._String) == null) {
			core.put(core._String, new String_(core));
		}
	}

	private String_(Core core) {
		super(core);
		CObject prototype = prototype();
		prototype.put(core._charAt, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).charAt(arguments().get(0));
					}
				};
			}
		});
		prototype.put(core._charCodeAt, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).charCodeAt(arguments().get(0));
					}
				};
			}
		});
		prototype.put(core._concat, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).concat(arguments());
					}
				};
			}
		});
		prototype.put(core._indexOf, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).indexOf(arguments());
					}
				};
			}
		});
		prototype.put(core._lastIndexOf, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).lastIndexOf(arguments());
					}
				};
			}
		});
		prototype.put(core._localeCompare, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).localeCompare(arguments().get(0));
					}
				};
			}
		});
		prototype.put(core._match, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).match((CRegExp)arguments().get(0));
					}
				};
			}
		});
		prototype.put(core._replace, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).replace(arguments());
					}
				};
			}
		});
		prototype.put(core._search, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).search((CRegExp)arguments().get(0));
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
						return ((CString)this_()).slice(arguments());
					}
				};
			}
		});
		prototype.put(core._split, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).split(arguments());
					}
				};
			}
		});
		prototype.put(core._substr, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).substr(arguments());
					}
				};
			}
		});
		prototype.put(core._substring, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).substring(arguments());
					}
				};
			}
		});
		prototype.put(core._toLocaleLowerCase, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).toLocaleLowerCase();
					}
				};
			}
		});
		prototype.put(core._toLocaleUpperCase, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).toLocaleUpperCase();
					}
				};
			}
		});
		prototype.put(core._toLowerCase, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).toLowerCase();
					}
				};
			}
		});
		prototype.put(core._toUpperCase, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CString)this_()).toUpperCase();
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
	public <S extends Script<?>> CString alloc(S script, Object ...args) {
		return new CString(this, args != null && args.length > 0 ? args[0] : null);
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
				return new CString(String_.this, arguments().get(0));
			}
		};
	}
}
