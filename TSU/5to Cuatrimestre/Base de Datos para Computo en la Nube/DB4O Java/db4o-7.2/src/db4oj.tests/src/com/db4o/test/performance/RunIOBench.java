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

import com.db4o.io.*;

/**
 * 
 * @exclude
 */
public class RunIOBench {
	public static void main(String[] args) throws IOException {

		RandomAccessFile recordedIn = new RandomAccessFile(Util.BENCHFILE+".1", "rw");
		new File(Util.DBFILE).delete();
		IoAdapter testadapt = new RandomAccessFileAdapter().open(Util.DBFILE,
				false, 1024, false);

		// IoAdapter testadapt = new MemoryIoAdapter().open(Util.DBFILE, false,
		// 1024);
		// IoAdapter testadapt = new SymbianIoAdapter().open(Util.DBFILE,
		// false, 1024);
		long bench = benchmark(recordedIn, testadapt);
		System.out.println("tested IOAdapter: ["
				+ testadapt.getClass().getName() + "]\nspeed: " + bench);
	}

	public static long benchmark(RandomAccessFile recordedIn, IoAdapter adapter)
			throws IOException {
		byte[] defaultData = new byte[1000];
		long start = System.currentTimeMillis();
		int runs = 0;
		try {
			while (true) {
				runs++;
				char type = recordedIn.readChar();
				if (type == 'q') {
					break;
				}
				if (type == 'f') {
					adapter.sync();
					continue;
				}
				long pos = recordedIn.readLong();
				int length = recordedIn.readInt();
				adapter.seek(pos);
				byte[] data = (length <= defaultData.length ? defaultData
						: new byte[length]);
				switch (type) {
				case 'r':
					adapter.read(data, length);
					break;
				case 'w':
					adapter.write(data, length);
					break;
				default:
					throw new IllegalArgumentException("Unknown access type: "
							+ type);
				}
			}
		} finally {
			recordedIn.close();
			adapter.close();
		}
		// System.err.println(runs);
		return System.currentTimeMillis() - start;
	}
}