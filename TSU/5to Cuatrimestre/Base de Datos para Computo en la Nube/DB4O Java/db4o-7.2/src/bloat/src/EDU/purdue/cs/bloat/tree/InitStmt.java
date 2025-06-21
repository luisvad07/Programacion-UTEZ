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

/**
 * <tt>InitStmt</tt> groups together the initialization of local variables (<tt>LocalExpr</tt>).
 * 
 * @see LocalExpr
 * @see Tree#initLocals
 */
public class InitStmt extends Stmt implements Assign {
	LocalExpr[] targets;

	/**
	 * Constructor.
	 * 
	 * @param targets
	 *            The instances of LocalExpr that are to be initialized.
	 */
	public InitStmt(final LocalExpr[] targets) {
		this.targets = new LocalExpr[targets.length];

		for (int i = 0; i < targets.length; i++) {
			this.targets[i] = targets[i];
			this.targets[i].setParent(this);
		}
	}

	/**
	 * Returns the local variables (<tt>LocalExpr</tt>s) initialized by this
	 * <tt>InitStmt</tt>.
	 */
	public LocalExpr[] targets() {
		return targets;
	}

	/**
	 * Returns the local variables (<tt>LocalExpr</tt>s) defined by this
	 * <tt>InitStmt</tt>. These are the same local variables that are the
	 * targets of the <tt>InitStmt</tt>.
	 */
	public DefExpr[] defs() {
		return targets;
	}

	public void visitForceChildren(final TreeVisitor visitor) {
		for (int i = 0; i < targets.length; i++) {
			targets[i].visit(visitor);
		}
	}

	public void visit(final TreeVisitor visitor) {
		visitor.visitInitStmt(this);
	}

	public Object clone() {
		final LocalExpr[] t = new LocalExpr[targets.length];

		for (int i = 0; i < targets.length; i++) {
			t[i] = (LocalExpr) targets[i].clone();
		}

		return copyInto(new InitStmt(t));
	}
}
