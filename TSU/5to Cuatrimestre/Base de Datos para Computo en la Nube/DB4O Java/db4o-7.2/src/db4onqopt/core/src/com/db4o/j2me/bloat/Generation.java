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

import EDU.purdue.cs.bloat.editor.*;
import EDU.purdue.cs.bloat.file.*;
import EDU.purdue.cs.bloat.reflect.*;

import com.db4o.j2me.bloat.testdata.*;
import com.db4o.reflect.self.*;

// TODO: Use plain classes for testing, not the SelfReflector test cases
// (which already implement SelfReflectable functionality)
public class Generation {

	public static void main(String[] args) {
		String outputDirName = "generated";
		ClassFileLoader loader = new ClassFileLoader();
		BloatJ2MEContext context = new BloatJ2MEContext(loader, outputDirName);
		
		ClassEditor ce = context.createClass(Modifiers.PUBLIC,
				"com.db4o.j2me.bloat.testdata.GeneratedDogSelfReflectionRegistry", Type.getType(Type.classDescriptor(SelfReflectionRegistry.class.getName())),
				new Type[0]);
		context.createLoadClassConstMethod(ce);

		RegistryEnhancer registryEnhancer = new RegistryEnhancer(context, ce,
				Dog.class);
		registryEnhancer.generate();
		ce.commit();

		enhanceClass(context,"../bin","com.db4o.j2me.bloat.testdata.Dog");
		enhanceClass(context,"../bin","com.db4o.j2me.bloat.testdata.Animal");
	}
	
	private static void enhanceClass(BloatJ2MEContext context,String path,String name) {
		ClassEditor cled = context.loadClass(path,name);
		ClassEnhancer classEnhancer = new ClassEnhancer(context, cled);
		classEnhancer.generate();
		cled.commit();
	}
}
