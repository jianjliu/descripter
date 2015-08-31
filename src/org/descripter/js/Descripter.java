
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

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.descripter.js.api.*;
import org.descripter.js.parse.*;

/**
 * <p>Represents a visitor to descript parsed JavaScript into Java source.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Descripter extends Visitor<Script<?>> implements JSParserVisitor
{
	/**
	 * <p>Constructs a visitor of this type.</p>
	 * <p>Note, a script source can only be javafied in any script context.</p>
	 * @param script A script script context.
	 * @since Descripter 1.0
	 */
	public Descripter(Script<?> script) {
		super(script);
	}

	/**
	 * <p>Descripts the given script code into Java source with the specified class name.</p>
	 * @param cls Specifies a class name.
	 * @param js JavaScript source.
	 * @return The descripted Java source.
	 * @since Descripter 1.0
	 */
	public String descript(String cls, String js) {
		return descript(cls, new StringReader(js));
	}

	/**
	 * <p>Descripts the script code from the given reader into Java source with the specified class name.</p>
	 * @param cls Specifies a class name.
	 * @param reader A {@link Reader} to read script source.
	 * @return The descripted Java source.
	 * @since Descripter 1.0
	 */
	public String descript(String cls, Reader reader) {
		String src = "";
		cls = cls.replace('/', '.').replace('\\', '.');
		int last = cls.lastIndexOf('.');
		if (last != -1) {
			src = catn(
					src,
					stmt(cats("package", cls.substring(0, last)))
			);
			cls = cls.substring(last + 1);
		}
		src = catn(
				src,
				"",
				stmt(cats("import", "org.descripter.js.api.*")),
				"",
				cats("public", "class", par(cls, cats("W", "extends", "Script<?>")), "extends", par("Script", "W")),
				"{",
				tab(cats("public", "void", def("run"), "{"))
		);
		try {
			src = catn(
					src,
					new JSParser(reader).Program().jjtAccept(this, tab2("")),
					tab("}")
			);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		src = catn(
				src,
				"",
				tab(cats("public", inv(cls, cats("W", "with")), "{")),
				tab2(stmt(inv("super", "with")))
		);
		src = catn(
				src,
				tab("}"),
				!list.isEmpty() ? catn(
						"",
						tab(stmt(cats("private", "final", "Key[]", "_", "=", "{", list(list.toArray()), "}"))),
						""
				) : "", 
				tab(cats("public", "static", "void", inv("main", cats("String[]", "args")), "{")),
				tab2(stmt(cats(par(cls, "Core"), "desc", "=", nevv(par(cls, "Core"), nevv("Core"))))),
				tab2(stmt(run("desc"))),
				tab("}"),
				"}"
		);
		return src;
	}

	private final List<String> list = new ArrayList<String>();
	private final Map<String, Integer> map = new HashMap<String, Integer>();

	/**
	 * <p>Returns the internal integer key of an identifier.</p>
	 * @param id The identifier
	 * @return The internal integer key.
	 * @since Descripter 1.0
	 */
	protected final int id(Object id) {
		String name = id.toString();
		if (Key.has(name)) {
			Key k = key(name);
			if (k.context() instanceof Core) {
				return k.hashCode();
			}
		}
		if (map.containsKey(name)) {
			return map.get(name);
		}
		int i = map.size();
		map.put(name, i);
		list.add(inv("key", qt(name)));
		return i;
	}

	/**
	 * <p>Returns an expression to localize an identifier.</p>
	 * @param id The identifier
	 * @return The string of the expression to localize the identifier.
	 * @since Descripter 1.0
	 */
	protected final String local(Object id) {
		int i = id(id);
		return inv("my", i < 0 ? inv(ref("Key", "get"), i) : cat("_[", i, "]"));
	}

	/**
	 * <p>Returns an expression to access a global identifier.</p>
	 * @param id The global identifier
	 * @return The string of the expression to access the global identifier.
	 * @since Descripter 1.0
	 */
	protected final String global(Object id) {
		int i = id(id);
		return inv("var", i < 0 ? inv(ref("Key", "get"), i) : cat("_[", i, "]"));
	}

	/**
	 * <p>Evaluates a piece of script code in the current script context.</p>
	 * <p>This method simply throws an {@link UnsupportedOperationException}.</p>
	 * @param js JavaScript source.
	 * @return The returned value from the script code.
	 * @since Descripter 1.0
	 */
	@Override
	public final Object eval(String js) {
		throw new UnsupportedOperationException();
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
	public Object visit(ECMAThisReference node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, global("this"));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAGroup node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, node.getChild(0).jjtAccept(this, ""));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMALiteral node, Object data) {
		if (DEBUG) debug(node);
		String s = node.jjtGetValue().toString();
		switch (node.firstToken.kind) {
			case JSParserConstants.STRING_LITERAL:
				return cat(data, str(s));
			case JSParserConstants.NULL_LITERAL:
				return cat(data, global("null"));
			case JSParserConstants.BOOLEAN_LITERAL:
				return new Bool(cat(data, s));
			case JSParserConstants.REGULAR_EXPRESSION_LITERAL:
				int last = s.lastIndexOf('/');
				return cat(data, cat("re", arg(list(
						qt(s.substring(1, last)),
						qt(s.substring(last + 1))
				))));
			default:
				return cat(data, s);
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
	public Object visit(ECMAIdentifier node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, global(node.jjtGetValue()));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAArrayLiteral node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, "array(new Object[]{", list(node.acceptChildren(this, "")), "})");
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAObjectLiteral node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, "object()", cat(node.acceptChildren(this, "")));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAPropertyAssignment node, Object data) {
		if (DEBUG) debug(node);
		int i = id(node.getChild(0).jjtGetValue().toString());
		return cat(data, ".set(", list(
				i < 0 ? inv(ref("Key", "get"), i) : cat("_[", i, "]"),
				node.getChild(1).jjtAccept(this, "")
		), ")");
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAAccess node, Object data) {
		if (DEBUG) debug(node);
		data = node.getChild(0).jjtAccept(this, data);
		for (int i = 1; i < node.jjtGetNumChildren(); i++) {
			data = node.getChild(i).jjtAccept(this, data);
		}
		return data;
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMANewOperation node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, "alloc", args(
				node.getChild(0).jjtAccept(this, ""),
				node.getChild(1).acceptChildren(this, "")
		));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAAllocation node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, "alloc", arg(node.getChild(0).jjtAccept(this, "")));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAArrayAccessor node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, "[", node.getChild(0).jjtAccept(this, ""), "]");
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAMemberAccessor node, Object data) {
		if (DEBUG) debug(node);
		return ref(data, node.getChild(0).jjtAccept(this, ""));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAArguments node, Object data) {
		if (DEBUG) debug(node);
		return cat("call", args(data, node.acceptChildren(this, "")));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAPostfixExpression node, Object data) {
		if (DEBUG) debug(node);
		Object o = node.getChild(0).jjtAccept(this, "");
		switch (node.getChild(1).firstToken.kind) {
			case JSParserConstants.INC:
				return cat(data, def(ref(def(ref(o, "uninc")), "evaluate")));
			case JSParserConstants.DEC:
				return cat(data, def(ref(def(ref(o, "undec")), "evaluate")));
			default:
				throw new RuntimeException();
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
	public Object visit(ECMAOperator node, Object data) {
		if (DEBUG) debug(node);
		if (data instanceof Object[]) {
			Object[] arr = arr(data);
			Object a = arr[0], b = arr[1];
			switch (node.firstToken.kind) {
				case JSParserConstants.LT:
					return new Bool(cat("lt",  arg(list(a, b))));
				case JSParserConstants.GT:
					return new Bool(cat("gt",  arg(list(a, b))));
				case JSParserConstants.LTE:
					return new Bool(cat("lte", arg(list(a, b))));
				case JSParserConstants.GTE:
					return new Bool(cat("gte", arg(list(a, b))));
				case JSParserConstants.EQ:
					return new Bool(cat("eq",  arg(list(a, b))));
				case JSParserConstants.NE:
					return new Bool(cat("!eq", arg(list(a, b))));
				case JSParserConstants.EQS:
					return new Bool(cat("eqs", arg(list(a, b))));
				case JSParserConstants.NEQS:
					return new Bool(cat("!eqs",arg(list(a, b))));
				case JSParserConstants.PLUS:
					return cat("add",   arg(list(a, b)));
				case JSParserConstants.MINUS:
					return cat("sub",   arg(list(a, b)));
				case JSParserConstants.MUL:
					return cat("mul",   arg(list(a, b)));
				case JSParserConstants.MOD:
					return cat("mod",   arg(list(a, b)));
				case JSParserConstants.DIV:
					return cat("div",   arg(list(a, b)));
				case JSParserConstants.SHL:
					return cat("shl",   arg(list(a, b)));
				case JSParserConstants.SHR:
					return cat("shr",   arg(list(a, b)));
				case JSParserConstants.SHRU:
					return cat("shru",  arg(list(a, b)));
				case JSParserConstants.AND:
					return cat("and",   arg(list(a, b)));
				case JSParserConstants.OR:
					return cat("or",    arg(list(a, b)));
				case JSParserConstants.XOR:
					return cat("xor",   arg(list(a, b)));
				case JSParserConstants.BAND:
					return new Bool(cat("band", arg(list(a, b))));
				case JSParserConstants.BOR:
					return new Bool(cat("bor",  arg(list(a, b))));
				case JSParserConstants.AADD:
					return cat(a, ".", "aadd",  arg(b));
				case JSParserConstants.ASUB:
					return cat(a, ".", "asub",  arg(b));
				case JSParserConstants.AMUL:
					return cat(a, ".", "amul",  arg(b));
				case JSParserConstants.ADIV:
					return cat(a, ".", "adiv",  arg(b));
				case JSParserConstants.ASHL:
					return cat(a, ".", "ashl",  arg(b));
				case JSParserConstants.ASHR:
					return cat(a, ".", "ashr",  arg(b));
				case JSParserConstants.ASHRU:
					return cat(a, ".", "ashru", arg(b));
				case JSParserConstants.AAND:
					return cat(a, ".", "aand",  arg(b));
				case JSParserConstants.AOR:
					return cat(a, ".", "aor",   arg(b));
				case JSParserConstants.AXOR:
					return cat(a, ".", "axor",  arg(b));
				case JSParserConstants.INSTANCEOF:
					return new Bool(cat("instanceOf", arg(list(a, b))));
				default:
					throw new RuntimeException();
			}
		} else {
			switch (node.firstToken.kind) {
				case JSParserConstants.DELETE:
					return cat(data, ".delete()");
				case JSParserConstants.VOID:
					return cat("comma", arg(list(data, "null")));
				case JSParserConstants.TYPEOF:
					return cat("typeof", arg(data));
				case JSParserConstants.INC:
					return cat(data, ".inc()");
				case JSParserConstants.DEC:
					return cat(data, ".dec()");
				case JSParserConstants.PLUS:
					return data;
				case JSParserConstants.MINUS:
					return cat("neg", arg(data));
				case JSParserConstants.NOT:
					return cat("not", arg(data));
				case JSParserConstants.BNOT:
					return new Bool(cat("bnot", arg(data)));
				default:
					throw new RuntimeException();
			}
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
	public Object visit(ECMAUnaryExpression node, Object data) {
		if (DEBUG) debug(node);
		return cat(
				data,
				node.jjtGetChild(0).jjtAccept(this, node.jjtGetChild(1).jjtAccept(this, ""))
		);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMABinaryOperation node, Object data) {
		if (DEBUG) debug(node);
		return visitBinary(node, data);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	protected Object visitBinary(AbstractNode node, Object data) {
		Object o = node.getChild(0).jjtAccept(this, "");
		for (int i = 1, n = node.jjtGetNumChildren(); i < n; i += 2) {
			o = node.getChild(i).jjtAccept(
					this, new Object[]{o, node.getChild( i + 1).jjtAccept(this, "")}
			);
		}
		return bool(data) ? cat(data, o) : o;
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAAndOperation node, Object data) {
		if (DEBUG) debug(node);
		return visitBinary(node, data);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAOrOperation node, Object data) {
		if (DEBUG) debug(node);
		return visitBinary(node, data);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAConditionalExpression node, Object data) {
		if (DEBUG) debug(node);
		return cat(
				data,
				car(node.getChild(0).jjtAccept(this, "")), "?",
			    node.getChild(1).jjtAccept(this, ""), ":",
			    node.getChild(2).jjtAccept(this, "")
		);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAAssignmentExpression node, Object data) {
		if (DEBUG) debug(node);
		return cat(
				data,
				asg(
						node.getChild(0).jjtAccept(this, ""),
						node.getChild(2).jjtAccept(this, "")
				)
		);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMACommaExpression node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, "comma", arg(list(node.acceptChildren(this, ""))));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAJavaStatement node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, node.firstToken.image.substring(1));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMABlock node, Object data) {
		if (DEBUG) debug(node);
		return catn(
				"{",
				catn(node.acceptChildren(this, tab(data))),
				cat(data, "}")
		);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAStatementList node, Object data) {
		if (DEBUG) debug(node);
		return catn(node.acceptChildren(this, data));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAVariableStatement node, Object data) {
		if (DEBUG) debug(node);
		return stmt(cat(data, node.getChild(0).jjtAccept(this, "")));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAVariableDeclarationList node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, "comma", arg(list(node.acceptChildren(this, ""))));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAVariableDeclaration node, Object data) {
		if (DEBUG) debug(node);
		String s = local(node.getChild(0).jjtGetValue());
		if (node.jjtGetNumChildren() > 1) {
			s = cat(asg(s, node.getChild(1).jjtAccept(this, "")));
		}
		return cat(data, s);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAEmptyExpression node, Object data) {
		if (DEBUG) debug(node);
		return data;
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAExpressionStatement node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, stmt(node.getChild(0).jjtAccept(this, "")));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAIfStatement node, Object data) {
		if (DEBUG) debug(node);
		String s = cats(
				cnd("if", car(node.getChild(0).jjtAccept(this, ""))),
				node.getChild(1).jjtAccept(this, data)
		);
		if (node.jjtGetNumChildren() > 2) {
			s = cats(
					s,
					"else",
					node.getChild(2).jjtAccept(this, data)
			);
		}
		return cat(data, s);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMADoWhileStatement node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, stmt(cats(
				"do",
				node.getChild(0).jjtAccept(this, data),
				cnd("while", car(node.getChild(1).jjtAccept(this, "")))
		)));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAWhileStatement node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, cats(
				cnd("while", car(node.getChild(0).jjtAccept(this, ""))),
				node.getChild(1).jjtAccept(this, data)
		));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAForStatement node, Object data) {
		if (DEBUG) debug(node);
		return visitFor(node, data);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	protected Object visitFor(AbstractNode node, Object data) {
		return cat(data, cats(
				cat(
						"for",
						arg(concat(";",
								node.getChild(0).jjtAccept(this, ""),
								car(
										node.getChild(1).jjtAccept(this, "")
								),
								node.getChild(2).jjtAccept(this, "")
						))
				),
				node.getChild(3).jjtAccept(this, data)
		));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAForVarStatement node, Object data) {
		if (DEBUG) debug(node);
		return visitFor(node, data);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAForVarInStatement node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, stmt(catn(
				nevv(
						"MyFor",
						"this",
						node.getChild(1).jjtAccept(this, ""),
						id(node.getChild(0).getChild(0).jjtGetValue())
				),
				cat(data, "{"),
				tab(cat(data, cats("protected", "void", def("each")))),
				tab(cat(data, node.getChild(2).jjtAccept(this, tab(data)))),
				cat(data, run("}"))
		)));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAForInStatement node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, stmt(catn(
				nevv(
						"MyFor",
						"this",
						node.getChild(1).jjtAccept(this, ""),
						node.getChild(0).jjtAccept(this, "")
				),
				cat(data, "{"),
				tab(cat(data, cats("protected", "void", def("each")))),
				tab(cat(data, node.getChild(2).jjtAccept(this, tab(data)))),
				cat(data, run("}"))
		)));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAContinueStatement node, Object data) {
		if (DEBUG) debug(node);
		return node.jjtGetNumChildren() < 1 ? cat(data, stmt("continue")) :
			cat(data, stmt(cats("continue", node.getChild(0).jjtGetValue())));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMABreakStatement node, Object data) {
		if (DEBUG) debug(node);
		return node.jjtGetNumChildren() < 1 ? cat(data, stmt("break")) :
			cat(data, stmt(cats("break", node.getChild(0).jjtGetValue())));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAReturnStatement node, Object data) {
		if (DEBUG) debug(node);
		return node.jjtGetNumChildren() < 1 ? cat(data, stmt("return")) :
			cat(data, stmt(cats("return", node.getChild(0).jjtAccept(this, ""))));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAWithStatement node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, stmt(catn(
				nevv("With", "this", node.getChild(0).jjtAccept(this, "")),
				cat(data, "{"),
				tab(cat(data, cats("public", "void", def("run")))),
				tab(cat(data, node.getChild(1).jjtAccept(this, tab(data)))),
				cat(data, run("}"))
		)));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMASwitchStatement node, Object data) {
		if (DEBUG) debug(node);
		Object[] arr = new Object[] {data, nevv("Cases", "this"), "{", 0};
		node.getChild(1).jjtAccept(this, arr);
		return cat(
				data,
				cats(
						inv(
								"switch",
								inv(
										ref(
												arr[1],
												"indexOf"
										),
										node.getChild(0).jjtAccept(this, "")
								)
						),
						arr[2]
				)
		);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMACaseBlock node, Object data) {
		if (DEBUG) debug(node);
		node.childrenAccept(this, data);
		Object[] arr = arr(data);
		arr[2] = catn(
				arr[2],
				cat(arr[0], "}")
		);
		return null;
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMACaseClause node, Object data) {
		if (DEBUG) debug(node);
		node.getChild(0).jjtAccept(this, data);
		Object[] arr = arr(data);
		AbstractNode sub1 = node.getChild(1);
		arr[2] = sub1 instanceof ECMABlock ? cats(
				arr[2],
				sub1.jjtAccept(this, tab(arr[0]))
		) : catn(
				arr[2],
				sub1.jjtAccept(this, tab2(arr[0]))
		);
		return null;
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMACase node, Object data) {
		if (DEBUG) debug(node);
		Object[] arr = arr(data);
		if (node.jjtGetNumChildren() > 0) {
			arr[1] = inv(
					ref(arr[1], "add"),
					node.getChild(0).jjtAccept(this, "")
			);
			arr[2] = catn(
					arr[2],
					cat(tab(arr[0]), cats("case", arr[3]), ":")
			);
			arr[3] = ((Integer)arr[3]) + 1;
		} else {
			arr[2] = catn(
					arr[2],
					cat(tab(arr[0]), "default", ":")
			);
		}
		return null;
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMALabelledStatement node, Object data) {
		if (DEBUG) debug(node);
		return catn(
				cat(
						data,
						node.getChild(0).jjtGetValue(),
						":"
				),
				node.getChild(1).jjtAccept(this, data)
		);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAThrowStatement node, Object data) {
		if (DEBUG) debug(node);
		return stmt(cat(
				data,
				cats("throw", nevv("Thrown", node.getChild(0).jjtAccept(this, "")))
		));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMATryStatement node, Object data) {
		if (DEBUG) debug(node);
		return cat(data, cats(
				"try",
				cat(node.acceptChildren(this, data))
		));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMACatch node, Object data) {
		if (DEBUG) debug(node);
		return catn(
				cats("", cat("catch", arg(cats("Thrown", "thrown"))), "{"),
				stmt(cat(
						tab(data),
						asg(
								local(node.getChild(0).jjtGetValue()),
								"thrown"
						)
				)),
				catn(
						node.getChild(1).acceptChildren(this, tab(data))
				),
				cat(data, "}")
		);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAFinally node, Object data) {
		if (DEBUG) debug(node);
		return cats(
				"",
				"finally",
				catn(node.acceptChildren(this, data))
		);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMADebuggerStatement node, Object data) {
		if (DEBUG) debug(node);
		return null;
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAFunctionDeclaration node, Object data) {
		if (DEBUG) debug(node);
		return stmt(visitFunction(node, data));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	protected Object visitFunction(AbstractNode node, Object data) {
		int n = node.jjtGetNumChildren();
		String f = catn(
				nevv("MyFunction", "this"),
				cat(data, "{"),
				tab(cat(data, cats("public", "MyFunctor", def("functor"), "{"))),
				tab2(cat(data, cats("return", nevv("MyFunctor", "this"), "{"))),
				tab3(cat(data, cats("public", "Object", def("function"), "{"))),
				node.getChild(n - 2).jjtAccept(this, tab4(data)),
				catn(
						node.getChild(n - 1).acceptChildren(this, tab4(data))
				),
				tab4(cat(data, stmt(cats("return", "null")))),
				tab3(cat(data, "}")),
				tab2(cat(data, stmt("}"))),
				tab(cat(data, "}")),
				cat(data, "}")
		);
		if (node.jjtGetNumChildren() > 2) {
			f = cat(data, asg(local(node.getChild(0).jjtGetValue()), f));
		} else {
			f = cat(data, f);
		}
		return f;
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAFunctionExpression node, Object data) {
		if (DEBUG) debug(node);
		return visitFunction(node, data);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAFormalParameterList node, Object data) {
		if (DEBUG) debug(node);
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			data = stmt(cat(
					data,
					asg(
							local(node.getChild(i).jjtGetValue()),
							inv(ref(def("arguments"), "get"), i)
					)
			));
		}
		return data;
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(ECMAProgram node, Object data) {
		return catn(node.acceptChildren(this, data));
	}
}
