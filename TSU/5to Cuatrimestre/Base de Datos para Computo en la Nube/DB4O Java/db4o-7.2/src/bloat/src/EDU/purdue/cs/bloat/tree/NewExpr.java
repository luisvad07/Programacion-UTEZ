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
 * NewExpr represents the <tt>new</tt> opcode that creates a new object of a
 * specified type.
 */
public class NewExpr extends Expr {
	// new
	Type objectType;

	/**
	 * Constructor.
	 * 
	 * @param objectType
	 *            The type of the object to create.
	 * @param type
	 *            The type of this expression.
	 */
	public NewExpr(final Type objectType, final Type type) {
		super(type);
		this.objectType = objectType;
	}

	/**
	 * Returns the <tt>Type</tt> of the object being created.
	 */
	public Type objectType() {
		return objectType;
	}

	public void visitForceChildren(final TreeVisitor visitor) {
	}

	public void visit(final TreeVisitor visitor) {
		visitor.visitNewExpr(this);
	}

	public int exprHashCode() {
		return 16 + objectType.hashCode();
	}

	public boolean equalsExpr(final Expr other) {
		return false;
	}

	public Object clone() {
		return copyInto(new NewExpr(objectType, type));
	}
}
