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

import EDU.purdue.cs.bloat.cfg.*;

/**
 * IfZeroStmt evaluates an expression and executes one of its two branches
 * depending on whether or not the expression evaluated to zero.
 */
public class IfZeroStmt extends IfStmt {
	Expr expr; // Expression to evaluate

	/**
	 * Constructor.
	 * 
	 * @param comparison
	 *            Comparison operator.
	 * @param expr
	 *            An expression to be evaluated.
	 * @param trueTarget
	 *            Basic Block that is executed if the expression evaluates to
	 *            zero.
	 * @param falseTarget
	 *            Basic Block that is executed if the expression evaluates to
	 *            non-zero.
	 */
	public IfZeroStmt(final int comparison, final Expr expr,
			final Block trueTarget, final Block falseTarget) {
		super(comparison, trueTarget, falseTarget);
		this.expr = expr;
		expr.setParent(this);
	}

	/**
	 * @return The expression that is evaluated.
	 */
	public Expr expr() {
		return expr;
	}

	public void visitForceChildren(final TreeVisitor visitor) {
		expr.visit(visitor);
	}

	public void visit(final TreeVisitor visitor) {
		visitor.visitIfZeroStmt(this);
	}

	public Object clone() {
		return copyInto(new IfZeroStmt(comparison, (Expr) expr.clone(),
				trueTarget, falseTarget));
	}
}
