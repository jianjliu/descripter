
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

import org.descripter.js.*;
import org.descripter.js.api.core.*;
import org.descripter.js.api.global.*;

/**
 * <p>Facilitates with the core features of a JavaScript engine.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Core extends Global<Core>
{
	/**
	 * <p>The runtime {@link Key} for the <tt>undefined</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _undefined             = key("undefined");
	/**
	 * <p>The runtime {@link Key} for the <tt>this</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _this                  = key("this");
	/**
	 * <p>The runtime {@link Key} for the <tt>null</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _null                  = key("null");

	/**
	 * <p>The runtime {@link Key} for the <tt>Array</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _Array                 = key("Array");
	/**
	 * <p>The runtime {@link Key} for the <tt>Boolean</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _Boolean               = key("Boolean");
	/**
	 * <p>The runtime {@link Key} for the <tt>Date</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _Date                  = key("Date");
	/**
	 * <p>The runtime {@link Key} for the <tt>Error</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _Error                 = key("Error");
	/**
	 * <p>The runtime {@link Key} for the <tt>Function</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _Function              = key("Function");
	/**
	 * <p>The runtime {@link Key} for the <tt>Math</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _Math                  = key("Math");
	/**
	 * <p>The runtime {@link Key} for the <tt>Number</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _Number                = key("Number");
	/**
	 * <p>The runtime {@link Key} for the <tt>Object</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _Object                = key("Object");
	/**
	 * <p>The runtime {@link Key} for the <tt>RegExp</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _RegExp                = key("RegExp");
	/**
	 * <p>The runtime {@link Key} for the <tt>String</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _String                = key("String");
	/**
	 * <p>The runtime {@link Key} for the <tt>SyntaxError</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _SyntaxError           = key("SyntaxError");
	/**
	 * <p>The runtime {@link Key} for the <tt>TypeError</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _TypeError             = key("TypeError");
	/**
	 * <p>The runtime {@link Key} for the <tt>URIError</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _URIError              = key("URIError");

	/**
	 * <p>The runtime {@link Key} for the <tt>apply</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _apply                 = key("apply");
	/**
	 * <p>The runtime {@link Key} for the <tt>arguments</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _arguments             = key("arguments");
	/**
	 * <p>The runtime {@link Key} for the <tt>call</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _call                  = key("call");
	/**
	 * <p>The runtime {@link Key} for the <tt>callee</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _callee                = key("callee");
	/**
	 * <p>The runtime {@link Key} for the <tt>caller</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _caller                = key("caller");
	/**
	 * <p>The runtime {@link Key} for the <tt>constructor</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _constructor           = key("constructor");
	/**
	 * <p>The runtime {@link Key} for the <tt>index</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _index                 = key("index");
	/**
	 * <p>The runtime {@link Key} for the <tt>input</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _input                 = key("input");
	/**
	 * <p>The runtime {@link Key} for the <tt>length</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _length                = key("length");
	/**
	 * <p>The runtime {@link Key} for the <tt>prototype</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _prototype             = key("prototype");

	/**
	 * <p>The runtime {@link Key} for the <tt>hasOwnProperty</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _hasOwnProperty        = key("hasOwnProperty");
	/**
	 * <p>The runtime {@link Key} for the <tt>isPrototypeOf</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _isPrototypeOf         = key("isPrototypeOf");
	/**
	 * <p>The runtime {@link Key} for the <tt>propertyIsEnumerable</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _propertyIsEnumerable  = key("propertyIsEnumerable");
	/**
	 * <p>The runtime {@link Key} for the <tt>toLocaleString</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toLocaleString        = key("toLocaleString");
	/**
	 * <p>The runtime {@link Key} for the <tt>toSource</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toSource              = key("toSource");
	/**
	 * <p>The runtime {@link Key} for the <tt>toString</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toString              = key("toString");
	/**
	 * <p>The runtime {@link Key} for the <tt>valueOf</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _valueOf               = key("valueOf");

	/**
	 * <p>The runtime {@link Key} for the <tt>concat</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _concat                = key("concat");
	/**
	 * <p>The runtime {@link Key} for the <tt>join</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _join                  = key("join");
	/**
	 * <p>The runtime {@link Key} for the <tt>pop</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _pop                   = key("pop");
	/**
	 * <p>The runtime {@link Key} for the <tt>push</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _push                  = key("push");
	/**
	 * <p>The runtime {@link Key} for the <tt>reverse</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _reverse               = key("reverse");
	/**
	 * <p>The runtime {@link Key} for the <tt>shift</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _shift                 = key("shift");
	/**
	 * <p>The runtime {@link Key} for the <tt>slice</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _slice                 = key("slice");
	/**
	 * <p>The runtime {@link Key} for the <tt>sort</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _sort                  = key("sort");
	/**
	 * <p>The runtime {@link Key} for the <tt>splice</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _splice                = key("splice");
	/**
	 * <p>The runtime {@link Key} for the <tt>unshift</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _unshift               = key("unshift");

	/**
	 * <p>The runtime {@link Key} for the <tt>getDate</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getDate               = key("getDate");
	/**
	 * <p>The runtime {@link Key} for the <tt>getUTCDate</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getUTCDate            = key("getUTCDate");
	/**
	 * <p>The runtime {@link Key} for the <tt>getDay</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getDay                = key("getDay");
	/**
	 * <p>The runtime {@link Key} for the <tt>getUTCDay</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getUTCDay             = key("getUTCDay");
	/**
	 * <p>The runtime {@link Key} for the <tt>getFullYear</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getFullYear           = key("getFullYear");
	/**
	 * <p>The runtime {@link Key} for the <tt>getUTCFullYear</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getUTCFullYear        = key("getUTCFullYear");
	/**
	 * <p>The runtime {@link Key} for the <tt>getHours</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getHours              = key("getHours");
	/**
	 * <p>The runtime {@link Key} for the <tt>getUTCHours</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getUTCHours           = key("getUTCHours");
	/**
	 * <p>The runtime {@link Key} for the <tt>getMilliseconds</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getMilliseconds       = key("getMilliseconds");
	/**
	 * <p>The runtime {@link Key} for the <tt>getUTCMilliseconds</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getUTCMilliseconds    = key("getUTCMilliseconds");
	/**
	 * <p>The runtime {@link Key} for the <tt>getMinutes</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getMinutes            = key("getMinutes");
	/**
	 * <p>The runtime {@link Key} for the <tt>getUTCMinutes</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getUTCMinutes         = key("getUTCMinutes");
	/**
	 * <p>The runtime {@link Key} for the <tt>getMonth</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getMonth              = key("getMonth");
	/**
	 * <p>The runtime {@link Key} for the <tt>getUTCMonth</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getUTCMonth           = key("getUTCMonth");
	/**
	 * <p>The runtime {@link Key} for the <tt>getSeconds</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getSeconds            = key("getSeconds");
	/**
	 * <p>The runtime {@link Key} for the <tt>getUTCSeconds</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getUTCSeconds         = key("getUTCSeconds");
	/**
	 * <p>The runtime {@link Key} for the <tt>getTime</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getTime               = key("getTime");
	/**
	 * <p>The runtime {@link Key} for the <tt>getTimezoneOffset</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getTimezoneOffset     = key("getTimezoneOffset");
	/**
	 * <p>The runtime {@link Key} for the <tt>getYear</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _getYear               = key("getYear");
	/**
	 * <p>The runtime {@link Key} for the <tt>setDate</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setDate               = key("setDate");
	/**
	 * <p>The runtime {@link Key} for the <tt>setUTCDate</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setUTCDate            = key("setUTCDate");
	/**
	 * <p>The runtime {@link Key} for the <tt>setFullYear</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setFullYear           = key("setFullYear");
	/**
	 * <p>The runtime {@link Key} for the <tt>setUTCFullYear</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setUTCFullYear        = key("setUTCFullYear");
	/**
	 * <p>The runtime {@link Key} for the <tt>setHours</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setHours              = key("setHours");
	/**
	 * <p>The runtime {@link Key} for the <tt>setUTCHours</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setUTCHours           = key("setUTCHours");
	/**
	 * <p>The runtime {@link Key} for the <tt>setMilliseconds</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setMilliseconds       = key("setMilliseconds");
	/**
	 * <p>The runtime {@link Key} for the <tt>setUTCMilliseconds</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setUTCMilliseconds    = key("setUTCMilliseconds");
	/**
	 * <p>The runtime {@link Key} for the <tt>setMinutes</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setMinutes            = key("setMinutes");
	/**
	 * <p>The runtime {@link Key} for the <tt>setUTCMinutes</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setUTCMinutes         = key("setUTCMinutes");
	/**
	 * <p>The runtime {@link Key} for the <tt>setMonth</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setMonth              = key("setMonth");
	/**
	 * <p>The runtime {@link Key} for the <tt>setUTCMonth</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setUTCMonth           = key("setUTCMonth");
	/**
	 * <p>The runtime {@link Key} for the <tt>setSeconds</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setSeconds            = key("setSeconds");
	/**
	 * <p>The runtime {@link Key} for the <tt>setUTCSeconds</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setUTCSeconds         = key("setUTCSeconds");
	/**
	 * <p>The runtime {@link Key} for the <tt>setTime</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setTime               = key("setTime");
	/**
	 * <p>The runtime {@link Key} for the <tt>setYear</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _setYear               = key("setYear");
	/**
	 * <p>The runtime {@link Key} for the <tt>toDateString</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toDateString          = key("toDateString");
	/**
	 * <p>The runtime {@link Key} for the <tt>toGMTString</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toGMTString           = key("toGMTString");
	/**
	 * <p>The runtime {@link Key} for the <tt>toLocaleDateString</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toLocaleDateString    = key("toLocaleDateString");
	/**
	 * <p>The runtime {@link Key} for the <tt>toLocaleTimeString</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toLocaleTimeString    = key("toLocaleTimeString");
	/**
	 * <p>The runtime {@link Key} for the <tt>toTimeString</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toTimeString          = key("toTimeString");
	/**
	 * <p>The runtime {@link Key} for the <tt>toUTCString</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toUTCString           = key("toUTCString");

	/**
	 * <p>The runtime {@link Key} for the <tt>message</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _message               = key("message");
	/**
	 * <p>The runtime {@link Key} for the <tt>name</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _name                  = key("name");

	/**
	 * <p>The runtime {@link Key} for the <tt>E</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _E                     = key("E");
	/**
	 * <p>The runtime {@link Key} for the <tt>LN10</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _LN10                  = key("LN10");
	/**
	 * <p>The runtime {@link Key} for the <tt>LN2</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _LN2                   = key("LN2");
	/**
	 * <p>The runtime {@link Key} for the <tt>LOG10E</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _LOG10E                = key("LOG10E");
	/**
	 * <p>The runtime {@link Key} for the <tt>LOG2E</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _LOG2E                 = key("LOG2E");
	/**
	 * <p>The runtime {@link Key} for the <tt>PI</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _PI                    = key("PI");
	/**
	 * <p>The runtime {@link Key} for the <tt>SQRT1_2</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _SQRT1_2               = key("SQRT1_2");
	/**
	 * <p>The runtime {@link Key} for the <tt>SQRT2</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _SQRT2                 = key("SQRT2");
	/**
	 * <p>The runtime {@link Key} for the <tt>abs</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _abs                   = key("abs");
	/**
	 * <p>The runtime {@link Key} for the <tt>acos</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _acos                  = key("acos");
	/**
	 * <p>The runtime {@link Key} for the <tt>asin</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _asin                  = key("asin");
	/**
	 * <p>The runtime {@link Key} for the <tt>atan</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _atan                  = key("atan");
	/**
	 * <p>The runtime {@link Key} for the <tt>atan2</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _atan2                 = key("atan2");
	/**
	 * <p>The runtime {@link Key} for the <tt>ceil</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _ceil                  = key("ceil");
	/**
	 * <p>The runtime {@link Key} for the <tt>cos</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _cos                   = key("cos");
	/**
	 * <p>The runtime {@link Key} for the <tt>exp</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _exp                   = key("exp");
	/**
	 * <p>The runtime {@link Key} for the <tt>floor</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _floor                 = key("floor");
	/**
	 * <p>The runtime {@link Key} for the <tt>log</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _log                   = key("log");
	/**
	 * <p>The runtime {@link Key} for the <tt>max</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _max                   = key("max");
	/**
	 * <p>The runtime {@link Key} for the <tt>min</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _min                   = key("min");
	/**
	 * <p>The runtime {@link Key} for the <tt>pow</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _pow                   = key("pow");
	/**
	 * <p>The runtime {@link Key} for the <tt>random</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _random                = key("random");
	/**
	 * <p>The runtime {@link Key} for the <tt>round</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _round                 = key("round");
	/**
	 * <p>The runtime {@link Key} for the <tt>sin</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _sin                   = key("sin");
	/**
	 * <p>The runtime {@link Key} for the <tt>sinh</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _sinh                  = key("sinh");	/**
	 * <p>The runtime {@link Key} for the <tt>sqrt</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _sqrt                  = key("sqrt");
	/**
	 * <p>The runtime {@link Key} for the <tt>tan</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _tan                   = key("tan");
	/**
	 * <p>The runtime {@link Key} for the <tt>tanh</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _tanh                  = key("tanh");

	/**
	 * <p>The runtime {@link Key} for the <tt>MAX_VALUE</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _MAX_VALUE             = key("MAX_VALUE");
	/**
	 * <p>The runtime {@link Key} for the <tt>MIN_VALUE</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _MIN_VALUE             = key("MIN_VALUE");
	/**
	 * <p>The runtime {@link Key} for the <tt>NaN</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _NaN                   = key("NaN");
	/**
	 * <p>The runtime {@link Key} for the <tt>NEGATIVE_INFINITY</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _NEGATIVE_INFINITY     = key("NEGATIVE_INFINITY");
	/**
	 * <p>The runtime {@link Key} for the <tt>POSITIVE_INFINITY</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _POSITIVE_INFINITY     = key("POSITIVE_INFINITY");
	/**
	 * <p>The runtime {@link Key} for the <tt>toExponential</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toExponential         = key("toExponential");
	/**
	 * <p>The runtime {@link Key} for the <tt>toFixed</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toFixed               = key("toFixed");
	/**
	 * <p>The runtime {@link Key} for the <tt>toPrecision</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toPrecision           = key("toPrecision");

	/**
	 * <p>The runtime {@link Key} for the <tt>index</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _global                = key("index");
	/**
	 * <p>The runtime {@link Key} for the <tt>ignoreCase</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _ignoreCase            = key("ignoreCase");
	/**
	 * <p>The runtime {@link Key} for the <tt>lastIndex</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _lastIndex             = key("lastIndex");
	/**
	 * <p>The runtime {@link Key} for the <tt>multiline</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _multiline             = key("multiline");
	/**
	 * <p>The runtime {@link Key} for the <tt>source</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _source                = key("source");
	/**
	 * <p>The runtime {@link Key} for the <tt>exec</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _exec                  = key("exec");
	/**
	 * <p>The runtime {@link Key} for the <tt>test</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _test                  = key("test");

	/**
	 * <p>The runtime {@link Key} for the <tt>charAt</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _charAt                = key("charAt");
	/**
	 * <p>The runtime {@link Key} for the <tt>charCodeAt</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _charCodeAt            = key("charCodeAt");
	/**
	 * <p>The runtime {@link Key} for the <tt>indexOf</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _indexOf               = key("indexOf");
	/**
	 * <p>The runtime {@link Key} for the <tt>lastIndexOf</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _lastIndexOf           = key("lastIndexOf");
	/**
	 * <p>The runtime {@link Key} for the <tt>localeCompare</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _localeCompare         = key("localeCompare");
	/**
	 * <p>The runtime {@link Key} for the <tt>match</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _match                 = key("match");
	/**
	 * <p>The runtime {@link Key} for the <tt>replace</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _replace               = key("replace");
	/**
	 * <p>The runtime {@link Key} for the <tt>search</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _search                = key("search");
	/**
	 * <p>The runtime {@link Key} for the <tt>split</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _split                 = key("split");
	/**
	 * <p>The runtime {@link Key} for the <tt>substr</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _substr                = key("substr");
	/**
	 * <p>The runtime {@link Key} for the <tt>substring</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _substring             = key("substring");
	/**
	 * <p>The runtime {@link Key} for the <tt>toLocaleLowerCase</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toLocaleLowerCase     = key("toLocaleLowerCase");
	/**
	 * <p>The runtime {@link Key} for the <tt>toLocaleUpperCase</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toLocaleUpperCase     = key("toLocaleUpperCase");
	/**
	 * <p>The runtime {@link Key} for the <tt>toLowerCase</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toLowerCase           = key("toLowerCase");
	/**
	 * <p>The runtime {@link Key} for the <tt>toUpperCase</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _toUpperCase           = key("toUpperCase");

	/**
	 * <p>The runtime {@link Key} for the global service <tt>escape</tt>.</p>
	 * @see #escape(Script, Object...)
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	public final Key _escape   = globalize("escape");
	/**
	 * <p>The runtime {@link Key} for the global service <tt>eval</tt>.</p>
	 * @see #eval(Script, Object...)
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	public final Key _eval     = globalize("eval");
	/**
	 * <p>The runtime {@link Key} for the native global service <tt>print</tt>.</p>
	 * @see #print(Script, Object...)
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	public final Key _print    = globalize("print");
	/**
	 * <p>The runtime {@link Key} for the global service <tt>unescape</tt>.</p>
	 * @see #unescape(Script, Object...)
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	public final Key _unescape = globalize("unescape");
	/**
	 * <p>The runtime {@link Key} for the native global service <tt>compile</tt>.</p>
	 * @see #compile(Script, Object...)
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	public final Key _compile  = globalize("compile");
	/**
	 * <p>The runtime {@link Key} for the native global service <tt>descript</tt>.</p>
	 * @see #descript(Script, Object...)
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	public final Key _descript = globalize("descript");
	/**
	 * <p>The runtime {@link Key} for the native global service <tt>execute</tt>.</p>
	 * @see #execute(Script, Object...)
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	public final Key _execute  = globalize("execute");

	/**
	 * <p>Constructs a stand-alone script context with core features of a JavaScript engine.</p>
	 * @see #Core(boolean)
	 * @since Descripter 1.0
	 */
	public Core() {
		this(false);
	}

	/**
	 * <p>Constructs a script context with core features of a JavaScript engine and read-only mode specified.</p>
	 * @param readOnly <tt>true</tt> for a read-only core context; <tt>false</tt>, otherwise.
	 * @see #Core()
	 * @since Descripter 1.0
	 */
	public Core(boolean readOnly) {
		super(null);

		System.out.println("Descripter 1.0 Core,  All right reserved.");
		System.out.println("Copyright (C) 2010-2012  J.J.Liu<jianjunliu@126.com> www.descripter.org");

		put(_null, CObject._null);

		Function_.create(this);
		Object_  .create(this);
		Array_   .create(this);
		Boolean_ .create(this);
		Date_    .create(this);
		Error_   .create(this);
		CMath    .create(this);
		Number_  .create(this);
		RegExp_  .create(this);
		String_  .create(this);

		readOnly(readOnly);
		System.out.println("Descripter 1.0 Core initialized.");
	}

	/**
	 * <p>Gets the engine {@link Core} of this {@link Global} script context.</p>
	 * <p>This method simply returns <tt>this</tt> object.</p>
	 * @return The engine {@link Core} of this {@link Global} script context
	 * @since Descripter 1.0
	 */
	@Override
	public Core core() {
		return this;
	}

	/**
	 * <p>Gets the global <tt>Array</tt> function.</p>
	 * @return The global <tt>Array</tt> function
	 * @see #_Array
	 * @since Descripter 1.0
	 */
	public final Array_ _Array() {
		return (Array_)get(_Array);
	}

	/**
	 * <p>Gets the global <tt>Boolean</tt> function.</p>
	 * @return The global <tt>Boolean</tt> function
	 * @see #_Boolean
	 * @since Descripter 1.0
	 */
	public final Boolean_ _Boolean() {
		return (Boolean_)get(_Boolean);
	}

	/**
	 * <p>Gets the global <tt>Function</tt> function.</p>
	 * @return The global <tt>Function</tt> function
	 * @see #_Function
	 * @since Descripter 1.0
	 */
	public final Function_ _Function() {
		return (Function_)get(_Function);
	}

	/**
	 * <p>Gets the global <tt>Number</tt> function.</p>
	 * @return The global <tt>Number</tt> function
	 * @see #_Number
	 * @since Descripter 1.0
	 */
	public final Number_ _Number() {
		return (Number_)get(_Number);
	}

	/**
	 * <p>Gets the global <tt>Object</tt> function.</p>
	 * @return The global <tt>Object</tt> function
	 * @see #_Object
	 * @since Descripter 1.0
	 */
	public final Object_ _Object() {
		return (Object_)get(_Object);
	}

	/**
	 * <p>Gets the global <tt>RegExp</tt> function.</p>
	 * @return The global <tt>RegExp</tt> function
	 * @see #_RegExp
	 * @since Descripter 1.0
	 */
	public final RegExp_ _RegExp() {
		return (RegExp_)get(_RegExp);
	}

	/**
	 * <p>Gets the global <tt>String</tt> function.</p>
	 * @return The global <tt>String</tt> function
	 * @see #_String
	 * @since Descripter 1.0
	 */
	public final String_ _String() {
		return (String_)get(_String);
	}

	/**
	 * <p>Returns a string representation of the current object.</p>
	 * @return The string representation of the current object
	 * @since Descripter 1.0
	 */
	@Override
	public String toString() {
		return "[Core object]";
	}

	/**
	 * <p>Executes the script context of this type.</p>
	 * @since Descripter 1.0
	 */
	@Override
	public void run() {
	}

	/**
	 * <p>Tests whether a value is the not-a-number value, simulating the JavaScript global function of 
	 * the same name.</p>
	 * <p>In JavaScript, This function tests its argument to determine whether it is the 
	 * value <tt>NaN</tt>, which represents an illegal number (such as the result of 
	 * division by zero). This function is required because comparing a <tt>NaN</tt> 
	 * with any value, including itself, always returns <tt>false</tt>, so it is not 
	 * possible to test for <tt>NaN</tt> with the == or === operators.</p>
	 * <p>A common use in JavaScript of this function is to test the results of {@link #parseFloat(Script, Object[])} 
	 * and {@link #parseInt(Script, Object[])} to determine if they represent legal numbers. You can 
	 * also use {@link #isNaN(Script, Object[])} to check for arithmetic errors, such as division by 
	 * zero</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @return <tt>true</tt> if <tt>v</tt> is (or can be converted to) the special 
	 * not-a-number value; <tt>false</tt> if <tt>v</tt> is any other value.
	 * @since Descripter 1.0
	 */
	public boolean isNaN(Script<?> script, Object ...args) {
		return false;
	}

	/**
	 * <p>Parses an integer from a string, 
	 * simulating the JavaScript global function of the same name.</p>
	 * <p>In JavaScript, this function parses and returns the first number (with an 
	 * optional leading minus sign) that occurs in <tt>value</tt>. Parsing stops, and 
	 * the value is returned, when it encounters a character in <tt>value</tt> that is 
	 * not a valid digit for the specified radix. If <tt>value</tt> does not begin with 
	 * a number that it can parse, the function returns the not-a-number value <tt>NaN</tt>. 
	 * Use the {@link #isNaN(Script, Object[])} function to test for this return value.</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @return The parsed number, or <tt>NaN</tt> if <tt>value</tt> does not begin with 
	 * a valid integer. In JavaScript 1.0, this function returns 0 instead of <tt>NaN</tt> 
	 * when it cannot parse <tt>value</tt>.
	 * @since Descripter 1.0
	 */
	public int parseInt(Script<?> script, Object ...args) {
		return 0;
	}

	/**
	 * <p>Parses a number from a string, 
	 * simulating the JavaScript global function of the same name.</p>
	 * <p>In JavaScript, this function parses and returns the first number that occurs in 
	 * <tt>value</tt>. Parsing stops, and the value is returned, when it encounters a 
	 * character in <tt>value</tt> that is not a valid part of the number. If <tt>value</tt> 
	 * does not begin with a number that it can parse, the function returns the not-a-number 
	 * value <tt>NaN</tt>. Test for this return value with the {@link #isNaN(Script, Object[])} 
	 * function. If you want to parse only the integer portion of a number, use 
	 * {@link #parseInt(Script, Object[])} instead of this one.</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @return The parsed number, or <tt>NaN</tt> if <tt>value</tt> does not begin 
	 * with a valid number. In JavaScript 1.0, this function returns 0 instead of <tt>NaN</tt> 
	 * when <tt>value</tt> cannot be parsed as a number.
	 * @since Descripter 1.0
	 */
	public double parseFloat(Script<?> script, Object ...args) {
		return 0;
	}

	/**
	 * <p>A native global service to emulate the JavaScript global function with the same name.</p>
	 * <p>This method only evaluates the string representation of the first element of <tt>args</tt>.</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @return The result of evaluation
	 * @see #_eval
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	public Object eval(Script<?> script, Object ...args) {
		return new Interpreter(script).eval(toString(args[0]));
	}

	/**
	 * <p>A global service to emulate the JavaScript global function with the same name.</p>
	 * <p>This method only escapes the string representation of the first element of <tt>args</tt>.</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @return The escaped string
	 * @see #_escape
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	public String escape(Script<?> script, Object ...args) {
		return null;
	}

	/**
	 * <p>A global service to emulate the JavaScript global function with the same name.</p>
	 * <p>This method only unescapes the string representation of the first element of <tt>args</tt>.</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @return The unescaped string
	 * @see #_unescape
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	public String unescape(Script<?> script, Object ...args) {
		return null;
	}

	/**
	 * <p>A native global service for standard output.</p>
	 * <p>This method prints the elements of <tt>args</tt> to <tt>stdio</tt> in lines.</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @see #_print
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	public void print(Script<?> script, Object ...args) {
		for (Object o : args) {
			System.out.print(toString(o));
		}
		System.out.println();
	}

	/**
	 * <p>A native global service for descripting and compiling JavaScript code.</p>
	 * <p>This method descripts and compiles the string of JavaScript source specified by the second element 
	 * of <tt>args</tt> into a Java class with the name specified by the first element of <tt>args</tt>.</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @return A {@link Runnable} {@link Class}
	 * @see #_compile
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	public Class<?> compile(Script<?> script, Object ...args) {
		String name = toString(args[0]);
		Memory memo = new Memory();
		if (memo.compile(name, descript(script, name, toString(args[1])))) {
			try {
				return memo.loadClass(name);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	/**
	 * <p>A native global service for descripting JavaScript code.</p>
	 * <p>This method descripts the string of JavaScript source specified by the second element of 
	 * <tt>args</tt> into Java source with the class name specified by the first element of <tt>args</tt>.</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @return A string of descripted Java source
	 * @see #_descript
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	public String descript(Script<?> script, Object ...args) {
		return new Descripter(script).descript(toString(args[0]), toString(args[1]));
	}

	/**
	 * <p>A native global service for executing a {@link Runnable} {@link Class}.</p>
	 * <p>This method only executes the {@link Runnable} {@link Class} specified by the first element of 
	 * <tt>args</tt>.</p>
	 * @param script The script context that invoked this service
	 * @param args An array of the arguments passed by the invocation
	 * @see #_execute
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	public void execute(Script<?> script, Object ...args) {
		try {
			((Runnable)((Class<?>)args[0]).getConstructors()[0].newInstance(
					script
			)).run();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
