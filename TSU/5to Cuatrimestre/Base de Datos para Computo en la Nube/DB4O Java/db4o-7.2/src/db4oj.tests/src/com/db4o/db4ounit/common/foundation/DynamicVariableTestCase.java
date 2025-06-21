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
package com.db4o.db4ounit.common.foundation;

import com.db4o.foundation.*;

import db4ounit.*;

public class DynamicVariableTestCase implements TestCase {

	public static void main(String[] args) {
		new ConsoleTestRunner(DynamicVariableTestCase.class).run();
	}

	public void testSingleThread() {
		final DynamicVariable variable = new DynamicVariable();
		checkVariableBehavior(variable);
	}

	public void testMultiThread() {
		final DynamicVariable variable = new DynamicVariable();
		final Collection4 failures = new Collection4();
		variable.with("mine", new Runnable() {
			public void run() {
				final Thread[] threads = createThreads(variable, failures);
				startAll(threads);
				for (int i=0; i<10; ++i) {
					Assert.areEqual("mine", variable.value());
				}
				joinAll(threads);
			}
		});
		Assert.isNull(variable.value());
		Assert.isTrue(failures.isEmpty(), failures.toString());
	}

	private void joinAll(final Thread[] threads) {
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void startAll(final Thread[] threads) {
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
	}

	private Thread[] createThreads(final DynamicVariable variable, final Collection4 failures) {
		final Thread[] threads = new Thread[5];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new Runnable() {
				public void run() {
					try {
						for (int i=0; i<10; ++i) {
							checkVariableBehavior(variable);
						}
					} catch (Exception failure) {
						synchronized (failures) {
							failures.add(failure);
						}
					}
				}
			});
		}
		return threads;
	}

	public void testTypeChecking() {

		final Runnable emptyBlock = new Runnable() {
			public void run() {
			}
		};

		final DynamicVariable stringVar = new DynamicVariable(String.class);
		stringVar.with("foo", emptyBlock);

		Assert.expect(IllegalArgumentException.class, new CodeBlock() {
			public void run() throws Throwable {
				stringVar.with(Boolean.TRUE, emptyBlock);
			}
		});

	}

	private void checkVariableBehavior(final DynamicVariable variable) {
		Assert.isNull(variable.value());
		variable.with("foo", new Runnable() {
			public void run() {
				Assert.areEqual("foo", variable.value());
				variable.with("bar", new Runnable() {
					public void run() {
						Assert.areEqual("bar", variable.value());
					}
				});
				Assert.areEqual("foo", variable.value());
			}
		});
		Assert.isNull(variable.value());
	}

}
