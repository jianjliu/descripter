
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.descripter.js.api.Script;
import org.descripter.js.api.core.CObject;

import com.sun.net.httpserver.HttpExchange;

/**
 * <p>Handles the execution of server-side scriptlets or pages.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Scriptlet<W extends Daemon> extends Script<W>
{
	private final HttpExchange xchg;

	/**
	 * <p>Constructs this type of context.</p>
	 * 
	 * @param with The base context
	 * @param xchg The {@link HttpExchange}
	 * @since Descripter 1.0
	 */
	public Scriptlet(W with, HttpExchange xchg) {
		super(with);
		this.xchg = xchg;
	}

	/**
	 * <p>Prepares to run a scriptlet or server-page from the current script context.</p>
	 * @since Descripter 1.0
	 */
	public void prelude() {
		CObject request = object();
		set(with._Request, request);
		set(with._Response, array());
		String method = xchg.getRequestMethod();
		URI uri = xchg.getRequestURI();
		if ("POST".equalsIgnoreCase(method)) {
		} else {
			String q = uri.getQuery();
			if (q != null) {
				String[] arr = q.split("&");
				for (String s : arr) {
					String[] pair = s.split("=");
					request.set(key(pair[0]), pair[1]);
				}
			}
		}
	}

	/**
	 * <p>Finishes running a scriptlet or server-page from the current script context.</p>
	 * @since Descripter 1.0
	 */
	public void finale() throws IOException {
		String response = with.getResponse(this).join("");
		xchg.getResponseHeaders().add("Content-Type", "text/html");
		xchg.sendResponseHeaders(200, response.length());
		OutputStream os = xchg.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	/**
	 * <p>Executes the script context of this type.</p>
	 * @since Descripter 1.0
	 */
	@Override
	public void run() {
		try {
			String path = with.root + xchg.getRequestURI().getPath();
			File file = new File(path);
			System.out.println(file.getAbsolutePath());
			if (!file.exists()) {
				xchg.sendResponseHeaders(404, -1);
			} else if (path.endsWith(".js")) {
				Executable.get(this, file, false).execute(this);
			} else if (path.endsWith(".jssp")) {
				Executable.get(this, file, true ).execute(this);
			} else {
				FileInputStream fis = new FileInputStream(file);
				String type = mime(path);
				if (type != null) {
					xchg.getResponseHeaders().add("Content-Type", type);
				}
				xchg.sendResponseHeaders(200, file.length());
				OutputStream os = xchg.getResponseBody();
				byte[] buf = new byte[4096];
				int len;
				while ((len = fis.read(buf)) > 0) {
					os.write(buf, 0, len);
				}
				fis.close();os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>Gets the MIME type for a path.</p>
	 * 
	 * @param path The file path.
	 * @return The MIME type for the file.
	 * @since Descripter 1.0
	 */
	public String mime(String path) {
		CObject mime = with.mime;
		int last = path.lastIndexOf('.');
		return last > 0 ? var(mime, path.substring(last)).toString() : null;
	}
}
