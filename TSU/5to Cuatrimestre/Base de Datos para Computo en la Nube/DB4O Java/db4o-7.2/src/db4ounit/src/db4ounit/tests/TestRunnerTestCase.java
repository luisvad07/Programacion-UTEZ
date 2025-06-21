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
import db4ounit.mocking.*;

public class TestRunnerTestCase implements TestCase { 
	
	static final RuntimeException FAILURE_EXCEPTION = new RuntimeException();
	
	public void testRun() {
		final RunsGreen greenTest = new RunsGreen();
		final RunsRed redTest = new RunsRed(FAILURE_EXCEPTION);
		final Iterable4 tests = Iterators.iterable(new Object[] {
			greenTest,
			redTest,
		});
		
		final MethodCallRecorder recorder = new MethodCallRecorder();
		final TestListener listener = new TestListener() {
			
			public void testStarted(Test test) {
				recorder.record(new MethodCall("testStarted", test));
			}
		
			public void testFailed(Test test, Throwable failure) {
				recorder.record(new MethodCall("testFailed", test, failure));
			}
		
			public void runStarted() {
				recorder.record(new MethodCall("runStarted"));
			}
		
			public void runFinished() {
				recorder.record(new MethodCall("runFinished"));
			}
			
		};
		new TestRunner(tests).run(listener);
		
		recorder.verify(new MethodCall[] {
			new MethodCall("runStarted"),
			new MethodCall("testStarted", greenTest),
			new MethodCall("testStarted", redTest),
			new MethodCall("testFailed", redTest, FAILURE_EXCEPTION),
			new MethodCall("runFinished"),
		});
	}
	
	public void testRunWithException() {
	    Test test = new Test() {

            public String label() {
                return "Test"; //$NON-NLS-1$
            }

            public void run() {
                Assert.areEqual(0, 1);
            }
	        
	    };
	    
	    Iterable4 tests = Iterators.iterable(new Object[] {
	            test,
	    });
	    final TestResult result = new TestResult();
		new TestRunner(tests).run(result);
		Assert.areEqual(1, result.failures().size());
	}

}
