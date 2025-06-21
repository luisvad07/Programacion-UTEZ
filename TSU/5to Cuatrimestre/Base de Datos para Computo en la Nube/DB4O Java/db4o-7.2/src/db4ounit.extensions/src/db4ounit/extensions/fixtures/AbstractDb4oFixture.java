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

import com.db4o.config.*;
import com.db4o.defragment.*;

import db4ounit.extensions.*;

public abstract class AbstractDb4oFixture implements Db4oFixture {

	private final ConfigurationSource _configSource;
	private Configuration _config;
	private FixtureConfiguration _fixtureConfiguration;

	protected AbstractDb4oFixture(ConfigurationSource configSource) {
		_configSource=configSource;
	}
	
	public void fixtureConfiguration(FixtureConfiguration fc) {
		_fixtureConfiguration = fc;
	}
	
	public void reopen(Class testCaseClass) throws Exception {
		close();
		open(testCaseClass);
	}

	public Configuration config() {
		if(_config==null) {
			_config=_configSource.config();
		}
		return _config;
	}
	
	public void clean() {
		doClean();
		resetConfig();
	}
	
	public abstract boolean accept(Class clazz);

	protected abstract void doClean();	
	
	protected void resetConfig() {
		_config=null;
	}
	
	protected void defragment(String fileName) throws Exception{
        String targetFile = fileName + ".defrag.backup";
        DefragmentConfig defragConfig = new DefragmentConfig(fileName, targetFile);
        defragConfig.forceBackupDelete(true);
        defragConfig.db4oConfig(config());
		com.db4o.defragment.Defragment.defrag(defragConfig);
	}
	
	protected String buildLabel(String label) {
		if (null == _fixtureConfiguration) return label;
		return label + " - " + _fixtureConfiguration.getLabel();
	}

	protected void applyFixtureConfiguration(Class testCaseClass, final Configuration config) {
		if (null == _fixtureConfiguration) return;
		_fixtureConfiguration.configure(testCaseClass, config);
	}
	
	public String toString() {
		return label();
	}
}
