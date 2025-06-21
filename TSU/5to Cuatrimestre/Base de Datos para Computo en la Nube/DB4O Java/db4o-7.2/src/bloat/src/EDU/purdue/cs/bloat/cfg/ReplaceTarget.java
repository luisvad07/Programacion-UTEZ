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
package EDU.purdue.cs.bloat.cfg;

import java.util.*;

import EDU.purdue.cs.bloat.tree.*;

/**
 * <tt>ReplaceTarget</tt> replaces the block that is the target of a
 * <tt>JumpStmt</tt>, <tt>JsrStmt</tt>, <tt>RetStmt</tt>,
 * <tt>GotoStmt</tt>, <tt>SwitchStmt</tt>, or <tt>IfStmt</tt> with
 * another <tt>Block</tt>.
 */
public class ReplaceTarget extends TreeVisitor {
	Block oldDst;

	Block newDst;

	public ReplaceTarget(final Block oldDst, final Block newDst) {
		this.oldDst = oldDst;
		this.newDst = newDst;
	}

	public void visitTree(final Tree tree) {
		final Stmt last = tree.lastStmt();

		if (last instanceof JumpStmt) {
			final JumpStmt stmt = (JumpStmt) last;

			if (FlowGraph.DEBUG) {
				System.out.println("  Replacing " + oldDst + " with " + newDst
						+ " in " + stmt);
			}

			if (stmt.catchTargets().remove(oldDst)) {
				stmt.catchTargets().add(newDst);
			}

			stmt.visit(this);
		}
	}

	public void visitJsrStmt(final JsrStmt stmt) {
		if (stmt.sub().entry() == oldDst) {
			if (FlowGraph.DEBUG) {
				System.out.print("  replacing " + stmt);
			}

			stmt.block().graph().setSubEntry(stmt.sub(), newDst);

			if (FlowGraph.DEBUG) {
				System.out.println("   with " + stmt);
			}
		}
	}

	public void visitRetStmt(final RetStmt stmt) {
		final Iterator paths = stmt.sub().paths().iterator();

		while (paths.hasNext()) {
			final Block[] path = (Block[]) paths.next();

			if (FlowGraph.DEBUG) {
				System.out.println("  path = " + path[0] + " " + path[1]);
			}

			if (path[1] == oldDst) {
				if (FlowGraph.DEBUG) {
					System.out.println("  replacing ret to " + oldDst
							+ " with ret to " + newDst);
				}

				path[1] = newDst;
				((JsrStmt) path[0].tree().lastStmt()).setFollow(newDst);
			}
		}
	}

	public void visitGotoStmt(final GotoStmt stmt) {
		if (stmt.target() == oldDst) {
			if (FlowGraph.DEBUG) {
				System.out.print("  replacing " + stmt);
			}

			stmt.setTarget(newDst);

			if (FlowGraph.DEBUG) {
				System.out.println("   with " + stmt);
			}
		}
	}

	public void visitSwitchStmt(final SwitchStmt stmt) {
		if (stmt.defaultTarget() == oldDst) {
			if (FlowGraph.DEBUG) {
				System.out.print("  replacing " + stmt);
			}

			stmt.setDefaultTarget(newDst);

			if (FlowGraph.DEBUG) {
				System.out.println("   with " + stmt);
			}
		}

		final Block[] targets = stmt.targets();

		for (int i = 0; i < targets.length; i++) {
			if (targets[i] == oldDst) {
				if (FlowGraph.DEBUG) {
					System.out.print("  replacing " + stmt);
				}

				targets[i] = newDst;

				if (FlowGraph.DEBUG) {
					System.out.println("   with " + stmt);
				}
			}
		}
	}

	public void visitIfStmt(final IfStmt stmt) {
		if (stmt.trueTarget() == oldDst) {
			if (FlowGraph.DEBUG) {
				System.out.print("  replacing " + stmt);
			}

			stmt.setTrueTarget(newDst);

			if (FlowGraph.DEBUG) {
				System.out.println("   with " + stmt);
			}
		}

		if (stmt.falseTarget() == oldDst) {
			if (FlowGraph.DEBUG) {
				System.out.print("  replacing " + stmt);
			}

			stmt.setFalseTarget(newDst);

			if (FlowGraph.DEBUG) {
				System.out.println("   with " + stmt);
			}
		}
	}
}
