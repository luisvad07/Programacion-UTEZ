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
package EDU.purdue.cs.bloat.benchmark;

/**
 * This class allows Java to access the information obtained by the UNIX system
 * call <tt>times</tt>.
 */
public class Times {
	static {
		// Load native code from libbenchmark.so
		System.loadLibrary("times");
	}

	static float userTime;

	static float systemTime;

	/**
	 * Takes a "snapshot" of the system. Reads various items from the result of
	 * <tt>times</tt>.
	 * 
	 * @return <tt>true</tt> if everything is successful
	 */
	public static native boolean snapshot();

	/**
	 * Returns the user time used by this process in seconds.
	 */
	public static float userTime() {
		return (Times.userTime);
	}

	/**
	 * Returns the system time used by this process in seconds.
	 */
	public static float systemTime() {
		return (Times.systemTime);
	}

	/**
	 * Test program.
	 */
	public static void main(final String[] args) throws Exception {
		System.out.println("Starting Test");

		if (Times.snapshot() == false) {
			System.err.println("Error during snapshot");
			System.exit(1);
		}

		System.out.println("System time: " + Times.systemTime());
		System.out.println("User time: " + Times.userTime());

		System.out.println("Ending Test");
	}

}
