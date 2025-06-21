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
package db4ounit.tests;

import com.db4o.foundation.*;

import db4ounit.*;

public class ReflectionTestSuiteBuilderTestCase implements TestCase {
	
	private final static class ExcludingReflectionTestSuiteBuilder extends
			ReflectionTestSuiteBuilder {
		public ExcludingReflectionTestSuiteBuilder(Class[] classes) {
			super(classes);
		}

		protected boolean isApplicable(Class clazz) {
			return clazz!=NotAccepted.class;
		}
	}

	public static class NonTestFixture {
	}
	
	public void testUnmarkedTestFixture() {
		
		final ReflectionTestSuiteBuilder builder = new ReflectionTestSuiteBuilder(NonTestFixture.class);
		assertFailingTestCase(IllegalArgumentException.class, builder);
	}
	
	public static class Accepted implements TestCase {
		public void test() {
		}
	}

	public static class NotAccepted implements TestCase {
		public void test() {
		}
	}

	public void testNotAcceptedFixture() {
		ReflectionTestSuiteBuilder builder = new ExcludingReflectionTestSuiteBuilder(new Class[]{Accepted.class,NotAccepted.class});
		Assert.areEqual(1, Iterators.size(builder.iterator()));
	}
	
	public static class ConstructorThrows implements TestCase {
		
		public static final RuntimeException ERROR = new RuntimeException("no way");
		
		public ConstructorThrows() {
			throw ERROR;
		}
		
		public void test1() {
		}
		
		public void test2() {
		}
	}
	
	public void testConstructorFailuresAppearAsFailedTestCases() {
		
		final ReflectionTestSuiteBuilder builder = new ReflectionTestSuiteBuilder(ConstructorThrows.class);
		Assert.areEqual(2, Iterators.toArray(builder.iterator()).length);
	}

	private Throwable assertFailingTestCase(final Class expectedError,
			final ReflectionTestSuiteBuilder builder) {
		final Iterator4 tests = builder.iterator();
		FailingTest test = (FailingTest) Iterators.next(tests);
		Assert.areSame(expectedError, test.error().getClass());
		return test.error();
	}
}
