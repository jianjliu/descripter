
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
import org.descripter.js.api.core.CDate;
import org.descripter.js.api.core.CObject;

/**
 * <p>Emulates the global Date function in JavaScript.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Date_ extends Function<Core>
{
	/**
	 * <p>Creates the singleton of the global {@link Function} object of this type.</p>
	 * @param core The {@link Core} global script context
	 * @since Descripter 1.0
	 */
	public static final void create(Core core) {
		if (core.get(core._Date) == null) {
			core.put(core._Date, new Date_(core));
		}
	}

	private Date_(Core core) {
		super(core);
		CObject prototype = prototype();
		prototype.put(core._getDate, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getDate();
					}
				};
			}
		});
		prototype.put(core._getDay, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getDay();
					}
				};
			}
		});
		prototype.put(core._getFullYear, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getFullYear();
					}
				};
			}
		});
		prototype.put(core._getHours, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getHours();
					}
				};
			}
		});
		prototype.put(core._getMilliseconds, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getMilliseconds();
					}
				};
			}
		});
		prototype.put(core._getMinutes, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getMinutes();
					}
				};
			}
		});
		prototype.put(core._getMonth, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getMonth();
					}
				};
			}
		});
		prototype.put(core._getSeconds, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getSeconds();
					}
				};
			}
		});
		prototype.put(core._getTime, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getTime();
					}
				};
			}
		});
		prototype.put(core._getTimezoneOffset, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getTimezoneOffset();
					}
				};
			}
		});
		prototype.put(core._getUTCDate, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getUTCDate();
					}
				};
			}
		});
		prototype.put(core._getUTCDay, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getUTCDay();
					}
				};
			}
		});
		prototype.put(core._getUTCFullYear, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getUTCFullYear();
					}
				};
			}
		});
		prototype.put(core._getUTCHours, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getUTCHours();
					}
				};
			}
		});
		prototype.put(core._getUTCMilliseconds, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getUTCMilliseconds();
					}
				};
			}
		});
		prototype.put(core._getUTCMinutes, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getUTCMinutes();
					}
				};
			}
		});
		prototype.put(core._getUTCMonth, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getUTCMonth();
					}
				};
			}
		});
		prototype.put(core._getUTCSeconds, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).getUTCSeconds();
					}
				};
			}
		});
		prototype.put(core._setDate, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setDate(toNumber(arguments().get(0)));
					}
				};
			}
		});
		prototype.put(core._setFullYear, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setFullYear(arguments());
					}
				};
			}
		});
		prototype.put(core._setHours, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setHours(arguments());
					}
				};
			}
		});
		prototype.put(core._setMilliseconds, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setMilliseconds(toNumber(arguments().get(0)));
					}
				};
			}
		});
		prototype.put(core._setMinutes, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setMinutes(arguments());
					}
				};
			}
		});
		prototype.put(core._setMonth, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setMonth(arguments());
					}
				};
			}
		});
		prototype.put(core._setSeconds, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setSeconds(arguments());
					}
				};
			}
		});
		prototype.put(core._setTime, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setTime(toNumber(arguments().get(0)));
					}
				};
			}
		});
		prototype.put(core._setUTCDate, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setUTCDate(toNumber(arguments().get(0)));
					}
				};
			}
		});
		prototype.put(core._setUTCFullYear, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setUTCFullYear(arguments());
					}
				};
			}
		});
		prototype.put(core._setUTCHours, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setUTCHours(arguments());
					}
				};
			}
		});
		prototype.put(core._setUTCMilliseconds, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setUTCMilliseconds(toNumber(arguments().get(0)));
					}
				};
			}
		});
		prototype.put(core._setUTCMinutes, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setUTCMinutes(arguments());
					}
				};
			}
		});
		prototype.put(core._setUTCMonth, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setUTCMonth(arguments());
					}
				};
			}
		});
		prototype.put(core._setUTCSeconds, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).setUTCSeconds(arguments());
					}
				};
			}
		});
		prototype.put(core._toDateString, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).toDateString();
					}
				};
			}
		});
		prototype.put(core._toLocaleDateString, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).toLocaleDateString();
					}
				};
			}
		});
		prototype.put(core._toLocaleTimeString, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).toLocaleTimeString();
					}
				};
			}
		});
		prototype.put(core._toTimeString, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).toTimeString();
					}
				};
			}
		});
		prototype.put(core._toUTCString, new Function<Core>(core) {
			@Override
			protected Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return ((CDate)this_()).toUTCString();
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
	public <S extends Script<?>> CDate alloc(S script, Object ...args) {
		if (args != null && args.length > 0 && args[0] != null) {
			Object arg = valueOf(args[0]);
			if (arg instanceof Number) {
				return new CDate(this, (Number)arg);
			} else if (arg instanceof String) {
				return new CDate(this, (String)arg);
			}
		}
		return new CDate(this);
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
				return Date_.this.alloc(this, arguments().get(0));
			}
		};
	}
}
