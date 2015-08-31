
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
import org.descripter.js.api.core.CArray;

/**
 * <p>Tests JavaScript Arrays.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Arrays extends Global<Core>
{
	/**
	 * <p>Constructs a {@link Global} script context of this type.</p>
	 * @param with The containing {@link Core} context.
	 * @since Descripter 1.0
	 */
	public Arrays(Core with) {
		super(with);
	}

	/**
	 * <p>Executes the script context of this type.</p>
	 * @since Descripter 1.0
	 */
	@Override
	public void run() {
		CArray arr = new CArray(core()._Array(), new Object[]{"one", "two", "three"});
		print(arr.var(with._length));
		print(arr.length());
		print(arr.var(with._toString).call(this));
		call(arr.var(with._unshift), "zero");
		print(arr);
		call(arr.var(with._push), "four", "five");
		print(call(arr.var(with._toString)));
//		print(arr.pop());
		print(call(arr.var(with._pop)));
		print(call(arr.var(with._toString)));
		call(arr.var(with._sort), new MyFunction(this) {
			@Override
			public MyFunctor functor() {
				return new MyFunctor(this) {
					@Override
					public Object function() {
						CArray a = arguments();
						return - toString(a.get(0)).compareTo(toString(a.get(1)));
					}
				};
			}
		});
		print(call(arr.var(with._toString)));
	}

	/**
	 * <p>Creates and runs {@link Global} script contexts of the containing type.</p>
	 * @param args Ignored
	 * @since Descripter 1.0
	 */
	public static void main(String[] args) {
		Core c = new Core();
		new Arrays(c).run();
		new Arrays(c).run();
		new Arrays(c).run();
	}
}
