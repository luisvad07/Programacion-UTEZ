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
import EDU.purdue.cs.bloat.editor.*;

/**
 * RetStmt represents the <i>ret</i> opcode which returns from a subroutine.
 * Recall that when a subroutine returns, the <i>ret</i> opcode's argument
 * specifies a local variable that stores the return address of
 */
public class RetStmt extends JumpStmt {
	// ret

	Subroutine sub; // Subroutine from which to return.

	/**
	 * Constructor.
	 * 
	 * @param sub
	 *            The subroutine in which the return statement resides. That is,
	 *            from where the program control is returning.
	 * 
	 * @see Tree#addInstruction(Instruction, Subroutine)
	 */
	public RetStmt(final Subroutine sub) {
		this.sub = sub;
	}

	public void visitForceChildren(final TreeVisitor visitor) {
	}

	public void visit(final TreeVisitor visitor) {
		visitor.visitRetStmt(this);
	}

	public Subroutine sub() {
		return sub;
	}

	public Object clone() {
		return copyInto(new RetStmt(sub));
	}
}
