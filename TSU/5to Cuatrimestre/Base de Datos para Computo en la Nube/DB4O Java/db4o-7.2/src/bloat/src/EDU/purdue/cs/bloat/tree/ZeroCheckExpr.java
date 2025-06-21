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
 * ZeroCheckExpr represents a check for a zero value. For instance, when a
 * division operation is performed. a ZeroCheckExpr is inserted to ensure that
 * the divisor is not zero. It is used when division is performed (<i>idiv</i>,
 * <i>ldiv</i>) a remainder is taken (<i>irem</i>, <i>lrem</i>), or a field
 * is accessed (<i>getfield</i>, <i>putfield</i).
 */
public class ZeroCheckExpr extends CheckExpr {
	/**
	 * Constructor.
	 * 
	 * @param expr
	 *            The expression to check for a zero value.
	 * @param type
	 *            The type of this expression.
	 */
	public ZeroCheckExpr(final Expr expr, final Type type) {
		super(expr, type);
	}

	public void visit(final TreeVisitor visitor) {
		visitor.visitZeroCheckExpr(this);
	}

	public boolean equalsExpr(final Expr other) {
		return (other instanceof ZeroCheckExpr) && super.equalsExpr(other);
	}

	public Object clone() {
		return copyInto(new ZeroCheckExpr((Expr) expr.clone(), type));
	}
}
