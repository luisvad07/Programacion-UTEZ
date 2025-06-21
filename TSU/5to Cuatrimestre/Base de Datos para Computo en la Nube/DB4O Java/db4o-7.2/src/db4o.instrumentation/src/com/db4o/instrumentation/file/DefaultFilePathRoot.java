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
import java.util.*;

/**
 * @exclude
 */
public class DefaultFilePathRoot implements FilePathRoot {

	private final String[] _rootDirs;
	private final String _extension;
	
	public DefaultFilePathRoot(String[] rootDirs) {
		this(rootDirs, "");
	}

	public DefaultFilePathRoot(String[] rootDirs, String extension) {
		_rootDirs = rootDirs;
		_extension = extension;
	}

	public Iterator files() {
		return new FileSystemIterator(_rootDirs, _extension);
	}

	public String[] rootDirs() {
		return _rootDirs;
	}

	private static class FileSystemIterator implements Iterator {
		private final String _extension;
		private LinkedList _stack = new LinkedList();

		public FileSystemIterator(String[] roots, String extension) {
			_extension = extension;
			for (int rootIdx = 0; rootIdx < roots.length; rootIdx++) {
				File root = new File(roots[rootIdx]);
				push(new FileInstrumentationClassSource(root, root));
			}
			advanceQueue();
		}

		public boolean hasNext() {
			return !_stack.isEmpty();
		}

		public Object next() {
			InstrumentationClassSource top = pop();
			advanceQueue();
			return top;
		}


		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		private void advanceQueue() {
			while(!_stack.isEmpty() && !accept(peek())) {
				FileInstrumentationClassSource dir = pop();
				if(!dir.file().isDirectory()) {
					continue;
				}
				File[] children = dir.file().listFiles();
				for (int childIdx = 0; childIdx < children.length; childIdx++) {
					_stack.addFirst(new FileInstrumentationClassSource(dir.root(), children[childIdx]));
				}
			}
		}

		private boolean accept(FileInstrumentationClassSource file) {
			return file.file().isFile() && file.file().getName().endsWith(_extension);
		}

		private void push(InstrumentationClassSource root) {
			_stack.addFirst(root);
		}		

		private FileInstrumentationClassSource pop() {
			return (FileInstrumentationClassSource) _stack.removeFirst();
		}

		private FileInstrumentationClassSource peek() {
			return (FileInstrumentationClassSource)_stack.getFirst();
		}
	}
}
