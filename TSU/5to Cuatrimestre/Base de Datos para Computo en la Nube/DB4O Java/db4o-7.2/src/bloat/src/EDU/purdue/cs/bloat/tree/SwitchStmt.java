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
 * SwitchStmt represents a switch statement.
 */
public class SwitchStmt extends JumpStmt {
	Expr index;

	Block defaultTarget;

	Block[] targets;

	int[] values;

	/**
	 * Constructor.
	 * 
	 * @param index
	 *            The expression on which the switch is made.
	 * @param defaultTarget
	 *            The code to be executed if index is not contained in values.
	 * @param targets
	 *            The code to be executed for each value in values.
	 * @param values
	 *            The interesting values that index can have. That is, the
	 *            values of index in which a non-default target is executed.
	 */
	public SwitchStmt(final Expr index, final Block defaultTarget,
			final Block[] targets, final int[] values) {
		this.index = index;
		this.defaultTarget = defaultTarget;
		this.targets = targets;
		this.values = values;

		index.setParent(this);
	}

	public Expr index() {
		return index;
	}

	public void setDefaultTarget(final Block block) {
		this.defaultTarget = block;
	}

	public Block defaultTarget() {
		return defaultTarget;
	}

	public Block[] targets() {
		return targets;
	}

	public int[] values() {
		return values;
	}

	public void visitForceChildren(final TreeVisitor visitor) {
		index.visit(visitor);
	}

	public void visit(final TreeVisitor visitor) {
		visitor.visitSwitchStmt(this);
	}

	public Object clone() {
		final Block[] t = new Block[targets.length];
		System.arraycopy(targets, 0, t, 0, targets.length);

		final int[] v = new int[values.length];
		System.arraycopy(values, 0, v, 0, values.length);

		return copyInto(new SwitchStmt((Expr) index.clone(), defaultTarget, t,
				v));
	}
}
