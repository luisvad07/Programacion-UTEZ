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
package com.db4o.db4ounit.common.io;

import java.io.*;

import com.db4o.ext.*;
import com.db4o.foundation.io.*;
import com.db4o.io.*;

import db4ounit.*;

public class IoAdapterTest implements TestLifeCycle {
	
	public static void main(String[] args) {
		new ConsoleTestRunner(IoAdapterTest.class).run();
	}
	
	private String _cachedIoAdapterFile = Path4.getTempFileName();

	private String _randomAccessFileAdapterFile = Path4.getTempFileName();

	private IoAdapter[] _adapters;


	public void setUp() throws Exception {
		deleteAllTestFiles();
		initAdapters(false);
	}

	private void initAdapters(boolean readOnly) throws Exception {
		_adapters = new IoAdapter[] { initRandomAccessAdapter(readOnly),
				initCachedRandomAccessAdapter(readOnly), };
	}

	public void tearDown() throws Exception {
		closeAdapters();
		deleteAllTestFiles();
	}

	public void testReadWrite() throws Exception {
		for (int i = 0; i < _adapters.length; ++i) {
			assertReadWrite(_adapters[i]);
		}
	}

	private void assertReadWrite(IoAdapter adapter) throws IOException {
		adapter.seek(0);
		int count = 1024 * 8 + 10;
		byte[] data = new byte[count];
		for (int i = 0; i < count; ++i) {
			data[i] = (byte) (i % 256);
		}
		adapter.write(data);
		adapter.seek(0);
		byte[] readBytes = new byte[count];
		adapter.read(readBytes);
		for (int i = 0; i < count; i++) {
			Assert.areEqual(data[i], readBytes[i]);
		}
	}

	public void testSeek() throws Exception {
		for (int i = 0; i < _adapters.length; ++i) {
			assertSeek(_adapters[i]);
		}
	}

	public void testReadWriteBytes() throws Exception {
		String[] strs = {
				"short string",
				"this is a really long string, just to make sure that all IoAdapters work correctly. " };
		for (int i = 0; i < _adapters.length; i++) {
			for(int j = 0; j < strs.length; j++)
			assertReadWriteString(_adapters[i], strs[j]);
		}
	}
	
	private void assertReadWriteString(IoAdapter adapter, String str) throws Exception {
		byte[] data = str.getBytes();
		byte[] read = new byte[2048];
		adapter.seek(0);
		adapter.write(data);
		adapter.seek(0);
		adapter.read(read);
		Assert.areEqual(str, new String(read, 0, data.length));
	}
	
	public void testReadOnly() throws Exception {
		closeAdapters();
		initAdapters(true);
		for (int i = 0; i < _adapters.length; i++) {
			assertReadOnly(_adapters[i]);
		}
	}
	
	private void assertReadOnly(final IoAdapter adapter) {
		Assert.expect(Db4oIOException.class, new CodeBlock() {
			public void run() throws Throwable {
				adapter.write(new byte[] {0});
			}
		});
	}

	/*
	 * This test is disabled because the API difference between java & .net.
	 */
	public void _testReadWriteAheadFileEnd() throws Exception {
		String str = "this is a really long string, just to make sure that all IoAdapters work correctly. ";
		for (int i = 0; i < _adapters.length; i++) {
			assertReadWriteAheadFileEnd(_adapters[i], str);
		}
	}
	
	private void assertReadWriteAheadFileEnd(IoAdapter adapter, String str) throws Exception {
		byte[] data = str.getBytes();
		byte[] read = new byte[2048];
		adapter.seek(10);
		int readBytes = adapter.read(data);
		Assert.areEqual(-1, readBytes);
		Assert.areEqual(0, adapter.getLength());
		adapter.seek(0);
		readBytes = adapter.read(data);
		Assert.areEqual(-1, readBytes);
		Assert.areEqual(0, adapter.getLength());
		
		adapter.seek(10);
		adapter.write(data);
		Assert.areEqual(10 + data.length, adapter.getLength());
		
		
		adapter.seek(0);
		readBytes = adapter.read(read);
		Assert.areEqual(10 + data.length, readBytes);
		
		adapter.seek(20 + data.length);
		readBytes = adapter.read(read);
		Assert.areEqual(-1, readBytes);
		
		adapter.seek(1024 + data.length);
		readBytes = adapter.read(read);
		Assert.areEqual(-1, readBytes);
		
		adapter.seek(1200);
		adapter.write(data);
		adapter.seek(0);
		readBytes = adapter.read(read);
		Assert.areEqual(1200 + data.length, readBytes);		
	}
	
	public void testReopen() throws Exception {
		testReadWrite();
		closeAdapters();
		initAdapters(false);
		testReadWrite();
	}
	
	private void assertSeek(IoAdapter adapter) throws Exception {
		int count = 1024 * 2 + 10;
		byte[] data = new byte[count];
		for (int i = 0; i < data.length; ++i) {
			data[i] = (byte) (i % 256);
		}
		adapter.write(data);
		byte[] readBytes = new byte[count];
		adapter.seek(0);
		adapter.read(readBytes);
		for (int i = 0; i < count; i++) {
			Assert.areEqual(data[i], readBytes[i]);
		}
		adapter.seek(20);
		adapter.read(readBytes);
		for (int i = 0; i < count - 20; i++) {
			Assert.areEqual(data[i + 20], readBytes[i]);
		}

		byte[] writtenData = new byte[10];
		for (int i = 0; i < writtenData.length; ++i) {
			writtenData[i] = (byte) i;
		}
		adapter.seek(1000);
		adapter.write(writtenData);
		adapter.seek(1000);
		int readCount = adapter.read(readBytes, 10);
		Assert.areEqual(10, readCount);
		for (int i = 0; i < readCount; ++i) {
			Assert.areEqual(i, readBytes[i]);
		}
	}

	private IoAdapter initCachedRandomAccessAdapter(boolean readOnly) throws Exception {
		IoAdapter adapter = new CachedIoAdapter(new RandomAccessFileAdapter());
		adapter = adapter.open(_cachedIoAdapterFile, false, 0, readOnly);
		return adapter;
	}

	private IoAdapter initRandomAccessAdapter(boolean readOnly) throws Exception {
		IoAdapter adapter = new RandomAccessFileAdapter();
		adapter = adapter.open(_randomAccessFileAdapterFile, false, 0, readOnly);
		return adapter;
	}

	private void deleteAllTestFiles() throws Exception {
		new File(_cachedIoAdapterFile).delete();
		new File(_randomAccessFileAdapterFile).delete();
	}

	private void closeAdapters() {
		for (int i = 0; i < _adapters.length; ++i) {
			try {
				_adapters[i].close();
			} catch (Db4oIOException e) {
				// ignore
			}
		}
	}

}
