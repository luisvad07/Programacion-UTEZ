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
package db4ounit.tests.fixtures;

import com.db4o.foundation.*;

import db4ounit.*;
import db4ounit.fixtures.*;
import db4ounit.mocking.*;

public class FixtureBasedTestSuiteTestCase implements TestCase {
	
	static FixtureVariable RECORDER_FIXTURE = new FixtureVariable("recorder");
	
	static FixtureVariable FIXTURE1 = new FixtureVariable("f1");
	
	static FixtureVariable FIXTURE2 = new FixtureVariable("f2");
	
	public static final class TestUnit implements TestCase {
		public void testFoo() {
			record("testFoo");
		}
		
		public void testBar() {
			record("testBar");
		}

		private void record(final String test) {
			recorder().record(new MethodCall(test, FIXTURE1.value(), FIXTURE2.value()));
		}

		private MethodCallRecorder recorder() {
			return ((MethodCallRecorder)RECORDER_FIXTURE.value());
		}
	}
	
	public void test() {
		
		final MethodCallRecorder recorder = new MethodCallRecorder();
		
		run(new FixtureBasedTestSuite() {
			public FixtureProvider[] fixtureProviders() {
				return new FixtureProvider[] {
					new SimpleFixtureProvider(RECORDER_FIXTURE, new Object[] { recorder }),
					new SimpleFixtureProvider(FIXTURE1, new Object[] { "f11", "f12" }),
					new SimpleFixtureProvider(FIXTURE2, new Object[] { "f21", "f22" }),
				};
			}

			public Class[] testUnits() {
				return new Class[] { TestUnit.class };
			}
		});
		
		
//		System.out.println(CodeGenerator.generateMethodCallArray(recorder));
		
		recorder.verify(new MethodCall[] {
			new MethodCall("testFoo", new Object[] {"f11", "f21"}),
			new MethodCall("testFoo", new Object[] {"f11", "f22"}),
			new MethodCall("testFoo", new Object[] {"f12", "f21"}),
			new MethodCall("testFoo", new Object[] {"f12", "f22"}),
			new MethodCall("testBar", new Object[] {"f11", "f21"}),
			new MethodCall("testBar", new Object[] {"f11", "f22"}),
			new MethodCall("testBar", new Object[] {"f12", "f21"}),
			new MethodCall("testBar", new Object[] {"f12", "f22"})
		});
	}

	private void run(final FixtureBasedTestSuite suite) {
		final TestResult result = new TestResult();
		new TestRunner(suite).run(result);
		if (result.failures().size() > 0) {
			Assert.fail(Iterators.toString(result.failures()));
		}
	}
	
	public void testLabel() {
		final FixtureBasedTestSuite suite = new FixtureBasedTestSuite() {
			public FixtureProvider[] fixtureProviders() {
				return new FixtureProvider[] {
					new SimpleFixtureProvider(FIXTURE1, new Object[] { "f11", "f12" }),
					new SimpleFixtureProvider(FIXTURE2, new Object[] { "f21", "f22" }),
				};
			}

			public Class[] testUnits() {
				return new Class[] { TestUnit.class };
			}
		};
		final Iterable4 labels = Iterators.map(suite, new Function4() {
			public Object apply(Object arg) {
				return ((Test)arg).label();
			}
		});
		Iterator4Assert.areEqual(new Object[] {
			testLabel("testFoo", 0, 0),
			testLabel("testFoo", 1, 0),
			testLabel("testFoo", 0, 1),
			testLabel("testFoo", 1, 1),
			testLabel("testBar", 0, 0),
			testLabel("testBar", 1, 0),
			testLabel("testBar", 0, 1),
			testLabel("testBar", 1, 1)
		}, labels.iterator());
	}

	private String testLabel(final String testMethod, int fixture1Index, int fixture2Index) {
		final String prefix = "(f2[" + fixture1Index + "]) (f1[" + fixture2Index + "]) ";
		return prefix + TestUnit.class.getName() + "." + testMethod;
	}

}
