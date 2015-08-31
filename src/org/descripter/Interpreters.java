
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

package org.descripter;

import org.descripter.js.api.Core;
import org.descripter.js.api.Global;

/**
 * <p>Tests Interpreting JavaScript.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Interpreters extends Global<Core>
{
	/**
	 * <p>Constructs a {@link Global} script context of this type.</p>
	 * @param with The containing {@link Core} context.
	 * @since Descripter 1.0
	 */
	public Interpreters(Core with) {
		super(with);
	}

	/**
	 * <p>Executes the script context of this type.</p>
	 * @since Descripter 1.0
	 */
	@Override
	public void run() {
		Object o = eval(";;{a:111,b:222};");
		print(o);
		print(var(o, "a"));
		print(var(o, "b"));
		o = eval("/*test*/[12, 3.02e-9, 0xff, 'asgs', /gas/ig];");
		print(o);
		o = eval("Array;");
		print(o);
		o = eval("Array.prototype;");
		print(o);
		o = eval("Array['constructor'];");
		print(o);
		o = eval("new String('my string');");
		print(o);
		o = eval("function(){return 'hello';}();");
		print(o);
		o = eval("function(s){print(s);return s;}(22222);");
		print(o);
		o = eval("function(s){print(s);return s;}('hello2')");
		print(o);
		o = eval("function test(s){print(s);return s;}; test(333333);");
		print(o);
		o = eval("function test(s){print(s);return s;}; test('hello3');");
		print(o);
		o = eval("100+10+3*3-7+7-17%10+7;");
		print(o);
		o = eval("false||true&&true;");
		print(o);
		o = eval("-10001;");
		print(o);
		o = eval("0 ? '666' : 555;");
		print(o);
//		eval("if(0) print('000\n0\u0048'); else print('elseee');");
///*
		eval("try {print('try');do{print('doWhile');}while(false);throw 'thrown';} catch(e){print(e);} finally{print('finally');};");
		eval("with(String){print(prototype);};");
		eval("aaa='AAA';print(aaa);");
		eval("while(true){if(true)break;}print('abrupted');");
		eval("label:while(true){if(true)break label;}print('abrupted');");
		eval("var a='A',b='BB';print(a+b);");
		eval("var v=10;while(v){print(v);v=v-1;if(v) {print(v);print(!!v);break;}};print(v);");
		eval("var v=10;while(v){print(v);v=v-1;}");
		eval("switch('aa11'){case 'bb':{print('bb');}case 'aa11':{print('aa11');break;}default:{print('default');}};");
		eval("for(var v in Object){print(v+'='+Object[v]);}print(v);");
		eval("for(v in Object){print(v+'='+Object[v]);}print(v);");
		eval("var v=10;for(;v;v=v-1){print(v);}");
		eval("for(var v=10;v;v=v-1){print(v);}print(v);");
		eval("print(7&2);print(7|2);print(7<<2);print(-7>>2);print(-7>>>2);");
		eval("print(Math);print(Math.PI);print(Math.sin(0));print(Math.sin(Math.PI/4));");
		eval("var a = new Array(5); debugger;a[0]=1000; a[1]= 2000; print(a.length); print(a[1]);");
		eval("var s = 'Hello World!'; var v ='print(s);eval(\"print(s)\")'; print(v);eval(v);");
//		eval("eval(\"print('Hello World')\");#System.out.println(\"Hello Hash!\");");
		eval("print(Math.sin(Math.PI/4));");
	}

	/**
	 * <p>Creates and runs {@link Global} script contexts of the containing type.</p>
	 * @param args Ignored
	 * @since Descripter 1.0
	 */
	public static void main(String[] args) {
		Core c = new Core();
		new Interpreters(c).run();
	}
}
