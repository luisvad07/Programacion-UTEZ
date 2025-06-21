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
 * Methods and fields are described by their name and type descriptor.
 * NameAndType represents exactly that.
 * 
 * @author Nate Nystrom (<a
 *         href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class NameAndType {
	private String name;

	private Type type;

	/**
	 * Constructor.
	 */
	public NameAndType(final String name, final Type type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Returns the name.
	 */
	public String name() {
		return name;
	}

	/**
	 * Returns the type.
	 */
	public Type type() {
		return type;
	}

	/**
	 * Returns a string representation of the name and type.
	 */
	public String toString() {
		return "<NameandType " + name + " " + type + ">";
	}

	/**
	 * Check if an object is equal to this name and type.
	 * 
	 * @param obj
	 *            The object to compare against.
	 * @return <tt>true</tt> if equal
	 */
	public boolean equals(final Object obj) {
		return (obj instanceof NameAndType)
				&& ((NameAndType) obj).name.equals(name)
				&& ((NameAndType) obj).type.equals(type);
	}

	/**
	 * Returns a hash of the name and type.
	 */
	public int hashCode() {
		return name.hashCode() ^ type.hashCode();
	}
}
