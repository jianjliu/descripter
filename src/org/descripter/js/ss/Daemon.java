
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

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.descripter.js.api.Core;
import org.descripter.js.api.Global;
import org.descripter.js.api.Key;
import org.descripter.js.api.Script;
import org.descripter.js.api.core.CArray;
import org.descripter.js.api.core.CObject;

import com.sun.net.httpserver.HttpServer;

/**
 * <p>A simple HTTP server for servlets and server-pages in JavaScript.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Daemon extends Core
{
	/**
	 * <p>The command-line starter for a daemon of the containing type.</p>
	 * @param args A string array of the command-line arguments.
	 * @since Descripter 1.0
	 */
	public static void main(String[] args) {
		if (args == null || args.length < 1) {
			new Daemon(8000, "/echo").run();
			return;
		}
		int alen = args.length;
		if (args == null || alen > 2) {
			usage();
			return;
		}
		int port = 8000;
		String root = ".";
		if (alen > 0) {
			root = args[0];
		}
		if (alen > 1) {
			port = Integer.parseInt(args[1]);
		}
		new Daemon(port, root).run();
	}

	private final static void usage() {
		System.out.println("Descripter 1.0 Core Daemon,  All right reserved.");
		System.out.println("Copyright (C) 2010-2012  J.J.Liu<jianjunliu@126.com> www.descripter.org");
		System.out.println("java -jar descripter.jar <root> <port>");
		System.out.println("java -cp descripter.jar daemon <root> <port>");
		System.out.println("java -cp descripter.jar org.descripter.js.ss.Daemon <root> <port>");
	}

	/**
	 * <p>Executes the script context of this type.</p>
	 * @since Descripter 1.0
	 */
	@Override
	public void run() {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext(cntx, new HttpHandler(){
				@Override
				public void handle(HttpExchange xchg) throws IOException {
					new Thread(
							new Scriptlet<Daemon>(Daemon.this, xchg)
					).run();
				}});
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>The runtime {@link Key} for the <tt>Request</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _Request   = key("Request");
	/**
	 * <p>The runtime {@link Key} for the <tt>Response</tt> identifier or property.</p>
	 * @since Descripter 1.0
	 */
	public final Key _Response  = key("Response");

	/**
	 * <p>Gets the runtime <tt>Request</tt> identifier or property.</p>
	 * @param script A {@link Script} context
	 * @return The runtime <tt>Request</tt> identifier or property of the 
	 * specified {@link Script} context
	 * @since Descripter 1.0
	 */
	protected CObject getRequest(Script<?> script) {
		return (CObject)script.get(_Request);
	}

	/**
	 * <p>Gets the runtime <tt>Response</tt> identifier or property.</p>
	 * @param script A {@link Script} context
	 * @return The runtime <tt>Response</tt> identifier or property of the 
	 * specified {@link Script} context
	 * @since Descripter 1.0
	 */
	protected CArray getResponse(Script<?> script) {
		return (CArray)script.get(_Response);
	}

	/**
	 * <p>A native global service for <tt>HTTP Response</tt> output.</p>
	 * <p>This method prints the elements of <tt>args</tt> to <tt>HTTP Response</tt> in HTML lines.</p>
	 * @param script The script context that invoked this function
	 * @param args An array of the arguments passed by the invocation
	 * @see Core#_print
	 * @see Global#globalize(String)
	 * @since Descripter 1.0
	 */
	@Override
	public void print(Script<?> script, Object ...args) {
		CArray r = getResponse(script);
		r.push(array(args));
		r.put(r.length(), "\r\n");
	}

	/**
	 * <p>Constructs a writable {@link Core} context with the current path as the root.</p>
	 * @param port The port number for the HTTP server
	 * @param cntx The context path for the HTTP server
	 * @since Descripter 1.0
	 */
	public Daemon(int port, String cntx) {
		this(port, cntx, false);
	}

	/**
	 * <p>Constructs a {@link Core} context with the current path as the root.</p>
	 * @param port The port number for the HTTP server
	 * @param cntx The context path for the HTTP server
	 * @param readOnly <tt>true</tt> for a read-only core context; <tt>false</tt>, otherwise.
	 * @since Descripter 1.0
	 */
	public Daemon(int port, String cntx, boolean readOnly) {
		this(".", port, cntx, readOnly);
	}

	/**
	 * <p>Constructs a writable {@link Core} context.</p>
	 * @param root The root path for the HTTP server
	 * @param port The port number for the HTTP server
	 * @param cntx The context path for the HTTP server
	 * @since Descripter 1.0
	 */
	public Daemon(String root, int port, String cntx) {
		this(root, port, cntx, false);
	}

	/**
	 * <p>The root path for the HTTP server.</p>
	 * @since Descripter 1.0
	 */
	public final String root;
	/**
	 * <p>The context path for the HTTP server.</p>
	 * @since Descripter 1.0
	 */
	public final String cntx;
	/**
	 * <p>The port number for the HTTP server.</p>
	 * @since Descripter 1.0
	 */
	public final int port;
	/**
	 * <p>The object of MIME types.</p>
	 * @since Descripter 1.0
	 */
	public final CObject mime;

	/**
	 * <p>Constructs the specific {@link Core} context.</p>
	 * @param root The root path for the HTTP server
	 * @param port The port number for the HTTP server
	 * @param cntx The context path for the HTTP server
	 * @param readOnly <tt>true</tt> for a read-only core context; <tt>false</tt>, otherwise.
	 * @since Descripter 1.0
	 */
	public Daemon(String root, int port, String cntx, boolean readOnly) {
		super(false);

		System.out.println("Descripter 1.0 Daemon,  All right reserved.");
		System.out.println("Copyright (C) 2010-2012  J.J.Liu<jianjunliu@126.com> www.descripter.org");

		put(_this, this);
		this.port = port;
		this.cntx = cntx;
		this.root = root;
		this.mime = object();
		set(mime, "js"   , "application/javascript");
		set(mime, "class", "application/octet-stream");
		set(mime, "exe"  , "application/octet-stream");
		set(mime, "jar"  , "application/octet-stream");
		set(mime, "zip"  , "application/octet-stream");
		set(mime, "doc"  , "application/msword");
		set(mime, "pdf"  , "application/pdf");
		set(mime, "ogg"  , "application/x-ogg");
		set(mime, "swf"  , "application/x-shockwave-flash");
		set(mime, "mp3"  , "audio/mpeg");
		set(mime, "m3u"  , "audio/mpeg-url");
		set(mime, "gif"  , "image/gif");
		set(mime, "jpeg" , "image/jpeg");
		set(mime, "jpg"  , "image/jpeg");
		set(mime, "png"  , "image/png");
		set(mime, "css"  , "text/css");
		set(mime, "htm"  , "text/html");
		set(mime, "html" , "text/html");
		set(mime, "asc"  , "text/plain");
		set(mime, "txt"  , "text/plain");
		set(mime, "xml"  , "text/xml");
		set(mime, "mp4"  , "video/mp4");
		set(mime, "ogv"  , "video/ogg");
		set(mime, "mov"  , "video/quicktime");
		set(mime, "flv"  , "video/x-flv");
		readOnly(readOnly);
		System.out.println("Descripter 1.0 Daemon initialized.");
	}
}
