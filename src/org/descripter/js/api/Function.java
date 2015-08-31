
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

import org.descripter.js.api.core.CArray;
import org.descripter.js.api.core.CObject;

/**
 * <p>An abstract class of {@link Script} {@link Context}s to emulate function declaration in JavaScript.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public abstract class Function<W extends Script<?>> extends CObject
{
	/**
	 * <p>The parent {@link Script} {@link Context}.</p>
	 * @since Descripter 1.0
	 */
	public final W with;
	/**
	 * <p>The {@link Global} {@link Script} {@link Context}.</p>
	 * @since Descripter 1.0
	 */
	public final Global<? extends Core> in;

	/**
	 * <p>Constructs a script context of this type.</p>
	 * @param with The containing script context.
	 * @param in The {@link Global} {@link Script} {@link Context}
	 * @since Descripter 1.0
	 */
	protected Function(W with, Global<? extends Core> in) {
		super(in.core()._Function());
		this.with = with;
		this.in   = in;
		put(core()._constructor, constructor);
		put(core()._prototype, core().object());
	}

	/**
	 * <p>Constructs a script context of this type.</p>
	 * @param with The containing script context.
	 * @since Descripter 1.0
	 */
	protected Function(W with) {
		this(with, with.function.in);
		put(core()._apply, new Function<W>(with, in) {
			@Override
			public Functor<W> functor() {
				return new Functor<W>(this) {
					@Override
					public Object function() {
						CArray arguments = arguments();
						return Function.this.call(this, arguments.get(0), (CArray)arguments.get(1));
					}
				};
			}
		});
		put(core()._call, new Function<W>(with, in) {
			@Override
			public Functor<W> functor() {
				return new Functor<W>(this) {
					@Override
					public Object function() {
						CArray arguments = arguments();
						return Function.this.call(this, arguments.shift(), arguments);
					}
				};
			}
		});
	}

	/**
	 * <p>Gets the engine core of this global script context.</p>
	 * @return The engine core of this global script context
	 * @since Descripter 1.0
	 */
	@Override
	public final Core core() {
		return in.core();
	}

	/**
	 * <p>Gets the <tt>apply</tt> member of the {@link Function} object.</p>
	 * @return The <tt>apply</tt> member of the {@link Function} object
	 * @since Descripter 1.0
	 */
	public final Function<?> apply() {
		return (Function<?>)get(core()._apply);
	}

	/**
	 * <p>Gets the <tt>call</tt> member of the {@link Function} object.</p>
	 * @return The <tt>call</tt> member of the {@link Function} object
	 * @since Descripter 1.0
	 */
	public final Function<?> call() {
		return (Function<?>)get(core()._call);
	}

	/**
	 * <p>Gets the <tt>prototype</tt> member of the {@link Function} object.</p>
	 * @return The <tt>prototype</tt> member of the {@link Function} object
	 * @since Descripter 1.0
	 */
	public final CObject prototype() {
		return (CObject)get(core()._prototype);
	}

	/**
	 * <p>Gets the {@link Functor} of the {@link Function} object.</p>
	 * <p>Subclasses need to concretely implement this method.</p>
	 * @return The {@link Functor} of the {@link Function} object
	 * @since Descripter 1.0
	 */
	protected abstract Functor<W> functor();

	/**
	 * <p>Allocates a new object with this {@link Function}.</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @return The newly allocated object
	 * @since Descripter 1.0
	 */
	public <S extends Script<?>> CObject alloc(S script, Object ...args) {
		CObject o = new CObject(this);
		call(script, o, script.array(args));
		return o;
	}

	/**
	 * <p>Calls this {@link Function}.</p>
	 * @param script The script context that invoked this service
	 * @param base The base object used to invoke this {@link Function}
	 * @param args An array of the arguments passed by the invocation
	 * @return The return result of the invocation
	 * @since Descripter 1.0
	 */
	public final <S extends Script<?>> Object call(S script, Object base, CArray args) {
		Functor<?> f = functor();
		f.put(core()._this, base);
		f.put(core()._caller, script.function);
		f.put(core()._arguments, args.set(core()._callee, this));
		return f.function();
	}

	/**
	 * <p>Returns a string representation of the current object.</p>
	 * @return The string representation of the current object
	 * @since Descripter 1.0
	 */
	@Override
	public final String toString() {
		return "[Function object]";
	}
}
