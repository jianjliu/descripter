
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

import java.io.Reader;
import java.io.StringReader;

import org.descripter.js.Visitor;
import org.descripter.js.ss.parse.JSSPHtml;
import org.descripter.js.ss.parse.JSSPPage;
import org.descripter.js.ss.parse.JSSPParser;
import org.descripter.js.ss.parse.JSSPParserVisitor;
import org.descripter.js.ss.parse.JSSPScript;
import org.descripter.js.ss.parse.SimpleNode;

/**
 * <p>Converts server-pages to JavaScript.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Scriptizer extends Visitor<Scriptlet<? extends Daemon>> implements JSSPParserVisitor
{
	/**
	 * <p>Constructs a visitor of this type.</p>
	 * @param scriptlet A {@link Scriptlet} context.
	 * @since Descripter 1.0
	 */
	public Scriptizer(Scriptlet<? extends Daemon> scriptlet) {
		super(scriptlet);
	}

	/**
	 * <p>Converts JavaScript Server Page into JavaScript.</p>
	 * @param jssp The text of the JavaScript Server Page.
	 * @return The converted JavaScript code
	 * @since Descripter 1.0
	 */
	public String scriptize(String jssp) {
		return scriptize(new StringReader(jssp));
	}

	/**
	 * <p>Converts JavaScript Server Page into JavaScript.</p>
	 * @param reader A {@link Reader} to read the text of the JavaScript Server Page.
	 * @return The converted JavaScript code
	 * @since Descripter 1.0
	 */
	public String scriptize(Reader reader) {
		try {
			return new JSSPParser(reader).Page().jjtAccept(
					this,
					""
			).toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(SimpleNode node, Object data) {
		return node.jjtAccept(this, data);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(JSSPPage node, Object data) {
		return node.acceptChildren(this, data);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(JSSPScript node, Object data) {
		return cat(data, node.jjtGetValue());
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(JSSPHtml node, Object data) {
		return cat(
				data,
				stmt(
						inv("print",
								qt(
										esc(
												node.jjtGetValue().toString()
										)
								)
						)
				)
		);
	}
}
