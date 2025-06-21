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
package EDU.purdue.cs.bloat.util;

import java.util.*;

/**
 * ResizableArrayList is the same as ArrayList except that ensureSize not only
 * increases the size of the array (super.ensureCapacity), but it also fills the
 * empty space with null. This way, the size method will return the length of
 * the array and not just the number of elements in it. I guess.
 */
public class ResizeableArrayList extends ArrayList implements List, Cloneable,
		java.io.Serializable {
	/**
	 * This constructor is no longer supported in JDK1.2 public
	 * ResizeableArrayList(int initialCapacity, int capacityIncrement) {
	 * super(initialCapacity, capacityIncrement); }
	 */
	public ResizeableArrayList(final int initialCapacity) {
		super(initialCapacity);
	}

	public ResizeableArrayList() {
		super();
	}

	public ResizeableArrayList(final Collection c) {
		super(c);
	}

	public void ensureSize(final int size) {
		ensureCapacity(size);

		while (size() < size) {
			add(null);
		}
	}

	public Object clone() {
		return super.clone();
	}
}
