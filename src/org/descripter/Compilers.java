
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
 * <p>Tests Compiling JavaScript.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Compilers extends Global<Core>
{
	/**
	 * <p>Constructs a {@link Global} script context of this type.</p>
	 * @param with The containing {@link Core} context.
	 * @since Descripter 1.0
	 */
	public Compilers(Core with) {
		super(with);
	}

	/**
	 * <p>Executes the script context of this type.</p>
	 * @since Descripter 1.0
	 */
	@Override
	public void run() {
//		execute(compile("org.descripter.Test", "return;return 'aaa';return 111 ;return new Object; return new String('aaaastr') ; return /asdgsdg/ig;"));
//		execute(compile("org.descripter.Test", "return;"));
//		execute(compile("org.descripter.Test", "return 'asd';"));
//		execute(compile("org.descripter.Test", "return new String();return new String('asdgasd');return new Object;return new String('asdgasd');return new new Function;return new new new Function;return new Function('gsg')[1];"));
//		execute(compile("org.descripter.Test", "return new new Function;"));
//		execute(compile("org.descripter.Test", "return [Object, oo, 1 + 2, 'fasdg', true];"));
//		execute(compile("org.descripter.Test", "return {gadgs:111,  fgdfgd:true,fgd:'gdg',,,};"));
//		execute(compile("org.descripter.Test", "bbb=cc(),aaa=print('asd');if(true!=true){print('if');return 'gsdg';}else{print('else');gsgsd()};"));
//		execute(compile("org.descripter.Test", "while(true){print('while');};"));
//		execute(compile("org.descripter.Test", "return -1+55-(gggs++)+(--gsdgfff)+'gds'-3+666*2/5*6-9-8-gsgd--;return ++gsgas;return gasg--;with(gdgg){print('gggg')}throw vv;DOW:do{print('dowhile');break DOW;}while(true);"));
//		execute(compile("org.descripter.Test", "DOW:do{print('dowhile');break DOW;}while((aaaa>=7)==false);"));
//		execute(compile("org.descripter.Test", "var u=3,v=5,a;for(var aa=1;aa<=1;aa++){break;break DGSG;print('here');asdg();throw aa;}return v;"));
//		execute(compile("org.descripter.Test", "function f(a,b){print(a);print(b);};f(11,'gsdgsg');"));
//		execute(compile("org.descripter.Test", "try{DOW:do{print('dowhile');break DOW;}while((aaaa>=7)==false);throw 12;}catch(e){print(e);}finally{print('finally');};"));
		execute(compile("org.descripter.Test", "#System.out.println(\"Hello Hash!\");for(a in Object){print(a);};#System.out.println(\"Hello Hash!\");#System.out.println(\"Hello Hash!\");"));
		execute(compile("org.descripter.Test", "for(i=0;i<6;i++){#System.out.println(\"Hello Hash!\");print('for'+i);}"));
		execute(compile("org.descripter.Test", "switch('gds'){case 'gf':{print('gf');}default:print('default');case 'gds':print('gds');}"));
		execute(compile("org.descripter.Test", "print(Math.sin(Math.PI/4));"));
	}

	/**
	 * <p>Creates and runs {@link Global} script contexts of the containing type.</p>
	 * @param args Ignored
	 * @since Descripter 1.0
	 */
	public static void main(String[] args) {
		Core c = new Core();
		new Compilers(c).run();
	}
}
