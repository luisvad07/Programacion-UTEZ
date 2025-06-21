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
package com.db4o.ta.instrumentation.test;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import com.db4o.foundation.*;
import com.db4o.foundation.io.*;
import com.db4o.instrumentation.main.*;
import com.db4o.ta.instrumentation.test.jarcontents.*;
import com.db4o.ta.instrumentation.test.jarcontents.subpackage.*;

import db4ounit.*;

public class Db4oJarEnhancerTestCase implements TestCase {
	
	public void test() throws Exception {
		final File inputJar = mkTempFile("in.jar");		
		createJar(inputJar);
		
		final File outputJar = mkTempFile("out.jar");
		enhance(inputJar, outputJar);
		assertJarStructure(inputJar, outputJar);
		assertJarClassesWereInstrumented(outputJar);
	}

	private void assertJarClassesWereInstrumented(File jar) throws IOException, ClassNotFoundException {
		final Class[] klasses = new Class[] { Foo.class, Bar.class };
		final AssertingClassLoader loader = new AssertingClassLoader(jar, klasses);
		for (int i = 0; i < klasses.length; i++) {
			loader.assertAssignableFrom(Marker.class, klasses[i]);
		}
	}

	private void assertJarStructure(File inputJar, File outputJar) throws IOException {
		final Hashtable4 inputEntries = entryNameSet(inputJar);
		final Hashtable4 outputEntries = entryNameSet(outputJar);
		if (!inputEntries.containsAllKeys(outputEntries.keys())) {
			Assert.fail("Expecting: " + inputEntries + ", got: " + outputEntries);
		}
	}

	private Hashtable4 entryNameSet(File file) throws IOException {
		ZipFile zipFile = new ZipFile(file);
		try {
			return entryNameSet(zipFile);
		} finally {
			zipFile.close();
		}
	}

	private Hashtable4 entryNameSet(ZipFile zipFile) {
		final Hashtable4 set = new Hashtable4();
		final Enumeration entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			set.put(entry.getName(), entry.getName());
		}
		return set;
	}

	private void enhance(File inputJar, File outputJar) throws Exception {
		Db4oJarEnhancer enhancer = new Db4oJarEnhancer(new AddMarkerInterfaceClassEdit(Marker.class));
		enhancer.enhance(inputJar, outputJar, new String[0]);
	}

	private File mkTempFile(final String name) {
		final File temp = new File(Path4.combine(IO.mkTempDir("JarEnhancer"), name));
		temp.deleteOnExit();
		return temp;
	}

	private void createJar(File file) throws IOException {
		JarFileWriter jar = new JarFileWriter(file);
		try {
			jar.writeClass(Foo.class);
			jar.writeClass(Bar.class);
			jar.writeResourceString("resources/resource.txt", "just a resource");
		} finally {
			jar.close();
		}
	}

}
