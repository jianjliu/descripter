
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

import java.util.Set;

/**
 * <p>Emulates JavaScript variables.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public final class Var extends Objective<Objective<?>> implements Value
{
	private final Key index;

	/**
	 * <p>Constructs an {@link Objective} context of this type.</p>
	 * @param with The containing {@link Objective} context.
	 * @param index The {@link Key} to the containing {@link Objective} context.
	 * @since Descripter 1.0
	 */
	public Var(Objective<?> with, Key index) {
		super(with);
		this.index = index;
	}

	/**
	 * <p>Evaluates the {@link Var} {@link Value}.</p>
	 * @return The evaluated value
	 * @since Descripter 1.0
	 */
	@Override
	public Object evaluate() {
		return with.get(index);
	}

	/**
	 * <p>Deletes the current variable.</p>
	 * <p>This method hides the associated key from the containing context if it is visible.</p>
	 * <p>Note that this method does nothing if the current variable is undefined.</p>
	 * @return <tt>true</tt> if variable was defined; <tt>false</tt>, otherwise.
	 * @throws RuntimeException if the containing context is read-only.
	 * @since Descripter 1.0
	 */
	public boolean delete() {
		if (has(index)) {
			hide(index);
		}
		return false;
	}

	/**
	 * <p>Returns a string representation of the current value object.</p>
	 * @return The string representation of the current value object
	 * @since Descripter 1.0
	 */
	@Override
	public String toString() {
		return toString(evaluate());
	}

	/**
	 * <p>Tells if the specified key is visible in the current context.</p>
	 * @param k A key to test
	 * @return <tt>true</tt> if the <tt>key</tt> is visible in the current context; <tt>false</tt>, otherwise.
	 * @since Descripter 1.0
	 */
	@Override
	public final boolean has(Key k) {
		return ((Objective<?>)evaluate()).has(k);
	}

	/**
	 * <p>Tells if the current context owns the specified key.</p>
	 * @param k A key to test
	 * @return <tt>true</tt> if the current context owns the <tt>key</tt>; <tt>false</tt>, otherwise.
	 * @since Descripter 1.0
	 */
	@Override
	public final boolean owns(Key k) {
		return ((Objective<?>)evaluate()).owns(k);
	}

	/**
	 * <p>Returns the {@link Set} of keys that are visible to the current context.</p>
	 * @return The {@link Set} of keys that are visible to the current context.
	 * @since Descripter 1.0
	 */
	@Override
	public final Set<Key> keys() {
		return ((Objective<?>)evaluate()).keys();
	}

	/**
	 * <p>Returns the value associated with the specified key.</p>
	 * @param k A key to lookup
	 * @return The value associated with the specified key or <tt>null</tt> for none.
	 * @since Descripter 1.0
	 */
	@Override
	public final Object get(Key k) {
		return ((Objective<?>)evaluate()).get(k);
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
		((Objective<?>)evaluate()).put(key, evaluate(val));
	}

	/**
	 * <p>Assigns value to the current variable.</p>
	 * @param value The value to assign
	 * @return The assigned value
	 * @throws RuntimeException if the current context is read-only.
	 * @since Descripter 1.0
	 */
	public final <T> T assign(T value) {
		with.set(index, evaluate(value));
		return value;
	}

	/**
	 * <p>Allocates a new object with this variable of a {@link Function}.</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @return The newly allocated object
	 * @since Descripter 1.0
	 */
	public final <S extends Script<?>> Object allocate(S script, Object ...args) {
		return script.alloc(evaluate(), args);
	}

	/**
	 * <p>Calls this variable of a {@link Function}.</p>
	 * <p>This method evaluates the current variable. If the value is a function, it calls the function 
	 * based on the containing context; otherwise, it passes the call to the specified script context.</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @return The return result of the invocation
	 * @since Descripter 1.0
	 */
	public final <S extends Script<?>> Object call(S script, Object ...args) {
		Object o = evaluate();
		if (o instanceof Function<?>) {
			return ((Function<?>)o).call(script, with, script.array(args));
		}
		return script.call(o, args);
	}

	/**
	 * <p>Logically evaluates the current variable.</p>
	 * @return The boolean value of the current variable
	 * @since Descripter 1.0
	 */
	public final boolean bool() {
		return bool(this);
	}

	/**
	 * <p>Increments the current variable.</p>
	 * @return The incremented number value of the current variable
	 * @since Descripter 1.0
	 */
	public final double inc() {
		return assign(add(doubleValue(this), 1));
	}

	/**
	 * <p>Decrements the current variable.</p>
	 * @return The decremented number value of the current variable
	 * @since Descripter 1.0
	 */
	public final double dec() {
		return assign(sub(doubleValue(this), 1));
	}

	/**
	 * <p>Returns a {@link Value} that evaluates to this variable and increments it after evaluation.</p>
	 * @return A post-incrementing {@link Value} of this variable
	 * @since Descripter 1.0
	 */
	public final Value uninc() {
		return new Value() {
			@Override
			public Object evaluate() {
				Number v = toNumber(Var.this);
				assign(add(v, 1));
				return v;
			}
		};
	}

	/**
	 * <p>Returns a {@link Value} that evaluates to this variable and decrements it after evaluation.</p>
	 * @return A post-decrementing {@link Value} of this variable
	 * @since Descripter 1.0
	 */
	public final Value undec() {
		return new Value() {
			@Override
			public Object evaluate() {
				Number v = toNumber(Var.this);
				assign(sub(v, 1));
				return v;
			}
		};
	}

	/**
	 * <p>Adds a numeric operand onto the current variable, 
	 * resembling the assignment-with-addition operator in JavaScript.</p>
	 * @param o A numeric operand
	 * @return The numeric operand
	 * @since Descripter 1.0
	 */
	public final Object aadd(Object o) {
		return assign(add(o));
	}

	/**
	 * <p>Subtracts a numeric operand from the current variable, 
	 * resembling the assignment-with-subtraction operator in JavaScript.</p>
	 * @param o A numeric operand
	 * @return The numeric operand
	 * @since Descripter 1.0
	 */
	public final double asub(Object o) {
		return assign(sub(o));
	}

	/**
	 * <p>Multiplies a numeric operand onto the current variable, 
	 * resembling the assignment-with-multiplication operator in JavaScript.</p>
	 * @param o A numeric operand
	 * @return The numeric operand
	 * @since Descripter 1.0
	 */
	public final double amul(Object o) {
		return assign(mul(o));
	}

	/**
	 * <p>Modulo operates a numeric operand onto the current variable, 
	 * resembling the assignment-with-modulo operator in JavaScript.</p>
	 * @param o A numeric operand
	 * @return The numeric operand
	 * @since Descripter 1.0
	 */
	public final double amod(Object o) {
		return assign(mod(o));
	}

	/**
	 * <p>Divides the current variable by a numeric operand, 
	 * resembling the assignment-with-division operator in JavaScript.</p>
	 * @param o A numeric operand
	 * @return The numeric operand
	 * @since Descripter 1.0
	 */
	public final double adiv(Object o) {
		return assign(div(o));
	}

	/**
	 * <p>Assignment-with-bitwise-AND operation, resembling that of JavaScript, performs a boolean AND 
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
	 * @param o A numeric value.
	 * @return The bitwise-AND of the two operands.
	 * @since Descripter 1.0
	 */
	public final int aand(Object o) {
		return assign(and(o));
	}

	/**
	 * <p>Assignment-with-bitwise-OR operation, resembling that of JavaScript, performs a boolean OR operation 
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
	 * @param o A numeric value.
	 * @return The bitwise-OR of the two operands.
	 * @since Descripter 1.0
	 */
	public final int aor(Object o) {
		return assign(or(o));
	}

	/**
	 * <p>Assignment-with-bitwise-XOR operation, resembling that of JavaScript, performs a boolean exclusive 
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
	 * @param o A numeric value.
	 * @return The bitwise-exclusive-OR of the two operands.
	 * @since Descripter 1.0
	 */
	public final int axor(Object o) {
		return assign(xor(o));
	}

	/**
	 * <p>Assignment-with-shift-left operation, resembling that of JavaScript, moves all bits in the 
	 * current variable to the left by the number of places specified in the 
	 * second operand, which should be an integer between 0 and 31.</p>
	 * <p>A zero is used for the new first bit, and the value of the 32nd bit is lost. 
	 * Shifting a value left by one position is equivalent to multiplying by 2, shifting 
	 * two positions is equivalent to multiplying by 4, etc.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with values that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the values to 
	 * 32-bit integers by dropping any fractional part of the value or any bits beyond 
	 * the 32nd. The shift operation requires a (right-side) operand between 0 and 31. After 
	 * converting this operand to a 32-bit integer, it drops any bits beyond the 5th, which 
	 * yields a number in the appropriate range.</p>
	 * @param o The number of bits to shift.
	 * @return The shifted primitive instance.
	 * @since Descripter 1.0
	 */
	public final int ashl(Object o) {
		return assign(shl(o));
	}

	/**
	 * <p>Assignment-with-shift-right operation, resembling that of JavaScript, moves all bits in the 
	 * current variable to the right by the number of places specified in the 
	 * second operand (an integer between 0 and 31). Bits that are shifted off the right 
	 * are lost. The bits filled in on the left depend on the sign bit of the original 
	 * value, in order to preserve the sign of the result. If the current primitive 
	 * instance is positive, the result has zeros placed in the high bits; if the current 
	 * variable is negative, the result has ones placed in the high bits.</p>
	 * <p>Shifting a value right one place is equivalent to dividing by 2 (discarding the 
	 * remainder), shifting right two places is equivalent to integer division by 4, and 
	 * so on.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with values that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the values to 
	 * 32-bit integers by dropping any fractional part of the value or any bits beyond 
	 * the 32nd. The shift operation requires a (right-side) operand between 0 and 31. After 
	 * converting this operand to a 32-bit integer, it drops any bits beyond the 5th, which 
	 * yields a number in the appropriate range.</p>
	 * @param o The number of bits to shift.
	 * @return The shifted primitive instance.
	 * @since Descripter 1.0
	 */
	public final int ashr(Object o) {
		return assign(shr(o));
	}

	/**
	 * <p>Assignment-with-shift-right-unsigned operation, resembling that of JavaScript, moves all bits in 
	 * the current variable to the right by the number of places specified in the 
	 * second operand (an integer between 0 and 31). Bits that are shifted off the right 
	 * are lost. The result has zeros placed in the high bits.</p>
	 * <p>This operation is just like {@link #shr(Object)}, except that the bits shifted 
	 * in on the left are always zero, regardless of the sign of the current variable.</p>
	 * <p>Shifting a value right one place is equivalent to dividing by 2 (discarding the 
	 * remainder), shifting right two places is equivalent to integer division by 4, and 
	 * so on.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with values that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the values to 
	 * 32-bit integers by dropping any fractional part of the value or any bits beyond 
	 * the 32nd. The shift operation requires a (right-side) operand between 0 and 31. After 
	 * converting this operand to a 32-bit integer, it drops any bits beyond the 5th, which 
	 * yields a number in the appropriate range.</p>
	 * @param o The number of bits to shift.
	 * @return The shifted primitive instance.
	 * @since Descripter 1.0
	 */
	public final int ashru(Object o) {
		return assign(shru(o));
	}

	/**
	 * <p>Performs unary negation, resembling the unary minus operator in JavaScript.</p>
	 * <p>This operation converts a positive value to an equivalently negative value, and 
	 * vice versa. If the operand is not a number, it attempts to convert it to one.</p>
	 * @return The negation of the current variable.
	 * @since Descripter 1.0
	 */
	public final double neg() {
		return neg(this);
	}

	/**
	 * <p>Adds the current variable to a numeric operand or concatenates it with 
	 * a string operand, resembling the addition operator in JavaScript.</p>
	 * <p>If one value is a string, the other is converted to a string, and the two 
	 * strings are then concatenated. Object operands are converted to numbers or strings 
	 * that can be added or concatenated.</p>
	 * @param o A value or object.
	 * @return The sum or concatenation of the values.
	 * @since Descripter 1.0
	 */
	public final Object add(Object o) {
		return add(this, o);
	}

	/**
	 * <p>Subtracts the operand from the current variable, resembling the 
	 * subtraction operator in JavaScript.</p>
	 * <p>If used with non-numeric values, this operation attempts to convert them to 
	 * numbers.</p>
	 * @param o numeric value.
	 * @return The difference between the current variable and the operand.
	 * @since Descripter 1.0
	 */
	public final double sub(Object o) {
		return sub(this, o);
	}

	/**
	 * <p>Multiplies the current variable with the operand, resembling the 
	 * multiplication operator in JavaScript.</p>
	 * <p>If used with non-numeric values, this operation attempts to convert them to 
	 * numbers.</p>
	 * @param o Any numeric value.
	 * @return The product of the current variable and the operand.
	 * @since Descripter 1.0
	 */
	public final double mul(Object o) {
		return mul(this, o);
	}

	/**
	 * <p>Computes the current variable modulo the operand, resembling the modulo 
	 * operator in JavaScript.</p>
	 * <p>The operation returns the remainder when the current variable is divided 
	 * by the operand a certain number of times. If used with non-numeric values, the 
	 * operation attempts to convert them to numbers. The sign of the result is the same 
	 * as the sign of the current variable.</p>
	 * <p>This operation is typically used with integer values, it also works for 
	 * floating-point values.</p>
	 * @param o Any numeric value.
	 * @return The remainder.
	 * @since Descripter 1.0
	 */
	public final double mod(Object o) {
		return mod(this, o);
	}

	/**
	 * <p>Divides the current variable by the operand, resembling the division 
	 * operator in JavaScript.</p>
	 * <p>Used with non-numeric values, this operation attempts to convert them to 
	 * numbers. If you are used to programming languages that distinguish between integer 
	 * and floating-point numbers, you might expect to get an integer result when you 
	 * divide one integer by another. In JavaScript, however, all numbers are floating-point, 
	 * so all division operations have floating-point results. Division by zero yields positive 
	 * or negative infinity, while <tt>0/0</tt> evaluates to <tt>NaN</tt>.</p>
	 * @param o Any numeric value.
	 * @return The quotient of the current variable and the operand.
	 * @since Descripter 1.0
	 */
	public final double div(Object o) {
		return div(this, o);
	}

	/**
	 * <p>Bitwise-NOT operation, resembling that of JavaScript, operates by reversing all 
	 * bits in the current variable.</p>
	 * <p>Because of the way signed integers are represented in JavaScript, applying this 
	 * operation to a value is equivalent to changing its sign and subtracting 1.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * values using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with the current variable that are 
	 * not integers or are too large to fit in a 32-bit integer representation, it simply 
	 * coerces them to 32-bit integers by dropping any fractional part of the value or any 
	 * bits beyond the 32nd.</p>
	 * @return The bitwise-NOT of the current variable.
	 * @since Descripter 1.0
	 */
	public final int not() {
		return not(this);
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
	 * @param o A numeric value.
	 * @return The bitwise-AND of the two operands.
	 * @since Descripter 1.0
	 */
	public final int and(Object o) {
		return and(this, o);
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
	 * @param o A numeric value.
	 * @return The bitwise-OR of the two operands.
	 * @since Descripter 1.0
	 */
	public final int or(Object o) {
		return or(this, o);
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
	 * @param o A numeric value.
	 * @return The bitwise-exclusive-OR of the two operands.
	 * @since Descripter 1.0
	 */
	public final int xor(Object o) {
		return xor(this, o);
	}

	/**
	 * <p>Shift-left operation, resembling that of JavaScript, moves all bits in the 
	 * current primitive instance to the left by the number of places specified in the 
	 * second operand, which should be an integer between 0 and 31.</p>
	 * <p>A zero is used for the new first bit, and the value of the 32nd bit is lost. 
	 * Shifting a value left by one position is equivalent to multiplying by 2, shifting 
	 * two positions is equivalent to multiplying by 4, etc.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with values that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the values to 
	 * 32-bit integers by dropping any fractional part of the value or any bits beyond 
	 * the 32nd. The shift operation requires a (right-side) operand between 0 and 31. After 
	 * converting this operand to a 32-bit integer, it drops any bits beyond the 5th, which 
	 * yields a number in the appropriate range.</p>
	 * @param o The number of bits to shift.
	 * @return The shifted integer number.
	 * @since Descripter 1.0
	 */
	public final int shl(Object o) {
		return shl(this, o);
	}

	/**
	 * <p>Shift-right operation, resembling that of JavaScript, moves all bits in the 
	 * current primitive instance to the right by the number of places specified in the 
	 * second operand (an integer between 0 and 31). Bits that are shifted off the right 
	 * are lost. The bits filled in on the left depend on the sign bit of the original 
	 * value, in order to preserve the sign of the result. If the current primitive 
	 * instance is positive, the result has zeros placed in the high bits; if the current 
	 * primitive instance is negative, the result has ones placed in the high bits.</p>
	 * <p>Shifting a value right one place is equivalent to dividing by 2 (discarding the 
	 * remainder), shifting right two places is equivalent to integer division by 4, and 
	 * so on.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with values that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the values to 
	 * 32-bit integers by dropping any fractional part of the value or any bits beyond 
	 * the 32nd. The shift operation requires a (right-side) operand between 0 and 31. After 
	 * converting this operand to a 32-bit integer, it drops any bits beyond the 5th, which 
	 * yields a number in the appropriate range.</p>
	 * @param o The number of bits to shift.
	 * @return The shifted integer number.
	 * @since Descripter 1.0
	 */
	public final int shr(Object o) {
		return shr(this, o);
	}

	/**
	 * <p>Shift-right-unsigned operation, resembling that of JavaScript, moves all bits in 
	 * the current variable to the right by the number of places specified in the 
	 * second operand (an integer between 0 and 31). Bits that are shifted off the right 
	 * are lost. The result has zeros placed in the high bits.</p>
	 * <p>This operation is just like {@link #shr(Object)}, except that the bits shifted 
	 * in on the left are always zero, regardless of the sign of the current variable.</p>
	 * <p>Shifting a value right one place is equivalent to dividing by 2 (discarding the 
	 * remainder), shifting right two places is equivalent to integer division by 4, and 
	 * so on.</p>
	 * <p>Despite the fact that all numbers in JavaScript are floating-point numbers, the bitwise 
	 * operation requires numeric operands that have integer values. It operate on the integer 
	 * operands using a 32-bit integer representation instead of the equivalent floating-point 
	 * representation.</p>
	 * <p>If this bitwise operation is used with values that are not integers or are too 
	 * large to fit in a 32-bit integer representation, it simply coerces the values to 
	 * 32-bit integers by dropping any fractional part of the value or any bits beyond 
	 * the 32nd. The shift operation requires a (right-side) operand between 0 and 31. After 
	 * converting this operand to a 32-bit integer, it drops any bits beyond the 5th, which 
	 * yields a number in the appropriate range.</p>
	 * @param o The number of bits to shift.
	 * @return The shifted integer number.
	 * @since Descripter 1.0
	 */
	public final int shru(Object o) {
		return shru(this, o);
	}

	/**
	 * <p>Logical AND operation, resembling that of JavaScript, performs the Boolean AND 
	 * operation on the two values: it returns <tt>true</tt> if and only if both its first 
	 * operand and its second operand are <tt>true</tt>. If one or both of these operands 
	 * is <tt>false</tt>, it returns <tt>false</tt>.</p>
	 * <p>In JavaScript, depending on the value of the left-side expression, the operator 
	 * may or may not evaluate the right-side expression. The actual behavior of the operator 
	 * is somewhat more complicated. It starts by evaluating its first operand, the expression 
	 * on its left. If the value of this expression can be converted to <tt>false</tt> 
	 * (for example, if the left operand evaluates to <tt>null</tt>, 0, "", or undefined), 
	 * the operator returns the value of the left-side expression. Otherwise, it evaluates 
	 * its second operand, the expression on its right, and returns the value of that 
	 * expression. In JavaScript 1.0 and JavaScript 1.1, if the left-side expression 
	 * evaluates to <tt>false</tt>, the && operator returns <tt>false</tt> rather than 
	 * returning the unconverted value of the left-side expression.</p>
	 * <p>Sometimes, this operation probably does not do what the programmers intended. 
	 * To avoid problems, do not use expressions with side effects (assignments, increments, 
	 * decrements, and function calls) for the second operand unless you are quite sure 
	 * you know exactly what you are doing.</p>
	 * <p>Despite the fairly confusing way that this operation actually works, it is 
	 * easiest, and perfectly safe, to think of it as merely a Boolean algebra operator. 
	 * Although it does not actually return a boolean value, the value it returns can always 
	 * be converted to a boolean value.</p>
	 * @param o A value or object.
	 * @return The logical AND of the two operands.
	 * @since Descripter 1.0
	 */
	public final boolean band(Object o) {
		return band(this, o);
	}

	/**
	 * <p>Logical OR operation, resembling that of JavaScript, performs the Boolean OR 
	 * operation on the two values: it returns <tt>true</tt> if either the first operand or 
	 * the second operand is <tt>true</tt>, or if both are <tt>true</tt>. If both operands 
	 * are <tt>false</tt>, it returns <tt>false</tt>.</p>
	 * <p>In JavaScript, although the || operator is most often used simply as a Boolean OR 
	 * operator, it, like the && operator, has more complex behavior. It starts by evaluating 
	 * its first operand, the expression on its left. If the value of this expression can 
	 * be converted to <tt>true</tt>, it returns the unconverted value of the left-side 
	 * expression. Otherwise, it evaluates its second operand, the expression on its right, 
	 * and returns the value of that expression. In JavaScript 1.0 and JavaScript 1.1, if the 
	 * left-side expression can be converted to <tt>true</tt>, the operator returns <tt>true</tt> 
	 * and doesn't return the unconverted value of the left-side expression.</p>
	 * <p>As with the {@link #and(Object)} operation, you should avoid right-side 
	 * operands that include side effects, unless you purposely want to use the fact that 
	 * the right-side expression may not be evaluated in JavaScript.</p>
	 * <p>Even when this operation is used with operands that are not boolean values, it 
	 * can still be considered a Boolean OR operator because its return value, whatever the 
	 * type, can be converted to a boolean value.</p>
	 * @param o A value or object.
	 * @return The logical OR of the two operands.
	 * @since Descripter 1.0
	 */
	public final boolean bor(Object o) {
		return bor(this, o);
	}

	/**
	 * <p>Inverts the boolean value of the current variable, resembling the 
	 * logical NOT operator in JavaScript.</p>
	 * <p>This operation converts the current variable to a boolean value using 
	 * the following rules if necessary before inverting the converted value.</p>
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
	 * @return The inverted boolean value.
	 * @since Descripter 1.0
	 */
	public final boolean bnot() {
		return bnot(this);
	}

	/**
	 * <p>Less-than operation, resembling that of JavaScript, evaluates to <tt>true</tt> if 
	 * the current variable is less than the second operand; otherwise it 
	 * evaluates to <tt>false</tt>.</p>
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
	 * <p>The less-than-or-equal and greater-than-or-equal operations do not rely on the 
	 * equality or identity operations for determining whether two values are "equal." 
	 * Instead, the less-than-or-equal operator is simply defined as "not greater than", 
	 * and the greater-than-or-equal operator is defined as "not less than". The one 
	 * exception occurs when either operand is (or converts to) <tt>NaN</tt>, in which case 
	 * all comparison operations return <tt>false</tt>.</p>
	 * @param o A value or object.
	 * @return <tt>true</tt> if the current variable is less than the second 
	 * operand; otherwise <tt>false</tt>.
	 * @since Descripter 1.0
	 */
	public final boolean lt(Object o) {
		return lt(this, o);
	}

	/**
	 * <p>Less-than-or-equal operation, resembling that of JavaScript, evaluates to 
	 * <tt>true</tt> if the current variable is less than or equal to the second 
	 * operand; otherwise it evaluates to <tt>false</tt>.</p>
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
	 * <p>The less-than-or-equal and greater-than-or-equal operations do not rely on the 
	 * equality or identity operations for determining whether two values are "equal." 
	 * Instead, the less-than-or-equal operator is simply defined as "not greater than", 
	 * and the greater-than-or-equal operator is defined as "not less than". The one 
	 * exception occurs when either operand is (or converts to) <tt>NaN</tt>, in which case 
	 * all comparison operations return <tt>false</tt>.</p>
	 * @param o A value or object.
	 * @return <tt>true</tt> if the current variable is less than or equal to 
	 * the second operand; otherwise <tt>false</tt>.
	 * @since Descripter 1.0
	 */
	public final boolean lte(Object o) {
		return lte(this, o);
	}

	/**
	 * <p>Greater-than operation, resembling that of JavaScript, evaluates to <tt>true</tt> if 
	 * the current variable is greater than the second operand; otherwise it 
	 * evaluates to <tt>false</tt>.</p>
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
	 * <p>The less-than-or-equal and greater-than-or-equal operations do not rely on the 
	 * equality or identity operations for determining whether two values are "equal." 
	 * Instead, the less-than-or-equal operator is simply defined as "not greater than", 
	 * and the greater-than-or-equal operator is defined as "not less than". The one 
	 * exception occurs when either operand is (or converts to) <tt>NaN</tt>, in which case 
	 * all comparison operations return <tt>false</tt>.</p>
	 * @param o A value or object.
	 * @return <tt>true</tt> if the current variable is greater than the second 
	 * operand; otherwise <tt>false</tt>.
	 * @since Descripter 1.0
	 */
	public final boolean gt(Object o) {
		return gt(this, o);
	}

	/**
	 * <p>Greater-than-or-equal operation, resembling that of JavaScript, evaluates to 
	 * <tt>true</tt> if the current variable is greater than or equal to the 
	 * second operand; otherwise it evaluates to <tt>false</tt>.</p>
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
	 * <p>The less-than-or-equal and greater-than-or-equal operations do not rely on the 
	 * equality or identity operations for determining whether two values are "equal." 
	 * Instead, the less-than-or-equal operator is simply defined as "not greater than", 
	 * and the greater-than-or-equal operator is defined as "not less than". The one 
	 * exception occurs when either operand is (or converts to) <tt>NaN</tt>, in which case 
	 * all comparison operations return <tt>false</tt>.</p>
	 * @param o A value or object.
	 * @return <tt>true</tt> if the current variable is greater than or equal to 
	 * the second operand; otherwise <tt>false</tt>.
	 * @since Descripter 1.0
	 */
	public final boolean gte(Object o) {
		return gte(this, o);
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
	 * @param o Any value or object.
	 * @return <tt>true</tt> if the first operand equals the second; <tt>false</tt>, 
	 * otherwise;
	 * @since Descripter 1.0
	 */
	public final boolean eq(Object o) {
		return eq(this, o);
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
	 * To check whether a value is <tt>NaN</tt>, use the global <tt>isNaN</tt> service.</li>
	 * <li>If both values are strings and contain exactly the same characters in the same 
	 * positions, they are identical. If the strings differ in length or content, they are 
	 * not identical. Note that in some cases, the Unicode standard allows more than one 
	 * way to encode the same string. For efficiency, however, JavaScript's string 
	 * comparison compares strictly on a character-by-character basis, and it assumes that 
	 * all strings have been converted to a "normalized form" before they are compared.</li>
	 * <li>If both values are the boolean value <tt>true</tt> or both are the boolean 
	 * value <tt>false</tt>, they are identical.</li>
	 * <li>If both values refer to the same object, array, or function, they are identical. 
	 * If they refer to different objects (or arrays or functions) they are not identical, 
	 * even if both objects have identical properties or both arrays have identical elements.</li>
	 * <li>If both values are null or both values are undefined, they are identical.</li>
	 * </ul>
	 * @param o Any value or object.
	 * @return <tt>true</tt> if the first operand is identical to the second; 
	 * <tt>false</tt>, otherwise;
	 * @since Descripter 1.0
	 */
	public final boolean eqs(Object o) {
		return eqs(this, o);
	}
}
