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
 * <tt>MultiArrayOperand</tt> encapsulates the operands to the
 * <tt>multianewarray</tt> instruction. Each <tt>MultiArrayOperand</tt>
 * contains the type descriptor of the new multidimensional array the
 * instruction creates, as well as the number of dimensions in the array.
 * 
 * @author Nate Nystrom (<a
 *         href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class MultiArrayOperand {
	private Type type;

	private int dim;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The element type of the array.
	 * @param dim
	 *            The number of dimensions of the array.
	 */
	public MultiArrayOperand(final Type type, final int dim) {
		this.type = type;
		this.dim = dim;
	}

	/**
	 * Get the element type of the array.
	 * 
	 * @return The element type of the array.
	 */
	public Type type() {
		return type;
	}

	/**
	 * Get the number of dimensions of the array.
	 * 
	 * @return The number of dimensions of the array.
	 */
	public int dimensions() {
		return dim;
	}

	/**
	 * Convert the operand to a string.
	 * 
	 * @return A string representation of the operand.
	 */
	public String toString() {
		return type + " x " + dim;
	}
}
