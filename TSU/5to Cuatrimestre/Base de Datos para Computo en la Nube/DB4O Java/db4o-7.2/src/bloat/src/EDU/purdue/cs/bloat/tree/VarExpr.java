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
 * VarExpr represents an expression that accesses a local variable or a variable
 * on the stack.
 * 
 * @see StackExpr
 * @see LocalExpr
 * 
 * @see DefExpr
 */
public abstract class VarExpr extends MemExpr {
	int index;

	/**
	 * Constructor.
	 * 
	 * @param index
	 *            Index giving location of expression. For instance, the number
	 *            local variable represented or the position of the stack
	 *            variable represented.
	 * @param type
	 *            Type (descriptor) of this expression.
	 */
	public VarExpr(final int index, final Type type) {
		super(type);
		this.index = index;
	}

	public void setIndex(final int index) {
		this.index = index;
	}

	public int index() {
		return index;
	}

	/**
	 * Returns the expression that defines this expression.
	 */
	public DefExpr def() {
		if (isDef()) {
			return this;
		}

		return super.def();
	}
}
