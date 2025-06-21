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
package com.db4o.j2me.bloat;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;

public class EnhanceTestStarter {
	public static void main(String[] args) throws Exception {
		Generation.main(new String[0]);
		String[] classpath={
				"generated",
				"bin",
				"../db4oj/bin",
				"../db4ojdk1.2/bin",
		};
		URL[] urls=new URL[classpath.length];
		for (int pathIdx = 0; pathIdx < classpath.length; pathIdx++) {
			urls[pathIdx]=new File(classpath[pathIdx]).toURI().toURL();
		}
		// a risky move, but usually this should be the ext classloader
		ClassLoader extCL = ClassLoader.getSystemClassLoader().getParent();
		URLClassLoader urlCL=new URLClassLoader(urls,extCL);
		Class mainClazz=urlCL.loadClass(EnhanceTestMain.class.getName());
		Method mainMethod=mainClazz.getMethod("main",new Class[]{String[].class});
		mainMethod.invoke(null, new Object[]{new String[0]});
	}
}
