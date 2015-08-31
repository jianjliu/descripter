
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

package org.descripter.js;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import javax.tools.JavaFileObject.Kind;

/**
 * <p>Represents a memory class loader to compile Java source code on the fly.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Memory extends ClassLoader
{
	private final Map<String, ByteArrayOutputStream> map = new HashMap<String, ByteArrayOutputStream>();
	private final boolean verbose;

	/**
	 * <p>Constructs a class loader of this type.</p>
	 * @param verbose Compiles in verbose mode if it is set <tt>true</tt>; silently, otherwise.
	 * @since Descripter 1.0
	 */
	public Memory(boolean verbose) {
		this.verbose = verbose;
	}

	/**
	 * <p>Constructs a class loader of this type.</p>
	 * @since Descripter 1.0
	 */
	public Memory() {
		this(false);
	}

	/**
	 * <p>Finds the class with the name specified.</p>
	 * @param name The name of the class to find.
	 * @return The found class.
	 * @throws ClassNotFoundException if the specified class is not found.
	 * @since Descripter 1.0
	 */
	@Override
	protected synchronized Class<?> findClass(String name) throws ClassNotFoundException {
		ByteArrayOutputStream baos = map.remove(name);
		if (baos != null) {
			byte[] ba = baos.toByteArray();
			return defineClass(name, ba, 0, ba.length);
		}
		return super.findClass(name);
	}

	/**
	 * <p>Compiles the specified Java source.</p>
	 * @param name The name of the target class.
	 * @param code The code of the Java source.
	 * @return <tt>true</tt> if the compilation is successful; <tt>false</tt>, otherwise.
	 * @since Descripter 1.0
	 */
	public Boolean compile(final String name, final String code) {
		return ToolProvider.getSystemJavaCompiler().getTask(
				null,
				new ForwardingJavaFileManager<JavaFileManager>(
						ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null)
				) {
					@Override
					public JavaFileObject getJavaFileForOutput(
							Location location, final String name, Kind kind, FileObject source
					) {
						return new SimpleJavaFileObject(URI.create(
								"memo:///" + name.replace('.', '/') + kind.extension), kind) {
							@Override
							public OutputStream openOutputStream() {
								ByteArrayOutputStream baos = new ByteArrayOutputStream();
								map.put(name, baos);
								return baos;
							}
						};
					}
				},
				null,
				verbose ? Arrays.asList("-verbose") : null,
				null,
				Arrays.asList(new SimpleJavaFileObject(
						URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
						Kind.SOURCE
				) {
					@Override
					public CharSequence getCharContent(boolean ignoreEncodingErrors) {
						return code;
					}
				})
		).call();
	}
}
