
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

import java.lang.reflect.Method;

import org.descripter.js.api.core.CObject;
import org.descripter.js.api.core.CString;

/**
 * <p>An abstract base class to represent JavaScript objective contexts.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public abstract class Objective<W extends Objective<?>> extends Context<Key, Object, W>
{
	/**
	 * <p>Constructs an {@link Objective} {@link Context} of this type.</p>
	 * @param with The containing {@link Objective} {@link Context}.
	 * @since Descripter 1.0
	 */
	protected Objective(W with) {
		super(with);
	}

	/**
	 * <p>Gets a string member of this {@link Objective} {@link Context}.</p>
	 * @param key The name {@link Key} of the member.
	 * @return The string value of the member specified by the {@link Key}.
	 * @since Descripter 1.0
	 */
	public String getString(Key key) {
		return (String)get(key);
	}

	/**
	 * <p>Gets a number member of this {@link Objective} {@link Context}.</p>
	 * @param key The name {@link Key} of the member.
	 * @return The number value of the member specified by the {@link Key}.
	 * @since Descripter 1.0
	 */
	public Number getNumber(Key key) {
		return (Number)get(key);
	}

	/**
	 * <p>Gets a name {@link Key} from this {@link Objective} {@link Context}.</p>
	 * @param name The name for the {@link Key} to get.
	 * @return The {@link Key} that has the specified name.
	 * @since Descripter 1.0
	 */
	public Key key(String name) {
		return Key.get(this, name);
	}

	/**
	 * <p>Sets the value associated with the specified key.</p>
	 * @param key A {@link Key} to set the value
	 * @param val The value to set
	 * @throws RuntimeException if the current context is read-only.
	 * @since Descripter 1.0
	 */
	@Override
	public void put(Key key, Object val) {
		super.put(key, evaluate(val));
	}

	/**
	 * <p>Sets the value associated with the specified key and returns the current 
	 * {@link Objective} {@link Context}.</p>
	 * @param k A {@link Key} to set the value
	 * @param o The value to set
	 * @return The current {@link Objective} {@link Context}
	 * @throws RuntimeException if the current context is read-only.
	 * @since Descripter 1.0
	 */
	public Objective<W> set(Key k, Object o) {
		put(k, o);
		return this;
	}

	/**
	 * <p>Sets the value associated with the specified index and returns the current 
	 * {@link Objective} {@link Context}.</p>
	 * @param i An index to set the value
	 * @param o The value to set
	 * @return The current {@link Objective} {@link Context}
	 * @throws RuntimeException if the current context is read-only.
	 * @since Descripter 1.0
	 */
	public Objective<W> set(Integer i, Object o) {
		return set(key(i.toString()), o);
	}

	/**
	 * <p>Returns the value associated with the specified index.</p>
	 * @param i An index to lookup
	 * @return The value associated with the specified index or <tt>null</tt> for none.
	 * @since Descripter 1.0
	 */
	public Object get(Integer i) {
		return get(key(i.toString()));
	}

	/**
	 * <p>Tells if the specified index is visible from the current context.</p>
	 * @param i An index to test
	 * @return <tt>true</tt> if the index is visible from the current context; <tt>false</tt>, otherwise.
	 * @since Descripter 1.0
	 */
	public boolean has(Integer i) {
		return has(key(i.toString()));
	}

	/**
	 * <p>Hides the specified index from the current context if it is visible.</p>
	 * <p>Note that this method does nothing if the index is not visible.</p>
	 * @param i An index to remove
	 * @throws RuntimeException if the current context is read-only.
	 * @since Descripter 1.0
	 */
	public void hide(Integer i) {
		hide(key(i.toString()));
	}

	/**
	 * <p>Sets the value associated with the specified index.</p>
	 * @param i An index to set the value
	 * @param v The value to set
	 * @throws RuntimeException if the current context is read-only.
	 * @since Descripter 1.0
	 */
	public void put(Integer i, Object v) {
		put(key(i.toString()), evaluate(v));
	}

	/**
	 * <p>Returns a {@link Var} specified by a given index.</p>
	 * @param i The index of the {@link Var} to get
	 * @return The {@link Var} specified by the index
	 * @since Descripter 1.0
	 */
	public final Var var(Integer i) {
		return new Var(this, key(i.toString()));
	}

	/**
	 * <p>Returns a {@link Var} specified by a given {@link Key}.</p>
	 * @param k The {@link Key} of the {@link Var} to get
	 * @return The {@link Var} specified by the {@link Key}
	 * @since Descripter 1.0
	 */
	public final Var var(Key k) {
		return new Var(this, k);
	}

	/**
	 * <p>Evaluates the argument.</p>
	 * <p>If the argument is not an instance of {@link Value}, this method simply returns the argument itself. 
	 * Otherwise, it calls the {@link Value#evaluate()} method at the argument and returns the result.</p>
	 * @param o The object argument to evaluate
	 * @return The evaluation result or the argument itself
	 * @since Descripter 1.0
	 */
	public static final Object evaluate(Object o) {
		return o instanceof Value ? ((Value)o).evaluate() : o;
	}

	/**
	 * <p>Returns the primitive value associated with the argument, if there is one. </p>
	 * <p>This method first evaluates the argument. If the evaluation is not an instance of {@link CObject}, 
	 * it simply returns the evaluation of the argument. Otherwise, it calls the {@link CObject#valueOf()} method at 
	 * the evaluation and returns the result.</p>
	 * @param o The object argument to get value of
	 * @return The value of the object argument
	 * @since Descripter 1.0
	 */
	public static final Object valueOf(Object o) {
		o = evaluate(o);
		return o instanceof CObject ? ((CObject)o).valueOf() : o;
	}

	/**
	 * <p>Logically evaluates an object argument.</p>
	 * @param o The object argument to get boolean value of
	 * @return The boolean value of the argument
	 * @since Descripter 1.0
	 */
	public static final boolean bool(Object o) {
		o = valueOf(o);
		return o instanceof Boolean ? (Boolean)o :
		       o instanceof String ? ((String)o).length() > 0 :
		       o instanceof Character ? ((Character)o).charValue() > 0 :
		       o instanceof Number ? ((Number)o).doubleValue() != 0D : o != null;
	}

	/**
	 * <p>Numerically evaluates an object argument.</p>
	 * @param o The object argument to get number value of
	 * @return The number value of the argument
	 * @since Descripter 1.0
	 */
	public static final Number toNumber(Object o) {
		o = valueOf(o);
		return o == null ? 0 :
			o instanceof Character ? (int)((Character)o).charValue() :
			o instanceof Boolean ? ((Boolean)o ? 1 : 0) :
			o instanceof String && ((String)o).length() < 1 ? 0 : (Number)o;
	}

	/**
	 * <p>Gets the integer value of an object.</p>
	 * @param o The object to get integer value of
	 * @return The integer value of the object
	 * @since Descripter 1.0
	 */
	public static final int intValue(Object o) {
		return toNumber(o).intValue();
	}

	/**
	 * <p>Gets the double value of an object.</p>
	 * @param o The object to get double value of
	 * @return The double value of the object
	 * @since Descripter 1.0
	 */
	public static final double doubleValue(Object o) {
		return toNumber(o).doubleValue();
	}

	/**
	 * <p>Gets the string value of an object.</p>
	 * @param o The object to get string value of
	 * @return The string value of the object
	 * @since Descripter 1.0
	 */
	public static final String toString(Object o) {
		return o == null ? "undefined" : o.toString();
	}

	/**
	 * <p>The id for the type of <tt>undefined</tt>.</p>
	 * @since Descripter 1.0
	 */
	public final static int UNDEFINED = 0;
	/**
	 * <p>The id for the type of <tt>function</tt>.</p>
	 * @since Descripter 1.0
	 */
	public final static int FUNCTION  = 1;
	/**
	 * <p>The id for the type of <tt>object</tt>.</p>
	 * @since Descripter 1.0
	 */
	public final static int OBJECT    = 2;
	/**
	 * <p>The id for the type of <tt>string</tt>.</p>
	 * @since Descripter 1.0
	 */
	public final static int STRING    = 3;
	/**
	 * <p>The id for the type of <tt>number</tt>.</p>
	 * @since Descripter 1.0
	 */
	public final static int NUMBER    = 4;
	/**
	 * <p>The id for the type of <tt>boolean</tt>.</p>
	 * @since Descripter 1.0
	 */
	public final static int BOOLEAN   = 5;
	/**
	 * <p>The id for the type of <tt>unknown</tt>.</p>
	 * @since Descripter 1.0
	 */
	public final static int UNKNOWN   = 6;

	/**
	 * <p>Returns the type id of an object value.</p>
	 * @param o An object value
	 * @return The type id of the object value
	 * @since Descripter 1.0
	 */
	public static final int typeOf(Object o) {
		o = evaluate(o);
		if (o instanceof Function) {
			return FUNCTION;
		} else if (o instanceof CObject) {
			return OBJECT;
		} else if (o instanceof String) {
			return STRING;
		} else if (o instanceof Number) {
			return NUMBER;
		} else if (o instanceof Boolean) {
			return BOOLEAN;
		} else if (o instanceof Method) {
			return FUNCTION;
		} else if (o instanceof Character) {
			return NUMBER;
		} else if (o == null) {
			return UNDEFINED;
		} else {
			return UNKNOWN;
		}
	}

	/**
	 * <p>Returns a string indicating the data-type of the argument.</p>
	 * <p>Emulating the JavaScript <tt>typeof</tt> operator and <tt>typeof()</tt> 
	 * function, this invocation evaluates to "number", "string", or "boolean" if the  
	 * argument is a number, string, or boolean value. It evaluates to "object" for objects 
	 * and arrays. It evaluates to "function" for function instance and to "undefined" if 
	 * the argument is undefined.</p>
	 * @param o Any value or object.
	 * @return A string indicating the data-type of the argument.
	 * @since Descripter 1.0
	 */
	public static final String typeof(Object o) {
		switch (typeOf(o)) {
			case UNDEFINED:
				return "undefined";
			case OBJECT:
				return "object";
			case FUNCTION:
				return "function";
			case BOOLEAN:
				return "boolean";
			case NUMBER:
				return "number";
			case STRING:
				return "string";
			default:
				return "unknown";
		}
	}

	/**
	 * <p>Performs unary negation, resembling the unary minus operator in JavaScript.</p>
	 * <p>This operation converts a positive value to an equivalently negative value, and 
	 * vice versa. If the operand is not a number, it attempts to convert it to one.</p>
	 * @param a Any numeric value.
	 * @return The negation of the numeric value.
	 * @since Descripter 1.0
	 */
	public static final double neg(Object a) {
		return - doubleValue(a);
	}

	/**
	 * <p>Adds numeric operands, resembling the addition operator in JavaScript.</p>
	 * @param a A numeric value.
	 * @param b A numeric value.
	 * @return The sum of the values.
	 * @since Descripter 1.0
	 */
	public static final double add(Number a, Number b) {
		return doubleValue(a) + doubleValue(b);
	}

	/**
	 * <p>Adds numeric operands or concatenates string operands, resembling the addition 
	 * operator in JavaScript.</p>
	 * <p>If one operand is a string, the other is converted to a string, and the two 
	 * strings are then concatenated. Object operands are converted to numbers or strings 
	 * that can be added or concatenated. The conversion is performed by {@link #valueOf(Object)}  
	 * method and/or the {@link #toString(Object)} method on the object.</p>
	 * @param a A value or object.
	 * @param b A value or object.
	 * @return The sum or concatenation of the values.
	 * @since Descripter 1.0
	 */
	public static final Object add(Object a, Object b) {
		a = valueOf(a);
		b = valueOf(b);
		if (typeOf(a) == NUMBER && typeOf(b) == NUMBER) {
			return add((Number)a, (Number)b);
		} else {
			return toString(a).concat(toString(b));
		}
	}

	/**
	 * <p>Subtracts the second operand from the first operand, resembling the subtraction operator in JavaScript.</p>
	 * <p>If used with non-numeric operands, this operation attempts to convert them to numbers.</p>
	 * @param a Any numeric value.
	 * @param b Any numeric value.
	 * @return The difference between the operands.
	 * @since Descripter 1.0
	 */
	public static final double sub(Object a, Object b) {
		return doubleValue(a) - doubleValue(b);
	}

	/**
	 * <p>Multiplies the two operands, resembling the multiplication operator in JavaScript.</p>
	 * <p>If used with non-numeric operands, this operation attempts to convert them to numbers.</p>
	 * @param a Any numeric value.
	 * @param b Any numeric value.
	 * @return The product of the two operands.
	 * @since Descripter 1.0
	 */
	public static final double mul(Object a, Object b) {
		return doubleValue(a) * doubleValue(b);
	}

	/**
	 * <p>Computes the first operand modulo the second operand, resembling the modulo 
	 * operator in JavaScript.</p>
	 * <p>The operation returns the remainder when the first operand is divided by the 
	 * second operand a certain number of times. If used with non-numeric operands, the 
	 * operation attempts to convert them to numbers. The sign of the result is the same 
	 * as the sign of the first operand.</p>
	 * <p>This operation is typically used with integer operands, it also works for 
	 * floating-point values.</p>
	 * @param a Any numeric value.
	 * @param b Any numeric value.
	 * @return The remainder.
	 * @since Descripter 1.0
	 */
	public static final double mod(Object a, Object b) {
		return doubleValue(a) % doubleValue(b);
	}

	/**
	 * <p>Divides the first operand by the second, resembling the division operator 
	 * in JavaScript.</p>
	 * <p>Used with non-numeric operands, this operation attempts to convert them to 
	 * numbers. If you are used to programming languages that distinguish between integer 
	 * and floating-point numbers, you might expect to get an integer result when you 
	 * divide one integer by another. In JavaScript, however, all numbers are floating-point, 
	 * so all division operations have floating-point results. Division by zero yields positive 
	 * or negative infinity, while <tt>0/0</tt> evaluates to <tt>NaN</tt>.</p>
	 * @param a Any numeric value.
	 * @param b Any numeric value.
	 * @return The quotient of the two operands.
	 * @since Descripter 1.0
	 */
	public static final double div(Object a, Object b) {
		return doubleValue(a) / doubleValue(b);
	}

	/**
	 * <p>Bitwise-NOT operation, resembling that of JavaScript, operates by reversing all 
	 * bits in the operand.</p>
	 * <p>Because of the way signed integers are represented in JavaScript, applying this 
	 * operation to a value is equivalent to changing its sign and subtracting 1.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with operands that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the operands to 
	 * 32-bit integers by dropping any fractional part of the operand or any bits beyond 
	 * the 32nd.</p>
	 * @param o A numeric value.
	 * @return The bitwise-NOT of the operand.
	 * @since Descripter 1.0
	 */
	public static int not(Object o) {
		return ~intValue(o);
	}

	/**
	 * <p>Bitwise-AND operation, resembling that of JavaScript, performs a boolean AND 
	 * operation on each bit of the integer arguments. A bit is set in the result only if 
	 * the corresponding bit is set in both operands.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with operands that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the operands to 
	 * 32-bit integers by dropping any fractional part of the operand or any bits beyond 
	 * the 32nd.</p>
	 * @param a A numeric value.
	 * @param b A numeric value.
	 * @return The bitwise-AND of the two operands.
	 * @since Descripter 1.0
	 */
	public static int and(Object a, Object b) {
		return intValue(a) & intValue(b);
	}

	/**
	 * <p>Bitwise-OR operation, resembling that of JavaScript, performs a boolean OR operation 
	 * on each bit of the integer arguments. A bit is set in the result if the corresponding 
	 * bit is set in one or both of the operands.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with operands that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the operands to 
	 * 32-bit integers by dropping any fractional part of the operand or any bits beyond 
	 * the 32nd.</p>
	 * @param a A numeric value.
	 * @param b A numeric value.
	 * @return The bitwise-OR of the two operands.
	 * @since Descripter 1.0
	 */
	public static int or(Object a, Object b) {
		return intValue(a) | intValue(b);
	}

	/**
	 * <p>Bitwise-XOR operation, resembling that of JavaScript, performs a boolean exclusive 
	 * OR operation on each bit of the integer arguments. Exclusive OR means that either 
	 * operand one is <tt>true</tt> or operand two is <tt>true</tt>, but not both. A bit is 
	 * set in this operation's result if a corresponding bit is set in one (but not both) 
	 * of the two operands.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with operands that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the operands to 
	 * 32-bit integers by dropping any fractional part of the operand or any bits beyond 
	 * the 32nd.</p>
	 * @param a A numeric value.
	 * @param b A numeric value.
	 * @return The bitwise-exclusive-OR of the two operands.
	 * @since Descripter 1.0
	 */
	public static int xor(Object a, Object b) {
		return intValue(a) ^ intValue(b);
	}

	/**
	 * <p>Shift-left operation, resembling that of JavaScript, moves all bits in the first 
	 * operand to the left by the number of places specified in the second operand, which 
	 * should be an integer between 0 and 31.</p>
	 * <p>A zero is used for the new first bit, and the value of the 32nd bit is lost. 
	 * Shifting a value left by one position is equivalent to multiplying by 2, shifting 
	 * two positions is equivalent to multiplying by 4, etc.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with operands that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the operands to 
	 * 32-bit integers by dropping any fractional part of the operand or any bits beyond 
	 * the 32nd. The shift operation requires a right-side operand between 0 and 31. After 
	 * converting this operand to a 32-bit integer, it drops any bits beyond the 5th, which 
	 * yields a number in the appropriate range.</p>
	 * @param a A numeric value.
	 * @param b The number of bits to shift.
	 * @return The shifted integer number.
	 * @since Descripter 1.0
	 */
	public static int shl(Object a, Object b) {
		return intValue(a) << intValue(b);
	}

	/**
	 * <p>Shift-right operation, resembling that of JavaScript, moves all bits in the first 
	 * operand to the right by the number of places specified in the second operand (an 
	 * integer between 0 and 31). Bits that are shifted off the right are lost. The bits 
	 * filled in on the left depend on the sign bit of the original operand, in order to 
	 * preserve the sign of the result. If the first operand is positive, the result has 
	 * zeros placed in the high bits; if the first operand is negative, the result has ones 
	 * placed in the high bits.</p>
	 * <p>Shifting a value right one place is equivalent to dividing by 2 (discarding the 
	 * remainder), shifting right two places is equivalent to integer division by 4, and 
	 * so on.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with operands that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the operands to 
	 * 32-bit integers by dropping any fractional part of the operand or any bits beyond 
	 * the 32nd. The shift operation requires a right-side operand between 0 and 31. After 
	 * converting this operand to a 32-bit integer, it drops any bits beyond the 5th, which 
	 * yields a number in the appropriate range.</p>
	 * @param a A numeric value.
	 * @param b The number of bits to shift.
	 * @return The shifted integer number.
	 * @since Descripter 1.0
	 */
	public static int shr(Object a, Object b) {
		return intValue(a) >> intValue(b);
	}

	/**
	 * <p>Shift-right-unsigned operation, resembling that of JavaScript, moves all bits in 
	 * the first operand to the right by the number of places specified in the second 
	 * operand (an integer between 0 and 31). Bits that are shifted off the right are lost. 
	 * The result has zeros placed in the high bits.</p>
	 * <p>This operation is just like {@link #shr(Object, Object)}, except that the bits shifted 
	 * in on the left are always zero, regardless of the sign of the first operand.</p>
	 * <p>Shifting a value right one place is equivalent to dividing by 2 (discarding the 
	 * remainder), shifting right two places is equivalent to integer division by 4, and 
	 * so on.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with operands that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the operands to 
	 * 32-bit integers by dropping any fractional part of the operand or any bits beyond 
	 * the 32nd. The shift operation requires a right-side operand between 0 and 31. After 
	 * converting this operand to a 32-bit integer, it drops any bits beyond the 5th, which 
	 * yields a number in the appropriate range.</p>
	 * @param a A numeric value.
	 * @param b The number of bits to shift.
	 * @return The shifted integer number.
	 * @since Descripter 1.0
	 */
	public static int shru(Object a, Object b) {
		return intValue(a) >>> intValue(b);
	}

	/**
	 * <p>Bitwise-AND operation, resembling that of JavaScript, performs a boolean AND 
	 * operation on each bit of the integer arguments. A bit is set in the result only if 
	 * the corresponding bit is set in both operands.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with operands that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the operands to 
	 * 32-bit integers by dropping any fractional part of the operand or any bits beyond 
	 * the 32nd.</p>
	 * @param a A numeric value.
	 * @param b A numeric value.
	 * @return The bitwise-AND of the two operands.
	 * @since Descripter 1.0
	 */
	public static boolean band(Object a, Object b) {
		return bool(a) && bool(b);
	}

	/**
	 * <p>Bitwise-OR operation, resembling that of JavaScript, performs a boolean OR operation 
	 * on each bit of the integer arguments. A bit is set in the result if the corresponding 
	 * bit is set in one or both of the operands.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with operands that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the operands to 
	 * 32-bit integers by dropping any fractional part of the operand or any bits beyond 
	 * the 32nd.</p>
	 * @param a A numeric value.
	 * @param b A numeric value.
	 * @return The bitwise-OR of the two operands.
	 * @since Descripter 1.0
	 */
	public static boolean bor(Object a, Object b) {
		return bool(a) || bool(b);
	}

	/**
	 * <p>Inverts the boolean value of its operand, resembling the logical NOT operator 
	 * in JavaScript.</p>
	 * <p>This operation converts its operand to a boolean value using the following rules 
	 * if necessary before inverting the converted value.</p>
	 * <ul>
	 * <li>If a number is used where a boolean value is expected, the number is converted 
	 * to <tt>true</tt> unless the number is 0 or NaN, which are converted to <tt>false</tt>.</li>
	 * <li>If a string is used where a boolean value is expected, it is converted to <tt>true</tt> 
	 * except for the empty string, which is converted to <tt>false</tt>.</li>
	 * <li><tt>null</tt> and the undefined value convert to <tt>false</tt>, and any 
	 * non-null object, array, or function converts to <tt>true</tt>.
	 * </ul>
	 * <p>You can convert any value <tt>x</tt> to its equivalent boolean value by applying 
	 * this operation twice: <tt>bnot(bnot(x))</tt></p>
	 * @param o A value or object.
	 * @return The inverted boolean value.
	 * @since Descripter 1.0
	 */
	public static boolean bnot(Object o) {
		return !bool(o);
	}

	/**
	 * <p>Less-than operation, resembling that of JavaScript, evaluates to <tt>true</tt> if 
	 * the first operand is less than the second operand; otherwise it evaluates to 
	 * <tt>false</tt>.</p>
	 * <p>The operands of this operation may be of any type. Comparison can be performed 
	 * only on numbers and strings, however, so operands that are not numbers or strings 
	 * are converted. Comparison and conversion occur as follows:</p>
	 * <ul>
	 * <li>If both operands are numbers, or if both convert to numbers, they are compared 
	 * numerically.</li>
	 * <li>If both operands are strings or convert to strings, they are compared as 
	 * strings.</li>
	 * <li>If one operand is or converts to a string, and one is or converts to a number, 
	 * the operation attempts to convert the string to a number and performs a numerical 
	 * comparison. If the string does not represent a number, it converts to <tt>NaN</tt>, 
	 * and the comparison is <tt>false</tt>. In JavaScript 1.1, the string-to-number 
	 * conversion causes an error instead of yielding <tt>NaN</tt>.</li>
	 * <li>If an object can be converted to either a number or a string, JavaScript performs 
	 * the numerical conversion. This means, for example, that Date objects are compared 
	 * numerically, and it is meaningful to compare two dates to see whether one is earlier 
	 * than the other.</li>
	 * <li>If the operands of the comparison operations cannot both be successfully converted 
	 * to numbers or to strings, these operations always return <tt>false</tt>.</li>
	 * <li>If either operand is or converts to <tt>NaN</tt>, the comparison operation always 
	 * yields <tt>false</tt>.</li>
	 * </ul>
	 * <p>Keep in mind that string comparison is done on a strict character-by-character 
	 * basis using the numerical value of each character from the Unicode encoding. Although 
	 * in some cases the Unicode standard allows equivalent strings to be encoded using 
	 * different sequences of characters, the JavaScript comparison operations do not 
	 * detect these encoding differences; they assume that all strings are expressed in 
	 * normalized form. Note in particular that string comparison is case-sensitive, and 
	 * in the Unicode encoding (at least for the ASCII subset), all capital letters are 
	 * "less than" all lowercase letters. This rule can cause confusing results if you do 
	 * not expect it.</p>
	 * <p>For a more robust string-comparison algorithm, see the {@link CString#localeCompare(Object)} 
	 * method, which also takes locale-specific definitions of alphabetical order into account. 
	 * For case-insensitive comparisons, you must first convert the strings to all lowercase or 
	 * all uppercase using {@link CString#toLowerCase()} or {@link CString#toUpperCase()}.</p>
	 * <p>The less-than-or-equal and greater-than-or-equal operations do not rely on the 
	 * equality or identity operations for determining whether two values are "equal." 
	 * Instead, the less-than-or-equal operator is simply defined as "not greater than", 
	 * and the greater-than-or-equal operator is defined as "not less than". The one 
	 * exception occurs when either operand is (or converts to) <tt>NaN</tt>, in which case 
	 * all comparison operations return <tt>false</tt>.</p>
	 * @param a A value or object.
	 * @param b A value or object.
	 * @return <tt>true</tt> if the first operand is less than the second operand; 
	 * otherwise <tt>false</tt>.
	 * @since Descripter 1.0
	 */
	public static boolean lt(Object a, Object b) {
		return sub(a, b) < 0;
	}

	/**
	 * <p>Less-than-or-equal operation, resembling that of JavaScript, evaluates to 
	 * <tt>true</tt> if the first operand is less than or equal to the second operand; 
	 * otherwise it evaluates to <tt>false</tt>.</p>
	 * <p>The operands of this operation may be of any type. Comparison can be performed 
	 * only on numbers and strings, however, so operands that are not numbers or strings 
	 * are converted. Comparison and conversion occur as follows:</p>
	 * <ul>
	 * <li>If both operands are numbers, or if both convert to numbers, they are compared 
	 * numerically.</li>
	 * <li>If both operands are strings or convert to strings, they are compared as 
	 * strings.</li>
	 * <li>If one operand is or converts to a string, and one is or converts to a number, 
	 * the operation attempts to convert the string to a number and performs a numerical 
	 * comparison. If the string does not represent a number, it converts to <tt>NaN</tt>, 
	 * and the comparison is <tt>false</tt>. In JavaScript 1.1, the string-to-number 
	 * conversion causes an error instead of yielding <tt>NaN</tt>.</li>
	 * <li>If an object can be converted to either a number or a string, JavaScript performs 
	 * the numerical conversion. This means, for example, that Date objects are compared 
	 * numerically, and it is meaningful to compare two dates to see whether one is earlier 
	 * than the other.</li>
	 * <li>If the operands of the comparison operations cannot both be successfully converted 
	 * to numbers or to strings, these operations always return <tt>false</tt>.</li>
	 * <li>If either operand is or converts to <tt>NaN</tt>, the comparison operation always 
	 * yields <tt>false</tt>.</li>
	 * </ul>
	 * <p>Keep in mind that string comparison is done on a strict character-by-character 
	 * basis using the numerical value of each character from the Unicode encoding. Although 
	 * in some cases the Unicode standard allows equivalent strings to be encoded using 
	 * different sequences of characters, the JavaScript comparison operations do not 
	 * detect these encoding differences; they assume that all strings are expressed in 
	 * normalized form. Note in particular that string comparison is case-sensitive, and 
	 * in the Unicode encoding (at least for the ASCII subset), all capital letters are 
	 * "less than" all lowercase letters. This rule can cause confusing results if you do 
	 * not expect it.</p>
	 * <p>For a more robust string-comparison algorithm, see the {@link CString#localeCompare(Object)} 
	 * method, which also takes locale-specific definitions of alphabetical order into account. 
	 * For case-insensitive comparisons, you must first convert the strings to all lowercase or 
	 * all uppercase using {@link CString#toLowerCase()} or {@link CString#toUpperCase()}.</p>
	 * <p>The less-than-or-equal and greater-than-or-equal operations do not rely on the 
	 * equality or identity operations for determining whether two values are "equal." 
	 * Instead, the less-than-or-equal operator is simply defined as "not greater than", 
	 * and the greater-than-or-equal operator is defined as "not less than". The one 
	 * exception occurs when either operand is (or converts to) <tt>NaN</tt>, in which case 
	 * all comparison operations return <tt>false</tt>.</p>
	 * @param a A value or object.
	 * @param b A value or object.
	 * @return <tt>true</tt> if the first operand is less than or equal to the second operand; 
	 * otherwise <tt>false</tt>.
	 * @since Descripter 1.0
	 */
	public static boolean lte(Object a, Object b) {
		return sub(a, b) <= 0;
	}

	/**
	 * <p>Greater-than operation, resembling that of JavaScript, evaluates to <tt>true</tt> if 
	 * the first operand is greater than the second operand; otherwise it evaluates to 
	 * <tt>false</tt>.</p>
	 * <p>The operands of this operation may be of any type. Comparison can be performed 
	 * only on numbers and strings, however, so operands that are not numbers or strings 
	 * are converted. Comparison and conversion occur as follows:</p>
	 * <ul>
	 * <li>If both operands are numbers, or if both convert to numbers, they are compared 
	 * numerically.</li>
	 * <li>If both operands are strings or convert to strings, they are compared as 
	 * strings.</li>
	 * <li>If one operand is or converts to a string, and one is or converts to a number, 
	 * the operation attempts to convert the string to a number and performs a numerical 
	 * comparison. If the string does not represent a number, it converts to <tt>NaN</tt>, 
	 * and the comparison is <tt>false</tt>. In JavaScript 1.1, the string-to-number 
	 * conversion causes an error instead of yielding <tt>NaN</tt>.</li>
	 * <li>If an object can be converted to either a number or a string, JavaScript performs 
	 * the numerical conversion. This means, for example, that Date objects are compared 
	 * numerically, and it is meaningful to compare two dates to see whether one is earlier 
	 * than the other.</li>
	 * <li>If the operands of the comparison operations cannot both be successfully converted 
	 * to numbers or to strings, these operations always return <tt>false</tt>.</li>
	 * <li>If either operand is or converts to <tt>NaN</tt>, the comparison operation always 
	 * yields <tt>false</tt>.</li>
	 * </ul>
	 * <p>Keep in mind that string comparison is done on a strict character-by-character 
	 * basis using the numerical value of each character from the Unicode encoding. Although 
	 * in some cases the Unicode standard allows equivalent strings to be encoded using 
	 * different sequences of characters, the JavaScript comparison operations do not 
	 * detect these encoding differences; they assume that all strings are expressed in 
	 * normalized form. Note in particular that string comparison is case-sensitive, and 
	 * in the Unicode encoding (at least for the ASCII subset), all capital letters are 
	 * "less than" all lowercase letters. This rule can cause confusing results if you do 
	 * not expect it.</p>
	 * <p>For a more robust string-comparison algorithm, see the {@link CString#localeCompare(Object)} 
	 * method, which also takes locale-specific definitions of alphabetical order into account. 
	 * For case-insensitive comparisons, you must first convert the strings to all lowercase or 
	 * all uppercase using {@link CString#toLowerCase()} or {@link CString#toUpperCase()}.</p>
	 * <p>The less-than-or-equal and greater-than-or-equal operations do not rely on the 
	 * equality or identity operations for determining whether two values are "equal." 
	 * Instead, the less-than-or-equal operator is simply defined as "not greater than", 
	 * and the greater-than-or-equal operator is defined as "not less than". The one 
	 * exception occurs when either operand is (or converts to) <tt>NaN</tt>, in which case 
	 * all comparison operations return <tt>false</tt>.</p>
	 * @param a A value or object.
	 * @param b A value or object.
	 * @return <tt>true</tt> if the first operand is greater than the second operand; 
	 * otherwise <tt>false</tt>.
	 * @since Descripter 1.0
	 */
	public static boolean gt(Object a, Object b) {
		return sub(a, b) > 0;
	}

	/**
	 * <p>Greater-than-or-equal operation, resembling that of JavaScript, evaluates to 
	 * <tt>true</tt> if the first operand is greater than or equal to the second operand; 
	 * otherwise it evaluates to <tt>false</tt>.</p>
	 * <p>The operands of this operation may be of any type. Comparison can be performed 
	 * only on numbers and strings, however, so operands that are not numbers or strings 
	 * are converted. Comparison and conversion occur as follows:</p>
	 * <ul>
	 * <li>If both operands are numbers, or if both convert to numbers, they are compared 
	 * numerically.</li>
	 * <li>If both operands are strings or convert to strings, they are compared as 
	 * strings.</li>
	 * <li>If one operand is or converts to a string, and one is or converts to a number, 
	 * the operation attempts to convert the string to a number and performs a numerical 
	 * comparison. If the string does not represent a number, it converts to <tt>NaN</tt>, 
	 * and the comparison is <tt>false</tt>. In JavaScript 1.1, the string-to-number 
	 * conversion causes an error instead of yielding <tt>NaN</tt>.</li>
	 * <li>If an object can be converted to either a number or a string, JavaScript performs 
	 * the numerical conversion. This means, for example, that Date objects are compared 
	 * numerically, and it is meaningful to compare two dates to see whether one is earlier 
	 * than the other.</li>
	 * <li>If the operands of the comparison operations cannot both be successfully converted 
	 * to numbers or to strings, these operations always return <tt>false</tt>.</li>
	 * <li>If either operand is or converts to <tt>NaN</tt>, the comparison operation always 
	 * yields <tt>false</tt>.</li>
	 * </ul>
	 * <p>Keep in mind that string comparison is done on a strict character-by-character 
	 * basis using the numerical value of each character from the Unicode encoding. Although 
	 * in some cases the Unicode standard allows equivalent strings to be encoded using 
	 * different sequences of characters, the JavaScript comparison operations do not 
	 * detect these encoding differences; they assume that all strings are expressed in 
	 * normalized form. Note in particular that string comparison is case-sensitive, and 
	 * in the Unicode encoding (at least for the ASCII subset), all capital letters are 
	 * "less than" all lowercase letters. This rule can cause confusing results if you do 
	 * not expect it.</p>
	 * <p>For a more robust string-comparison algorithm, see the {@link CString#localeCompare(Object)} 
	 * method, which also takes locale-specific definitions of alphabetical order into account. 
	 * For case-insensitive comparisons, you must first convert the strings to all lowercase or 
	 * all uppercase using {@link CString#toLowerCase()} or {@link CString#toUpperCase()}.</p>
	 * <p>The less-than-or-equal and greater-than-or-equal operations do not rely on the 
	 * equality or identity operations for determining whether two values are "equal." 
	 * Instead, the less-than-or-equal operator is simply defined as "not greater than", 
	 * and the greater-than-or-equal operator is defined as "not less than". The one 
	 * exception occurs when either operand is (or converts to) <tt>NaN</tt>, in which case 
	 * all comparison operations return <tt>false</tt>.</p>
	 * @param a A value or object.
	 * @param b A value or object.
	 * @return <tt>true</tt> if the first operand is greater than or equal to the second operand; 
	 * otherwise <tt>false</tt>.
	 * @since Descripter 1.0
	 */
	public static boolean gte(Object a, Object b) {
		return sub(a, b) >= 0;
	}

	/**
	 * <p>Checks whether the two operands are "equal" using a more relaxed definition of 
	 * sameness that allows type conversions, resembling the equality operator in 
	 * JavaScript.</p>
	 * <p>The equality and identity operations check whether two values are the same, using 
	 * two different definitions of sameness. Both operations accept operands of any type, 
	 * and both return <tt>true</tt> if their operands are the same and <tt>false</tt> 
	 * if they are different. The identity operation checks whether its two operands are 
	 * "identical" using a strict definition of sameness. The equality operation checks 
	 * whether its two operands are "equal" using a more relaxed definition of sameness 
	 * that allows type conversions.</p>
	 * <p>The identity operation is standardized by ECMAScript v3 and implemented in 
	 * JavaScript 1.3 and later. Be sure you understand the differences between the 
	 * assignment, equality, and identity operations, and be careful to use the correct one 
	 * when coding! Although it is tempting to call all three operations "equals," it may 
	 * help to reduce confusion if you read "gets or is assigned" for assignment operation, 
	 * "is equal to" for equality operation, and "is identical to" for identity operation.</p>
	 * <p>In JavaScript, numbers, strings, and boolean values are compared by value. In this 
	 * case, two separate values are involved, and the equality and identity operations 
	 * check that these two values are identical. This means that two variables are equal 
	 * or identical only if they contain the same value. For example, two strings are equal 
	 * only if they each contain exactly the same characters.</p>
	 * <p>On the other hand, objects, arrays, and functions are compared by reference. This 
	 * means that two variables are equal only if they refer to the same object. Two 
	 * separate arrays are never equal or identical, even if they contain equal or identical 
	 * elements. Two variables that contain references to objects, arrays, or functions are 
	 * equal only if they refer to the same object, array, or function. If you want to test 
	 * that two distinct objects contain the same properties or that two distinct arrays 
	 * contain the same elements, you'll have to check the properties or elements individually 
	 * for equality or identity. And, if any of the properties or elements are themselves 
	 * objects or arrays, you'll have to decide how deep you want the comparison to go.</p>
	 * <p>The following rules determine whether two values are equal according to the 
	 * equality operation:
	 * <ul>
	 * <li>If the two values have the same type, test them for identity. If the values are 
	 * identical, they are equal; if they are not identical, they are not equal.</li>
	 * <li>If the two values do not have the same type, they may still be equal. Use the 
	 * following rules and type conversions to check for equality:</li>
	 * <ul>
	 * <li>If one value is null and the other is undefined, they are equal.</li>
	 * <li>If one value is a number and the other is a string, convert the string to a 
	 * number and try the comparison again, using the converted value.</li>
	 * <li>If either value is <tt>true</tt>, convert it to 1 and try the comparison 
	 * again. If either value is <tt>false</tt>, convert it to 0 and try the comparison 
	 * again.</li>
	 * <li>If one value is an object and the other is a number or string, convert the 
	 * object to a primitive and try the comparison again. An object is converted to a 
	 * primitive value by either its <tt>toString()</tt> method or its <tt>valueOf()</tt> 
	 * method. The built-in classes of core JavaScript attempt <tt>valueOf()</tt> 
	 * conversion before <tt>toString()</tt> conversion, except for the Date class, 
	 * which performs <tt>toString()</tt> conversion. Objects that are not part of core 
	 * JavaScript may convert themselves to primitive values in an implementation-defined 
	 * way.</li>
	 * <li>Any other combinations of values are not equal.</li>
	 * </ul>
	 * </ul>
	 * @param a Any value or object.
	 * @param b Any value or object.
	 * @return <tt>true</tt> if the first operand equals the second; <tt>false</tt>, 
	 * otherwise;
	 * @since Descripter 1.0
	 */
	public static boolean eq(Object a, Object b) {
		return valueOf(a).equals(valueOf(b));
	}

	/**
	 * <p>Checks whether the two operands are "identical" using a strict definition of 
	 * sameness, resembling the identity operator in JavaScript.</p>
	 * <p>The equality and identity operations check whether two values are the same, using 
	 * two different definitions of sameness. Both operations accept operands of any type, 
	 * and both return <tt>true</tt> if their operands are the same and <tt>false</tt> 
	 * if they are different. The identity operation checks whether its two operands are 
	 * "identical" using a strict definition of sameness. The equality operation checks 
	 * whether its two operands are "equal" using a more relaxed definition of sameness 
	 * that allows type conversions.</p>
	 * <p>The identity operation is standardized by ECMAScript v3 and implemented in 
	 * JavaScript 1.3 and later. Be sure you understand the differences between the 
	 * assignment, equality, and identity operations, and be careful to use the correct one 
	 * when coding! Although it is tempting to call all three operations "equals," it may 
	 * help to reduce confusion if you read "gets or is assigned" for assignment operation, 
	 * "is equal to" for equality operation, and "is identical to" for identity operation.</p>
	 * <p>In JavaScript, numbers, strings, and boolean values are compared by value. In this 
	 * case, two separate values are involved, and the equality and identity operations 
	 * check that these two values are identical. This means that two variables are equal 
	 * or identical only if they contain the same value. For example, two strings are equal 
	 * only if they each contain exactly the same characters.</p>
	 * <p>On the other hand, objects, arrays, and functions are compared by reference. This 
	 * means that two variables are equal only if they refer to the same object. Two 
	 * separate arrays are never equal or identical, even if they contain equal or identical 
	 * elements. Two variables that contain references to objects, arrays, or functions are 
	 * equal only if they refer to the same object, array, or function. If you want to test 
	 * that two distinct objects contain the same properties or that two distinct arrays 
	 * contain the same elements, you'll have to check the properties or elements individually 
	 * for equality or identity. And, if any of the properties or elements are themselves 
	 * objects or arrays, you'll have to decide how deep you want the comparison to go.</p>
	 * <p>The following rules determine whether two values are identical according to the identity operation:
	 * <ul>
	 * <li>If the two values have different types, they are not identical.</li>
	 * <li>If both values are numbers and have the same value, they are identical, unless 
	 * either or both values are <tt>NaN</tt>, in which case they are not identical. 
	 * The <tt>NaN</tt> value is never identical to any other value, including itself! 
	 * To check whether a value is <tt>NaN</tt>, use the global {@link Core#isNaN(Script, Object[])} 
	 * function.</li>
	 * <li>If both values are strings and contain exactly the same characters in the same 
	 * positions, they are identical. If the strings differ in length or content, they are 
	 * not identical. Note that in some cases, the Unicode standard allows more than one 
	 * way to encode the same string. For efficiency, however, JavaScript's string 
	 * comparison compares strictly on a character-by-character basis, and it assumes that 
	 * all strings have been converted to a "normalized form" before they are compared. 
	 * See the {@link CString#localeCompare(Object)} for another way to compare strings.</li>
	 * <li>If both values are the boolean value <tt>true</tt> or both are the boolean 
	 * value <tt>false</tt>, they are identical.</li>
	 * <li>If both values refer to the same object, array, or function, they are identical. 
	 * If they refer to different objects (or arrays or functions) they are not identical, 
	 * even if both objects have identical properties or both arrays have identical elements.</li>
	 * <li>If both values are null or both values are undefined, they are identical.</li>
	 * </ul>
	 * @param a Any value or object.
	 * @param b Any value or object.
	 * @return <tt>true</tt> if the first operand is identical to the second; 
	 * <tt>false</tt>, otherwise;
	 * @since Descripter 1.0
	 */
	public static boolean eqs(Object a, Object b) {
		return evaluate(a).equals(evaluate(b));
	}

	/**
	 * <p>Comma operation, resembling the comma operator of JavaScript, evaluates the 
	 * first operand, evaluates the second operand, and then returns the value of the 
	 * second operand.</p>
	 * <p>In JavaScript, this strange operator is useful only in a few limited circumstances, 
	 * primarily when you need to evaluate several independent expressions with side effects 
	 * in a situation where only a single expression is allowed. In practice, the comma 
	 * operator is really used only in conjunction with the <tt>for</tt> loop statement.</p>
	 * @param args An array of arguments.
	 * @return The last operand.
	 * @since Descripter 1.0
	 */
	public static Object comma(Object...args) {
		Object v = null;
		if (args != null) {
			for (Object o : args) {
				v = evaluate(o);
			}
		}
		return v;
	}
}
