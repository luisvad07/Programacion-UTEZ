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

import java.io.IOException;
import java.io.Writer;

public class TestFailure extends Printable {
	
	Test _test;
	Throwable _failure;
	
	public TestFailure(Test test, Throwable failure) {
		_test = test;
		_failure = failure;
	}
	
	public Test getTest() {
		return _test;
	}
	
	public Throwable getFailure() {
		return _failure;
	}
	
	public void print(Writer writer) throws IOException {
		writer.write(_test.label());
		writer.write(": ");
		// TODO: don't print the first stack trace elements
		// which reference db4ounit.Assert methods
		TestPlatform.printStackTrace(writer, _failure);
	}
}
