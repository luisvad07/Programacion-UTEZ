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
package db4ounit.extensions.tests;

import com.db4o.foundation.*;

import db4ounit.*;
import db4ounit.extensions.*;
import db4ounit.extensions.fixtures.*;
import db4ounit.fixtures.*;


public class DynamicFixtureTestCase implements TestSuiteBuilder {
	
	public Iterator4 iterator() {
		// The test case simply runs FooTestSuite
		// with a Db4oInMemory fixture to ensure the 
		// the db4o fixture can be successfully propagated
		// to FooTestUnit#test.
		return new Db4oTestSuiteBuilder(
					new Db4oInMemory(),
					FooTestSuite.class).iterator();
	}	
	
	/**
	 * One of the possibly many test units.
	 */
	public static class FooTestUnit extends AbstractDb4oTestCase {
		
		private final Object[] values = MultiValueFixtureProvider.value();
		
		public void test() {
			Assert.isNotNull(db());
			Assert.isNotNull(values);
		}
	}
	
	/**
	 * The test suite which binds together fixture providers and test units.
	 */
	public static class FooTestSuite extends FixtureBasedTestSuite {

		public FixtureProvider[] fixtureProviders() {
			return new FixtureProvider[] {
				new MultiValueFixtureProvider(new Object[][] {
					new Object[] { "foo", "bar" },
					new Object[] { new Integer(1), new Integer(42) },
				}),
			};
		}
	
		public Class[] testUnits() {
			return new Class[] {
				FooTestUnit.class,
			};
		}
	}
}
