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
package com.db4o.ta.instrumentation.test.integration;

import java.lang.reflect.*;
import java.net.*;

import com.db4o.instrumentation.*;
import com.db4o.instrumentation.classfilter.*;
import com.db4o.instrumentation.core.*;
import com.db4o.instrumentation.main.*;
import com.db4o.ta.instrumentation.*;

public class RunTASampleInstrumented {

	public static void main(String[] args) throws Exception {
		ClassFilter filter = new ByNameClassFilter(new String[] { Project.class.getName(), PrioritizedProject.class.getName(), UnitOfWork.class.getName() });
		BloatClassEdit edit = new InjectTransparentActivationEdit(filter);
		ClassLoader loader = new BloatInstrumentingClassLoader(new URL[]{}, RunTASampleInstrumented.class.getClassLoader(), filter, edit);
		Class mainClass = loader.loadClass(TransparentActivationSampleMain.class.getName());
		Method mainMethod = mainClass.getMethod("main", new Class[]{ String[].class });
		mainMethod.invoke(null, new Object[]{ new String[]{} });
	}
}
