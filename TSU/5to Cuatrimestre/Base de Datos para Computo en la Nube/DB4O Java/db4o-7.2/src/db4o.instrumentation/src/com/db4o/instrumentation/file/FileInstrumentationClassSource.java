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
package com.db4o.instrumentation.file;

import java.io.*;

import com.db4o.instrumentation.util.*;

/**
 * @exclude
 */
public class FileInstrumentationClassSource implements Comparable, InstrumentationClassSource {
	private final File _root;
	private final File _file;

	public FileInstrumentationClassSource(File root, File file) {
		this._root = root;
		this._file = file;
	}
	
	public File root() {
		return _root;
	}

	public File file() {
		return _file;
	}
	
	public String className() throws IOException {
		return BloatUtil.classNameForPath(classPath());
	}

	private String classPath() throws IOException {
		return _file.getCanonicalPath().substring(_root.getCanonicalPath().length()+1);
	}

	public File targetPath(File targetBase) throws IOException {
		return new File(targetBase, classPath());
	}
	
	public int hashCode() {
		return 43 * _root.hashCode() + _file.hashCode();
	}

	public InputStream inputStream() throws IOException {
		return new FileInputStream(_file);
	}
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final FileInstrumentationClassSource other = (FileInstrumentationClassSource) obj;
		return _root.equals(other._root) && _file.equals(other._file);
	}

	public int compareTo(Object o) {
		return _file.compareTo(((FileInstrumentationClassSource)o)._file);
	}
	
	public String toString() {
		return _file + " [" + _root + "]";
	}

    public File sourceFile() {
        return _file;
    }
	
}
