
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
import java.util.Set;

import org.descripter.js.api.*;
import org.descripter.js.parse.*;

/**
 * <p>Represents a visitor to interpret parsed JavaScript.</p>
 * 
 * @author <a href="mailto:jianjunliu@126.com">J.J.Liu (Jianjun Liu)</a> at <a href="http://www.descripter.org" target="_blank">http://www.descripter.org</a>
 * @since Descripter 1.0
 */
public class Interpreter extends Visitor<Script<?>> implements JSParserVisitor
{
	/**
	 * <p>Constructs a visitor of this type.</p>
	 * <p>Note, a script source can be interpreted in any script context.</p>
	 * @param script A script context.
	 * @since Descripter 1.0
	 */
	public Interpreter(Script<?> script) {
		super(script);
	}

	/**
	 * <p>Evaluates a piece of script code in the current script context.</p>
	 * @param js JavaScript source.
	 * @return The returned value from the script code.
	 * @since Descripter 1.0
	 */
	@Override
	public Object eval(String js) {
		return eval(new StringReader(js));
	}

	/**
	 * <p>Evaluates the script code from the given reader in the current context.</p>
	 * @param reader A {@link Reader} to read script source.
	 * @return The returned value from the script code.
	 * @since Descripter 1.0
	 */
	public Object eval(Reader reader) {
		try {
			return new JSParser(reader).Program().jjtAccept(this, this);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>Visits the children nodes of the specified node with the same argument datum and 
	 * returns the argument.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	protected final Object visitChildren(AbstractNode node, Object data) {
		node.acceptChildren(this, data);
		return data;
	}

	/**
	 * <p>Visits the children nodes of the specified node with the same argument datum and 
	 * returns an array of the returned data.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An array of the returned {@link Object} data.
	 * @since Descripter 1.0
	 */
	protected final Object evalChildren(AbstractNode node, Object data) {
		Object[] a = node.acceptChildren(this, data);
		return a != null && a.length > 0 ? a[a.length - 1] : data;
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
		return get(core()._this);
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
		return node.getChild(0).jjtAccept(this, data);
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
			case JSParserConstants.BOOLEAN_LITERAL:
				return s.toLowerCase().equals("true") ? true : false;
			case JSParserConstants.HEX_INTEGER_LITERAL:
				try {
					return Integer.parseInt(s.substring(2), 16);
				} catch(NumberFormatException e) {
					return Long.parseLong(s.substring(2), 16);
				}
			case JSParserConstants.DECIMAL_LITERAL:
				try {
					return Integer.parseInt(s);
				} catch(NumberFormatException e) {
					try {
						return Long.parseLong(s);
					} catch(NumberFormatException nfe) {
						return Double.parseDouble(s);
					}
				}
			case JSParserConstants.NULL_LITERAL:
				return get(core()._null);
			case JSParserConstants.REGULAR_EXPRESSION_LITERAL:
				int last = s.lastIndexOf('/');
				return re(s.substring(1, last), s.substring(last + 1));
			default:
				return s.substring(1, s.length() - 1);
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
		return var(data, node.jjtGetValue());
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
		return array(node.acceptChildren(this, this));
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
		return visitChildren(node, object());
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
		set(data, node.getChild(0).jjtGetValue(), node.getChild(1).jjtAccept(this, this));
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
		return alloc(
				node.getChild(0).jjtAccept(this, this),
				node.getChild(1).acceptChildren(this, this)
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
	public Object visit(ECMAAllocation node, Object data) {
		if (DEBUG) debug(node);
		return alloc(
				node.getChild(0).jjtAccept(this, this)
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
	public Object visit(ECMAArrayAccessor node, Object data) {
		if (DEBUG) debug(node);
		return var(data, node.getChild(0).jjtAccept(this, this).toString());
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
		return node.getChild(0).jjtAccept(this, data);
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
		return call(data, node.acceptChildren(this, this));
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
		Var v = (Var)node.getChild(0).jjtAccept(this, this);
		switch (node.getChild(1).firstToken.kind) {
			case JSParserConstants.INC:
				return v.uninc();
			case JSParserConstants.DEC:
				return v.undec();
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
			Object a = ((Object[])data)[0], b = ((Object[])data)[1];
			switch (node.firstToken.kind) {
				case JSParserConstants.LT:
					return lt  (a, b);
				case JSParserConstants.GT:
					return gt  (a, b);
				case JSParserConstants.LTE:
					return lte (a, b);
				case JSParserConstants.GTE:
					return gte (a, b);
				case JSParserConstants.EQ:
					return eq  (a, b);
				case JSParserConstants.NE:
					return !eq (a, b);
				case JSParserConstants.EQS:
					return eqs (a, b);
				case JSParserConstants.NEQS:
					return !eqs(a, b);
				case JSParserConstants.PLUS:
					return add (a, b);
				case JSParserConstants.MINUS:
					return sub (a, b);
				case JSParserConstants.MUL:
					return mul (a, b);
				case JSParserConstants.MOD:
					return mod (a, b);
				case JSParserConstants.DIV:
					return div (a, b);
				case JSParserConstants.SHL:
					return shl (a, b);
				case JSParserConstants.SHR:
					return shr (a, b);
				case JSParserConstants.SHRU:
					return shru(a, b);
				case JSParserConstants.AND:
					return and (a, b);
				case JSParserConstants.OR:
					return or  (a, b);
				case JSParserConstants.XOR:
					return xor (a, b);
				case JSParserConstants.BAND:
					return band(a, b);
				case JSParserConstants.BOR:
					return bor (a, b);
				case JSParserConstants.ASSIGN:
					return ((Var)b).assign(a);
				case JSParserConstants.AADD:
					return ((Var)b).aadd(a);
				case JSParserConstants.ASUB:
					return ((Var)b).asub(a);
				case JSParserConstants.AMUL:
					return ((Var)b).amul(a);
				case JSParserConstants.ADIV:
					return ((Var)b).adiv(a);
				case JSParserConstants.ASHL:
					return ((Var)b).ashl(a);
				case JSParserConstants.ASHR:
					return ((Var)b).ashr(a);
				case JSParserConstants.ASHRU:
					return ((Var)b).ashru(a);
				case JSParserConstants.AAND:
					return ((Var)b).aand(a);
				case JSParserConstants.AOR:
					return ((Var)b).aor(a);
				case JSParserConstants.AXOR:
					return ((Var)b).axor(a);
				case JSParserConstants.INSTANCEOF:
					return instanceOf(b, a);
				default:
					return null;
			}
		} else {
			switch (node.firstToken.kind) {
				case JSParserConstants.DELETE:
					return ((Var)data).delete();
				case JSParserConstants.VOID:
					return null;
				case JSParserConstants.TYPEOF:
					return typeof(data);
				case JSParserConstants.INC:
					return ((Var)data).inc();
				case JSParserConstants.DEC:
					return ((Var)data).dec();
				case JSParserConstants.PLUS:
					return data;
				case JSParserConstants.MINUS:
					return neg(data);
				case JSParserConstants.NOT:
					return not(data);
				case JSParserConstants.BNOT:
					return bnot(data);
				default:
					return null;
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
		return node.getChild(0).jjtAccept(this, node.getChild(1).jjtAccept(this, this));
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
		Object o = node.getChild(0).jjtAccept(this, this);
		for (int i = 1, n = node.jjtGetNumChildren(); i < n; i += 2) {
			o = node.getChild(i).jjtAccept(
					this, new Object[]{o, node.getChild( i + 1).jjtAccept(this, this)}
			);
		}
		return o;
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
		Object o = node.getChild(0).jjtAccept(this, this);
		for (int i = 1, n = node.jjtGetNumChildren(); i < n; i += 2) {
			if (bool(o)) {
				o = node.getChild(i).jjtAccept(
						this, new Object[]{o, node.getChild( i + 1).jjtAccept(this, this)}
				);
			}
		}
		return o;
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
		Object o = node.getChild(0).jjtAccept(this, this);
		for (int i = 1, n = node.jjtGetNumChildren(); i < n; i += 2) {
			if (bnot(o)) {
				o = node.getChild(i).jjtAccept(
						this, new Object[]{o, node.getChild( i + 1).jjtAccept(this, this)}
				);
			}
		}
		return o;
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
		return bool(node.getChild(0).jjtAccept(this, this)) ?
				    node.getChild(1).jjtAccept(this, this) :
				    node.getChild(2).jjtAccept(this, this);
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
		return node.getChild(1).jjtAccept(this, new Object[]{
				node.getChild(2).jjtAccept(this, this),
				node.getChild(0).jjtAccept(this, this)
		});
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
		return visitChildren(node, data);
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
	public Object visit(ECMABlock node, Object data) {
		if (DEBUG) debug(node);
		Interpreter interp = new Interpreter(this);
		return interp.visitChildren(node, interp);
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
		return visitChildren(node, this);
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
		return node.getChild(0).jjtAccept(this, this);
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
		return visitChildren(node, data);
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
		Var v = ((Script<?>)data).my(node.getChild(0).jjtGetValue().toString());
		if (node.jjtGetNumChildren() > 1) {
			v.assign(node.getChild(1).jjtAccept(this, this));
		}
		return v;
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
		return node.getChild(0).jjtAccept(this, data);
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
		if (bool(node.getChild(0).jjtAccept(this, this))) {
			node.getChild(1).jjtAccept(this, this);
		} else if (node.jjtGetNumChildren() > 2) {
			node.getChild(2).jjtAccept(this, this);
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
	public Object visit(ECMADoWhileStatement node, Object data) {
		if (DEBUG) debug(node);
		do {
			try {
				node.getChild(0).jjtAccept(this, this);
			} catch (Break e) {
				e.tryThrow(null);
				break;
			} catch (Continue e) {
				e.tryThrow(null);
				continue;
			}
		} while (bool(node.getChild(1).jjtAccept(this, this)));
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
	public Object visit(ECMAWhileStatement node, Object data) {
		if (DEBUG) debug(node);
		while (bool(node.getChild(0).jjtAccept(this, this))) {
			try {
				node.getChild(1).jjtAccept(this, this);
			} catch (Break e) {
				e.tryThrow(null);
				break;
			} catch (Continue e) {
				e.tryThrow(null);
				continue;
			}
		};
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
	public Object visit(ECMAForStatement node, Object data) {
		if (DEBUG) debug(node);
		return visitForStatement(node);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	protected Object visitForStatement(AbstractNode node) {
		node.getChild(0).jjtAccept(this, this);
		while (bool(node.getChild(1).jjtAccept(this, true))) {
			try {
				node.getChild(3).jjtAccept(this, this);
				node.getChild(2).jjtAccept(this, this);
			} catch (Break e) {
				e.tryThrow(null);
				break;
			} catch (Continue e) {
				e.tryThrow(null);
				continue;
			}
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
	public Object visit(ECMAForVarStatement node, Object data) {
		if (DEBUG) debug(node);
		return new Interpreter(this).visitForStatement(node);
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
		return new Interpreter(this).visitForInStatement(node);
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	protected Object visitForInStatement(AbstractNode node) {
		if (DEBUG) debug(node);
		Var var = (Var)node.getChild(0).jjtAccept(this, this);
		Objective<?> base = object(node.getChild(1).jjtAccept(this, this));
		if (base != null) {
			Set<Key> keys = base.keys();
			for (Key k : keys) {
				var.assign(k);
				try {
					node.getChild(2).jjtAccept(this, this);
				} catch (Break e) {
					e.tryThrow(null);
					break;
				} catch (Continue e) {
					e.tryThrow(null);
					continue;
				}
			}
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
	public Object visit(ECMAForInStatement node, Object data) {
		if (DEBUG) debug(node);
		return visitForInStatement(node);
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
		throw node.jjtGetNumChildren() < 1 ? new Continue() :
			new Continue(node.getChild(0).jjtGetValue());
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
		throw node.jjtGetNumChildren() < 1 ? new Break() :
				new Break(node.getChild(0).jjtGetValue());
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
		throw new Return(node.jjtGetNumChildren() < 1 ? null :
			node.getChild(0).jjtAccept(this, data));
	}

	/**
	 * <p>Visits a parsed node of the specified type.</p>
	 * @param node The node to visit.
	 * @param data An {@link Object} datum passed to this method.
	 * @return An {@link Object} datum.
	 * @since Descripter 1.0
	 */
	@Override
	public Object visit(final ECMAWithStatement node, Object data) {
		if (DEBUG) debug(node);
		new With<Interpreter>(this, node.getChild(0).jjtAccept(this, this)) {
			@Override
			public void run() {
				Interpreter interp = new Interpreter(this);
				node.getChild(1).jjtAccept(interp, interp);
			}
			
		}.run();
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
	public Object visit(ECMASwitchStatement node, Object data) {
		if (DEBUG) debug(node);
		try {
			return node.getChild(1).jjtAccept(this, new Object[]{
					node.getChild(0).jjtAccept(this, this), false
				});
		} catch (Break e) {
			return null;
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
	public Object visit(ECMACaseBlock node, Object data) {
		if (DEBUG) debug(node);
		return visitChildren(node, data);
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
		if (bnot(((Object[])data)[1])) {
			node.getChild(0).jjtAccept(this, data);
		}
		if (bool(((Object[])data)[1])) {
			return node.getChild(1).jjtAccept(this, this);
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
	public Object visit(ECMACase node, Object data) {
		if (DEBUG) debug(node);
		if (node.jjtGetNumChildren() > 0) {
			((Object[])data)[1] = eqs(
					((Object[])data)[0],
					node.getChild(0).jjtAccept(this, this)
			);
		} else {
			((Object[])data)[1] = true;
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
		Object label = node.getChild(0).jjtGetValue();
		while (true) {
			try {
				node.getChild(1).jjtAccept(this, this);
			} catch (Break e) {
				e.tryThrow(label);
				return null;
			} catch (Continue e) {
				e.tryThrow(label);
				continue;
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
	public Object visit(ECMAThrowStatement node, Object data) {
		if (DEBUG) debug(node);
		throw new Thrown(node.getChild(0).jjtAccept(this, this));
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
		boolean catched = false;
		int last = node.jjtGetNumChildren() - 1;
		try {
			return node.getChild(0).jjtAccept(this, this);
		} catch (Thrown e) {
			if (last > 1) {
				node.getChild(last - 1).jjtAccept(this, e);
				node.getChild(last).jjtAccept(this, this);
			} else {
				node.getChild(last).jjtAccept(this, e);
			}
			catched = true;
		} catch (Abrupt e) {
			catched = true;
			throw e;
		} finally {
			if (!catched) {
				node.getChild(last).jjtAccept(this, this);
			}
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
	public Object visit(ECMACatch node, Object data) {
		if (DEBUG) debug(node);
		Interpreter interp = new Interpreter(this);
		interp.var(node.getChild(0).jjtGetValue()).assign(data);
		return node.getChild(1).jjtAccept(interp, interp);
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
		Object o = node.getChild(0).jjtAccept(this, this);
		if (data instanceof Thrown) {
			throw (Thrown)data;
		}
		return o;
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
	public Object visit(final ECMAFunctionDeclaration node, Object data) {
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
	protected Object visitFunction(final AbstractNode node, Object data) {
		Function<?> f = new Function<Interpreter>(this) {
			@Override
			protected Functor<Interpreter> functor() {
				return new Functor<Interpreter>(this) {
					@Override
					public Object function() {
						Interpreter interp = new Interpreter(this);
						int n = node.jjtGetNumChildren();
						node.getChild(n - 2).jjtAccept(interp, this);
						try {
							return interp.evalChildren(node.getChild(n - 1), this);
						} catch (Return e) {
							return e.value;
						} catch (Break e) {
							if (e.value != null) {
								throw new RuntimeException(e);
							}
							return null;
						} catch (Abrupt e) {
							throw new RuntimeException(e);
						}
					}
				};
			}
		};
		if (node.jjtGetNumChildren() > 2) {
			var(node.getChild(0).jjtGetValue()).assign(f);
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
	public Object visit(final ECMAFunctionExpression node, Object data) {
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
		int n = node.jjtGetNumChildren();
		Functor<?> f = (Functor<?>)data;
		for (int i = 0; i < n; i++) {
			f.my(node.getChild(i).jjtGetValue().toString()).assign(f.arguments().get(i));			
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
	public Object visit(ECMAProgram node, Object data) {
		if (DEBUG) debug(node);
		return evalChildren(node, data);
	}
}
