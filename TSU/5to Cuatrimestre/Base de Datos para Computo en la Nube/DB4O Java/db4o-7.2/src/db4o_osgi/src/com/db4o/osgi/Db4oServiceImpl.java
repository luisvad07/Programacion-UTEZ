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
package com.db4o.osgi;

import org.osgi.framework.*;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.ext.*;
import com.db4o.reflect.jdk.*;

class Db4oServiceImpl implements Db4oService {

	private final Bundle _bundle;

	public Db4oServiceImpl(Bundle bundle) {
		_bundle = bundle;
	}

	public Configuration newConfiguration() {
		Configuration config = Db4o.newConfiguration();
		configureReflector(config);
		return config;
	}

	public ObjectContainer openClient(String hostName, int port, String user, String password) throws Db4oException {
		return openClient(Db4o.cloneConfiguration(), hostName, port, user, password);
	}

	public ObjectContainer openClient(Configuration config, String hostName, int port, String user, String password) throws Db4oException {
		return Db4o.openClient(config(config), hostName, port, user, password);
	}

	public ObjectContainer openFile(String databaseFileName) throws Db4oException {
		return openFile(Db4o.cloneConfiguration(), databaseFileName);
	}

	public ObjectContainer openFile(Configuration config, String databaseFileName) throws Db4oException {
		return Db4o.openFile(config(config), databaseFileName);
	}

	public ObjectServer openServer(String databaseFileName, int port) throws Db4oException {
		return openServer(Db4o.cloneConfiguration(), databaseFileName, port);
	}

	public ObjectServer openServer(Configuration config, String databaseFileName, int port) throws Db4oException {
		return Db4o.openServer(config(config), databaseFileName, port);
	}

	private Configuration config(Configuration config) {
		if (config == null) {
			config = Db4o.newConfiguration();
		}
		configureReflector(config);
		return config;
	}

	private void configureReflector(Configuration config) {
		config.reflectWith(new JdkReflector(new OSGiLoader(_bundle, new ClassLoaderJdkLoader(getClass().getClassLoader()))));
	}

}
