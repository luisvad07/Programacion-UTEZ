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
 * <tt>ArrayLengthExpr</tt> represents the <i>arraylength</i> opcode which
 * gets length of an array.
 */
public class ArrayLengthExpr extends Expr {
	Expr array;

	/**
	 * Constructor.
	 * 
	 * @param array
	 *            Array whose length is sought.
	 * @param type
	 *            The type of this expression.
	 */
	public ArrayLengthExpr(final Expr array, final Type type) {
		super(type);
		this.array = array;
		array.setParent(this);
	}

	public Expr array() {
		return array;
	}

	public void visitForceChildren(final TreeVisitor visitor) {
		if (visitor.reverse()) {
			array.visit(visitor);
		} else {
			array.visit(visitor);
		}
	}

	public void visit(final TreeVisitor visitor) {
		visitor.visitArrayLengthExpr(this);
	}

	public int exprHashCode() {
		return 3 + array.exprHashCode() ^ type.simple().hashCode();
	}

	public boolean equalsExpr(final Expr other) {
		return (other != null) && (other instanceof ArrayLengthExpr)
				&& ((ArrayLengthExpr) other).array.equalsExpr(array);
	}

	public Object clone() {
		return copyInto(new ArrayLengthExpr((Expr) array.clone(), type));
	}
}
