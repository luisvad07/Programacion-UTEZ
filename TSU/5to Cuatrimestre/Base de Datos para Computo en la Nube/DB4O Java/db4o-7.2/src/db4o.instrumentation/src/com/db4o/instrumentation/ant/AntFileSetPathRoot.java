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
package com.db4o.instrumentation.ant;

import java.io.*;
import java.util.*;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;
import org.apache.tools.ant.types.resources.*;

import com.db4o.instrumentation.core.*;
import com.db4o.instrumentation.file.*;
import com.db4o.instrumentation.util.*;

/**
 * @exclude
 */
class AntFileSetPathRoot implements FilePathRoot, ClassFilter {

	private FileSet[] _fileSets;
	private DirectoryScanner[] _scanners;

	public AntFileSetPathRoot(FileSet[] fileSets) {
		_fileSets = fileSets;
		_scanners = new DirectoryScanner[_fileSets.length];
		for (int fileSetIdx = 0; fileSetIdx < _fileSets.length; fileSetIdx++) {
			DirectoryScanner scanner = _fileSets[fileSetIdx].getDirectoryScanner();
			scanner.scan();
			_scanners[fileSetIdx] = scanner;
		}
	}
	
	public Iterator files() {
		return new FileSetIterator(_fileSets);
	}

	public String[] rootDirs() throws IOException {
		String[] rootDirs = new String[_fileSets.length];
		for (int rootIdx = 0; rootIdx < _fileSets.length; rootIdx++) {
			FileSet curFileSet = _fileSets[rootIdx];
			File rootDir = curFileSet.getDir();
			if(rootDir == null && (curFileSet instanceof ZipFileSet)) {				
				ZipFileSet zipFileSet = (ZipFileSet)curFileSet;
				rootDir = zipFileSet.getSrc();
			}
			if(rootDir == null) {				
				rootDir = File.listRoots()[0]; // XXX
			}
			rootDirs[rootIdx] = rootDir.getCanonicalPath();
		}
		return rootDirs;
	}

	private static class FileSetIterator implements Iterator {

		private final FileSet[] _fileSets;
		private int _fileSetIdx;
		private Iterator _fileSetIter;
		
		public FileSetIterator(FileSet[] fileSets) {
			_fileSets = fileSets;
			advanceFileSet();
		}

		public boolean hasNext() {
			return _fileSetIter.hasNext();
		}

		public Object next() {
			Resource resource = (Resource)_fileSetIter.next();
			advanceFileSet();
			if(resource instanceof FileResource) {
				FileResource fileRes = (FileResource)resource;
				return new FileInstrumentationClassSource(fileRes.getBaseDir(), fileRes.getFile());
			}
			return new AntJarEntryInstrumentationClassSource(resource);
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		private void advanceFileSet() {
			while((_fileSetIter == null || !_fileSetIter.hasNext()) && _fileSetIdx < _fileSets.length) {
				_fileSetIter = _fileSets[_fileSetIdx].iterator();
				_fileSetIdx++;
			}
		}
	}

	public boolean accept(Class clazz) {
// // Ultra slow, but works with current Jar approach
//		try {
//			for (Iterator fileSetIter = files(); fileSetIter.hasNext();) {
//				InstrumentationClassSource source = (InstrumentationClassSource) fileSetIter.next();
//				if(clazz.getName().equals(source.className())) {
//					return true;
//				}
//			}
//		}
//		catch (IOException exc) {
//			// FIXME
//			throw new RuntimeException(exc.getMessage());
//		}
		for (int scannerIdx = 0; scannerIdx < _scanners.length; scannerIdx++) {
			DirectoryScanner scanner = _scanners[scannerIdx];
			String[] files = scanner.getIncludedFiles();
			for (int fileIdx = 0; fileIdx < files.length; fileIdx++) {
				String fileName = files[fileIdx];
				String clazzName = BloatUtil.classNameForPath(fileName);
				if(clazz.getName().equals(clazzName)) {
					return true;
				}
			}
		}
		return false;
	}
}
