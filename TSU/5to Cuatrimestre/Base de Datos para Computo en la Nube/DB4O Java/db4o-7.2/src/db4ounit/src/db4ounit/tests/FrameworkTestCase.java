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

public class FrameworkTestCase implements TestCase {
	public final static RuntimeException EXCEPTION = new RuntimeException();
	
	public void testRunsGreen() {
		TestResult result = new TestResult();
		new TestRunner(Iterators.cons(new RunsGreen())).run(result);
		Assert.isTrue(result.failures().size() == 0, "not green");
	}
	
	public void testRunsRed() {
		TestResult result = new TestResult();
		new TestRunner(Iterators.cons(new RunsRed(EXCEPTION))).run(result);
		Assert.isTrue(result.failures().size() == 1, "not red");
	}
	
	public static void runTestAndExpect(Test test,int expFailures) {
		runTestAndExpect(test,expFailures,true);
	}
	
	public static void runTestAndExpect(Test test,int expFailures, boolean checkException) {
		runTestAndExpect(Iterators.cons(test), expFailures, checkException);
	}

	public static void runTestAndExpect(final Iterable4 tests, int expFailures, boolean checkException) {
		final TestResult result = new TestResult();
		new TestRunner(tests).run(result);
		if (expFailures != result.failures().size()) {
			Assert.fail(result.failures().toString());
		}
		if (checkException) {
			for(Iterator4 iter=result.failures().iterator(); iter.moveNext();) {
				TestFailure failure = (TestFailure) iter.current();
				Assert.areEqual(EXCEPTION, failure.getFailure());
			}
		}
	}
}
