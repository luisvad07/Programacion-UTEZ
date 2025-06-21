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
package com.db4o.db4ounit.common.exceptions;

import com.db4o.*;
import com.db4o.ext.*;
import com.db4o.foundation.io.*;
import com.db4o.io.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class IncompatibleFileFormatExceptionTestCase implements Db4oTestCase, TestLifeCycle {
	
	public static void main(String[] args) throws Exception {
		new ConsoleTestRunner(IncompatibleFileFormatExceptionTestCase.class).run();
	}

	private static final String INCOMPATIBLE_FILE_FORMAT = Path4.getTempFileName();

	public void setUp() throws Exception {
		File4.delete(INCOMPATIBLE_FILE_FORMAT);
		IoAdapter adapter = new RandomAccessFileAdapter();
		adapter = adapter.open(INCOMPATIBLE_FILE_FORMAT, false, 0, false);
		adapter.write(new byte[] { 1, 2, 3 }, 3);
		adapter.close();
	}

	public void tearDown() throws Exception {
		File4.delete(INCOMPATIBLE_FILE_FORMAT);
	}

	public void test() {
		Assert.expect(IncompatibleFileFormatException.class, new CodeBlock() {
			public void run() throws Throwable {
				Db4o.openFile(INCOMPATIBLE_FILE_FORMAT);
			}
		});
		File4.delete(INCOMPATIBLE_FILE_FORMAT);
		IoAdapter adapter = new RandomAccessFileAdapter();
		Assert.isFalse(adapter.exists(INCOMPATIBLE_FILE_FORMAT));
	}

}
