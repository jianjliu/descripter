
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

import java.util.AbstractList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.descripter.js.api.Function;
import org.descripter.js.api.Key;

/**
 * <p>Emulates JavaScript Array objects.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class CArray extends CObject
{
	/**
	 * <p>Constructs a {@link CObject} context of this type.</p>
	 * @param constructor The constructor {@link Function} object.
	 * @since Descripter 1.0
	 */
	public CArray(Function<?> constructor) {
		super(constructor);
	}

	/**
	 * <p>Constructs a {@link CObject} context of this type.</p>
	 * @param constructor The constructor {@link Function} object.
	 * @param length The length of the array being constructed
	 * @since Descripter 1.0
	 */
	public CArray(Function<?> constructor, int length) {
		this(constructor);
		length(length);
	}

	/**
	 * <p>Constructs a {@link CObject} context of this type.</p>
	 * @param constructor The constructor {@link Function} object.
	 * @param array An array of initial elements for the array being constructed
	 * @since Descripter 1.0
	 */
	public CArray(Function<?> constructor, Object ...array) {
		this(constructor, array.length);
		for (int i = 0; i < array.length; i++) {
			Object o = array[i];
			if (o != null) {
				super.put(i, o);
			}
		}
	}

	/**
	 * <p>Returns a string representation of the current object.</p>
	 * @return The string representation of the current object
	 * @since Descripter 1.0
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0, length = length(); i < length; i++) {
			if (i > 0) {
				sb.append(',');
			}
			Object o = get(i);
			if (o instanceof String) {
				sb.append('"');
				sb.append(o);
				sb.append('"');
				
			} else {
				sb.append(o);
			}
		}
		sb.append(']');
		return sb.toString();
	}

	/**
	 * <p>Returns the length of the current array.</p>
	 * @return The length of the current array
	 * @since Descripter 1.0
	 */
	public final int length() {
		return intValue(getNumber(core()._length));
	}

	/**
	 * <p>Sets the length of the current array.</p>
	 * @param length The new length for the current array
	 * @since Descripter 1.0
	 */
	public final void length(int length) {
		if (length < 0) {
			length = 0;
		}
		for (int i = length, len = length(); i < len; i++) {
			hide(i);
		}
		super.put(core()._length, length);
	}

	/**
	 * <p>Gets the <tt>callee</tt> {@link Function} property of the current array.</p>
	 * @return The <tt>callee</tt> {@link Function} property of the current array
	 * @since Descripter 1.0
	 */
	public final Function<?> callee() {
		return (Function<?>)get(core()._callee);
	}

	/**
	 * <p>Gets the <tt>index</tt> property of the current array.</p>
	 * @return The <tt>index</tt> property of the current array
	 * @since Descripter 1.0
	 */
	public final int index() {
		return getNumber(core()._index).intValue();
	}

	/**
	 * <p>Gets the <tt>input</tt> property of the current array.</p>
	 * @return The <tt>input</tt> property of the current array
	 * @since Descripter 1.0
	 */
	public final String input() {
		return getString(core()._index);
	}

	/**
	 * <p>Sets the value associated with the specified key.</p>
	 * @param key A {@link Key} to set the value
	 * @param val The value to set
	 * @throws RuntimeException if the current context is read-only.
	 * @since Descripter 1.0
	 */
	@Override
	public final void put(Key key, Object val) {
		if (key.equals(core()._length)) {
			length(intValue(val));
		} else {
			if (key.hashCode() >= length()) {
				length(key.hashCode() + 1);
			}
			super.put(key, val);
		}
	}

	/**
	 * <p>Creates and returns a new array object that is the result of concatenating the
	 * arguments to the current array instance. This invocation does not modify the current 
	 * array.</p>
	 * @param args An array of values to be concatenated with the current array.
	 * @return A new array object, which is formed by concatenating the specified arguments
	 * to the current array.
	 * @since Descripter 1.0
	 */
	public final CArray concat(CArray args) {
		CArray array = slice(0);
		for (int j = 0, length = args.length(); j < length; j++) {
			Object o = args.get(j);
			if (o instanceof CArray) {
				CArray a = (CArray)o;
				for (int i = 0, len = a.length(); i < len; i++) {
					array.put(array.length(), a.get(i));
				}
			} else {
				array.put(array.length(), o);
			}
		}
		return array;
	}

	/**
	 * <p>Converts each element of the current array instance to a string and then 
	 * concatenates those strings, inserting a comma between the elements and returns 
	 * the resulting string.</p>
	 * @return The string that results from converting each element of the current array
	 * to a string and then concatenating them together with a comma between elements.
	 * @see #join(String)
	 * @since Descripter 1.0
	 */
	public final String join() {
		return join("");
	}

	/**
	 * <p>Converts each element of the current array instance to a string and then 
	 * concatenates those strings, inserting the separator string specified by 
	 * <tt>separator</tt> between the elements and returns the resulting string.</p>
	 * @param separator An optional string used to separate one element of the current array
	 * from the next in the returned string. If this argument is omitted, 
	 * <tt>undefined</tt> or <tt>null</tt>, a comma is used.
	 * @return The string that results from converting each element of the current array
	 * to a string and then concatenating them together, with the <tt>separator</tt>
	 * string between elements.
	 * @see #join()
	 * @since Descripter 1.0
	 */
	public final String join(String separator) {
		if (separator == null) {
			return join();
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0, length = length(); i < length; i++) {
			if (i > 0) {
				sb.append(separator);
			}
			sb.append(get(i).toString());
		}
		return sb.toString();
	}

	/**
	 * <p>Deletes the last element of the current array instance, decrements the length of 
	 * the current array, and returns the value of the deleted element. If the current array is 
	 * already empty, this invocation does not change the array and returns the undefined <tt>null</tt> value.</p>
	 * @return The last element of the current array.
	 * @since Descripter 1.0
	 */
	public final Object pop() {
		int last = length() - 1;
		Object o = get(last);
		length(last);
		return o;
	}

	/**
	 * <p>Appends the arguments to the end of the current array instance by modifying the 
	 * array directly rather than creating a new one.</p>
	 * @param args An array of values to be appended to the end of the current array.
	 * @return The new length of the array, after the specified value are appended to it.
	 * @since Descripter 1.0
	 */
	public final int push(CArray args) {
		for (int i = 0, length = args.length(); i < length; i++) {
			put(length(), args.get(i));
		}
		return length();
	}

	/**
	 * <p>Reverses the order of the elements of the current array instance by rearranging 
	 * them in place without creating a new array. If there are multiple references to the 
	 * array, the new order of the array elements is visible through all references after 
	 * this invocation.</p>
	 * @return The reversed array
	 * @since Descripter 1.0
	 */
	public final CArray reverse() {
		CArray array = new CArray(constructor);
		for (int i = length() - 1; i >= 0; i--) {
			array.put(array.length(), get(i));
		}
		return array;
	}

	private final void move(int start, int count) {
		int length = length();
		if (count < 0) {
			for (int i = start, j = i - count; i < length; i++, j++) {
				put(i, get(j));
			}
			length(length - count);
		} else if (count > 0) {
			for (int i = length - 1, j = i + count; i >= start; i--, j--) {
				put(j, get(i));
			}
		}
	}

	/**
	 * <p>Removes and returns the first element of the current array instance, shifting 
	 * all subsequent elements down one place to occupy the newly vacant space at the 
	 * start of the array. If the current array is empty, this invocation does nothing 
	 * and returns the undefined value <tt>null</tt>. Note that this invocation does 
	 * not create a new array; instead, it modifies the current array directly.</p>
	 * @return The former first element of the current array.
	 * @see #pop()
	 * @since Descripter 1.0
	 */
	public final Object shift() {
		Object o = get(0);
		move(1, -1);
		return o;
	}

	/**
	 * <p>Returns a slice, or sub-array, of the current array instance without modifying 
	 * it. The returned array contains the element positioned by the first value of the 
	 * argument list and all subsequent elements up to, but not including, the element 
	 * positioned by the second value of the argument list. If the second value is not 
	 * specified or undefined, the returned array contains all elements from the position 
	 * specified by the first value to the end of the current array.</p>
	 * @param args A list of the argument values. The first value specifies the array 
	 * index at which the slice is to begin. If this value is negative, it specifies a 
	 * position measured from the end of the current array. That is, -1 indicates the 
	 * last element, -2 indicates the next from the last element, and so on. The second 
	 * value specifies the array index immediately after the end of the slice. If it is 
	 * undefined or not specified, the slice includes all array elements from the position 
	 * specified by the first value to the end of the array. If the second value is 
	 * negative, it specifies the array element measured from the end of the array.
	 * @return A new array that contains the elements of current array instance from the 
	 * element positioned by the first value of <tt>args</tt>, up to, but not including, 
	 * the element positioned by the second value of <tt>args</tt>.
	 * @see #slice(int)
	 * @see #slice(int, int)
	 * @see #splice(CArray)
	 * @see #splice(int)
	 * @see #splice(int, int)
	 * @see #splice(int, int, CArray)
	 * @since Descripter 1.0
	 */
	public final CArray slice(CArray args) {
		switch (args.length()) {
			case 1:
				return slice(intValue(args.get(0)));
			case 2:
				return slice(intValue(args.get(0)), intValue(args.get(1)));
			default:
				return null;
		}
	}

	/**
	 * <p>Returns a slice, or sub-array, of the current array instance without modifying 
	 * it. The returned array contains the element positioned by <tt>start</tt> and 
	 * all subsequent elements up to the end of the current array.</p>
	 * @param start The array index at which the slice is to begin. If negative, this 
	 * argument specifies a position measured from the end of the current array. That is, 
	 * -1 indicates the last element, -2 indicates the next from the last element, and so on.
	 * @return A new array that contains the elements of current array instance from the 
	 * element positioned by <tt>start</tt>, up to the end of the current array.
	 * @see #slice(CArray)
	 * @see #slice(int, int)
	 * @see #splice(CArray)
	 * @see #splice(int)
	 * @see #splice(int, int)
	 * @see #splice(int, int, CArray)
	 * @since Descripter 1.0
	 */
	public final CArray slice(int start) {
		return slice(start, length());
	}

	/**
	 * <p>Returns a slice, or sub-array, of the current array instance without modifying 
	 * it. The returned array contains the element positioned by <tt>start</tt> and 
	 * all subsequent elements up to, but not including, the element positioned by 
	 * <tt>end</tt>. If <tt>end</tt> is an undefined value, the returned array 
	 * contains all elements from the <tt>start</tt> to the end of the current array.</p>
	 * @param start The array index at which the slice is to begin. If negative, this 
	 * argument specifies a position measured from the end of the current array. That is, 
	 * -1 indicates the last element, -2 indicates the next from the last element, and so on.
	 * @param end The array index immediately after the end of the slice. If undefined, 
	 * the slice includes all array elements from the <tt>start</tt> to the end 
	 * of the array. If this argument is negative, it specifies an array element measured 
	 * from the end of the array.
	 * @return A new array that contains the elements of current array instance from the 
	 * element positioned by <tt>start</tt>, up to, but not including, the element 
	 * positioned by <tt>end</tt>.
	 * @see #slice(CArray)
	 * @see #slice(int)
	 * @see #splice(CArray)
	 * @see #splice(int)
	 * @see #splice(int, int)
	 * @see #splice(int, int, CArray)
	 * @since Descripter 1.0
	 */
	public final CArray slice(int start, int end) {
		int length = length();
		if (start < 0) {
			start += length;
		}
		if (end < 0) {
			end += length;
		}
		CArray array = new CArray(constructor);
		for (int i = start, j = 0; i < end; i++, j++) {
			if (has(i)) {
				array.put(j, get(i));
			}
		}
		return array;
	}

	/**
	 * <p>Sorts the elements of the current array instance in place by arranging them in 
	 * alphabetical order (more precisely, the order determined by the character encoding).
	 * To do this, elements are first converted to strings, if necessary, so that they can 
	 * be compared. Note that the array is sorted in place, and no copy is made.
	 * And undefined elements are always sorted to the end of the array.</p>
	 * @return A reference to the current array.
	 * @see #sort(Function)
	 * @since Descripter 1.0
	 */
	public final CArray sort() {
		Collections.sort(list());
		return this;
	}

	private List<Element> list() {
		return new AbstractList<Element>() {
			@Override
			public Element get(int index) {
				return new Element(CArray.this.get(index));
			}

			@Override
			public Element set(int index, Element element) {
				CArray.this.put(index, element.value);
				return element;
			}

			@Override
			public int size() {
				return CArray.this.length();
			}
		};
	}

	private static class Element implements Comparable<Element>
	{
		public final Object value;
	
		public Element(Object value) {
			this.value = value;
		}

		@Override
		public int compareTo(Element e) {
			return toString().compareTo(e.toString());
		}

		@Override
		public String toString() {
			return CArray.toString(value);
		}
	}

	/**
	 * <p>Sorts the elements of the current array instance with the custom ordering function 
	 * <tt>orderer</tt>. Note that the array is sorted in place, and no copy is made.
	 * And undefined elements are always sorted to the end of the array because undefined 
	 * values are never passed to the ordering function you supply.</p>
	 * @param orderer A comparison function that compares two values and returns a 
	 * number indicating their relative order. This function should take two arguments, 
	 * <tt>a</tt> and <tt>b</tt> for instance, and should return one of the following:
	 * <ul>
	 * <li>A value less than zero, if, according to your sort criteria, <tt>a</tt> is 
	 * less than <tt>b</tt> and should appear before <tt>b</tt> in the sorted 
	 * array.</li>
	 * <li>Zero, if <tt>a</tt> and <tt>b</tt> are equivalent for the purposes of 
	 * this sort.</li>
	 * <li>A value greater than zero, if <tt>a</tt> is greater than <tt>b</tt> 
	 * for the purposes of the sort.</li>
	 * </ul>
	 * @return A reference to the current array.
	 * @see #sort()
	 * @since Descripter 1.0
	 */
	public final CArray sort(final Function<?> orderer) {
		if (orderer == null) {
			return sort();
		}
		Collections.sort(
				list(),
				new Comparator<Element>() {
					@Override
					public int compare(Element e1, Element e2) {
						return intValue(core().call(orderer, e1.value, e2.value));
					}
				}
		);
		return this;
	}

	/**
	 * <p>Deletes elements, numbered by the second value of <tt>args</tt>, starting with and 
	 * including the element positioned by the first value of <tt>args</tt>, and replaces 
	 * them with the values listed by <tt>args</tt> from the third value. Array elements 
	 * that appear after the insertion or deletion are moved as necessary so that they 
	 * remain contiguous with the rest of the array.</p> 
	 * <p>Note that, this invocation modifies the current array directly.</p>
	 * @param args A list of the argument values. The first value specifies the array 
	 * index at which the deletion and insertion is to begin. The second value specifies 
	 * the number of elements, starting with and including the element positioned by the 
	 * first value, to be deleted from the current array. If the second value is undefined, 
	 * this invocation deletes all elements from the position specified by the first value 
	 * to the end of the array. The rest of the list provides the values to be inserted 
	 * into the current array, beginning at the position specified by the first value.
	 * @return An array containing the elements, if any, deleted from the current array.
	 * @see #slice(CArray)
	 * @see #slice(int)
	 * @see #slice(int, int)
	 * @see #splice(int)
	 * @see #splice(int, int)
	 * @see #splice(int, int, CArray)
	 * @since Descripter 1.0
	 */
	public final CArray splice(CArray args) {
		switch (args.length()) {
			case 1:
				return splice(intValue(args.get(0)));
			case 2:
				return splice(intValue(args.get(0)), intValue(args.get(1)));
			case 3:
				return splice(intValue(args.get(0)), intValue(args.get(1)), (CArray)args.get(2));
			default:
				return null;
		}
	}

	/**
	 * <p>Deletes zero or more array elements starting with and including the element 
	 * positioned by <tt>start</tt>.</p>
	 * <p>Note that, this invocation modifies the current array directly.</p>
	 * @param start The array index at which the insertion and/or deletion is to begin.
	 * @return An array containing the elements, if any, deleted from the current array.
	 * @see #slice(CArray)
	 * @see #slice(int)
	 * @see #slice(int, int)
	 * @see #splice(CArray)
	 * @see #splice(int, int)
	 * @see #splice(int, int, CArray)
	 * @since Descripter 1.0
	 */
	public final CArray splice(int start) {
		return splice(start, length());
	}

	/**
	 * <p>Deletes <tt>deleteCount</tt> elements of the current array instance starting 
	 * with and including the element positioned by <tt>start</tt>. Array elements 
	 * that appear after deletion are moved as necessary so that they remain contiguous 
	 * with the rest of the array.</p> 
	 * <p>Note that, this invocation modifies the current array directly.</p>
	 * @param start The array index at which the deletion is to begin.
	 * @param deleteCount The number of elements, starting with and including the element 
	 * positioned by <tt>start</tt>, to be deleted from the current array. If this 
	 * argument is undefined, this invocation deletes all elements from <tt>start</tt> to the end 
	 * of the array.
	 * @return An array containing the elements, if any, deleted from the current array.
	 * @see #slice(CArray)
	 * @see #slice(int)
	 * @see #slice(int, int)
	 * @see #splice(CArray)
	 * @see #splice(int)
	 * @see #splice(int, int, CArray)
	 * @since Descripter 1.0
	 */
	public final CArray splice(int start, int deleteCount) {
		CArray array = slice(start, start + deleteCount);
		move(start, - deleteCount);
		return array;
	}

	/**
	 * <p>Deletes <tt>deleteCount</tt> elements starting with and including the 
	 * element positioned by <tt>start</tt> and replaces them with the argument 
	 * <tt>values</tt>. Array elements that appear after the insertion or deletion are 
	 * moved as necessary so that they remain contiguous with the rest of the array.</p> 
	 * <p>Note that, this invocation modifies the current array directly.</p>
	 * @param start The array index at which the deletion and insertion is to begin.
	 * @param deleteCount The number of elements, starting with and including the element 
	 * positioned by <tt>start</tt>, to be deleted from the current array. If this 
	 * argument is undefined, this invocation deletes all elements from <tt>start</tt> to the end 
	 * of the array.
	 * @param values The array of values to be inserted into the current array, beginning at the index 
	 * specified by <tt>start</tt>.
	 * @return An array containing the elements, if any, deleted from the current array.
	 * @see #slice(CArray)
	 * @see #slice(int)
	 * @see #slice(int, int)
	 * @see #splice(CArray)
	 * @see #splice(int)
	 * @see #splice(int, int)
	 * @since Descripter 1.0
	 */
	public final CArray splice(int start, int deleteCount, CArray values) {
		CArray array = slice(start, start + deleteCount);
		move(start, values.length() - deleteCount);
		for (int i = start, j = 0; j < values.length(); i++, j++) {
			put(i, values.get(j));
		}
		return array;
	}

	/**
	 * <p>Inserts the arguments at the beginning of the current array instance, 
	 * shifting the existing elements to higher indexes to make room. The first argument becomes 
	 * the new element 0 of the array. Note that this invocation does not create a new 
	 * array; it modifies the current array directly.</p>
	 * @param values An array of values that are inserted at the start of the current array.
	 * @return The new length of the current array.
	 * @see #shift()
	 * @since Descripter 1.0
	 */
	public final int unshift(CArray values) {
		splice(0, 0, values);
		return length();
	}
}
