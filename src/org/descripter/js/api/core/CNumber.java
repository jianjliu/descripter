
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

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.descripter.js.api.Function;

/**
 * <p>Emulates JavaScript Number objects.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class CNumber extends CObject
{
	private final Number value;

	/**
	 * <p>Constructs a {@link CObject} context of this type.</p>
	 * @param constructor The constructor {@link Function} object.
	 * @param value The initial value
	 * @since Descripter 1.0
	 */
	public CNumber(Function<?> constructor, Number value) {
		super(constructor);
		this.value = value;
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
	public final Number valueOf() {
		return value;
	}

	/**
	 * <p>Converts the current number to a string using exponential notation with the 
	 * specified number of digits after the decimal place.</p>
	 * @param digits The number of digits that appears after the decimal point. This may be a 
	 * value between 0 and 20, inclusive, and implementations may optionally support a larger 
	 * range of values. If this argument is undefined, as many digits as necessary are used.
	 * @return A string representation of the current number, in exponential notation, 
	 * with one digit before the decimal place and <tt>digits</tt> digits after the 
	 * decimal place. The fractional part of the number is rounded, or padded with zeros, 
	 * as necessary, so that it has the specified length.
	 * @throws RuntimeException JavaScript throws a <tt>RangeError</tt> if 
	 * <tt>digits</tt> is too small or too large.
	 * @throws RuntimeException JavaScript throws a <tt>TypeError</tt> if this method 
	 * is invoked on an instance that is not a number.
	 * @see #toFixed(Object)
	 * @see #toLocaleString()
	 * @see #toPrecision()
	 * @see #toPrecision(int)
	 * @see #toString()
	 * @since Descripter 1.0
	 */
	public final String toExponential(Object digits) {
		DecimalFormat df = new DecimalFormat("0.0E0");
		df.setDecimalSeparatorAlwaysShown(false);
		df.setMaximumFractionDigits(intValue(digits));
		return df.format(value);
	}

	/**
	 * <p>Converts the current number to a string that contains a specified number of 
	 * digits after the decimal place.</p>
	 * @param digits The number of digits to appear after the decimal point; this may be a 
	 * value between 0 and 20, inclusive, and implementations may optionally support a 
	 * larger range of values. If this argument is undefined, it is treated as 0.
	 * @return A string representation of the current number that does not use exponential 
	 * notation and has exactly <tt>digits</tt> digits after the decimal place. The number 
	 * is rounded if necessary, and the fractional part is padded with zeros if necessary so 
	 * that it has the specified length. If the current number is greater than 1e+21, this 
	 * method simply calls {@link #toString()} and returns a string in exponential 
	 * notation.
	 * @throws RuntimeException JavaScript throws a <tt>RangeError</tt> if 
	 * <tt>digits</tt> is too small or too large.
	 * @throws RuntimeException JavaScript throws a <tt>TypeError</tt> if this method 
	 * is invoked on an instance that is not a number.
	 * @see #toExponential(Object)
	 * @see #toLocaleString()
	 * @see #toPrecision()
	 * @see #toPrecision(int)
	 * @see #toString()
	 * @since Descripter 1.0
	 */
	public final String toFixed(Object digits) {
		DecimalFormat df = new DecimalFormat("0.0");
		df.setDecimalSeparatorAlwaysShown(false);
		df.setMaximumFractionDigits(intValue(digits));
		df.setRoundingMode(RoundingMode.DOWN);
		return df.format(value);
	}

	/**
	 * <p>Converts the current number to a string.</p>
	 * <p>This method simply calls {@link #toString()} to convert the number to a base-10 
	 * value.</p>
	 * @return A string representation of the current number. The number is rounded or 
	 * padded with zeros as necessary.
	 * @throws RuntimeException JavaScript throws a <tt>TypeError</tt> if this method 
	 * is invoked on an instance that is not a number.
	 * @see #toExponential(Object)
	 * @see #toFixed(Object)
	 * @see #toLocaleString()
	 * @see #toPrecision(int)
	 * @see #toString()
	 * @since Descripter 1.0
	 */
	public final String toPrecision() {
		return toString(value);
	}

	/**
	 * <p>Converts the current number to a string using the specified number of significant 
	 * digits. Uses exponential or fixed-point notation depending on the size of the number 
	 * and the number of significant digits specified.</p>
	 * @param precision The number of significant digits to appear in the returned string. 
	 * This may be a value between 1 and 21, inclusive. Implementations are allowed to 
	 * optionally support larger and smaller values of precision. If this argument is 
	 * undefined, the {@link #toString()} method is used instead to convert the number to 
	 * a base-10 value.
	 * @return A string representation of the current number that contains 
	 * <tt>precision</tt> significant digits. If <tt>precision</tt> is large 
	 * enough to include all the digits of the integer part of the number, the returned 
	 * string uses fixed-point notation. Otherwise, exponential notation is used with one 
	 * digit before the decimal place and <tt>precision - 1</tt> digits after the 
	 * decimal place. The number is rounded or padded with zeros as necessary.
	 * @throws RuntimeException JavaScript throws a <tt>RangeError</tt> if 
	 * <tt>digits</tt> is too small or too large.
	 * @throws RuntimeException JavaScript throws a <tt>TypeError</tt> if this method 
	 * is invoked on an instance that is not a number.
	 * @see #toExponential(Object)
	 * @see #toFixed(Object)
	 * @see #toLocaleString()
	 * @see #toPrecision()
	 * @see #toString()
	 * @since Descripter 1.0
	 */
	public final String toPrecision(int precision) {
		DecimalFormat df = new DecimalFormat("0.0E0");
		df.setMinimumIntegerDigits(precision);
		df.setMaximumFractionDigits(0);
		df.setDecimalSeparatorAlwaysShown(false);
		double d = Double.parseDouble(df.format(value));
		if (value instanceof Double || value instanceof Float) {
			return Double.toString(d);
		} else {
			return Long.toString((long)d);
		}
	}
}
