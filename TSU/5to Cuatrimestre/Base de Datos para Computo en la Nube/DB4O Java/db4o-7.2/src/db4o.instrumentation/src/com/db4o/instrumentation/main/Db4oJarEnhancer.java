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
package com.db4o.instrumentation.main;

import java.io.*;

import com.db4o.foundation.io.*;
import com.db4o.instrumentation.core.*;
import com.db4o.instrumentation.util.*;

/**
 * Enhances classes stored in jar files keeping the jar structure
 * untouched.
 */
public class Db4oJarEnhancer {
	
	private final Db4oFileInstrumentor _fileEnhancer;

	public Db4oJarEnhancer(BloatClassEdit classEdit) {
		_fileEnhancer = new Db4oFileInstrumentor(classEdit);
	}

	public void enhance(File inputJar, File outputJar, String[] classPath) throws Exception {
		final String workingDir = tempDir(inputJar.getName());
		try {
			extractJarTo(inputJar, workingDir);
			enhance(workingDir, classPath);
			makeJarFromDirectory(workingDir, outputJar);
		} finally {
			deleteDirectory(workingDir);
		}
	}

	private void deleteDirectory(String workingDir) {
		Directory4.delete(workingDir, true);
	}

	private void enhance(String workingDir, String[] classPath) throws Exception  {
		_fileEnhancer.enhance(workingDir, workingDir, classPath);
	}

	private String tempDir(String name) {
		return Path4.combine(Path4.getTempPath(), name + "-working");
	}

	private void extractJarTo(File inputJar, String workingDir) throws IOException {
		new ZipFileExtraction(inputJar, workingDir);
	}
	
	private void makeJarFromDirectory(String workingDir, File outputJar) throws IOException {
		new ZipFileCreation(workingDir, outputJar);
	}
}
