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

public class TestFailureCollection extends Printable implements Iterable4 {
	
	private final Collection4 _failures = new Collection4();
	
	public Iterator4 iterator() {
		return _failures.iterator();
	}
	
	public int size() {
		return _failures.size();
	}
	
	public void add(TestFailure failure) {
		_failures.add(failure);
	}
	
	public void print(Writer writer) throws IOException {
		printSummary(writer);
		printDetails(writer);
	}

	private void printSummary(Writer writer) throws IOException {
		int index = 1;
		Iterator4 e = iterator();
		while (e.moveNext()) {
			writer.write(String.valueOf(index));
			writer.write(") ");
			writer.write(((TestFailure)e.current()).getTest().label());
			writer.write(TestPlatform.NEW_LINE);
			++index;
		}
	}

	private void printDetails(Writer writer) throws IOException {
		int index = 1;
		Iterator4 e = iterator();
		while (e.moveNext()) {
			writer.write(TestPlatform.NEW_LINE);
			writer.write(String.valueOf(index));
			writer.write(") ");
			((Printable)e.current()).print(writer);
			writer.write(TestPlatform.NEW_LINE);
			++index;
		}
	}
}
