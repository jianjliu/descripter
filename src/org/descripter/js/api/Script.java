
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.descripter.js.api.core.CArray;
import org.descripter.js.api.core.CObject;
import org.descripter.js.api.core.CRegExp;

/**
 * <p>Emulates script contexts of JavaScript codes.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public abstract class Script<W extends Script<?>> extends Objective<W> implements Runnable
{
	/**
	 * <p>The containing function context.</p>
	 * @since Descripter 1.0
	 */
	public final Function<?> function;

	/**
	 * <p>Constructs a script context of this type.</p>
	 * @param with The containing script context.
	 * @since Descripter 1.0
	 */
	protected Script(W with) {
		super(with);
		this.function = with != null ? with.function : new Function<Core>((Core)this, (Global<?>)this) {
			@Override
			protected Functor<Core> functor() {
				return null;
			}
		};
	}

	/**
	 * <p>Constructs a script context of this type.</p>
	 * @param function The containing function context.
	 * @since Descripter 1.0
	 */
	protected Script(Function<W> function) {
		super(function.with);
		this.function = function;
	}

	/**
	 * <p>Returns a string representation of the current object.</p>
	 * @return The string representation of the current object
	 * @since Descripter 1.0
	 */
	@Override
	public String toString() {
		return "[Script object]";
	}

	/**
	 * <p>Gets the engine {@link Core} of this {@link Script} context.</p>
	 * @return The engine {@link Core} of this {@link Script} context
	 * @since Descripter 1.0
	 */
	public Core core() {
		return function.core();
	}

	/**
	 * <p>Allocates an object with the specified function object and arguments.</p>
	 * @param o A function object
	 * @param args An array of arguments
	 * @return The newly allocated object
	 * @since Descripter 1.0
	 */
	public final CObject alloc(Object o, Object ...args) {
		o = evaluate(o);
		if (o instanceof Function<?>) {
			return ((Function<?>)o).alloc(this, args);
		}
		throw new UnsupportedOperationException();
	}

	/**
	 * <p>Calls the given function object with the specified arguments.</p>
	 * @param o A function object
	 * @param args An array of arguments
	 * @return The return result of the invocation
	 * @since Descripter 1.0
	 */
	public final Object call(Object o, Object ...args) {
		if (o instanceof Var) {
			return ((Var)o).call(this, args);
		}
		o = evaluate(o);
		if (o instanceof Function<?>) {
			return ((Function<?>)o).call(this, function.in, array(args));
		} else if (o instanceof Method &&
				Core.class.isAssignableFrom(((Method)o).getDeclaringClass())) {
			try {
				return ((Method)o).invoke(core(), new Object[]{this, args});
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		} else if (bnot(o)) {
			return null;
		}
		throw new UnsupportedOperationException();
	}

	/**
	 * <p>Checks if an object is instance of a function object.</p>
	 * @param o An object
	 * @param c A function object
	 * @return <tt>true</tt> if the object is instance of the function object; <tt>false</tt>, otherwise.
	 * @since Descripter 1.0
	 */
	public final boolean instanceOf(Object o, Object c) {
		o = evaluate(o);
		if (o instanceof CObject) {
			c = evaluate(c);
			if (c instanceof Function<?>) {
				return ((CObject)o).with(((Function<?>)c).prototype());
			}
		}
		return false;
	}

	/**
	 * <p>Initializes and returns a {@link Var} specified by the given {@link Key}.</p>
	 * @param k The {@link Key} of the {@link Var} to initialize and get
	 * @return The {@link Var} specified by the {@link Key}
	 * @since Descripter 1.0
	 */
	public final Var my(Key k) {
		put(k, null);
		return var(k);
	}

	/**
	 * <p>Initializes and returns a {@link Var} specified by the given string key.</p>
	 * @param k The string key of the {@link Var} to initialize and get
	 * @return The {@link Var} specified by the string key
	 * @since Descripter 1.0
	 */
	public final Var my(String k) {
		return my(key(k));
	}

	/**
	 * <p>Returns a {@link Var} specified by the given string key.</p>
	 * @param k The key of the {@link Var} to get
	 * @return The {@link Var} specified by the key
	 * @since Descripter 1.0
	 */
	public final Var var(String k) {
		return var(key(k));
	}

	/**
	 * <p>Returns a {@link Var} specified by the given key.</p>
	 * @param o The key of the {@link Var} to get
	 * @return The {@link Var} specified by the key
	 * @since Descripter 1.0
	 */
	public final Var var(Object o) {
		if (o instanceof Key) {
			return var((Key)o);
		}
		o = valueOf(o);
		return o instanceof Key ? var((Key)o) :
		       o instanceof Integer ? var((Integer)o) : var(toString(o));
	}

	/**
	 * <p>Returns a {@link Var} specified by the given key and based on an object.</p>
	 * @param o The base object of the {@link Var} to get
	 * @param k The key of the {@link Var} to get
	 * @return The {@link Var} specified by the key and based on the object
	 * @since Descripter 1.0
	 */
	public final Var var(Object o, Key k) {
		return object(o).var(k);
	}

	/**
	 * <p>Returns a {@link Var} specified by the given string key and based on an object.</p>
	 * @param o The base object of the {@link Var} to get
	 * @param k The string key of the {@link Var} to get
	 * @return The {@link Var} specified by the string key and based on the object
	 * @since Descripter 1.0
	 */
	public final Var var(Object o, String k) {
		return object(o).var(key(k));
	}

	/**
	 * <p>Returns a {@link Var} specified by the given key and based on an object.</p>
	 * @param o The base object of the {@link Var} to get
	 * @param p The key of the {@link Var} to get
	 * @return The {@link Var} specified by the key and based on the object
	 * @since Descripter 1.0
	 */
	public final Var var(Object o, Object p) {
		if (p instanceof Key) {
			return var(o, (Key)p);
		}
		p = valueOf(p);
		return p instanceof Key ? var(o, (Key)p) :
		       p instanceof Integer ? var(o, (Integer)p) : var(o, toString(p));
	}

	/**
	 * <p>Sets the value associated with the specified key and returns the current 
	 * {@link Script} context.</p>
	 * @param k A {@link Key} to set the value
	 * @param o The value to set
	 * @return The current {@link Script} context
	 * @throws RuntimeException if the current context is read-only.
	 * @since Descripter 1.0
	 */
	@Override
	public final Script<W> set(Key k, Object o) {
		in(k).put(k, o);
		return this;
	}

	/**
	 * <p>Sets the value associated with an {@link Objective} context and specified by a {@link Key}.</p>
	 * @param o An {@link Objective} context
	 * @param k A {@link Key} to set the value
	 * @param v The value to set
	 * @return The {@link Objective} context
	 * @throws RuntimeException if the {@link Objective} context is read-only.
	 * @since Descripter 1.0
	 */
	public final Objective<?> set(Object o, Key k, Object v) {
		return object(o).set(k, v);
	}

	/**
	 * <p>Sets the value associated with an {@link Objective} context and specified by a string key.</p>
	 * @param o An {@link Objective} context
	 * @param k A string key to set the value
	 * @param v The value to set
	 * @return The {@link Objective} context
	 * @throws RuntimeException if the {@link Objective} context is read-only.
	 * @since Descripter 1.0
	 */
	public final Objective<?> set(Object o, String k, Object v) {
		return object(o).set(key(k), v);
	}

	/**
	 * <p>Sets the value associated with an {@link Objective} context and specified by a key.</p>
	 * @param o An {@link Objective} context
	 * @param p A key to set the value
	 * @param v The value to set
	 * @return The {@link Objective} context
	 * @throws RuntimeException if the {@link Objective} context is read-only.
	 * @since Descripter 1.0
	 */
	public final Objective<?> set(Object o, Object p, Object v) {
		if (p instanceof Key) {
			return set(o, (Key)p, v);
		}
		p = valueOf(p);
		return p instanceof Key ? set(o, (Key)p, v) :
		       p instanceof Integer ? set(o, (Integer)p, v) : set(o, toString(p), v);
	}

	/**
	 * <p>Returns a new array.</p>
	 * @return The newly created {@link CArray}
	 * @since Descripter 1.0
	 */
	public final CArray array() {
		return new CArray(core()._Array());
	}

	/**
	 * <p>Returns a new array from a Java array.</p>
	 * @param array A Java array
	 * @return The newly created {@link CArray}
	 * @since Descripter 1.0
	 */
	public final CArray array(Object[] array) {
		return new CArray(core()._Array(), array);
	}

	/**
	 * <p>Returns a new object.</p>
	 * @return The newly created {@link CObject}
	 * @since Descripter 1.0
	 */
	public final CObject object() {
		return new CObject(core()._Object());
	}

	/**
	 * <p>Returns a new object from a specified object.</p>
	 * @param o An existing object
	 * @return The existing or newly created {@link Objective}
	 * @since Descripter 1.0
	 */
	public final Objective<?> object(Object o) {
		o = evaluate(o);
		return o instanceof Objective<?> ? (Objective<?>)o : alloc(core()._Object(), o);
	}

	/**
	 * <p>Creates a {@link CRegExp} object.</p>
	 * @param re A regular expression string
	 * @return The newly created {@link CRegExp} object
	 * @since Descripter 1.0
	 */
	public final CRegExp re(String re) {
		return new CRegExp(core()._RegExp(), re);
	}

	/**
	 * <p>Creates a {@link CRegExp} object with flags.</p>
	 * @param regexp A regular expression string
	 * @param flags A string of flags for regular expression
	 * @return The newly created {@link CRegExp} object
	 * @since Descripter 1.0
	 */
	public final CRegExp re(String regexp, String flags) {
		return new CRegExp(core()._RegExp(), regexp, flags);
	}

	/**
	 * <p>Locally evaluates a string as JavaScript code.</p>
	 * @param s A string of JavaScript code
	 * @return A result returned from evaluation of the piece of the code or <tt>null</tt> for none
	 * @since Descripter 1.0
	 */
	public Object eval(String s) {
		return call(get(core()._eval), s);
	}

	/**
	 * <p>Locally evaluates the string value from a variable with the specified {@link Key} as JavaScript code.</p>
	 * @param k A {@link Key} of a variable specifying a string of JavaScript code
	 * @return A result returned from evaluation of the piece of the code or <tt>null</tt> for none
	 * @since Descripter 1.0
	 */
	public final Object eval(Key k) {
		return call(get(core()._eval), get(k));
	}

	/**
	 * <p>Locally evaluates the string value from an object as JavaScript code.</p>
	 * @param o An object specifying a string of JavaScript code
	 * @return A result returned from evaluation of the piece of the code or <tt>null</tt> for none
	 * @since Descripter 1.0
	 */
	public final Object eval(Object o) {
		return call(get(core()._eval), evaluate(o));
	}

	/**
	 * <p>Prints the arguments.</p>
	 * <p>This method prints the elements of <tt>args</tt> in lines.</p>
	 * @param args The arguments to print
	 * @return A result returned from calling the corresponding global service
	 * @since Descripter 1.0
	 */
	public final Object print(Object ... args) {
		return call(get(core()._print), args);
	}

	/**
	 * <p>Locally compiles arguments with calling the corresponding native global service.</p>
	 * @param args The arguments to compile
	 * @return A result returned from calling the corresponding global service
	 * @since Descripter 1.0
	 */
	public final Object compile(Object ... args) {
		return call(get(core()._compile), args);
	}

	/**
	 * <p>Locally descripts arguments with calling the corresponding native global service.</p>
	 * @param args The arguments to descript
	 * @return A result returned from calling the corresponding global service
	 * @since Descripter 1.0
	 */
	public final Object descript(Object ... args) {
		return call(get(core()._descript), args);
	}

	/**
	 * <p>Locally executes arguments with calling the corresponding native global service.</p>
	 * @param args The arguments to execute
	 * @return A result returned from calling the corresponding global service
	 * @since Descripter 1.0
	 */
	public final Object execute(Object ... args) {
		return call(get(core()._execute), args);
	}

	/**
	 * <p>An abstract base class for {@link Script} contexts without generic types.</p>
	 * 
	 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
	 * @since Descripter 1.0
	 */
	public static abstract class MyScript extends Script<Script<?>>
	{
		/**
		 * <p>Constructs a script context of this type.</p>
		 * @param with The containing script context.
		 * @since Descripter 1.0
		 */
		protected MyScript(Script<?> with) {
			super(with);
		}
	}

	/**
	 * <p>An abstract base class of {@link Functor} without generic types.</p>
	 * 
	 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
	 * @since Descripter 1.0
	 */
	public static abstract class MyFunctor extends Functor<Script<?>>
	{
		/**
		 * <p>Constructs a script context of this type.</p>
		 * @param with The containing script context.
		 * @since Descripter 1.0
		 */
		protected MyFunctor(MyFunction with) {
			super(with);
		}
	}

	/**
	 * <p>An abstract base class of {@link Function} without generic types.</p>
	 * 
	 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
	 * @since Descripter 1.0
	 */
	public static abstract class MyFunction extends Function<Script<?>>
	{
		/**
		 * <p>Constructs a script context of this type.</p>
		 * @param with The containing script context.
		 * @since Descripter 1.0
		 */
		protected MyFunction(Script<?> with) {
			super(with);
		}

		/**
		 * <p>Gets the {@link MyFunctor} of the {@link MyFunction} object.</p>
		 * <p>Subclasses need to concretely implement this method.</p>
		 * @return The {@link MyFunctor} of the {@link MyFunction} object
		 * @since Descripter 1.0
		 */
		@Override
		public abstract MyFunctor functor();
	}

	/**
	 * <p>An abstract base class of {@link For} without generic types.</p>
	 * 
	 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
	 * @since Descripter 1.0
	 */
	public static abstract class MyFor extends For<Script<?>>
	{
		/**
		 * <p>Constructs a script context of this type.</p>
		 * @param with The containing script context.
		 * @param base The base object to loop in
		 * @param key The {@link Key} of the looping variable
		 * @since Descripter 1.0
		 */
		protected MyFor(Script<?> with, Object base, Key key) {
			super(with, base, key);
		}

		/**
		 * <p>Constructs a script context of this type.</p>
		 * @param with The containing script context.
		 * @param base The base object to loop in
		 * @param var A new looping {@link Var}
		 * @since Descripter 1.0
		 */
		protected MyFor(Script<?> with, Object base, Var var) {
			super(with, base, var);
		}
	}
}
