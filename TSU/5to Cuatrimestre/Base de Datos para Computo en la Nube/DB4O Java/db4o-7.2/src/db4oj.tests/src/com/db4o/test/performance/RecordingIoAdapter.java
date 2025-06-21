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
package com.db4o.test.performance;

import java.io.*;

import com.db4o.*;
import com.db4o.ext.*;
import com.db4o.io.*;

/**
 * IO adapter for benchmark.
 * @exclude
 */
public class RecordingIoAdapter extends VanillaIoAdapter {
	// NOTE/FIXME: Ugly hack to prevent YapRandomAccessFile timer file handle from
	// writing asonchronously to our log file. Very fragile, basically YapRandomAccessFile
	// timer handles - or IoAdapter design ;P - needs to be fixed.
	private int _runningId;
	
	private String _logPath;

	private RandomAccessFile _writer;

	private long _pos;
	
	private int _runs;

	public RecordingIoAdapter(IoAdapter adapter, String logPath) {
		super(adapter);
		_logPath = logPath;
		_runningId=0;
	}

	protected RecordingIoAdapter(IoAdapter adapter, String logPath,
			String file, boolean append, long initialLength, boolean readOnly) throws Db4oIOException {
		super(adapter, file, append, initialLength, readOnly);
		try {
			_writer = new RandomAccessFile(logPath, "rw");
		} catch (IOException e) {
			throw new Db4oIOException(e);
		}
		_runs=0;
	}

	public void close() throws Db4oIOException {
		super.close();
		writeLogChar('q');
		//System.err.println(_runs);
	}

	public IoAdapter open(String path, boolean lockFile, long initialLength, boolean readOnly)
			throws Db4oIOException {
		_runningId++;
		return new RecordingIoAdapter(_delegate, _logPath+"."+_runningId, path, lockFile,
				initialLength, readOnly);
	}

	public int read(byte[] buffer, int length) throws Db4oIOException {
		writeLog('r',_pos,length);
		return super.read(buffer,length);

	}

	public void seek(long pos) throws Db4oIOException {
		_pos = pos;
		super.seek(pos);
	}

	public void write(byte[] buffer, int length) throws Db4oIOException {
		writeLog('w',_pos,length);
		super.write(buffer,length);
	}
	
	public void sync() throws Db4oIOException {
		writeLogChar('f');
		super.sync();
	}
	
	private void writeLog(char type, long pos, int length)
			throws Db4oIOException {
		try {
			_writer.writeChar(type);
			_writer.writeLong(pos);
			_writer.writeInt(length);
		} catch (IOException e) {
			throw new Db4oIOException(e);
		}
		_runs++;
	}
	
	private void writeLogChar(char c) throws Db4oIOException {
		try {
			_writer.writeChar(c);
		} catch (IOException e) {
			throw new Db4oIOException(e);
		}
		_runs++;
	}
}