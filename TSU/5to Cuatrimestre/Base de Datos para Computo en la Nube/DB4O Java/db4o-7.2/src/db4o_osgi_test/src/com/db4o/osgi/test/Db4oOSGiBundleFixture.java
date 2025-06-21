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
package com.db4o.osgi.test;

import java.io.*;

import org.osgi.framework.*;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.foundation.*;
import com.db4o.osgi.*;

import db4ounit.extensions.*;
import db4ounit.extensions.fixtures.*;
import db4ounit.extensions.util.*;

class Db4oOSGiBundleFixture extends AbstractSoloDb4oFixture {

	private final BundleContext _context;
	private final String _fileName;
	private Configuration _config;
	private Configuration _origConfig;
	
	
	public Db4oOSGiBundleFixture(BundleContext context, String fileName) {
		super(new IndependentConfigurationSource());
		_context = context;
		_fileName = CrossPlatformServices.databasePath(fileName);
	}

	protected ObjectContainer createDatabase(Configuration config) {
		// hack around sticky stream reference, should actually be fixed in config API
		_config = config;
		_origConfig = null;
		if(_config instanceof DeepClone) {
			_origConfig = _config;
			_config = (Configuration) ((DeepClone)_config).deepClone(null);
		}
	    ServiceReference sRef = _context.getServiceReference(Db4oService.class.getName());
	    Db4oService dbs = (Db4oService)_context.getService(sRef);
	    return dbs.openFile(_config,_fileName);
	}

	protected void doClean() {
		_config = null;
		new File(_fileName).delete();
	}

	public void defragment() throws Exception {
		defragment(_fileName);
	}

	public String label() {
		return "OSGi/bundle";
	}

	public boolean accept(Class clazz) {
		return super.accept(clazz)&&(!(OptOutNoFileSystemData.class.isAssignableFrom(clazz)));
	}

	public void configureAtRuntime(RuntimeConfigureAction action) {
		action.apply(_config);
		if(_origConfig != null) {
			action.apply(_origConfig);
		}
	}

}
