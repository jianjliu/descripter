
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

package org.descripter.js.api.core;

import org.descripter.js.api.Core;
import org.descripter.js.api.Function;
import org.descripter.js.api.Functor;

/**
 * <p>Emulates the JavaScript Math object.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class CMath extends CObject
{
	/**
	 * The constant e, the base of the natural logarithm.
	 * @since Descripter 1.0
	 */
	public final static double E       = Math.E;
	/**
	 * The natural logarithm of 2.
	 * @since Descripter 1.0
	 */
	public final static double LN2     = Math.log(2);
	/**
	 * The natural logarithm of 10.
	 * @since Descripter 1.0
	 */
	public final static double LN10    = Math.log(10);
	/**
	 * The base-2 logarithm of e.
	 * @since Descripter 1.0
	 */
	public final static double LOG2E   = 1 / LN2;
	/**
	 * The base-10 logarithm of e.
	 * @since Descripter 1.0
	 */
	public final static double LOG10E  = 1 / LN10;
	/**
	 * The constant pi, the ratio of the circumference of a circle to its diameter. 
	 * It has a value of approximately 3.14159265358979.
	 * @since Descripter 1.0
	 */
	public final static double PI      = Math.PI;
	/**
	 * The square root of 2.
	 * @since Descripter 1.0
	 */
	public final static double SQRT2   = Math.sqrt(2);
	/**
	 * The reciprocal of the square root of 2, that is, 
	 * the number 1 divided by the square root of 2.
	 * @since Descripter 1.0
	 */
	public final static double SQRT1_2 = 1 / SQRT2;

	/**
	 * <p>Creates the singleton of the global object of this type.</p>
	 * @param core The {@link Core} global script context
	 * @since Descripter 1.0
	 */
	public static final void create(Core core) {
		if (core.get(core._Math) == null) {
			core.put(core._Math, new CMath(core));
		}
	}

	/**
	 * <p>Returns the largest of the arguments.</p>
	 * @param args An array of arguments.
	 * @return The largest of the arguments
	 * @since Descripter 1.0
	 */
	public final Number max(CArray args) {
		double m = doubleValue(args.get(0));
		for (int i = 1, len = args.length(); i < len; i++) {
			m = Math.max(m, doubleValue(args.get(i)));
		}
		return m;
	}

	/**
	 * <p>Returns the smallest of the arguments.</p>
	 * @param args An array of arguments.
	 * @return The smallest of the arguments
	 * @since Descripter 1.0
	 */
	public final Number min(CArray args) {
		double m = doubleValue(args.get(0));
		for (int i = 1, len = args.length(); i < len; i++) {
			m = Math.min(m, doubleValue(args.get(i)));
		}
		return m;
	}

	private CMath(Core core) {
		super(core._Object());
		put(core._E      , E);
		put(core._LN10   , LN10);
		put(core._LN2    , LN2);
		put(core._LOG10E , LOG10E);
		put(core._LOG2E  , LOG2E);
		put(core._PI     , PI);
		put(core._SQRT1_2, SQRT1_2);
		put(core._SQRT2  , SQRT2);
		put(core._abs, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.abs(doubleValue(arguments().get(0)));
					}
				};
			}
		});
		put(core._acos, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.acos(doubleValue(arguments().get(0)));
					}
				};
			}
		});
		put(core._asin, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.asin(doubleValue(arguments().get(0)));
					}
				};
			}
		});
		put(core._atan, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.atan(doubleValue(arguments().get(0)));
					}
				};
			}
		});
		put(core._atan2, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.atan2(doubleValue(arguments().get(0)), doubleValue(arguments().get(1)));
					}
				};
			}
		});
		put(core._ceil, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.ceil(doubleValue(arguments().get(0)));
					}
				};
			}
		});
		put(core._cos, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.cos(doubleValue(arguments().get(0)));
					}
				};
			}
		});
		put(core._exp, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.exp(doubleValue(arguments().get(0)));
					}
				};
			}
		});
		put(core._floor, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.floor(doubleValue(arguments().get(0)));
					}
				};
			}
		});
		put(core._max, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return CMath.this.max(arguments());
					}
				};
			}
		});
		put(core._min, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return CMath.this.min(arguments());
					}
				};
			}
		});
		put(core._log, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.log(doubleValue(arguments().get(0)));
					}
				};
			}
		});
		put(core._pow, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.pow(doubleValue(arguments().get(0)), doubleValue(arguments().get(1)));
					}
				};
			}
		});
		put(core._random, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.random();
					}
				};
			}
		});
		put(core._round, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.round(doubleValue(arguments().get(0)));
					}
				};
			}
		});
		put(core._sin, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.sin(doubleValue(arguments().get(0)));
					}
				};
			}
		});
		put(core._sinh, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.sinh(doubleValue(arguments().get(0)));
					}
				};
			}
		});
		put(core._sqrt, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.sqrt(doubleValue(arguments().get(0)));
					}
				};
			}
		});
		put(core._tan, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.tan(doubleValue(arguments().get(0)));
					}
				};
			}
		});
		put(core._tanh, new Function<Core>(core) {
			@Override
			public Functor<Core> functor() {
				return new Functor<Core>(this) {
					@Override
					public Object function() {
						return Math.tanh(doubleValue(arguments().get(0)));
					}
				};
			}
		});
	}
}
