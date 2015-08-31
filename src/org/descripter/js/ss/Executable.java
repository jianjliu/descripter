
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

package org.descripter.js.ss;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.descripter.js.Descripter;
import org.descripter.js.Memory;

/**
 * <p>Manages descripted and compiled executables for the server-side scriptlet and pages.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Executable
{
	/**
	 * <p>The class of the executable.</p>
	 * @since Descripter 1.0
	 */
	protected final Class<?> clazz;
	/**
	 * <p>The time-stamp of the executable.</p>
	 * @since Descripter 1.0
	 */
	protected final long stamp;

	/**
	 * <p>Constructs an executable object.</p>
	 * @param file A requested source file
	 * @param clazz The compiled executable class
	 * @since Descripter 1.0
	 */
	protected Executable(File file, Class<?> clazz) {
		this.stamp = file.lastModified();
		this.clazz = clazz;
	}

	/**
	 * <p>Executes this executable in a given {@link Scriptlet}.</p>
	 * @param s A {@link Scriptlet} for executing this executable
	 * @return The return result of the execution
	 * @throws IOExeption if an {@link IOException} happens when executing
	 * @since Descripter 1.0
	 */
	public Object execute(Scriptlet<?> s) throws IOException {
		s.prelude();
		Object ret = s.execute(clazz);
		s.finale();
		return ret;
	}

	/**
	 * <p>Determines if an update for the current executable from the specified {@link File} is unnecessary.</p>
	 * @param file A {@link File}
	 * @return <tt>true</tt> if an update is unnecessary; <tt>false</tt>, otherwise.
	 * @since Descripter 1.0
	 */
	public boolean updated(File file) {
		return stamp >= file.lastModified();
	}

	private static final Map<String, Executable> map = new HashMap<String, Executable>();
	private static final Memory memo = new Memory(true);
	private static int next = 0;

	/**
	 * <p>Statically gets or creates an executable.</p>
	 * @param scriptlet A {@link Scriptlet} to compile and/or descript the source file
	 * @param file A {@link File} of Server-Side JavaScript source or JavaScript Server Page
	 * @param jssp <tt>true</tt> if the source file is a JavaScript Server Page; otherwise, it is 
	 * Server-Side JavaScript source.
	 * @return The existing or newly created executable.
	 * @since Descripter 1.0
	 */
	public static synchronized final Executable get(Scriptlet<?> scriptlet, File file, boolean jssp)
			throws FileNotFoundException, ClassNotFoundException {
		String path = file.getAbsolutePath();
		if (map.containsKey(path)) {
			Executable x = map.get(path);
			if (x.updated(file)) {
				return x;
			}
		}
		String name = "J_S_S_" + next++;
		String java = jssp ? new Descripter(scriptlet).descript(
				name,
				new Scriptizer(scriptlet).scriptize(
						new FileReader(file)
				)
		) : new Descripter(scriptlet).descript(
				name,
				new FileReader(file)
		);
		if (memo.compile(name, java)) {
			map.put(
					path,
					new Executable(
							file,
							memo.loadClass(name)
					)
			);
		}
		return map.get(path);
	}
}
