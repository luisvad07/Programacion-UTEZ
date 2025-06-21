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
package EDU.purdue.cs.bloat.editor;

/**
 * IncOperand encapsulates the operands to the iinc instruction. It is necessary
 * because the <tt>iinc</tt> has two operands: a local variable and an integer
 * by which to increment the local variable.
 * 
 * @author Nate Nystrom (<a
 *         href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class IncOperand {
	private LocalVariable var;

	private int incr;

	/**
	 * Constructor.
	 * 
	 * @param var
	 *            The local variable to increment.
	 * @param incr
	 *            The amount to increment by.
	 */
	public IncOperand(final LocalVariable var, final int incr) {
		this.var = var;
		this.incr = incr;
	}

	/**
	 * Get the local variable to increment.
	 * 
	 * @return The local variable to increment.
	 */
	public LocalVariable var() {
		return var;
	}

	/**
	 * Get the amount to increment by.
	 * 
	 * @return The amount to increment by.
	 */
	public int incr() {
		return incr;
	}

	/**
	 * Convert the operand to a string.
	 * 
	 * @return A string representation of the operand.
	 */
	public String toString() {
		return "" + var + " by " + incr;
	}
}
