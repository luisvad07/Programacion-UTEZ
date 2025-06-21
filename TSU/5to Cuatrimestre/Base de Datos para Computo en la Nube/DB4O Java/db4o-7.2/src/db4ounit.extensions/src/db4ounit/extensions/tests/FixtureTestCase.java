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
import com.db4o.foundation.io.*;

import db4ounit.*;
import db4ounit.extensions.*;
import db4ounit.extensions.fixtures.*;
import db4ounit.extensions.util.*;
import db4ounit.tests.*;

public class FixtureTestCase implements TestCase {
	private final class ExcludingInMemoryFixture extends Db4oInMemory {
		public ExcludingInMemoryFixture(ConfigurationSource source) {
			super(source);
		}

		public boolean accept(Class clazz) {
			return !OptOutFromTestFixture.class.isAssignableFrom(clazz);
		}
	}

	public void testSingleTestWithDifferentFixtures() {
		ConfigurationSource configSource=new IndependentConfigurationSource();
		assertSimpleDb4o(new Db4oInMemory(configSource));
		assertSimpleDb4o(new Db4oSolo(configSource));
	}
	
	public void testMultipleTestsSingleFixture() {
		MultipleDb4oTestCase.resetConfigureCalls();
		FrameworkTestCase.runTestAndExpect(new Db4oTestSuiteBuilder(new Db4oInMemory(new IndependentConfigurationSource()), MultipleDb4oTestCase.class), 2, false);
		Assert.areEqual(2,MultipleDb4oTestCase.configureCalls());
	}

	public void testSelectiveFixture() {
		final Db4oFixture fixture=new ExcludingInMemoryFixture(new IndependentConfigurationSource());
		final Iterator4 tests = new Db4oTestSuiteBuilder(fixture, new Class[]{AcceptedTestCase.class,NotAcceptedTestCase.class}).iterator();
		final Test test = nextTest(tests);
		Assert.isFalse(tests.moveNext());
		FrameworkTestCase.runTestAndExpect(test,0);
	}

	private void assertSimpleDb4o(Db4oFixture fixture) {
		final Iterator4 tests = new Db4oTestSuiteBuilder(fixture, SimpleDb4oTestCase.class).iterator();
		final Test test = nextTest(tests);
		
		SimpleDb4oTestCase.EXPECTED_FIXTURE_VARIABLE.with(fixture, new Runnable() {
			public void run() {
				FrameworkTestCase.runTestAndExpect(test, 0);
			}
		});

		final SimpleDb4oTestCase subject = (SimpleDb4oTestCase)Db4oTestSuiteBuilder.getTestSubject(test);
		Assert.isTrue(subject.everythingCalled());
	}

	private Test nextTest(Iterator4 tests) {
		return (Test) Iterators.next(tests);
	}

	public void testInterfaceIsAvailable() {
		Assert.isTrue(Db4oTestCase.class.isAssignableFrom(AbstractDb4oTestCase.class));
	}
	
	public void testDeleteDir() throws Exception {
		File4.mkdirs("a/b/c");
		Assert.isTrue(File4.exists("a"));
		IOUtil.deleteDir("a");
		Assert.isFalse(File4.exists("a"));
	}
}
