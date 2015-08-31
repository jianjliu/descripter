
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

import java.util.Locale;
import java.util.regex.Matcher;

import org.descripter.js.api.Function;

/**
 * <p>Emulates JavaScript String objects.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class CString extends CObject
{
	private final String value;

	/**
	 * <p>Constructs a {@link CObject} context of this type.</p>
	 * @param constructor The constructor {@link Function} object.
	 * @param value The initial value
	 * @since Descripter 1.0
	 */
	public CString(Function<?> constructor, Object value) {
		super(constructor);
		this.value = value == null ? "" : toString(value);
	}

	/**
	 * <p>Gets the length of the current string, an integer that indicates the number of 
	 * characters in the current string. For any string <tt>s</tt>, the index of the 
	 * last character is <tt>s.length() - 1</tt>. The length property of a string may 
	 * not be deleted.</p>
	 * @return The length of the current string.
	 * @since Descripter 1.0
	 */
	public final int length() {
		return value.length();
	}

	/**
	 * <p>Returns the character string of length 1 at the specified <tt>index</tt> within 
	 * the current string. An index ranges from 0 to <tt>length() - 1</tt>. The first 
	 * character of the sequence is at index 0, the next at index 1, and so on, as for 
	 * array indexing. If <tt>index</tt> is not between 0 and <tt>length() - 1</tt>, 
	 * this invocation returns an empty string.</p>
	 * @param index The index of the character
	 * @return The character string of length 1 at the specified index of the current string
	 * @see #charCodeAt(Object)
	 * @see #indexOf(Object)
	 * @see #indexOf(Object, Object)
	 * @see #lastIndexOf(Object)
	 * @see #lastIndexOf(Object, Object)
	 * @since Descripter 1.0
	 */
	public final String charAt(Object index) {
		return new String(new char[]{value.charAt(intValue(index))});
	}

	/**
	 * <p>Returns the character encoding at a specific <tt>index</tt> within the current 
	 * string. An index ranges from 0 to <tt>length() - 1</tt>. The first 
	 * character of the sequence is at index 0, the next at index 1, and so on, as for 
	 * array indexing. If <tt>index</tt> is not between 0 and <tt>length() - 1</tt>, 
	 * this invocation returns <tt>NaN</tt>.</p>
	 * @param index The index of the character
	 * @return The Unicode encoding of the character within the current string. The return 
	 * value is a 16-bit integer between 0 and 65535.
	 * @see #charAt(Object)
	 * @see #indexOf(Object)
	 * @see #indexOf(Object, Object)
	 * @see #lastIndexOf(Object)
	 * @see #lastIndexOf(Object, Object)
	 * @since Descripter 1.0
	 */
	public final char charCodeAt(Object index) {
		return value.charAt(intValue(index));
	}

	/**
	 * <p>Converts the argument to a string (if necessary) and appends them, in order, to 
	 * the end of the current string and returns the resulting concatenation.</p>
	 * <p>Note that the current string itself is not modified.</p>
	 * <p>This method is an analog to {@link CArray#concat(CArray)}. Note that it is 
	 * often easier to use {@link org.descripter.js.api.Objective#add(Object, Object)} perform string concatenation.</p>
	 * @param other A value to be concatenated to the current string
	 * @return A new string that results from concatenating the argument to the current 
	 * string.
	 * @see CArray#concat(CArray)
	 * @since Descripter 1.0
	 */
	public final String concat(Object other) {
		return value.concat(toString(other));
	}

	/**
	 * <p>Converts the arguments to a string (if necessary) and appends them, in order, to 
	 * the end of the current string and returns the resulting concatenation.</p>
	 * <p>Note that the current string itself is not modified.</p>
	 * <p>This method is an analog to {@link CArray#concat(CArray)}. Note that it is 
	 * often easier to use {@link org.descripter.js.api.Objective#add(Object, Object)} perform string concatenation.</p>
	 * @param args An array of values to be concatenated to the current string
	 * @return A new string that results from concatenating the argument to the current 
	 * string.
	 * @since Descripter 1.0
	 */
	public final String concat(CArray args) {
		StringBuilder sb = new StringBuilder(value);
		for (int i = 0, len = args.length(); i < len; i++) {
			sb.append(args.get(i));
		}
		return sb.toString();
	}

	/**
	 * <p>Searches the current string instance from beginning to end to see if it contains 
	 * an occurrence of the substring <tt>other</tt>. The search begins at position 
	 * <tt>pos</tt> within string, or at the beginning of string if <tt>pos</tt> is  
	 * undefined. If an occurrence of the substring is found, this invocation returns the 
	 * position of the first character of the first occurrence of the substring within 
	 * the current string. Character positions within string are numbered starting with 
	 * zero. If no occurrence of substring is found within the current string, this invocation 
	 * returns -1.</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * other</tt>, The substring that is to be searched for within the current string</li><li><tt>
	 * pos</tt>, An optional integer argument that specifies the position within the 
	 * current string at which the search is to start. Legal values are 0 (the position of 
	 * the first character in the string) to <tt>length() - 1</tt> (the position of 
	 * the last character in the string). If this argument is undefined, the search begins 
	 * at the first character of the string</li></ul>
	 * @return The position of the first occurrence of <tt>other</tt> within string that 
	 * appears after the <tt>pos</tt> position, if any, or -1 if no such occurrence 
	 * is found.
	 * @see #indexOf(Object)
	 * @see #indexOf(Object, Object)
	 * @see #charAt(Object)
	 * @see #lastIndexOf(CArray)
	 * @see #lastIndexOf(Object)
	 * @see #lastIndexOf(Object, Object)
	 * @see #substr(CArray)
	 * @see #substr(Object)
	 * @see #substr(Object, Object)
	 * @see #substring(CArray)
	 * @see #substring(Object)
	 * @see #substring(Object, Object)
	 * @since Descripter 1.0
	 */
	public final int indexOf(CArray args) {
		if (args.length() > 1) {
			return indexOf(args.get(0), args.get(1));
		}
		return indexOf(args.get(0));
	}

	/**
	 * <p>Searches the current string instance from beginning to end to see if it contains 
	 * an occurrence of the substring <tt>other</tt>. The search begins at the beginning 
	 * of the current string. If an occurrence of the substring is found, this invocation 
	 * returns the position of the first character of the first occurrence of the substring 
	 * within the current string. Character positions within string are numbered starting with 
	 * zero. If no occurrence of substring is found within the current string, this invocation 
	 * returns -1.</p>
	 * @param other The substring that is to be searched for within the current string
	 * @return The position of the first occurrence of <tt>other</tt> within string, if 
	 * any, or -1 if no such occurrence is found.
	 * @see #indexOf(CArray)
	 * @see #indexOf(Object, Object)
	 * @see #charAt(Object)
	 * @see #lastIndexOf(CArray)
	 * @see #lastIndexOf(Object)
	 * @see #lastIndexOf(Object, Object)
	 * @see #substr(CArray)
	 * @see #substr(Object)
	 * @see #substr(Object, Object)
	 * @see #substring(CArray)
	 * @see #substring(Object)
	 * @see #substring(Object, Object)
	 * @since Descripter 1.0
	 */
	public final int indexOf(Object other) {
		return value.indexOf(toString(other));
	}

	/**
	 * <p>Searches the current string instance from beginning to end to see if it contains 
	 * an occurrence of the substring <tt>other</tt>. The search begins at position 
	 * <tt>pos</tt> within string, or at the beginning of string if <tt>pos</tt> is  
	 * undefined. If an occurrence of the substring is found, this invocation returns the 
	 * position of the first character of the first occurrence of the substring within 
	 * the current string. Character positions within string are numbered starting with 
	 * zero. If no occurrence of substring is found within the current string, this invocation 
	 * returns -1.</p>
	 * @param other The substring that is to be searched for within the current string
	 * @param pos An optional integer argument that specifies the position within the 
	 * current string at which the search is to start. Legal values are 0 (the position of 
	 * the first character in the string) to <tt>length() - 1</tt> (the position of 
	 * the last character in the string). If this argument is undefined, the search begins 
	 * at the first character of the string
	 * @return The position of the first occurrence of <tt>other</tt> within string that 
	 * appears after the <tt>pos</tt> position, if any, or -1 if no such occurrence 
	 * is found.
	 * @see #indexOf(CArray)
	 * @see #indexOf(Object)
	 * @see #charAt(Object)
	 * @see #lastIndexOf(CArray)
	 * @see #lastIndexOf(Object)
	 * @see #lastIndexOf(Object, Object)
	 * @see #substr(CArray)
	 * @see #substr(Object)
	 * @see #substr(Object, Object)
	 * @see #substring(CArray)
	 * @see #substring(Object)
	 * @see #substring(Object, Object)
	 * @since Descripter 1.0
	 */
	public final int indexOf(Object other, Object pos) {
		return value.indexOf(toString(other), intValue(pos));
	}

	/**
	 * <p>Searches the current string instance from end to beginning to see if it contains 
	 * an occurrence of the substring <tt>other</tt>. The search begins at position 
	 * <tt>pos</tt> within string, or at the end of string if <tt>pos</tt> is  
	 * undefined. If an occurrence of the substring is found, this invocation returns the 
	 * position of the first character that occurrence. Since this method 
	 * searches from end to beginning of the string, the first occurrence found is the last 
	 * one in the string that occurs before the <tt>pos</tt> position. If no occurrence 
	 * of substring is found within the current string, this invocation returns -1.</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * other</tt>, The substring that is to be searched for within the current string</li><li><tt>
	 * pos</tt>, An optional integer argument that specifies the position within the 
	 * current string at which the search is to start. Legal values are 0 (the position of 
	 * the first character in the string) to <tt>length() - 1</tt> (the position of 
	 * the last character in the string). If this argument is undefined, the search begins 
	 * at the last character of the string</li></ul>
	 * @return The position of the last occurrence of <tt>other</tt> within string that 
	 * appears before the <tt>pos</tt> position, if any, or -1 if no such occurrence 
	 * is found.
	 * @see #lastIndexOf(Object)
	 * @see #lastIndexOf(Object, Object)
	 * @see #charAt(Object)
	 * @see #indexOf(CArray)
	 * @see #indexOf(Object)
	 * @see #indexOf(Object, Object)
	 * @see #substr(CArray)
	 * @see #substr(Object)
	 * @see #substr(Object, Object)
	 * @see #substring(CArray)
	 * @see #substring(Object)
	 * @see #substring(Object, Object)
	 * @since Descripter 1.0
	 */
	public final int lastIndexOf(CArray args) {
		if (args.length() > 1) {
			return lastIndexOf(args.get(0), args.get(1));
		}
		return lastIndexOf(args.get(0));
	}

	/**
	 * <p>Searches the current string instance from end to beginning to see if it contains 
	 * an occurrence of the substring <tt>other</tt>. The search begins at the end 
	 * of the current string. If an occurrence of the substring is found, this invocation 
	 * returns the position of the first character of that occurrence. If no occurrence of 
	 * substring is found within the current string, this invocation returns -1.</p>
	 * @param other The substring that is to be searched for within the current string
	 * @return The position of the last occurrence of <tt>other</tt> within string, if 
	 * any, or -1 if no such occurrence is found.
	 * @see #lastIndexOf(CArray)
	 * @see #lastIndexOf(Object, Object)
	 * @see #charAt(Object)
	 * @see #indexOf(CArray)
	 * @see #indexOf(Object)
	 * @see #indexOf(Object, Object)
	 * @see #substr(CArray)
	 * @see #substr(Object)
	 * @see #substr(Object, Object)
	 * @see #substring(CArray)
	 * @see #substring(Object)
	 * @see #substring(Object, Object)
	 * @since Descripter 1.0
	 */
	public final int lastIndexOf(Object other) {
		return value.lastIndexOf(toString(other));
	}

	/**
	 * <p>Searches the current string instance from end to beginning to see if it contains 
	 * an occurrence of the substring <tt>other</tt>. The search begins at position 
	 * <tt>pos</tt> within string, or at the end of string if <tt>pos</tt> is  
	 * undefined. If an occurrence of the substring is found, this invocation returns the 
	 * position of the first character that occurrence. Since this method 
	 * searches from end to beginning of the string, the first occurrence found is the last 
	 * one in the string that occurs before the <tt>pos</tt> position. If no occurrence 
	 * of substring is found within the current string, this invocation returns -1.</p>
	 * @param other The substring that is to be searched for within the current string
	 * @param pos An optional integer argument that specifies the position within the 
	 * current string at which the search is to start. Legal values are 0 (the position of 
	 * the first character in the string) to <tt>length() - 1</tt> (the position of 
	 * the last character in the string). If this argument is undefined, the search begins 
	 * at the last character of the string
	 * @return The position of the last occurrence of <tt>other</tt> within string that 
	 * appears before the <tt>pos</tt> position, if any, or -1 if no such occurrence 
	 * is found.
	 * @see #lastIndexOf(CArray)
	 * @see #lastIndexOf(Object)
	 * @see #charAt(Object)
	 * @see #indexOf(CArray)
	 * @see #indexOf(Object)
	 * @see #indexOf(Object, Object)
	 * @see #substr(CArray)
	 * @see #substr(Object)
	 * @see #substr(Object, Object)
	 * @see #substring(CArray)
	 * @see #substring(Object)
	 * @see #substring(Object, Object)
	 * @since Descripter 1.0
	 */
	public final int lastIndexOf(Object other, Object pos) {
		return value.lastIndexOf(toString(other), intValue(pos));
	}

	/**
	 * <p>Compares strings taking the collation order of the default locale into account.</p>
	 * <p>The ECMAScript standard does not specify how the locale-specific comparison is done; 
	 * it merely specifies that this function utilize the collation order provided by the 
	 * underlying operating system.</p>
	 * @param other A string to be compared, in a locale-sensitive fashion, with the current string
	 * @return An integer number that indicates the result of the comparison. If the current 
	 * string is "less than" the string <tt>other</tt>, this invocation returns a 
	 * number less than zero. If the current string is "greater than" <tt>other</tt>, 
	 * it returns a integer number greater than zero. And if the strings are identical or 
	 * indistinguishable according to the locale ordering conventions, the method returns 0.
	 * @since Descripter 1.0
	 */
	public final int localeCompare(Object other) {
		return value.compareTo(toString(other));
	}

	/**
	 * <p>Searches the current string for one or more matches of <tt>regexp</tt>. 
	 * The behavior of this invocation depends significantly on whether <tt>regexp</tt> 
	 * has the "g" attribute or not .</p>
	 * <p>If <tt>regexp</tt> does not have the "g" attribute, this invocation searches 
	 * string for a single match. If no match is found, it returns <tt>null</tt>. 
	 * Otherwise, it returns an array containing information about the match that it found. 
	 * Element 0 of the array contains the matched text. The remaining elements contain 
	 * the text that matches any parenthesized subexpressions within the regular expression. 
	 * In addition to these normal array elements, the returned array also has two object 
	 * properties. The <tt>index</tt> property (see {@link CArray#index()}) of the array  
	 * specifies the character position within string of the start of the matched text. Also, 
	 * the <tt>input</tt> property (see {@link CArray#input()}) of the returned array 
	 * is a reference to string itself.</p>
	 * <p>If <tt>regexp</tt> has the "g" flag, this invocation does a global search, 
	 * searching string for all matching substrings. It returns <tt>null</tt> if no 
	 * match is found, and it returns an array if one or more matches are found. The 
	 * contents of this returned array are quite different for global matches, however. In 
	 * this case, the array elements contain each of the matched substrings within string. 
	 * The returned array does not have <tt>index</tt> (see {@link CArray#index()}) 
	 * or <tt>input</tt> (see {@link CArray#input()}) properties in this case. Note 
	 * that for global matches, this invocation does not provide information about 
	 * parenthesized subexpressions, nor does it specify where within string each match 
	 * occurred. If you need to obtain this information for a global search, you can use 
	 * {@link CRegExp#exec(Object)}.</p>
	 * @param regexp A RegExp object that specifies the pattern to be matched
	 * @return An array containing the results of the match. The contents of the array 
	 * depend on whether regexp has the global "g" attribute set.
	 * @see #replace(CRegExp, String)
	 * @see #search(CRegExp)
	 * @see CArray#index()
	 * @see CArray#input()
	 * @see org.descripter.js.api.Script#re(String)
	 * @see org.descripter.js.api.Script#re(String, String)
	 * @see CRegExp#exec(Object)
	 * @see CRegExp#test(Object)
	 * @since Descripter 1.0
	 */
	public final CArray match(CRegExp regexp) {
		return regexp.exec(this);
	}

	/**
	 * <p>Performs a search-and-replace operation on the current string.</p>
	 * <p>This invocation searches the current string for one or more substrings that 
	 * match <tt>regexp</tt> and replaces them with the replacement string 
	 * <tt>newSubStr</tt>.</p>
	 * <p>If <tt>regexp</tt> has the global "g" attribute specified, this invocation 
	 * replaces all matching substrings. Otherwise, it replaces only the first matching 
	 * substring.</p>
	 * <p>Note that the $ character has special meaning within the replacement string 
	 * <tt>newSubStr</tt>. As shown in the following, it indicates that a string 
	 * derived from the pattern match is used in the replacement.</p>
	 * <ul>
	 * <li>$1, $2, ..., $99 The text that matched the 1st through 99th parenthesized 
	 * subexpression within <tt>regexp</tt></li>
	 * <li>$& The substring that matched <tt>regexp</tt></li>
	 * <li>$' The text to the left of the matched substring</li>
	 * <li>$' The text to the right of the matched substring</li>
	 * <li>$$ A literal dollar sign</li>
	 * </ul>
	 * @param args An array of arguments:<ul><li><tt>
	 * regexp</tt>, The RegExp object that specifies the pattern to be replaced</li><li><tt>
	 * newSubStr</tt>, A string that specifies the replacement text</li></ul>
	 * @return A new string, with the first match, or all matches, of <tt>regexp</tt> 
	 * replaced with the replacement.
	 * @see #replace(CRegExp, String)
	 * @see #replace(CRegExp, Function)
	 * @see #match(CRegExp)
	 * @see #search(CRegExp)
	 * @see org.descripter.js.api.Script#re(String)
	 * @see org.descripter.js.api.Script#re(String, String)
	 * @see CRegExp#exec(Object)
	 * @see CRegExp#test(Object)
	 * @since Descripter 1.0
	 */
	public final String replace(CArray args) {
		CRegExp regexp = (CRegExp)args.get(0);
		Object arg = args.get(1);
		if (arg instanceof Function<?>) {
			return replace(regexp, (Function<?>)arg);
		}
		return replace(regexp, toString(arg));
	}

	/**
	 * <p>Performs a search-and-replace operation on the current string.</p>
	 * <p>This invocation searches the current string for one or more substrings that 
	 * match <tt>regexp</tt> and replaces them with the replacement string 
	 * <tt>newSubStr</tt>.</p>
	 * <p>If <tt>regexp</tt> has the global "g" attribute specified, this invocation 
	 * replaces all matching substrings. Otherwise, it replaces only the first matching 
	 * substring.</p>
	 * <p>Note that the $ character has special meaning within the replacement string 
	 * <tt>newSubStr</tt>. As shown in the following, it indicates that a string 
	 * derived from the pattern match is used in the replacement.</p>
	 * <ul>
	 * <li>$1, $2, ..., $99 The text that matched the 1st through 99th parenthesized 
	 * subexpression within <tt>regexp</tt></li>
	 * <li>$& The substring that matched <tt>regexp</tt></li>
	 * <li>$' The text to the left of the matched substring</li>
	 * <li>$' The text to the right of the matched substring</li>
	 * <li>$$ A literal dollar sign</li>
	 * </ul>
	 * @param regexp The RegExp object that specifies the pattern to be replaced
	 * @param newSubStr A string that specifies the replacement text
	 * @return A new string, with the first match, or all matches, of <tt>regexp</tt> 
	 * replaced with the replacement.
	 * @see #replace(CArray)
	 * @see #replace(CRegExp, String)
	 * @see #replace(CRegExp, Function)
	 * @see #match(CRegExp)
	 * @see #search(CRegExp)
	 * @see org.descripter.js.api.Script#re(String)
	 * @see org.descripter.js.api.Script#re(String, String)
	 * @see CRegExp#exec(Object)
	 * @see CRegExp#test(Object)
	 * @since Descripter 1.0
	 */
	public final String replace(CRegExp regexp, String newSubStr) {
		Matcher m = regexp.pattern().matcher(value);
		return regexp.global() ? m.replaceAll(newSubStr) : m.replaceFirst(newSubStr);
	}

	/**
	 * <p>Performs a search-and-replace operation on the current string.</p>
	 * <p>This invocation searches the current string for one or more substrings that 
	 * match <tt>regexp</tt> and replaces them with the replacement string generated by 
	 * <tt>lambda</tt>.</p>
	 * <p>If <tt>regexp</tt> has the global "g" attribute specified, this invocation 
	 * replaces all matching substrings. Otherwise, it replaces only the first matching 
	 * substring.</p>
	 * @param regexp The RegExp object that specifies the pattern to be replaced
	 * @param lambda A function that is invoked to generate the replacement text
	 * @return A new string, with the first match, or all matches, of <tt>regexp</tt> 
	 * replaced with the replacement.
	 * @see #replace(CArray)
	 * @see #replace(CRegExp, String)
	 * @see #match(CRegExp)
	 * @see #search(CRegExp)
	 * @see org.descripter.js.api.Script#re(String)
	 * @see org.descripter.js.api.Script#re(String, String)
	 * @see CRegExp#exec(Object)
	 * @see CRegExp#test(Object)
	 * @since Descripter 1.0
	 */
	public final String replace(CRegExp regexp, Function<?> lambda) {
		return replace(regexp, toString(core().call(lambda)));
	}

	/**
	 * <p>Looks for a substring matching <tt>regexp</tt> within the current string 
	 * and returns the position of the first character of the matching substring, 
	 * or -1 if no match was found.</p>
	 * <p>This invocation does not do global matches; it ignores the "g" flag of 
	 * <tt>regexp</tt>. It also ignores the <tt>lastIndex</tt> property 
	 * (see {@link CRegExp#lastIndex()} of 
	 * <tt>regexp</tt> and always searches from the beginning of the string, which 
	 * means that it always returns the position of the first match in the string.</p>
	 * @param regexp A RegExp object that specifies the pattern to be searched for in the current string.
	 * @return The position of the start of the first substring of the current string 
	 * that matches <tt>regexp</tt>, or -1 if no match is found.
	 * @see #replace(CRegExp, String)
	 * @see #replace(CRegExp, Function)
	 * @see #match(CRegExp)
	 * @see org.descripter.js.api.Script#re(String)
	 * @see org.descripter.js.api.Script#re(String, String)
	 * @see CRegExp#exec(Object)
	 * @see CRegExp#test(Object)
	 * @since Descripter 1.0
	 */
	public final int search(CRegExp regexp) {
		Matcher m = regexp.pattern().matcher(value);
		boolean res = m.find();
		return res ? m.start() : -1;
	}

	/**
	 * <p>Returns a string containing a slice, or substring, of the current string without 
	 * modify it.</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * begin</tt>, The string index where the slice is to begin. If negative, this argument 
	 * specifies a position measured from the end of the string. That is, -1 indicates the 
	 * last character, -2 indicates the second from last character, and so on.</li><li><tt>
	 * end</tt>, The string index immediately after the end of the slice. If undefined, 
	 * the slice includes all characters from <tt>begin</tt> to the end of the string. 
	 * If this argument is negative, it specifies a position measured from the end of the 
	 * string.</li></ul>
	 * @return A new string that contains all the characters of string from and including 
	 * <tt>begin</tt>, and up to but not including <tt>end</tt>.
	 * @see #slice(Object)
	 * @see #slice(Object, Object)
	 * @see #substr(CArray)
	 * @see #substr(Object)
	 * @see #substr(Object, Object)
	 * @see #substring(CArray)
	 * @see #substring(Object)
	 * @see #substring(Object, Object)
	 * @see CArray#slice(CArray)
	 * @see CArray#slice(int)
	 * @see CArray#slice(int, int)
	 * @since Descripter 1.0
	 */
	public final String slice(CArray args) {
		if (args.length() > 1) {
			return slice(args.get(0), args.get(1));
		}
		return slice(args.get(0));
	}

	/**
	 * <p>Returns a string containing a slice, or substring, of the current string without 
	 * modify it.</p>
	 * @param begin The string index where the slice is to begin. If negative, this argument 
	 * specifies a position measured from the end of the string. That is, -1 indicates the 
	 * last character, -2 indicates the second from last character, and so on.
	 * @return A new string that contains all the characters of string from and including 
	 * <tt>begin</tt>.
	 * @see #slice(CArray)
	 * @see #slice(Object, Object)
	 * @see #substr(CArray)
	 * @see #substr(Object)
	 * @see #substr(Object, Object)
	 * @see #substring(CArray)
	 * @see #substring(Object)
	 * @see #substring(Object, Object)
	 * @see CArray#slice(CArray)
	 * @see CArray#slice(int)
	 * @see CArray#slice(int, int)
	 * @since Descripter 1.0
	 */
	public final String slice(Object begin) {
		int from = intValue(begin);
		from = from < 0 ? length() + from : from;
		return value.substring(from);
	}

	/**
	 * <p>Returns a string containing a slice, or substring, of the current string without 
	 * modify it.</p>
	 * @param begin The string index where the slice is to begin. If negative, this argument 
	 * specifies a position measured from the end of the string. That is, -1 indicates the 
	 * last character, -2 indicates the second from last character, and so on.
	 * @param end The string index immediately after the end of the slice. If undefined, 
	 * the slice includes all characters from <tt>begin</tt> to the end of the string. 
	 * If this argument is negative, it specifies a position measured from the end of the 
	 * string.
	 * @return A new string that contains all the characters of string from and including 
	 * <tt>begin</tt>, and up to but not including <tt>end</tt>.
	 * @see #slice(CArray)
	 * @see #slice(Object)
	 * @see #substr(CArray)
	 * @see #substr(Object)
	 * @see #substr(Object, Object)
	 * @see #substring(CArray)
	 * @see #substring(Object)
	 * @see #substring(Object, Object)
	 * @see CArray#slice(CArray)
	 * @see CArray#slice(int)
	 * @see CArray#slice(int, int)
	 * @since Descripter 1.0
	 */
	public final String slice(Object begin, Object end) {
		int length = length();
		int from = intValue(begin);
		from = from < 0 ? length + from : from;
		int to = intValue(end);
		to = to == 0 ? length : to < 0 ? to + length : to;
		return value.substring(from, to);
	}

	/**
	 * <p>Creates and returns an array of as many as <tt>limit</tt> substrings of the 
	 * current string. These substrings are created by searching the string from start to 
	 * end for text that matches <tt>separator</tt> and breaking the string before and 
	 * after that matching text. The <tt>separator</tt> text is not included in any of 
	 * the returned substrings, except as noted at the end of this section. Note that if 
	 * the <tt>separator</tt> matches the beginning of the string, the first element 
	 * of the returned array will be an empty string, the text that appears before the 
	 * <tt>separator</tt>. Similarly, if the <tt>separator</tt> matches the end of 
	 * the string, the last element of the array (assuming no conflicting <tt>limit</tt>) 
	 * will be the empty string.</p>
	 * <p>If <tt>separator</tt> is undefined, the current string is not split at all, 
	 * and the returned array contains only a single, unbroken string element. If 
	 * <tt>separator</tt> is the empty string or a regular expression that matches 
	 * the empty string, the string is broken between each character, and the returned 
	 * array has the same length as the string does, assuming no smaller <tt>limit</tt> 
	 * is specified. Note that this is a special case because the empty strings before 
	 * the first character and after the last character are not matched.</p>
	 * <p>As noted earlier, the substrings in the array returned by this invocation do not 
	 * contain the delimiting text <tt>separator</tt> used to split the string. However, 
	 * if <tt>separator</tt> is a regular expression that contains parenthesized 
	 * subexpressions, the substrings that match those parenthesized subexpressions 
	 * (but not the text that matches the regular expression as a whole) are included in 
	 * the returned array.</p>
	 * <p>Note that this method is the inverse of the {@link CArray#join()} or 
	 * {@link CArray#join(String)} method.</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * separator</tt>, The string or regular expression at which the current string splits.</li><li><tt>
	 * limit</tt>, This optional integer specifies the maximum length of the returned 
	 * array. If defined, no more than this number of substrings will be returned. 
	 * If undefined, the entire string will be split, regardless of its length.</li></ul>
	 * @return An array of strings, created by splitting string into substrings at the 
	 * boundaries specified by <tt>separator</tt>. The substrings in the returned 
	 * array do not include <tt>separator</tt> itself, except in the case noted in the 
	 * above description.
	 * @see #split(Object)
	 * @see #split(Object, Object)
	 * @see CArray#join()
	 * @see CArray#join(String)
	 * @since Descripter 1.0
	 */
	public final CArray split(CArray args) {
		if (args.length() > 1) {
			return split(args.get(0), args.get(1));
		}
		return split(args.get(0));
	}

	/**
	 * <p>Creates and returns an array of substrings of the current string. These 
	 * substrings are created by searching the string from start to end for text that 
	 * matches <tt>separator</tt> and breaking the string before and after that 
	 * matching text. The <tt>separator</tt> text is not included in any of the 
	 * returned substrings, except as noted at the end of this section. Note that if the 
	 * <tt>separator</tt> matches the beginning of the string, the first element of 
	 * the returned array will be an empty string, the text that appears before the 
	 * <tt>separator</tt>. Similarly, if the <tt>separator</tt> matches the end of 
	 * the string, the last element of the array will be the empty string.</p>
	 * <p>If <tt>separator</tt> is undefined, the current string is not split at all, 
	 * and the returned array contains only a single, unbroken string element. If 
	 * <tt>separator</tt> is the empty string or a regular expression that matches 
	 * the empty string, the string is broken between each character, and the returned 
	 * array has the same length as the string does. Note that this is a special case 
	 * because the empty strings before the first character and after the last character 
	 * are not matched.</p>
	 * <p>As noted earlier, the substrings in the array returned by this invocation do not 
	 * contain the delimiting text <tt>separator</tt> used to split the string. However, 
	 * if <tt>separator</tt> is a regular expression that contains parenthesized 
	 * subexpressions, the substrings that match those parenthesized subexpressions 
	 * (but not the text that matches the regular expression as a whole) are included in 
	 * the returned array.</p>
	 * <p>Note that this method is the inverse of the {@link CArray#join()} or 
	 * {@link CArray#join(String)} method.</p>
	 * @param separator The string or regular expression at which the current string splits.
	 * @return An array of strings, created by splitting string into substrings at the 
	 * boundaries specified by <tt>separator</tt>. The substrings in the returned 
	 * array do not include <tt>separator</tt> itself, except in the case noted in the 
	 * above description.
	 * @see #split(CArray)
	 * @see #split(Object, Object)
	 * @see CArray#join()
	 * @see CArray#join(String)
	 * @since Descripter 1.0
	 */
	public final CArray split(Object separator) {
		separator = valueOf(separator);
		return separator instanceof CRegExp ?
				core().array((Object[])((CRegExp)separator).pattern().split(value)) :
				core().array((Object[])value.split(toString(separator)));
	}

	/**
	 * <p>Creates and returns an array of as many as <tt>limit</tt> substrings of the 
	 * current string. These substrings are created by searching the string from start to 
	 * end for text that matches <tt>separator</tt> and breaking the string before and 
	 * after that matching text. The <tt>separator</tt> text is not included in any of 
	 * the returned substrings, except as noted at the end of this section. Note that if 
	 * the <tt>separator</tt> matches the beginning of the string, the first element 
	 * of the returned array will be an empty string, the text that appears before the 
	 * <tt>separator</tt>. Similarly, if the <tt>separator</tt> matches the end of 
	 * the string, the last element of the array (assuming no conflicting <tt>limit</tt>) 
	 * will be the empty string.</p>
	 * <p>If <tt>separator</tt> is undefined, the current string is not split at all, 
	 * and the returned array contains only a single, unbroken string element. If 
	 * <tt>separator</tt> is the empty string or a regular expression that matches 
	 * the empty string, the string is broken between each character, and the returned 
	 * array has the same length as the string does, assuming no smaller <tt>limit</tt> 
	 * is specified. Note that this is a special case because the empty strings before 
	 * the first character and after the last character are not matched.</p>
	 * <p>As noted earlier, the substrings in the array returned by this invocation do not 
	 * contain the delimiting text <tt>separator</tt> used to split the string. However, 
	 * if <tt>separator</tt> is a regular expression that contains parenthesized 
	 * subexpressions, the substrings that match those parenthesized subexpressions 
	 * (but not the text that matches the regular expression as a whole) are included in 
	 * the returned array.</p>
	 * <p>Note that this method is the inverse of the {@link CArray#join()} or 
	 * {@link CArray#join(String)} method.</p>
	 * @param separator The string or regular expression at which the current string splits.
	 * @param limit This optional integer specifies the maximum length of the returned 
	 * array. If defined, no more than this number of substrings will be returned. 
	 * If undefined, the entire string will be split, regardless of its length.
	 * @return An array of strings, created by splitting string into substrings at the 
	 * boundaries specified by <tt>separator</tt>. The substrings in the returned 
	 * array do not include <tt>separator</tt> itself, except in the case noted in the 
	 * above description.
	 * @see #split(CArray)
	 * @see #split(Object)
	 * @see CArray#join()
	 * @see CArray#join(String)
	 * @since Descripter 1.0
	 */
	public final CArray split(Object separator, Object limit) {
		int lim = intValue(limit);
		separator = valueOf(separator);
		return separator instanceof CRegExp ?
				core().array((Object[])((CRegExp)separator).pattern().split(value, lim)) :
				core().array((Object[])value.split(toString(separator), lim));
	}

	/**
	 * <p>Extracts and returns a substring of the current string without modifying it.</p>
	 * <p>Note this method specifies the desired substring with a character position and a 
	 * <tt>length</tt>. This provides a useful alternative to 
	 * {@link #substring(Object, Object)}, which specify a substring with two 
	 * character positions. Note, however, that this method has not been standardized by 
	 * ECMAScript and is therefore deprecated</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * start</tt>, The start position of the substring. If this argument is negative, it 
	 * specifies a position measured from the end of the string: -1 specifies the last character, 
	 * -2 specifies the second-to-last character, and so on.</li><li><tt>
	 * length</tt>, The number of characters in the substring. If this argument is undefined, 
	 * the returned substring includes all characters from the starting position to the end of 
	 * the string.</li></ul>
	 * @return A copy of the portion of the current string starting at and including the character 
	 * specified by <tt>start</tt> and continuing for <tt>length</tt> characters, 
	 * or to the end of the string if <tt>length</tt> is undefined.
	 * @see #substr(Object)
	 * @see #substr(Object, Object)
	 * @see #slice(CArray)
	 * @see #slice(Object)
	 * @see #slice(Object, Object)
	 * @see #substring(CArray)
	 * @see #substring(Object)
	 * @see #substring(Object, Object)
	 * @since Descripter 1.0
	 */
	public final String substr(CArray args) {
		if (args.length() > 1) {
			return substr(args.get(0), args.get(1));
		}
		return substr(args.get(0));
	}

	/**
	 * <p>Extracts and returns a substring of the current string without modifying it.</p>
	 * <p>Note that this method has not been standardized by ECMAScript and is therefore 
	 * deprecated</p>
	 * @param start The start position of the substring. If this argument is negative, it 
	 * specifies a position measured from the end of the string: -1 specifies the last character, 
	 * -2 specifies the second-to-last character, and so on.
	 * @return A copy of the portion of the current string starting at and including the character 
	 * specified by <tt>start</tt> to the end of the string.
	 * @see #substr(CArray)
	 * @see #substr(Object, Object)
	 * @see #slice(CArray)
	 * @see #slice(Object)
	 * @see #slice(Object, Object)
	 * @see #substring(CArray)
	 * @see #substring(Object)
	 * @see #substring(Object, Object)
	 * @since Descripter 1.0
	 */
	public final String substr(Object start) {
		int from = intValue(start);
		from = from < 0 ? length() + from : from;
		return value.substring(from);
	}

	/**
	 * <p>Extracts and returns a substring of the current string without modifying it.</p>
	 * <p>Note this method specifies the desired substring with a character position and a 
	 * <tt>length</tt>. This provides a useful alternative to 
	 * {@link #substring(Object, Object)}, which specify a substring with two 
	 * character positions. Note, however, that this method has not been standardized by 
	 * ECMAScript and is therefore deprecated</p>
	 * @param start The start position of the substring. If this argument is negative, it 
	 * specifies a position measured from the end of the string: -1 specifies the last character, 
	 * -2 specifies the second-to-last character, and so on.
	 * @param length The number of characters in the substring. If this argument is undefined, 
	 * the returned substring includes all characters from the starting position to the end of 
	 * the string.
	 * @return A copy of the portion of the current string starting at and including the character 
	 * specified by <tt>start</tt> and continuing for <tt>length</tt> characters, 
	 * or to the end of the string if <tt>length</tt> is undefined.
	 * @see #substr(CArray)
	 * @see #substr(Object)
	 * @see #slice(CArray)
	 * @see #slice(Object)
	 * @see #slice(Object, Object)
	 * @see #substring(CArray)
	 * @see #substring(Object)
	 * @see #substring(Object, Object)
	 * @since Descripter 1.0
	 */
	public final String substr(Object start, Object length) {
		int from = intValue(start);
		from = from < 0 ? length() + from : from;
		int to = from + intValue(length);
		return value.substring(from, to);
	}

	/**
	 * <p>Returns a substring of the current string consisting of the characters between 
	 * positions <tt>from</tt> and <tt>to</tt>. The character at position <tt>from</tt> 
	 * is included, but the character at position <tt>to</tt> is not included.</p>
	 * <p>If <tt>from</tt> equals <tt>to</tt>, this method returns an empty 
	 * (length 0) string. If <tt>from</tt> is greater than <tt>to</tt>, this method 
	 * first swaps the two arguments and then returns the substring between them.</p>
	 * <p>It is important to remember that the character at position <tt>from</tt> is 
	 * included in the substring but that the character at position <tt>to</tt> is 
	 * not included in the substring. While this may seem arbitrary or counter-intuitive, 
	 * a notable feature of this system is that the length of the returned substring is 
	 * always equal to <tt>to - from</tt>.</p>
	 * <p>Note that {@link #slice(Object, Object)} and the nonstandard 
	 * {@link #substr(Object, Object)} can also extract substrings from a string. 
	 * Unlike those methods, this method does not accept negative arguments.</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * from</tt>, A nonnegative integer that specifies the position within the current 
	 * string of the first character of the desired substring.</li><li><tt>
	 * to</tt>, A nonnegative optional integer that is one greater than the position of 
	 * the last character of the desired substring. If this argument is undefined, the 
	 * returned substring runs to the end of the string.</li></ul>
	 * @return A new string, of length <tt>to - from</tt>, which contains a substring 
	 * of the current string. The new string contains characters copied from positions 
	 * <tt>from</tt> to <tt>to</tt> - 1 of the string.
	 * @see #substring(Object)
	 * @see #substring(Object, Object)
	 * @see #charAt(Object)
	 * @see #indexOf(CArray)
	 * @see #indexOf(Object)
	 * @see #indexOf(Object, Object)
	 * @see #lastIndexOf(CArray)
	 * @see #lastIndexOf(Object)
	 * @see #lastIndexOf(Object, Object)
	 * @see #slice(CArray)
	 * @see #slice(Object)
	 * @see #slice(Object, Object)
	 * @see #substr(CArray)
	 * @see #substr(Object)
	 * @see #substr(Object, Object)
	 * @since Descripter 1.0
	 */
	public final String substring(CArray args) {
		if (args.length() > 1) {
			return substring(args.get(0), args.get(1));
		}
		return substring(args.get(0));
	}

	/**
	 * <p>Returns a substring of the current string consisting of the characters from 
	 * position <tt>from</tt> to the end of the string. The character at position 
	 * <tt>from</tt> is included.</p>
	 * <p>It is important to remember that the character at position <tt>from</tt> is 
	 * included in the substring.</p>
	 * <p>Note that {@link #slice(Object)} and the nonstandard 
	 * {@link #substr(Object)} can also extract substrings from a string. 
	 * Unlike those methods, this method does not accept negative arguments.</p>
	 * @param from A nonnegative integer that specifies the position within the current 
	 * string of the first character of the desired substring.
	 * @return  A substring of the current string containing characters copied from 
	 * position <tt>from</tt> to the end of the current string.
	 * @see #substring(CArray)
	 * @see #substring(Object, Object)
	 * @see #charAt(Object)
	 * @see #indexOf(CArray)
	 * @see #indexOf(Object)
	 * @see #indexOf(Object, Object)
	 * @see #lastIndexOf(CArray)
	 * @see #lastIndexOf(Object)
	 * @see #lastIndexOf(Object, Object)
	 * @see #slice(CArray)
	 * @see #slice(Object)
	 * @see #slice(Object, Object)
	 * @see #substr(CArray)
	 * @see #substr(Object)
	 * @see #substr(Object, Object)
	 * @since Descripter 1.0
	 */
	public final String substring(Object from) {
		return value.substring(intValue(from));
	}

	/**
	 * <p>Returns a substring of the current string consisting of the characters between 
	 * positions <tt>from</tt> and <tt>to</tt>. The character at position <tt>from</tt> 
	 * is included, but the character at position <tt>to</tt> is not included.</p>
	 * <p>If <tt>from</tt> equals <tt>to</tt>, this method returns an empty 
	 * (length 0) string. If <tt>from</tt> is greater than <tt>to</tt>, this method 
	 * first swaps the two arguments and then returns the substring between them.</p>
	 * <p>It is important to remember that the character at position <tt>from</tt> is 
	 * included in the substring but that the character at position <tt>to</tt> is 
	 * not included in the substring. While this may seem arbitrary or counter-intuitive, 
	 * a notable feature of this system is that the length of the returned substring is 
	 * always equal to <tt>to - from</tt>.</p>
	 * <p>Note that {@link #slice(Object, Object)} and the nonstandard 
	 * {@link #substr(Object, Object)} can also extract substrings from a string. 
	 * Unlike those methods, this method does not accept negative arguments.</p>
	 * @param from A nonnegative integer that specifies the position within the current 
	 * string of the first character of the desired substring.
	 * @param to A nonnegative optional integer that is one greater than the position of 
	 * the last character of the desired substring. If this argument is undefined, the 
	 * returned substring runs to the end of the string.
	 * @return A new string, of length <tt>to - from</tt>, which contains a substring 
	 * of the current string. The new string contains characters copied from positions 
	 * <tt>from</tt> to <tt>to</tt> - 1 of the string.
	 * @see #substring(CArray)
	 * @see #substring(Object)
	 * @see #charAt(Object)
	 * @see #indexOf(CArray)
	 * @see #indexOf(Object)
	 * @see #indexOf(Object, Object)
	 * @see #lastIndexOf(CArray)
	 * @see #lastIndexOf(Object)
	 * @see #lastIndexOf(Object, Object)
	 * @see #slice(CArray)
	 * @see #slice(Object)
	 * @see #slice(Object, Object)
	 * @see #substr(CArray)
	 * @see #substr(Object)
	 * @see #substr(Object, Object)
	 * @since Descripter 1.0
	 */
	public final String substring(Object from, Object to) {
		return value.substring(
				intValue(from),
				intValue(to  )
		);
	}

	/**
	 * <p>Converts the current object instance to a string, localized as appropriate 
	 * for the current locale.</p>
	 * @return A string representing the current object instance, localized as 
	 * appropriate for the current locale.
	 * @since Descripter 1.0
	 */
	@Override
	public final String toLocaleString() {
		return value.toString();
	}

	/**
	 * <p>Returns a copy of the current string, converted to lower-case letters in a 
	 * locale-specific way. Only a few languages, such as Turkish, have locale-specific 
	 * case mappings, so this method usually returns the same value as 
	 * {@link #toLowerCase()}.</p>
	 * @return A copy of the current string, converted to lower-case letters in a 
	 * locale-specific way.
	 * @see #toLocaleUpperCase()
	 * @see #toLowerCase()
	 * @see #toUpperCase()
	 * @since Descripter 1.0
	 */
	public final String toLocaleLowerCase() {
		return value.toLowerCase();
	}

	/**
	 * <p>Returns a copy of the current string, converted to upper-case letters in a 
	 * locale-specific way. Only a few languages, such as Turkish, have locale-specific 
	 * case mappings, so this method usually returns the same value as 
	 * {@link #toUpperCase()}.</p>
	 * @return A copy of the current string, converted to upper-case letters in a 
	 * locale-specific way.
	 * @see #toLocaleLowerCase()
	 * @see #toLowerCase()
	 * @see #toUpperCase()
	 * @since Descripter 1.0
	 */
	public final String toLocaleUpperCase() {
		return value.toUpperCase();
	}

	/**
	 * <p>Returns a copy of string, with each upper-case letter converted to its lower-case 
	 * equivalent, if it has one.</p>
	 * @return A copy of string, with each upper-case letter converted to its lower-case 
	 * equivalent, if it has one.
	 * @see #toLocaleLowerCase()
	 * @see #toLocaleUpperCase()
	 * @see #toUpperCase()
	 * @since Descripter 1.0
	 */
	public final String toLowerCase() {
		return value.toLowerCase(Locale.ENGLISH);
	}

	/**
	 * <p>Returns a copy of string, with each lower-case letter converted to its upper-case 
	 * equivalent, if it has one.</p>
	 * @return A copy of string, with each lower-case letter converted to its upper-case 
	 * equivalent, if it has one.
	 * @see #toLocaleLowerCase()
	 * @see #toLocaleUpperCase()
	 * @see #toLowerCase()
	 * @since Descripter 1.0
	 */
	public final String toUpperCase() {
		return value.toUpperCase(Locale.ENGLISH);
	}

	/**
	 * <p>Returns a string representation of the current object.</p>
	 * @return The string representation of the current object
	 * @since Descripter 1.0
	 */
	@Override
	public final String toString() {
		return value.toString();
	}

	/**
	 * <p>Returns the primitive value associated with this object, if there is one. </p>
	 * @return The primitive value associated with this object, if there is one, or this object itself.
	 * @since Descripter 1.0
	 */
	@Override
	public final String valueOf() {
		return value;
	}
}
