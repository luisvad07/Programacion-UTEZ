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
package com.db4o.instrumentation.util;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import com.db4o.foundation.io.*;

/**
 * Extracts the contents of a zip file to a directory.
 * 
 * The operation is performed as a side effect of the
 * constructor execution.
 */
public class ZipFileExtraction {

	private final ZipFile _zipFile;
	private final String _targetDir;

	public ZipFileExtraction(File file, String targetDir) throws IOException {
		_targetDir = targetDir;
		_zipFile = new ZipFile(file);
		try {
			extractEntries();
		} finally {
			_zipFile.close();
		}
	}
	
	private void extractEntries() throws IOException {
		final Enumeration entries = _zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			extractEntry(entry);
		}
	}

	private void extractEntry(ZipEntry entry) throws IOException {
		if (entry.isDirectory()) {
			File4.mkdirs(targetPathFor(entry));
			return;
		}
		extractFileEntry(entry);
	}

	private void extractFileEntry(ZipEntry entry) throws FileNotFoundException,
			IOException {
		final FileOutputStream fos = new FileOutputStream(targetPathFor(entry));
		try {
			final InputStream is = _zipFile.getInputStream(entry);
			try {
				copy(is, fos);
			} finally {
				is.close();
			}
		} finally {
			fos.close();
		}
	}

	private void copy(InputStream is, OutputStream os) throws IOException{
		byte[] buffer = new byte[64*1024];
		int bytesRead = 0;
		while (-1 != (bytesRead = is.read(buffer))) {
			os.write(buffer, 0, bytesRead);
		}
	}

	private String targetPathFor(ZipEntry entry) {
		final String targetPath = Path4.combine(_targetDir, entry.getName());
		File4.mkdirs(Path4.getDirectoryName(targetPath));
		return targetPath;
	}
}
