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

import java.io.*;
import java.util.*;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.db4ounit.common.ta.collections.*;
import com.db4o.reflect.jdk.*;
import com.db4o.ta.*;

import db4ounit.extensions.*;

public class TransparentActivationSampleMain extends AbstractDb4oTestCase {

	private static final int PRIORITY = 42;
	private static final String FILENAME = "ta.db4o";

	public static void main(String[] args) {
		Configuration config = Db4o.newConfiguration();
		config.add(new PagedListSupport());
		config.add(new TransparentActivationSupport());
		config.reflectWith(new JdkReflector(TransparentActivationSampleMain.class.getClassLoader()));
		
		new File(FILENAME).delete();
		ObjectContainer db = Db4o.openFile(config, FILENAME);
		PrioritizedProject project = new PrioritizedProject("db4o",PRIORITY);
		project.logWorkDone(new UnitOfWork("ta kick-off", new Date(1000), new Date(2000)));
		db.store(project);
		db.close();
		project = null;

		db = Db4o.openFile(config, FILENAME);
		ObjectSet result = db.query(PrioritizedProject.class);
		System.out.println(result.size());
		project = (PrioritizedProject) result.next();
		System.out.println(project.getPriority());
		System.out.println(project.totalTimeSpent());
		db.close();
		new File(FILENAME).delete();
	}
}
