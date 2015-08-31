
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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.descripter.js.api.Function;

/**
 * <p>Emulates JavaScript Date objects.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class CDate extends CObject
{
	private final Calendar cal;

	/**
	 * <p>Constructs a {@link CObject} context of this type.</p>
	 * @param constructor The constructor {@link Function} object.
	 * @since Descripter 1.0
	 */
	public CDate(Function<?> constructor) {
		super(constructor);
		cal = new GregorianCalendar();
	}

	/**
	 * <p>Constructs a {@link CObject} context of this type.</p>
	 * @param constructor The constructor {@link Function} object.
	 * @param n The initial number value for the date object in milliseconds
	 * @since Descripter 1.0
	 */
	public CDate(Function<?> constructor, Number n) {
		super(constructor);
		cal = new GregorianCalendar();
		cal.setTimeInMillis(n.longValue());
	}

	/**
	 * <p>Constructs a {@link CObject} context of this type.</p>
	 * @param constructor The constructor {@link Function} object.
	 * @param s A string representation for the initial value of the date object
	 * @since Descripter 1.0
	 */
	public CDate(Function<?> constructor, String s) {
		super(constructor);
		cal = new GregorianCalendar();
		try {
			cal.setTime(DateFormat.getInstance().parse(s));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>Returns the day of the month of the current date instance, in local time.</p>
	 * @return The day of the month of the current date instance, using local time. 
	 * Return values are between 1 and 31.
	 * @see #getUTCDate()
	 * @since Descripter 1.0
	 */
	public final Number getDate() {
		return cal.get(Calendar.DATE);
	}

	/**
	 * <p>Returns the day of the week of the current date instance, in local time.</p>
	 * @return The day of the week of the current date instance, using local time. Return values 
	 * are between 0 (Sunday) and 6 (Saturday).
	 * @see #getUTCDay()
	 * @since Descripter 1.0
	 */
	public final Number getDay() {
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * <p>Returns the year of the current date instance in full four-digit form, in local 
	 * time.</p>
	 * @return The year that results when the current date instance is expressed in local 
	 * time. The return value is a full four-digit year, including the century, not a 
	 * two-digit abbreviation..
	 * @see #getUTCFullYear()
	 * @since Descripter 1.0
	 */
	public final Number getFullYear() {
		return cal.get(Calendar.YEAR);
	}

	/**
	 * <p>Returns the hours field of the current date instance, in local time.</p>
	 * @return The hours field, expressed in local time, of the current date instance. 
	 * Return values are between 0 (midnight) and 23 (11 p.m.).
	 * @see #getUTCHours()
	 * @since Descripter 1.0
	 */
	public final Number getHours() {
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * <p>Returns the milliseconds field of the current date instance, in local time.</p>
	 * @return The milliseconds field, expressed in local time, of the current date instance.
	 * @see #getUTCMilliseconds()
	 * @since Descripter 1.0
	 */
	public final Number getMilliseconds() {
		return cal.get(Calendar.MILLISECOND);
	}

	/**
	 * <p>Returns the minutes field of a Date object, in local or universal time.</p>
	 * @return The minutes field, expressed in local time, of the current date instance. 
	 * Return values are between 0 and 59.
	 * @see #getUTCMinutes()
	 * @since Descripter 1.0
	 */
	public final Number getMinutes() {
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * <p>Returns the month field of the current date instance, in local time.</p>
	 * @return The month of the year that results when the current date instance is 
	 * expressed in local time. The return value is an integer between 0 (January) 
	 * and 11 (December). Note that the date represents the first day of the month as 1 
	 * but represents the first month of the year as 0.
	 * @see #getUTCMonth()
	 * @since Descripter 1.0
	 */
	public final Number getMonth() {
		return cal.get(Calendar.MONTH);
	}

	/**
	 * <p>Returns the seconds field of the current date instance, in local time.</p>
	 * @return The seconds field, expressed in local time, of the current date instance. 
	 * Return values are between 0 and 59.
	 * @see #getUTCSeconds()
	 * @since Descripter 1.0
	 */
	public final Number getSeconds() {
		return cal.get(Calendar.SECOND);
	}

	/**
	 * <p>Returns the internal, millisecond representation of the current date instance.</p>
	 * <p>It converts the current date instance to a single integer. This is useful when 
	 * you want to compare two date instances or to determine the time elapsed between two 
	 * dates. Note that the millisecond representation of a date is independent of the 
	 * time zone, so there is no <tt>getUTCTime()</tt> method in addition to this one. 
	 * Don't confuse this method with the {@link #getDay()} and {@link #getDate()} methods, 
	 * which return the day of the week and the day of the month, respectively</p>
	 * @return The millisecond representation of the current date instance, that is, the 
	 * number of milliseconds between midnight (GMT) on 1/1/1970 and the date and time 
	 * specified by the date.
	 * @see #setTime(Number)
	 * @since Descripter 1.0
	 */
	public final Number getTime() {
		return cal.getTimeInMillis();
	}

	/**
	 * <p>Returns the difference, in minutes, between the local and UTC representations of 
	 * the current date instance. Note that the value returned depends on whether daylight 
	 * saving time is or would be in effect at the specific date.</p>
	 * <p>The return value is measured in minutes, rather than hours, because some 
	 * countries have time zones that are not at even one-hour intervals.</p>
	 * @return The difference, in minutes, between GMT and local time.
	 * @since Descripter 1.0
	 */
	public final Number getTimezoneOffset() {
		return cal.get(Calendar.ZONE_OFFSET) / 60000;
	}

	/**
	 * <p>Returns the day of the month of the current date instance, in universal time.</p>
	 * @return The day of the month (a value between 1 and 31) that results when the 
	 * current date instance is expressed in universal time.
	 * @see #getDate()
	 * @since Descripter 1.0
	 */
	public final Number getUTCDate() {
		return cal.get(Calendar.DATE);
	}

	/**
	 * <p>Returns the day of the week of current date instance, in universal time.</p>
	 * @return The day of the week that results when the current date instance is expressed 
	 * in universal time. Return values are between 0 (Sunday) and 6 (Saturday).
	 * @see #getDay()
	 * @since Descripter 1.0
	 */
	public final Number getUTCDay() {
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * <p>Returns the year of the current date instance in full four-digit form, in 
	 * universal time.</p>
	 * @return The year that results when the current date instance is expressed in 
	 * universal time. The return value is a full four-digit year, not a two-digit 
	 * abbreviation.
	 * @see #getFullYear()
	 * @since Descripter 1.0
	 */
	public final Number getUTCFullYear() {
		return cal.get(Calendar.YEAR);
	}

	/**
	 * <p>Returns the hours field of the current date instance, in universal time.</p>
	 * @return The hours field, expressed in universal time, of the current date instance. 
	 * The return value is an integer between 0 (midnight) and 23 (11 p.m.).
	 * @see #getHours()
	 * @since Descripter 1.0
	 */
	public final Number getUTCHours() {
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * <p>Returns the milliseconds field of current date instance, in universal time.</p>
	 * @return The milliseconds field, expressed in universal time, of the current date instance.
	 * @see #getMilliseconds()
	 * @since Descripter 1.0
	 */
	public final Number getUTCMilliseconds() {
		return cal.get(Calendar.MILLISECOND);
	}

	/**
	 * <p>Returns the minutes field of the current date instance, in universal time.</p>
	 * @return The minutes field, expressed in universal time, of the current date instance. 
	 * The return value is an integer between 0 and 59.
	 * @see #getMinutes()
	 * @since Descripter 1.0
	 */
	public final Number getUTCMinutes() {
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * <p>Returns the month field of the current date instance, in universal time.</p>
	 * @return The month of the year that results when the current date instance is 
	 * expressed in universal time. The return value is an integer between 0 (January) 
	 * and 11 (December). Note that the date represents the first day of the month as 1 
	 * but represents the first month of the year as 0.
	 * @see #getMonth()
	 * @since Descripter 1.0
	 */
	public final Number getUTCMonth() {
		return cal.get(Calendar.MONTH);
	}

	/**
	 * <p>Returns the seconds field of the current date instance, in universal time.</p>
	 * @return The seconds field, expressed in universal time, of the current date instance. 
	 * The return value is an integer between 0 and 59.
	 * @see #getSeconds()
	 * @since Descripter 1.0
	 */
	public final Number getUTCSeconds() {
		return cal.get(Calendar.SECOND);
	}

	/**
	 * <p>Sets the day of the month field of the current date instance, using local time.</p>
	 * @param day An integer between 1 and 31 that is used as the new value in local time 
	 * of the day-of-the-month field of the current date instance.
	 * @return The millisecond representation of the adjusted date. 
	 * Prior to ECMAScript standardization, this method returns nothing.
	 * @see #setUTCDate(Number)
	 * @since Descripter 1.0
	 */
	public final Number setDate(Number day) {
		cal.set(Calendar.DATE, day.intValue());
		return getTime();
	}

	/**
	 * <p>Sets the year and optionally month and day fields of the current date instance, 
	 * using local time.</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * yr</tt>, The year, expressed in local time, to be set in the current date instance. 
	 * This argument should be an integer that includes the century, such as 1999; it 
	 * should not be an abbreviation, such as 99.</li><li><tt>
	 * mo</tt>, An optional integer between 0 and 11 that is used as the new value in 
	 * local time of the month field of the current date instance.</li><li><tt>
	 * day</tt>, An optional integer between 1 and 31 that is used as the new value in 
	 * local time of the day-of-the-month field of the current date instance.</li></ul>
	 * @return The millisecond representation of the adjusted date. 
	 * @see #setUTCFullYear(CArray)
	 * @since Descripter 1.0
	 */
	public final Number setFullYear(CArray args) {
		if (args.has(0)) {
			cal.set(Calendar.YEAR, intValue(args.get(0)));
		}
		if (args.has(1)) {
			cal.set(Calendar.MONTH, intValue(args.get(1)));
		}
		if (args.has(2)) {
			cal.set(Calendar.DAY_OF_MONTH, intValue(args.get(2)));
		}
		return getTime();
	}

	/**
	 * <p>Sets the hour field and optionally the minutes, seconds, and milliseconds fields 
	 * of the current date instance, using local time.</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * hr</tt>, An integer between 0 (midnight) and 23 (11 p.m.) local time that is set 
	 * as the new hours value of the current date instance.</li><li><tt>
	 * min</tt>, An optional integer, between 0 and 59, that is used as the new value 
	 * in local time of the minutes field of the current date instance. This argument is 
	 * not supported prior to ECMAScript standardization.</li><li><tt>
	 * sec</tt>, An optional integer, between 0 and 59, that is used as the new value 
	 * in local time of the seconds field of the current date instance. This argument is 
	 * not supported prior to ECMAScript standardization.</li><li><tt>
	 * ms</tt>, An optional integer, between 0 and 999, that is used as the new value 
	 * in local time of the milliseconds field of the current date instance. This argument 
	 * is not supported prior to ECMAScript standardization.</li></ul>
	 * @return The millisecond representation of the adjusted date. 
	 * Prior to ECMAScript standardization, this method returns nothing.
	 * @see #setUTCHours(CArray)
	 * @since Descripter 1.0
	 */
	public final Number setHours(CArray args) {
		if (args.has(0)) {
			cal.set(Calendar.HOUR, intValue(args.get(0)));
		}
		if (args.has(1)) {
			cal.set(Calendar.MINUTE, intValue(args.get(1)));
		}
		if (args.has(2)) {
			cal.set(Calendar.SECOND, intValue(args.get(2)));
		}
		if (args.has(3)) {
			cal.set(Calendar.MILLISECOND, intValue(args.get(3)));
		}
		return getTime();
	}

	/**
	 * <p>Sets the milliseconds field of a date, using local time.</p>
	 * @param ms The milliseconds field, expressed in local time, to be set in the current 
	 * date instance. This argument should be an integer between 0 and 999.
	 * @return The millisecond representation of the adjusted date.
	 * @see #setUTCMilliseconds(Number)
	 * @since Descripter 1.0
	 */
	public final Number setMilliseconds(Number ms) {
		cal.set(Calendar.MILLISECOND, ms.intValue());
		return getTime();
	}

	/**
	 * <p>Sets the minutes field and optionally the seconds and milliseconds fields of the 
	 * current date instance, using local time.</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * min</tt>, An optional integer, between 0 and 59, that is used as the new value 
	 * in local time of the minutes field of the current date instance. This argument is 
	 * not supported prior to ECMAScript standardization.</li><li><tt>
	 * sec</tt>, An optional integer, between 0 and 59, that is used as the new value 
	 * in local time of the seconds field of the current date instance. This argument is 
	 * not supported prior to ECMAScript standardization.</li><li><tt>
	 * ms</tt>, An optional integer, between 0 and 999, that is used as the new value 
	 * in local time of the milliseconds field of the current date instance. This argument 
	 * is not supported prior to ECMAScript standardization.</li></ul>
	 * @return The millisecond representation of the adjusted date. 
	 * Prior to ECMAScript standardization, this method returns nothing.
	 * @see #setUTCMinutes(CArray)
	 * @since Descripter 1.0
	 */
	public final Number setMinutes(CArray args) {
		if (args.has(0)) {
			cal.set(Calendar.MINUTE, intValue(args.get(0)));
		}
		if (args.has(1)) {
			cal.set(Calendar.SECOND, intValue(args.get(1)));
		}
		if (args.has(2)) {
			cal.set(Calendar.MILLISECOND, intValue(args.get(2)));
		}
		return getTime();
	}

	/**
	 * <p>Sets the month field and optionally the day of the month of the current date 
	 * instance, using universal time.</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * mo</tt>, The month, expressed in universal time, to be set in the current date
	 * instance. This argument should be an integer between 0 (January) and 11 (December). 
	 * Note that months are numbered beginning with 0, while days within the month are 
	 * numbered beginning with 1.</li><li><tt>
	 * day</tt>, An optional integer between 1 and 31 that is used as the new value in 
	 * universal time of the day-of-the-month field of the current date instance.</li></ul>
	 * @return The millisecond representation of the adjusted date. 
	 * @see #setUTCMonth(CArray)
	 * @since Descripter 1.0
	 */
	public final Number setMonth(CArray args) {
		if (args.has(0)) {
			cal.set(Calendar.MONTH, intValue(args.get(0)));
		}
		if (args.has(1)) {
			cal.set(Calendar.DAY_OF_MONTH, intValue(args.get(1)));
		}
		return getTime();
	}

	/**
	 * <p>Sets the seconds field and optionally the milliseconds field of the current date 
	 * instance, using local .</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * sec</tt>, An integer between 0 and 59 that is set as the seconds value for the 
	 * current date instance.</li><li><tt>
	 * ms</tt>, An optional integer, between 0 and 999, that is used as the new value 
	 * in local time of the milliseconds field of the current date instance. This argument 
	 * is not supported prior to ECMAScript standardization.</li></ul>
	 * @return The millisecond representation of the adjusted date. Prior to ECMAScript 
	 * standardization, this method returns nothing.
	 * @see #setUTCSeconds(CArray)
	 * @since Descripter 1.0
	 */
	public final Number setSeconds(CArray args) {
		if (args.has(0)) {
			cal.set(Calendar.SECOND, intValue(args.get(0)));
		}
		if (args.has(1)) {
			cal.set(Calendar.MILLISECOND, intValue(args.get(1)));
		}
		return getTime();
	}

	/**
	 * <p>Sets the fields of the current date instance using the millisecond format.</p>
	 * @param time The number of milliseconds between the desired date and time and 
	 * midnight GMT on January 1, 1970. Representing a date in this millisecond format makes 
	 * it independent of time zone.
	 * @return The <tt>time</tt> argument. Prior to ECMAScript standardization, 
	 * this method returns nothing.
	 * @since Descripter 1.0
	 */
	public final Number setTime(Number time) {
		cal.setTimeInMillis(time.longValue());
		return getTime();
	}

	/**
	 * <p>Sets the day of the month field of the current date instance, using universal 
	 * time.</p>
	 * @param day The day of the month, expressed in universal time, to be set in the 
	 * current date instance. This argument should be an integer between 1 and 31.
	 * @return The millisecond representation of the adjusted date. 
	 * @see #setDate(Number)
	 * @since Descripter 1.0
	 */
	public final Number setUTCDate(Number day) {
		cal.set(Calendar.DATE, day.intValue());
		return getTime();
	}

	/**
	 * <p>Sets the year and optionally month and day fields of the current date instance, 
	 * using universal time.</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * yr</tt>, The year, expressed in universal time, to be set in the current date 
	 * instance. This argument should be an integer that includes the century, such as 
	 * 1999, not an abbreviation, such as 99.</li><li><tt>
	 * mo</tt>, An optional integer between 0 and 11 that is used as the new value in 
	 * universal time of the month field of the current date instance. Note that months 
	 * are numbered beginning with 0, while days within the month are numbered beginning 
	 * with 1.</li><li><tt>
	 * day</tt>, An optional integer between 1 and 31 that is used as the new value in 
	 * universal time of the day-of-the-month field of the current date instance.</li></ul>
	 * @return The millisecond representation of the adjusted date. 
	 * @see #setFullYear(CArray)
	 * @since Descripter 1.0
	 */
	public final Number setUTCFullYear(CArray args) {
		if (args.has(0)) {
			cal.set(Calendar.YEAR, intValue(args.get(0)));
		}
		if (args.has(1)) {
			cal.set(Calendar.MONTH, intValue(args.get(1)));
		}
		if (args.has(2)) {
			cal.set(Calendar.DAY_OF_MONTH, intValue(args.get(2)));
		}
		return getTime();
	}

	/**
	 * <p>Sets the hour field and optionally the minutes, seconds, and milliseconds fields 
	 * of the current date instance, using universal time.</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * hr</tt>, The hours field, expressed in universal time, to be set in the current 
	 * date instance. This argument should be an integer between 0 (midnight) and 23 
	 * (11 p.m.).</li><li><tt>
	 * min</tt>, An optional integer, between 0 and 59, that is used as the new value 
	 * in universal time of the minutes field of the current date instance.</li><li><tt>
	 * sec</tt>, An optional integer, between 0 and 59, that is used as the new value 
	 * in universal time of the seconds field of the current date instance.</li><li><tt>
	 * ms</tt>, An optional integer, between 0 and 999, that is used as the new value 
	 * in universal time of the milliseconds field of the current date instance.</li></ul>
	 * @return The millisecond representation of the adjusted date.
	 * @see #setHours(CArray)
	 * @since Descripter 1.0
	 */
	public final Number setUTCHours(CArray args) {
		if (args.has(0)) {
			cal.set(Calendar.HOUR, intValue(args.get(0)));
		}
		if (args.has(1)) {
			cal.set(Calendar.MINUTE, intValue(args.get(1)));
		}
		if (args.has(2)) {
			cal.set(Calendar.SECOND, intValue(args.get(2)));
		}
		if (args.has(3)) {
			cal.set(Calendar.MILLISECOND, intValue(args.get(3)));
		}
		return getTime();
	}

	/**
	 * <p>Sets the milliseconds field of the current date instance, using universal time.</p>
	 * @param ms The milliseconds field, expressed in universal time, to be set in the 
	 * current date instance. This argument should be an integer between 0 and 999.
	 * @return The millisecond representation of the adjusted date.
	 * @see #setMilliseconds(Number)
	 * @since Descripter 1.0
	 */
	public final Number setUTCMilliseconds(Number ms) {
		cal.set(Calendar.MILLISECOND, ms.intValue());
		return getTime();
	}

	/**
	 * <p>Sets the minutes field of the current date instance, using universal time.</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * min</tt>, An optional integer, between 0 and 59, that is used as the new value 
	 * in universal time of the minutes field of the current date instance.</li><li><tt>
	 * sec</tt>, An optional integer, between 0 and 59, that is used as the new value 
	 * in universal time of the seconds field of the current date instance.</li><li><tt>
	 * ms</tt>, An optional integer, between 0 and 999, that is used as the new value 
	 * in universal time of the milliseconds field of the current date instance.</li></ul>
	 * @return The millisecond representation of the adjusted date. 
	 * Prior to ECMAScript standardization, this method returns nothing.
	 * @see #setMinutes(CArray)
	 * @since Descripter 1.0
	 */
	public final Number setUTCMinutes(CArray args) {
		if (args.has(0)) {
			cal.set(Calendar.MINUTE, intValue(args.get(0)));
		}
		if (args.has(1)) {
			cal.set(Calendar.SECOND, intValue(args.get(1)));
		}
		if (args.has(2)) {
			cal.set(Calendar.MILLISECOND, intValue(args.get(2)));
		}
		return getTime();
	}

	/**
	 * <p>Sets the month field and optionally the day of the month of the current date 
	 * instance, using universal time.</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * mo</tt>, The month, expressed in universal time, to be set in the current date
	 * instance. This argument should be an integer between 0 (January) and 11 (December). 
	 * Note that months are numbered beginning with 0, while days within the month are 
	 * numbered beginning with 1.</li><li><tt>
	 * day</tt>, An optional integer between 1 and 31 that is used as the new value in 
	 * universal time of the day-of-the-month field of the current date instance.</li></ul>
	 * @return The millisecond representation of the adjusted date. 
	 * @see #setMonth(CArray)
	 * @since Descripter 1.0
	 */
	public final Number setUTCMonth(CArray args) {
		if (args.has(0)) {
			cal.set(Calendar.MONTH, intValue(args.get(0)));
		}
		if (args.has(1)) {
			cal.set(Calendar.DAY_OF_MONTH, intValue(args.get(1)));
		}
		return getTime();
	}

	/**
	 * <p>Sets the seconds field and optionally the milliseconds field of the current date 
	 * instance, using universal time.</p>
	 * @param args An array of arguments:<ul><li><tt>
	 * sec</tt>, The seconds field, expressed in universal time, to be set in the current 
	 * date instance. This argument should be an integer between 0 and 59.</li><li><tt>
	 * ms</tt>, An optional integer, between 0 and 999, that is used as the new value 
	 * in universal time of the milliseconds field of the current date instance.</li></ul>
	 * @return The millisecond representation of the adjusted date.
	 * @see #setSeconds(CArray)
	 * @since Descripter 1.0
	 */
	public final Number setUTCSeconds(CArray args) {
		if (args.has(0)) {
			cal.set(Calendar.SECOND, intValue(args.get(0)));
		}
		if (args.has(1)) {
			cal.set(Calendar.MILLISECOND, intValue(args.get(1)));
		}
		return getTime();
	}

	/**
	 * <p>Returns a string that represents the date portion of the current date instance, 
	 * expressed in the local time zone.</p>
	 * @return An implementation-dependent, human-readable string representation of the 
	 * date portion of the current date instance, expressed in the local time zone.
	 * @see #toLocaleDateString()
	 * @see #toLocaleString()
	 * @see #toLocaleTimeString()
	 * @see #toString()
	 * @see #toTimeString()
	 * @since Descripter 1.0
	 */
	public final String toDateString() {
		return cal.getTime().toString();
	}


	/**
	 * <p>Returns a string that represents the date portion of the current date instance, 
	 * expressed in the local time zone, using the local date formatting conventions.</p>
	 * @return An implementation-dependent, human-readable string representation of the 
	 * date portion of the current date instance, expressed in the local time zone and 
	 * formatted according to local conventions.
	 * @see #toDateString()
	 * @see #toLocaleString()
	 * @see #toLocaleTimeString()
	 * @see #toString()
	 * @see #toTimeString()
	 * @since Descripter 1.0
	 */
	public final String toLocaleDateString() {
		return cal.getTime().toString();
	}

	/**
	 * <p>Converts a date to a string, using the local time zone and the local date 
	 * formatting conventions.</p>
	 * <p>This method also uses local conventions for date and time formatting, so the 
	 * format may vary from platform to platform and from country to country. It returns 
	 * a string formatted in what is likely the user's preferred date and time format.</p>
	 * @return A string representation of the date and time specified by the current date 
	 * instance. The date and time are represented in the local time zone and formatted 
	 * using locally appropriate conventions.
	 * @see #toLocaleDateString()
	 * @see #toLocaleTimeString()
	 * @see #toString()
	 * @see #toUTCString()
	 * @since Descripter 1.0
	 */
	@Override
	public final String toLocaleString() {
		return cal.getTime().toString();
	}

	/**
	 * <p>Returns a string that represents the time portion of the current date instance, 
	 * expressed in the local time zone, using the local time formatting conventions.</p>
	 * @return An implementation-dependent, human-readable string representation of the 
	 * time portion of the current date instance, expressed in the local time zone and 
	 * formatted according to local conventions.
	 * @see #toDateString()
	 * @see #toLocaleDateString()
	 * @see #toLocaleString()
	 * @see #toString()
	 * @see #toTimeString()
	 * @since Descripter 1.0
	 */
	public final String toLocaleTimeString() {
		return cal.getTime().toString();
	}

	/**
	 * <p>Returns a string that represents the time portion of the current date instance, 
	 * expressed in the local time zone.</p>
	 * @return An implementation-dependent, human-readable string representation of the 
	 * time portion of the current date instance, expressed in the local time zone.
	 * @see #toDateString()
	 * @see #toLocaleDateString()
	 * @see #toLocaleString()
	 * @see #toLocaleTimeString()
	 * @see #toString()
	 * @since Descripter 1.0
	 */
	public final String toTimeString() {
		return cal.getTime().toString();
	}

	/**
	 * <p>Returns a string representation of the current object.</p>
	 * @return The string representation of the current object
	 * @since Descripter 1.0
	 */
	@Override
	public final String toString() {
		return cal.getTime().toString();
	}


	/**
	 * <p>Converts the current date instance to a string, using universal time.</p>
	 * @return A human-readable string representation, expressed in universal time, of the 
	 * current date instance.
	 * @see #toLocaleString()
	 * @see #toString()
	 * @since Descripter 1.0
	 */
	public final String toUTCString() {
		return cal.getTime().toString();
	}

	/**
	 * <p>Returns the primitive value associated with this object, if there is one. </p>
	 * @return The primitive value associated with this object, if there is one, or this object itself.
	 * @since Descripter 1.0
	 */
	@Override
	public final Number valueOf() {
		return cal.getTime().getTime();
	}
}
