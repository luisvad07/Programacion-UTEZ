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

import java.util.*;

/**
 * A PhiStmt is inserted into a CFG in Single Static Assignment for. It is used
 * to "merge" uses of the same variable in different basic blocks.
 * 
 * @see PhiJoinStmt
 * @see PhiCatchStmt
 */
public abstract class PhiStmt extends Stmt implements Assign {
	VarExpr target; // The variable into which the Phi statement assigns

	/**
	 * Constructor.
	 * 
	 * @param target
	 *            A stack expression or local variable that is the target of
	 *            this phi-statement.
	 */
	public PhiStmt(final VarExpr target) {
		this.target = target;
		target.setParent(this);
	}

	public VarExpr target() {
		return target;
	}

	/**
	 * Return the expressions (variables) defined by this PhiStmt. In this case,
	 * only the target is defined.
	 */
	public DefExpr[] defs() {
		return new DefExpr[] { target };
	}

	public abstract Collection operands();

	public Object clone() {
		throw new RuntimeException();
	}
}
