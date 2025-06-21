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
package EDU.purdue.cs.bloat.file;

import java.io.*;

/**
 * Attribute is an abstract class for an attribute defined for a method, field,
 * or class. An attribute consists of its name (represented as an index into the
 * constant pool) and its length. Attribute is extended to represent a constant
 * value, code, exceptions, etc.
 * 
 * @see Code
 * @see ConstantValue
 * @see Exceptions
 * 
 * @author Nate Nystrom (<a
 *         href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public abstract class Attribute {
	protected int nameIndex;

	protected int length;

	/**
	 * Constructor.
	 * 
	 * @param nameIndex
	 *            The index into the constant pool of the name of the attribute.
	 * @param length
	 *            The length of the attribute, excluding the header.
	 */
	public Attribute(final int nameIndex, final int length) {
		this.nameIndex = nameIndex;
		this.length = length;
	}

	/**
	 * Write the attribute to a data stream.
	 * 
	 * @param out
	 *            The data stream of the class file.
	 */
	public abstract void writeData(DataOutputStream out) throws IOException;

	/**
	 * Returns a string representation of the attribute.
	 */
	public String toString() {
		return "(attribute " + nameIndex + " " + length + ")";
	}

	/**
	 * Returns the index into the constant pool of the name of the attribute.
	 */
	public int nameIndex() {
		return nameIndex;
	}

	/**
	 * Returns the length of the attribute, excluding the header.
	 */
	public int length() {
		return length;
	}

	public Object clone() {
		throw new UnsupportedOperationException("Cannot clone Attribute! "
				+ " (subclass: " + this.getClass() + ")");
	}
}
