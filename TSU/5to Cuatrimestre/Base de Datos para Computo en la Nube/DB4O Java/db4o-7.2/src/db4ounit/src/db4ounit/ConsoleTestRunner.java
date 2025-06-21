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

import java.io.*;

import com.db4o.foundation.*;

public class ConsoleTestRunner {
	
	private final Iterable4 _suite;
	private final boolean _reportToFile;
	
	public ConsoleTestRunner(Iterator4 suite) {
		this(suite, true);
	}

	public ConsoleTestRunner(Iterator4 suite, boolean reportToFile) {
		if (null == suite) throw new IllegalArgumentException("suite");
		_suite = Iterators.iterable(suite);
		_reportToFile = reportToFile;
	}

	public ConsoleTestRunner(Iterable4 suite) {
		this(suite, true);
	}

	public ConsoleTestRunner(Iterable4 suite, final boolean reportToFile) {
		if (null == suite) throw new IllegalArgumentException("suite");
		_suite = suite;
		_reportToFile = reportToFile;
	}
	
	public ConsoleTestRunner(Class clazz) {
		this(new ReflectionTestSuiteBuilder(clazz));
	}	

	public int run() {
		return run(TestPlatform.getStdErr());
	}

	public int run(Writer writer) {		
		TestResult result = new TestResult();
		
		new TestRunner(_suite).run(new CompositeTestListener(new ConsoleListener(writer), result));
		
		reportResult(result, writer);
		return result.failures().size();
	}

	private void report(Exception x) {
		TestPlatform.printStackTrace(TestPlatform.getStdErr(), x);
	}

	private void reportResult(TestResult result, Writer writer) {
		if(_reportToFile) {
			reportToTextFile(result);
		}
		report(result, writer);
	}

	private void reportToTextFile(TestResult result) {
		try {
			java.io.Writer writer = TestPlatform.openTextFile("db4ounit.log");
			try {
				report(result, writer);
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			report(e);
		}
	}

	private void report(TestResult result, Writer writer) {
		try {
			result.print(writer);
			writer.flush();
		} catch (IOException e) {
			report(e);
		}
	}
}
