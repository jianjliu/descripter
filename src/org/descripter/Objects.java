
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
 * <p>Tests JavaScript Objects.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Objects extends Global<Core>
{
	/**
	 * <p>Constructs a {@link Global} script context of this type.</p>
	 * @param with The containing {@link Core} context.
	 * @since Descripter 1.0
	 */
	public Objects(Core with) {
		super(with);
	}

	/**
	 * <p>Executes the script context of this type.</p>
	 * @since Descripter 1.0
	 */
	@Override
	public void run() {
		print(var(with._Function).var(with._prototype).var(-10000));
		var(with._Function).var(with._prototype).set(-10000, "bgasdhwqoeghhqoweigqg");
		print(var(with._Function).var(with._prototype).var(-10000));
		print(var(with._this));
		print(typeof(var(with._this)));
		print(var(with._null));
		print(typeof(var(with._null)));
		print(typeof(null));
		print(new Object[] { null });
		print(typeof(var(with._Object).var(with._prototype)));
		print(instanceOf(var(with._Object).var(with._prototype), var(with._Object)));
		print(instanceOf(alloc(var(with._Function)).var(with._prototype), var(with._Object)));
		print(instanceOf(alloc(var(with._Object)), var(with._Object)));
		print(typeof(var(with._Object)));
		print(typeof(var(with._Object).var(with._call)));
		print(var(with._Object));
		Object o = alloc(var(with._Object));
		print(typeof(o));
		print(typeof(alloc(var(with._Object))));
		print(typeof(alloc(var(with._Function))));
		print(o);
	}

	/**
	 * <p>Creates and runs {@link Global} script contexts of the containing type.</p>
	 * @param args Ignored
	 * @since Descripter 1.0
	 */
	public static void main(String[] args) {
		Core c = new Core(true);
		new Objects(new Core()).run();
		c.readOnly(false);
		new Objects(c).run();
		System.out.println(null==null);
	}
}
