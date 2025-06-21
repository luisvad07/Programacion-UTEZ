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

import db4ounit.Assert;
import db4ounit.AssertionException;
import db4ounit.CodeBlock;
import db4ounit.TestCase;

public class AssertTestCase implements TestCase {
	public void testAreEqual() {
		Assert.areEqual(true, true);
		Assert.areEqual(42, 42);
		Assert.areEqual(new Integer(42), new Integer(42));
		Assert.areEqual(null, null);
		expectFailure(new CodeBlock() {
			public void run() throws Throwable {
				Assert.areEqual(true, false);
			}
		});
		expectFailure(new CodeBlock() {
			public void run() throws Throwable {
				Assert.areEqual(42, 43);
			}
		});
		expectFailure(new CodeBlock() {
			public void run() throws Throwable {
				Assert.areEqual(new Object(), new Object());
			}
		});
		expectFailure(new CodeBlock() {
			public void run() throws Throwable {
				Assert.areEqual(null, new Object());
			}
		});
	}	
	
	public void testAreSame() {
		expectFailure(new CodeBlock() {
			public void run() throws Throwable {
				Assert.areSame(new Object(), new Object());
			}
		});
		Assert.areSame(this, this);
	}
	
	private void expectFailure(CodeBlock block) {
		Assert.expect(AssertionException.class, block);
	}
}
