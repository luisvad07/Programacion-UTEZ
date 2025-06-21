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

/**
 * Mechanism for making assertions about things in BLOAT. If an assertion fails,
 * an <tt>IllegalArgumentException</tt> is thrown.
 */
public abstract class Assert {
	public static void isTrue(boolean test, final String msg) {
		if (!test) {
			throw new IllegalArgumentException("Assert.isTrue: " + msg);
		}
	}

	public static void isFalse(final boolean test, final String msg) {
		if (test) {
			throw new IllegalArgumentException("Assert.isFalse: " + msg);
		}
	}

	public static void isNotNull(final Object test, final String msg) {
		if (test == null) {
			throw new IllegalArgumentException("Assert.isNotNull: " + msg);
		}
	}

	public static void isNull(final Object test, final String msg) {
		if (test != null) {
			throw new IllegalArgumentException("Assert.isNull: " + msg);
		}
	}

	public static void isTrue(boolean test) {
		if (!test) {
			throw new IllegalArgumentException("Assert.isTrue failed");
		}
	}

	public static void isFalse(final boolean test) {
		if (test) {
			throw new IllegalArgumentException("Assert.isFalse failed");
		}
	}

	public static void isNotNull(final Object test) {
		if (test == null) {
			throw new IllegalArgumentException("Assert.isNotNull failed");
		}
	}

	public static void isNull(final Object test) {
		if (test != null) {
			throw new IllegalArgumentException("Assert.isNull failed");
		}
	}
}
