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
 * SRStmt represents the swizzle of a range of elements in an array.
 */
public class SRStmt extends Stmt {
	Expr array;

	Expr start; // starting value of range

	Expr end; // terminating value of range

	/**
	 * Constructor.
	 * 
	 * @param a
	 *            The array to swizzle.
	 * @param s
	 *            The starting value of the swizzle range.
	 * @param t
	 *            The terminating value of the swizzle range.
	 */
	public SRStmt(final Expr a, final Expr s, final Expr t) {
		this.array = a;
		this.start = s;
		this.end = t;
		array.setParent(this);
		start.setParent(this);
		end.setParent(this);
	}

	public Expr array() {
		return array;
	}

	public Expr start() {
		return start;
	}

	public Expr end() {
		return end;
	}

	public void visit(final TreeVisitor visitor) {
		visitor.visitSRStmt(this);
	}

	public Object clone() {
		return copyInto(new SRStmt((Expr) array.clone(), (Expr) start.clone(),
				(Expr) end.clone()));
	}

	public void visitForceChildren(final TreeVisitor visitor) {
		if (visitor.reverse()) {
			end.visit(visitor);
			start.visit(visitor);
			array.visit(visitor);
		} else {
			array.visit(visitor);
			start.visit(visitor);
			end.visit(visitor);
		}
	}
}
