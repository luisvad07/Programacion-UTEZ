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

import db4ounit.*;
import db4ounit.fixtures.*;

public class FixtureContextTestCase implements TestCase {
	
	public static final class ContextRef {
		public FixtureContext value;
	}
	
	public void test() {
		final FixtureVariable f1 = new FixtureVariable();
		final FixtureVariable f2 = new FixtureVariable();
		final ContextRef c1 = new ContextRef();
		final ContextRef c2 = new ContextRef();
		new FixtureContext().run(new Runnable() {
			public void run() {
				f1.with("foo", new Runnable() {
					public void run() {
						assertValue("foo", f1);
						assertNoValue(f2);
						c1.value = FixtureContext.current();
						f2.with("bar", new Runnable() {
							public void run() {
								assertValue("foo", f1);
								assertValue("bar", f2);
								c2.value = FixtureContext.current();
							}
						});
					}
				});
				
			}
		});
		assertNoValue(f1);
		assertNoValue(f2);
		
		c1.value.run(new Runnable() {
			public void run() {
				assertValue("foo", f1);
				assertNoValue(f2);
			}
		});
		
		c2.value.run(new Runnable() {
			public void run() {
				assertValue("foo", f1);
				assertValue("bar", f2);
			}
		});
	}

	private void assertNoValue(final FixtureVariable f1) {
		Assert.expect(IllegalStateException.class, new CodeBlock() {
			public void run() {
				use(f1.value());
			}

			private void use(Object value) {
			}
		});
	}

	private void assertValue(final String expected, final FixtureVariable fixture) {
		Assert.areEqual(expected, fixture.value());
	}

}
