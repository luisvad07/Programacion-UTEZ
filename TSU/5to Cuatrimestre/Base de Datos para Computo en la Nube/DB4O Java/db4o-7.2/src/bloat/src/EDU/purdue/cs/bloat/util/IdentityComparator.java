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
 * IdentityComparator compares two objects using the result of
 * System.identityHashCode.
 */
public class IdentityComparator implements Comparator {
	public int compare(final Object o1, final Object o2) {
		final int n1 = System.identityHashCode(o1);
		final int n2 = System.identityHashCode(o2);

		return n1 - n2;
		/*
		 * if (n1 > n2) { return 1; }
		 * 
		 * if (n1 < n2) { return -1; }
		 * 
		 * return 0;
		 */
	}
}
