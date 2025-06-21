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
package com.db4o.test.util;

import java.net.*;

import com.db4o.foundation.*;

public class ExcludingClassLoader extends URLClassLoader {
	private Collection4 _excludedNames;

	public ExcludingClassLoader(ClassLoader parent,Class[] excludedClasses) {
		this(parent, collectNames(excludedClasses));
	}
	
	public ExcludingClassLoader(ClassLoader parent,Collection4 excludedNames) {
		super(new URL[]{},parent);
		this._excludedNames=excludedNames;
	}

	protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
		if(_excludedNames.contains(name)) {
			throw new ClassNotFoundException(name);
		}
		return super.loadClass(name, resolve);
	}
	
	private static Collection4 collectNames(Class[] classes) {
		Collection4 names = new Collection4();
		for (int classIdx = 0; classIdx < classes.length; classIdx++) {
			names.add(classes[classIdx].getName());
		}
		return names;
	}

	public static void main(String[] args) throws Exception {
		ClassLoader parent=ExcludingClassLoader.class.getClassLoader();
		String excName=ExcludingClassLoader.class.getName();
		Collection4 excluded=new Collection4();
		ClassLoader incLoader=new ExcludingClassLoader(parent,excluded);
		System.out.println(incLoader.loadClass(excName));
		excluded.add(excName);
		try {
			System.out.println(incLoader.loadClass(excName));
		}
		catch(ClassNotFoundException exc) {
			System.out.println("Ok, not found.");
		}
	}
}
