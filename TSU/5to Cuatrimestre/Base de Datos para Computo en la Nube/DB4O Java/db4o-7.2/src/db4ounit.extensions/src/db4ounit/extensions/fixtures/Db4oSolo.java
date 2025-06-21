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
package db4ounit.extensions.fixtures;

import com.db4o.*;
import com.db4o.config.*;

import db4ounit.extensions.*;
import db4ounit.extensions.util.*;

public class Db4oSolo extends AbstractFileBasedDb4oFixture {
	
	private static final String FILE = "db4oSoloTest.yap"; 

	public Db4oSolo() {
		this(new IndependentConfigurationSource());	
	}

	public Db4oSolo(ConfigurationSource configSource) {
		super(configSource, filePath());	
	}
    
	public Db4oSolo(FixtureConfiguration fixtureConfiguration) {
		this();
		fixtureConfiguration(fixtureConfiguration);
	}

	protected ObjectContainer createDatabase(Configuration config) {
		return Db4o.openFile(config, getAbsolutePath());
	}
	
	public String label() {
		return buildLabel("SOLO");
	}

	public void defragment() throws Exception {
		defragment(filePath());
	}

	private static String filePath() {
		return CrossPlatformServices.databasePath(FILE);
	}
}
