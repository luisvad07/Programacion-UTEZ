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
 * <tt>JumpStmt</tt> is the super class for several classes that represent
 * statements that chang the flow of control in a program.
 * 
 * @see GotoStmt
 * @see IfStmt
 * @see JsrStmt
 */
public abstract class JumpStmt extends Stmt {
	Set catchTargets;

	public JumpStmt() {
		catchTargets = new HashSet();
	}

	/**
	 * The <tt>Block</tt> containing this <tt>JumpStmt</tt> may lie within a
	 * try block (i.e. it is a <i>protected</i> block). If so, then
	 * <tt>catchTargets()</tt> returns a set of <tt>Block</tt>s that begin
	 * the exception handler for the exception that may be thrown in the
	 * protected block.
	 */
	public Collection catchTargets() {
		return catchTargets;
	}

	protected Node copyInto(final Node node) {
		((JumpStmt) node).catchTargets.addAll(catchTargets);
		return super.copyInto(node);
	}
}
