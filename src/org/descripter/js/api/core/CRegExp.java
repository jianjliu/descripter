
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.descripter.js.api.Core;
import org.descripter.js.api.Function;
import org.descripter.js.api.Key;

/**
 * <p>Emulates JavaScript RegExp objects.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class CRegExp extends CObject
{
	private Pattern pattern = null;

	/**
	 * <p>Constructs a {@link CObject} context of this type.</p>
	 * @param constructor The constructor {@link Function} object.
	 * @param regex The string representation of a regular expression
	 * @param flags The flags of the regular expression
	 * @since Descripter 1.0
	 */
	public CRegExp(Function<?> constructor, String regex, String flags) {
		super(constructor);
		Core core = core();
		super.put(core._source, regex);
		boolean g = false, i = false, m = false;
		for (char c : flags.toCharArray()) {
			switch (c)
			{
				case 'g':
				case 'G':
					g = true;
					break;
				case 'i':
				case 'I':
					i = true;
					break;
				case 'm':
				case 'M':
					m = true;
					break;
				default:
			}
		}
		super.put(core._global, g);
		super.put(core._ignoreCase, i);
		super.put(core._multiline, m);
	}

	/**
	 * <p>Constructs a {@link CObject} context of this type.</p>
	 * @param constructor The constructor {@link Function} object.
	 * @param regex The string representation of a regular expression
	 * @since Descripter 1.0
	 */
	public CRegExp(Function<?> constructor, String regex) {
		super(constructor);
		super.put(core()._source, regex);
	}

	/**
	 * <p>Gets the internal {@link Pattern} object of the current {@link CRegExp} object.</p>
	 * @return The internal {@link Pattern} object of the current {@link CRegExp} object
	 * @since Descripter 1.0
	 */
	public final Pattern pattern() {
		if (pattern == null) {
			int flags = 0;
			if (ignoreCase()) {
				flags = Pattern.CASE_INSENSITIVE;
			}
			if (multiline()) {
				flags |= Pattern.MULTILINE;
			}
			pattern = Pattern.compile(source(), flags);
		}
		return pattern;
	}

	/**
	 * <p>Performs powerful, general-purpose pattern matching with the current regular expression instance.</p>
	 * <p>This method is the most powerful of all the regular expression and string 
	 * pattern-matching methods. It is a general-purpose method that is somewhat more 
	 * complex to use than {@link #test(Object)}, {@link CString#search(CRegExp)}, 
	 * {@link CString#replace(CRegExp, String)}, and {@link CString#match(CRegExp)}.</p>
	 * <p>This invocation searches string for text that matches the current regular expression. 
	 * If it finds a match, it returns an array of results; otherwise, it returns 
	 * <tt>null</tt>. Element 0 of the returned array is the matched text. Element 1 is 
	 * the text that matched the first parenthesized subexpression, if any, within the current 
	 * regular expression. Element 2 contains the text that matched the second subexpression, 
	 * and so on. The array length property specifies the number of elements in the array, 
	 * as usual. In addition to the array elements and the length property, the value 
	 * returned by the invocation also has two other properties. The <tt>index</tt> 
	 * property (see {@link CArray#index()}) specifies the character position of the first 
	 * character of the matched text. The <tt>input</tt> property (see {@link CArray#input()}) 
	 * refers to <tt>s</tt>. This returned array is the same as the array that is 
	 * returned by the {@link CString#match(CRegExp)} method, when invoked on a 
	 * non-global regular expression instance.</p>
	 * <p>When this method is invoked on a non-global pattern, it performs the search and 
	 * returns the result described earlier. When the current instance is a global regular 
	 * expression, however, the invocation behaves in a slightly more complex way. It begins 
	 * searching string at the character position specified by the <tt>lastIndex</tt> 
	 * property (see {@link #lastIndex()} of the current 
	 * regular expression. When it finds a match, it sets <tt>lastIndex</tt> to the 
	 * position of the first character after the match. This means that you can invoke 
	 * this method repeatedly in order to loop through all matches in a string. When 
	 * the invocation cannot find any more matches, it returns <tt>null</tt> and 
	 * resets <tt>lastIndex</tt> to zero. If you begin searching a new string 
	 * immediately after successfully finding a match in another string, you must be 
	 * careful to manually reset <tt>lastIndex</tt> to zero.</p>
	 * <p>Note that this invocation always includes full details of every match in the 
	 * array it returns, whether or not the current regular expression is a global pattern. 
	 * This is where this method differs from {@link CString#match(CRegExp)}, which 
	 * returns much less information when used with global patterns. Calling this method 
	 * repeatedly in a loop is the only way to obtain complete pattern-matching 
	 * information for a global pattern.</p>
	 * @param s The string to be tested.
	 * @return An array containing the results of the match or undefined 
	 * <tt>null</tt> if no match was found.
	 * @throws RuntimeException JavaScript throws a <tt>TypeError</tt> if this method 
	 * is invoked with an instance that is not a regular expression.
	 * @see #lastIndex()
	 * @see #test(Object)
	 * @see CString#match(CRegExp)
	 * @see CString#replace(CRegExp, String)
	 * @see CString#replace(CRegExp, Function)
	 * @see CString#search(CRegExp)
	 * @since Descripter 1.0
	 */
	public final CArray exec(Object s) {
		Matcher m = pattern().matcher(toString(s));
		boolean ret = false;
		if (global()) {
			ret = m.find(lastIndex());
			put(core()._lastIndex, ret ? m.end() : 0);
		} else {
			ret = m.find();
		}
		if (ret) {
			CArray a = new CArray(core()._Array());
			a.put(core()._index, m.start());
			a.put(core()._input, toString(s));
			for (int i = 0, n = m.groupCount(); i < n; i++) {
				a.put(a.length(), m.group(i));
			}
			return a;
		} else {
			return null;
		}
	}

	/**
	 * <p>Gets the <tt>global</tt> field of the current regular expression instance.</p>
	 * <p>The <tt>global</tt> field is a read-only boolean property of regular expression
	 * instances. It specifies whether a particular regular expression performs global matching, 
	 * that is, whether it was created with the "g" attribute.</p>
	 * @return The <tt>global</tt> property of the current {@link CRegExp} object
	 * @since Descripter 1.0
	 */
	public final boolean global() {
		return bool(get(core()._global));
	}

	/**
	 * <p>Gets the <tt>ignoreCase</tt> field of the current regular expression instance.</p>
	 * <p>The <tt>ignoreCase</tt> field is a read-only boolean property of regular expression 
	 * instances. It specifies whether a particular regular expression performs case-insensitive 
	 * matching, that is, whether it was created with the "i" attribute.</p>
	 * @return The <tt>ignoreCase</tt> property of the current {@link CRegExp} object
	 * @since Descripter 1.0
	 */
	public final boolean ignoreCase() {
		return bool(get(core()._ignoreCase));
	}

	/**
	 * <p>Gets the <tt>lastIndex</tt> field of the current regular expression instance.</p>
	 * <p>The <tt>lastIndex</tt> field is a read/write property of regular expression 
	 * instances. For regular expressions with the "g" attribute set, it contains an 
	 * integer that specifies the character position immediately following the last match 
	 * found by the {@link #exec(Object)} and {@link #test(Object)} methods. These methods 
	 * use this property as the starting point for the next search they conduct. This 
	 * allows you to call those methods repeatedly, to loop through all matches in a 
	 * string. Note that <tt>lastIndex</tt> is not used by regular expressions that do 
	 * not have the "g" attribute set and do not represent global patterns.</p>
	 * <p>This property is read/write, so you can set it at any time to specify where in the 
	 * target string the next search should begin. {@link #exec(Object)} and {@link #test(Object)} 
	 * automatically reset <tt>lastIndex</tt> to 0 when they fail to find a match 
	 * (or another match). If you begin to search a new string after a successful match 
	 * of some other string, you have to explicitly set this property to 0.</p>
	 * @return The <tt>lastIndex</tt> property of the current {@link CRegExp} object
	 * @since Descripter 1.0
	 */
	public final int lastIndex() {
		return intValue(get(core()._lastIndex));
	}

	/**
	 * <p>Gets the <tt>multiline</tt> field of the current regular expression instance.</p>
	 * <p>The <tt>multiline</tt> field is a read-only boolean property of regular expression 
	 * instances. It specifies whether a particular regular expression performs multiline 
	 * matching, that is, whether it was created with the "m" attribute.</p>
	 * @return The <tt>multiline</tt> property of the current {@link CRegExp} object
	 * @since Descripter 1.0
	 */
	public final boolean multiline() {
		return bool(get(core()._multiline));
	}

	/**
	 * <p>Gets the <tt>source</tt> field of the current regular expression instance.</p>
	 * <p>The <tt>source</tt> field is a read-only string property of regular expression 
	 * instances. It contains the text of the regular expression. This text does not include 
	 * the delimiting slashes used in regular-expression literals, and it does not include 
	 * the "g", "i", and "m" attributes.</p>
	 * @return The <tt>source</tt> property of the current {@link CRegExp} object
	 * @since Descripter 1.0
	 */
	public final String source() {
		return toString(get(core()._source));
	}

	/**
	 * <p>Tests whether a string contains the pattern represented by the current regular 
	 * expression.</p>
	 * <p></p>
	 * @param s The string to be tested.
	 * @return <tt>true</tt> if <tt>s</tt> contains text that matches the current 
	 * regular expression; false otherwise.
	 * @throws RuntimeException JavaScript throws a <tt>TypeError</tt> if this method 
	 * is invoked with an instance that is not a regular expression.
	 * @see #exec(Object)
	 * @see #lastIndex()
	 * @see CString#match(CRegExp)
	 * @see CString#replace(CRegExp, String)
	 * @see CString#replace(CRegExp, Function)
	 * @see CString#search(CRegExp)
	 * @see CString#substring(Object)
	 * @see CString#substring(Object, Object)
	 * @since Descripter 1.0
	 */
	public final boolean test(Object s) {
		Matcher m = pattern().matcher(toString(s));
		if (global()) {
			boolean ret = m.find(lastIndex());
			put(core()._lastIndex, ret ? m.end() : 0);
			return ret;
		} else {
			return m.find();
		}
	}

	/**
	 * <p>Sets the value associated with the specified key.</p>
	 * @param key A {@link Key} to set the value
	 * @param val The value to set
	 * @throws RuntimeException if the current context is read-only.
	 * @since Descripter 1.0
	 */
	@Override
	public void put(Integer key, Object val) {
		if (
				!key.equals(core()._global) &&
				!key.equals(core()._ignoreCase) &&
				!key.equals(core()._multiline) &&
				!key.equals(core()._source)) {
			super.put(key, val);
		}
	}

	/**
	 * <p>Returns a string representation of the current object.</p>
	 * @return The string representation of the current object
	 * @since Descripter 1.0
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("/");
		sb.append(source());
		sb.append("/");
		if (global()) {
			sb.append('g');
		}
		if (ignoreCase()) {
			sb.append('i');
		}
		if (multiline()) {
			sb.append('m');
		}
		return sb.toString();
	}
}
