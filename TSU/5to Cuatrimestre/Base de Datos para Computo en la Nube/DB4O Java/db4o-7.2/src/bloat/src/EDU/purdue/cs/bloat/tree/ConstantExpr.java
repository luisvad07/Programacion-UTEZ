/* Copyright (C) 2004 - 2008  db4objects Inc.  http://www.db4o.com

This file is part of the db4o open source object database.

db4o is free software; you can redistribute it and/or modify it under
the terms of version 2 of the GNU General Public License as published
by the Free Software Foundation and as clarified by db4objects' GPL 
interpretation policy, available at
http://www.db4o.com/about/company/legalpolicies/gplinterpretation/
Alternatively you can write to db4objects, Inc., 1900 S Norfolk Street,
Suite 350, San Mateo, CA 94403, USA.

db4o is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. */
package EDU.purdue.cs.bloat.tree;

import EDU.purdue.cs.bloat.editor.*;

/**
 * ConstantExpr represents a constant expression. It is used when opcodes <i>ldc</i>,
 * <i>iinc</i>, and <i>getstatic</i> are visited. It value must be an Integer,
 * Long, Float, Double, or String.
 */
public class ConstantExpr extends Expr implements LeafExpr {
	// ldc

	Object value; // The operand to the ldc instruction

	/**
	 * Constructor.
	 * 
	 * @param value
	 *            The operand of the ldc instruction
	 * @param type
	 *            The Type of the operand
	 */
	public ConstantExpr(final Object value, final Type type) {
		super(type);
		this.value = value;
	}

	/**
	 * @return The operand of the ldc instruction
	 */
	public Object value() {
		return value;
	}

	public void visitForceChildren(final TreeVisitor visitor) {
	}

	public void visit(final TreeVisitor visitor) {
		visitor.visitConstantExpr(this);
	}

	/**
	 * @return A hash code for this expression.
	 */
	public int exprHashCode() {
		if (value != null) {
			return 10 + value.hashCode();
		}

		return 10;
	}

	/**
	 * Compare this ConstantExpr to another Expr.
	 * 
	 * @param other
	 *            An Expr to compare this to.
	 * 
	 * @return True, if this and other are the same (that is, have the same
	 *         contents).
	 */
	public boolean equalsExpr(final Expr other) {
		if (!(other instanceof ConstantExpr)) {
			return false;
		}

		if (value == null) {
			return ((ConstantExpr) other).value == null;
		}

		if (((ConstantExpr) other).value == null) {
			return false;
		}

		return ((ConstantExpr) other).value.equals(value);
	}

	public Object clone() {
		return copyInto(new ConstantExpr(value, type));
	}
}
