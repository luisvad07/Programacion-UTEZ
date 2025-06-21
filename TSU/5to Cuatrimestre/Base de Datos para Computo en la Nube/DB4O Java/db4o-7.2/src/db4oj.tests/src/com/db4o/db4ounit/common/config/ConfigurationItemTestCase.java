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
package com.db4o.db4ounit.common.config;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.config.Configuration;
import com.db4o.config.ConfigurationItem;
import com.db4o.foundation.io.File4;
import com.db4o.foundation.io.Path4;
import com.db4o.internal.*;

import db4ounit.Assert;
import db4ounit.TestCase;

public class ConfigurationItemTestCase implements TestCase {
	
	static final class ConfigurationItemStub implements ConfigurationItem {

		private InternalObjectContainer _container;
		private Configuration _configuration;

		public void apply(InternalObjectContainer container) {
			Assert.isNotNull(container);
			_container = container;
		}

		public void prepare(Configuration configuration) {
			Assert.isNotNull(configuration);
			_configuration = configuration;
		}
		
		public Configuration preparedConfiguration() {
			return _configuration;
		}
		
		public InternalObjectContainer appliedContainer() {
			return _container;
		}
		
	}

	public void test() {
		Configuration configuration = Db4o.newConfiguration();
		
		ConfigurationItemStub item = new ConfigurationItemStub();
		configuration.add(item);
		
		Assert.areSame(configuration, item.preparedConfiguration());
		Assert.isNull(item.appliedContainer());
		
		File4.delete(databaseFile());
		
		ObjectContainer container = Db4o.openFile(configuration, databaseFile());
		container.close();
		
		Assert.areSame(container, item.appliedContainer());
	}

	private String databaseFile() {
		return Path4.combine(Path4.getTempPath(), getClass().getName());
	}
}
