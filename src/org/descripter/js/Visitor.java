
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

package org.descripter.js;

import org.descripter.js.api.Script;

/**
 * <p>An abstract base class for JavaScript parser visitors.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public abstract class Visitor<S extends Script<?>> extends Script<S>
{
	/**
	 * <p><tt>true</tt> for debug mode; <tt>false</tt>, otherwise.</p>
	 * @since Descripter 1.0
	 */
	public final static boolean DEBUG = false;

	/**
	 * <p>Prints debug information for an object.</p>
	 * <p>This method simply prints out the simple class name of the specified object.</p>
	 * @param o The object
	 * @since Descripter 1.0
	 */
	protected final void debug(Object o) {
		print(o.getClass().getSimpleName());
	}

	/**
	 * <p>Constructs a visitor of this type.</p>
	 * @param script A script context.
	 * @since Descripter 1.0
	 */
	protected Visitor(S script) {
		super(script);
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

	/**
	 * <p>Concatenates the arguments with the specified separator.</p>
	 * @param sep The separator
	 * @param args The arguments
	 * @return The concatenated string
	 * @since Descripter 1.0
	 */
	protected static String concat(String sep, Object...args) {
		StringBuilder sb = new StringBuilder();
		if (args != null && args.length > 0) {
			sb.append(args[0]);
			for (int i = 1; i < args.length; i++) {
				if (sep != null) {
					sb.append(sep);
				}
				sb.append(args[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * <p>Concatenates the arguments without a separator.</p>
	 * @param args The arguments
	 * @return The concatenated string
	 * @since Descripter 1.0
	 */
	protected static String cat(Object...args) {
		return concat(null, args);
	}

	/**
	 * <p>Concatenates the arguments with a space separator.</p>
	 * @param args The arguments
	 * @return The concatenated string
	 * @since Descripter 1.0
	 */
	protected static String cats(Object...args) {
		return concat(" ", args);
	}

	/**
	 * <p>Concatenates the arguments with a new-line separator.</p>
	 * @param args The arguments
	 * @return The concatenated string
	 * @since Descripter 1.0
	 */
	protected static String catn(Object...args) {
		return concat(System.lineSeparator(), args);
	}

	/**
	 * <p>Concatenates the arguments with a comma separator.</p>
	 * @param args The arguments
	 * @return The concatenated string
	 * @since Descripter 1.0
	 */
	protected static String list(Object...args) {
		return concat(", ", args);
	}

	/**
	 * <p>Groups an argument.</p>
	 * @param arg The argument to parenthesize
	 * @return The string of the parenthesized argument.
	 * @since Descripter 1.0
	 */
	protected static String arg(Object arg) {
		return cat("(", arg, ")");
	}

	/**
	 * <p>Groups a list of the base and the arguments.</p>
	 * @param base The base argument
	 * @param args The arguments
	 * @return The string of the parenthesized list of the base and the arguments.
	 * @since Descripter 1.0
	 */
	protected static String args(Object base, Object...args) {
		return args != null && args.length > 0 ? arg(list(base, list(args))) : arg(base);
	}

	/**
	 * <p>Casts the argument to an array.</p>
	 * @param data The argument
	 * @return The array of {@link Object}.
	 * @since Descripter 1.0
	 */
	protected static Object[] arr(Object data) {
		return (Object[])data;
	}

	/**
	 * <p>Returns a boolean evaluation of an argument when necessary.</p>
	 * @param arg The argument
	 * @return The boolean evaluation of the argument.
	 * @since Descripter 1.0
	 */
	protected static Object car(Object arg) {
		return arg instanceof Bool ? arg : cat("bool", arg(arg));
	}

	/**
	 * <p>Returns a conditional action over an argument.</p>
	 * @param arg The argument
	 * @return The conditional action over the argument.
	 * @since Descripter 1.0
	 */
	protected static String cnd(String act, Object arg) {
		return cat(act, arg(arg));
	}

	/**
	 * <p>Appends a new-line to the argument.</p>
	 * @param arg The argument
	 * @return The appended string.
	 * @since Descripter 1.0
	 */
	protected static String ln(Object arg) {
		return cat(arg, "\n");
	}

	/**
	 * <p>Prepends a tab to the argument.</p>
	 * @param arg The argument
	 * @return The prepended string.
	 * @since Descripter 1.0
	 */
	protected static String tab(Object arg) {
		return cat("\t", arg);
	}

	/**
	 * <p>Prepends two tabs to the argument.</p>
	 * @param arg The argument
	 * @return The prepended string.
	 * @since Descripter 1.0
	 */
	protected static String tab2(Object arg) {
		return cat("\t\t", arg);
	}

	/**
	 * <p>Prepends three tabs to the argument.</p>
	 * @param arg The argument
	 * @return The prepended string.
	 * @since Descripter 1.0
	 */
	protected static String tab3(Object arg) {
		return tab(tab2(arg));
	}

	/**
	 * <p>Prepends four tabs to the argument.</p>
	 * @param arg The argument
	 * @return The prepended string.
	 * @since Descripter 1.0
	 */
	protected static String tab4(Object arg) {
		return tab2(tab2(arg));
	}

	/**
	 * <p>Returns an invocation over an object without arguments.</p>
	 * @param o The {@link Object} to invoke
	 * @return The invocation over the object without arguments.
	 * @since Descripter 1.0
	 */
	protected static String def(Object o) {
		return cat(o, "()");
	}

	/**
	 * <p>Returns an expression of a generic type with a parameter.</p>
	 * @param t The generic type
	 * @param p The parameter
	 * @return The expression of the generic type with the parameter.
	 * @since Descripter 1.0
	 */
	protected static String par(Object t, Object p) {
		return cat(t, "<", p, ">");
	}

	/**
	 * <p>Returns an expression of a member reference to an object.</p>
	 * @param obj The base object
	 * @param ref The member to reference
	 * @return The expression of the member reference to the object.
	 * @since Descripter 1.0
	 */
	protected static String ref(Object obj, Object ref) {
		return cat(obj, ".", ref);
	}

	/**
	 * <p>Returns an expression to execute a base object.</p>
	 * @param bas The base object to execute
	 * @return The expression to execute the base object.
	 * @since Descripter 1.0
	 */
	protected static String run(Object bas) {
		return def(ref(bas, "run"));
	}

	/**
	 * <p>Returns an invocation of a function with arguments.</p>
	 * @param fun The function to invoke
	 * @param args The arguments to pass to the invocation
	 * @return The invocation of the function with the arguments.
	 * @since Descripter 1.0
	 */
	protected static String inv(String fun, Object... args) {
		return cat(fun, arg(list(args)));
	}

	/**
	 * <p>Returns a new expression of a class with arguments.</p>
	 * @param cls The class to construct
	 * @param args The arguments to pass to the constructor
	 * @return The construction of the class with the arguments.
	 * @since Descripter 1.0
	 */
	protected static String nevv(String cls, Object... args) {
		return cats("new", inv(cls, args));
	}

	/**
	 * <p>Returns a cast expression of a type over an argument.</p>
	 * @param type The target type to cast
	 * @param arg The argument to cast
	 * @return The cast expression of the type over the argument.
	 * @since Descripter 1.0
	 */
	protected static String cast(String type, Object arg) {
		return cat(arg(type), arg);
	}

	/**
	 * <p>Returns a statement from an argument.</p>
	 * @param arg The argument
	 * @return The string of the statement.
	 * @since Descripter 1.0
	 */
	protected static String stmt(Object arg) {
		return cat(arg, ";");
	}

	/**
	 * <p>Returns a Java string literal from a JavaScript one.</p>
	 * @param s A JavaScript string literal
	 * @return The escaped Java string literal.
	 * @since Descripter 1.0
	 */
	protected static final String str(String s) {
		return qt(esc(s.substring(1, s.length() - 1)));
	}

	/**
	 * <p>Escapes a string.</p>
	 * @param s The string to escape
	 * @return The escaped string.
	 * @since Descripter 1.0
	 */
	protected static final String esc(String s) {
		int len = s.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (c > 0xfff) {
				sb.append("\\u").append(Integer.toHexString(c));
			} else if (c > 0xff) {
				sb.append("\\u0").append(Integer.toHexString(c));
			} else if (c > 0x7f) {
				sb.append("\\u00").append(Integer.toHexString(c));
			} else if (c < 32) {
				switch (c) {
					case '\b':
						sb.append('\\').append('b');
						break;
					case '\n':
						sb.append('\\').append('n');
						break;
					case '\t':
						sb.append('\\').append('t');
						break;
					case '\f':
						sb.append('\\').append('f');
						break;
					case '\r':
						sb.append('\\').append('r');
						break;
					default :
						if (c > 0xf) {
							sb.append("\\u00").append(Integer.toHexString(c));
						} else {
							sb.append("\\u000").append(Integer.toHexString(c));
						}
						break;
				}
			} else {
				switch (c) {
					case '"':
						sb.append('\\').append('"');
						break;
					case '\\':
						sb.append('\\').append('\\');
						break;
					default :
						sb.append(c);
						break;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * <p>Returns a double-quoted string literal for Java source.</p>
	 * @param s A string
	 * @return The Java string literal.
	 * @since Descripter 1.0
	 */
	protected static final String qt(String s) {
		return cat("\"", s, "\"");
	}

	/**
	 * <p>Returns an assignment expression.</p>
	 * @param var The variable
	 * @param arg The value argument
	 * @return The string of the assignment expression.
	 * @since Descripter 1.0
	 */
	protected static final String asg(Object var, Object arg) {
		return cat(var, ".", "assign", arg(arg));
	}
}
