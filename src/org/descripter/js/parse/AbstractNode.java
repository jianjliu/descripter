
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
package org.descripter.js.parse;

/**
 * <p>An abstract base class for JJTree nodes.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public abstract class AbstractNode extends SimpleNode
{
	/**
	 * <p>Constructs a {@link AbstractNode} without a JJTreeParser.</p>
	 * @param i The id.
	 * @since Descripter 1.0
	 */
	protected AbstractNode(int i) {
		super(i);
	}

	/**
	 * <p>Constructs a {@link AbstractNode} with a JSParser.</p>
	 * @param p The JSParser
	 * @param i The id.
	 * @since Descripter 1.0
	 */
	protected AbstractNode(JSParser p, int i) {
		super(p, i);
	}

	/**
	 * <p>The first token of this node.</p>
	 * @since Descripter 1.0
	 */
	public Token firstToken;
	/**
	 * <p>The last token of this node.</p>
	 * @since Descripter 1.0
	 */
	public Token lastToken;

	/**
	 * <p>Accepts children.</p>
	 * @param visitor The visitor
	 * @param data The data argument
	 * @return An array of results.
	 * @since Descripter 1.0
	 **/
	public final Object[] acceptChildren(JSParserVisitor visitor, Object data) {
		int n = jjtGetNumChildren();
		Object[] list = new Object[n];
		for (int i = 0; i < n; ++i) {
			list[i] = getChild(i).jjtAccept(visitor, data);
		}
		return list;
	}

	/**
	 * <p>Gets a child node.</p>
	 * @param i The index of the node to get.
	 * @return The {@link AbstractNode}.
	 * @since Descripter 1.0
	 **/
	public final AbstractNode getChild(int i) {
		return (AbstractNode)jjtGetChild(i);
	}
}
