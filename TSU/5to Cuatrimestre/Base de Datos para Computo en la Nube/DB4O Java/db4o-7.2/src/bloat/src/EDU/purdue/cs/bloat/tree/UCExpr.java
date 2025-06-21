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
 * UPExpr represents an update check opcode which checks the persistent store to
 * determine if a variable needs to be updated.
 */
public class UCExpr extends CheckExpr {
	public static final int POINTER = 1;

	public static final int SCALAR = 2;

	int kind;

	/**
	 * Constructor.
	 * 
	 * @param expr
	 *            The expression to check to see if it needs to be updated.
	 * @param kind
	 *            The kind of expression (POINTER or SCALAR) to be checked.
	 * @param type
	 *            The type of this expression.
	 */
	public UCExpr(final Expr expr, final int kind, final Type type) {
		super(expr, type);
		this.kind = kind;
	}

	public int kind() {
		return kind;
	}

	public void visit(final TreeVisitor visitor) {
		visitor.visitUCExpr(this);
	}

	public boolean equalsExpr(final Expr other) {
		return (other instanceof UCExpr) && super.equalsExpr(other)
				&& (((UCExpr) other).kind == kind);
	}

	public Object clone() {
		return copyInto(new UCExpr((Expr) expr.clone(), kind, type));
	}
}
