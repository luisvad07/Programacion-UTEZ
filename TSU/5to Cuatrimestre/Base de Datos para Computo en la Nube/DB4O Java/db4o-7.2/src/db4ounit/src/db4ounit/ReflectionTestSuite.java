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
package db4ounit;

import com.db4o.foundation.*;

/**
 * Support for hierarchically chained test suites.
 * 
 * In the topmost test package define an AllTests class which extends
 * ReflectionTestSuite and returns all subpackage.AllTests classes as
 * testCases. Example:
 * 
 * package org.acme.tests;
 * 
 * public class AllTests extends ReflectionTestSuite {
 * 	protected Class[] testCases() {
 * 		return new Class[] {
 *			org.acme.tests.subsystem1.AllTests.class,
 *			org.acme.tests.subsystem2.AllTests.class,
 *		};
 *	} 			
 * }
 */
public abstract class ReflectionTestSuite implements TestSuiteBuilder {

	public Iterator4 iterator() {
		return new ReflectionTestSuiteBuilder(testCases()).iterator();
	}

	protected abstract Class[] testCases();
	
	public int run() {
		return new ConsoleTestRunner(iterator()).run();
	}

}
