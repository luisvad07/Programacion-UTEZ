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
 * MonitorStmt represents the <tt>monitorenter</tt> and <tt>monitorexit</tt>
 * opcodes, which gain and release ownership of the monitor associated with a
 * given object.
 */
public class MonitorStmt extends Stmt {
	public static final int ENTER = 0;

	public static final int EXIT = 1;

	int kind;

	Expr object;

	/**
	 * Constructor.
	 * 
	 * @param kind
	 *            The kind of monitor statement: ENTER or EXIT.
	 * @param object
	 *            The expression (object) whose monitor is being entered or
	 *            exited.
	 */
	public MonitorStmt(final int kind, final Expr object) {
		this.kind = kind;
		this.object = object;

		object.setParent(this);
	}

	public Expr object() {
		return object;
	}

	public int kind() {
		return kind;
	}

	public void visitForceChildren(final TreeVisitor visitor) {
		object.visit(visitor);
	}

	public void visit(final TreeVisitor visitor) {
		visitor.visitMonitorStmt(this);
	}

	public Object clone() {
		return copyInto(new MonitorStmt(kind, (Expr) object.clone()));
	}
}
