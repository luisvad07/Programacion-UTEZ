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
package com.db4o.db4ounit.common.handlers;

import com.db4o.Db4o;
import com.db4o.config.Configuration;
import com.db4o.diagnostic.DeletionFailed;
import com.db4o.diagnostic.Diagnostic;
import com.db4o.diagnostic.DiagnosticListener;
import com.db4o.ext.ExtObjectContainer;

import db4ounit.Assert;

/**
 * @exclude
 */
public class CascadedDeleteFileFormatUpdateTestCase extends FormatMigrationTestCaseBase {
	
	private boolean _failed;
	
	protected void configureForStore(Configuration config) {
		config.objectClass(ParentItem.class).cascadeOnDelete(true);
	}
	
	protected void configureForTest(Configuration config) {
		configureForStore(config);
		config.diagnostic().addListener(new DiagnosticListener() {
			public void onDiagnostic(Diagnostic d) {
				if(d instanceof DeletionFailed){
					// Can't assert directly here, db4o eats the exception. :/
					_failed = true;
				}
			}
		});
	}
	
	public static class ParentItem {

		public ChildItem[] _children;
		
		public static ParentItem newTestInstance(){
			ParentItem item = new ParentItem();
			item._children = new ChildItem[]{
				new ChildItem(),
				new ChildItem(),
			};
			return item;
		}
		
	}
	
	public static class ChildItem {
		
	}

	protected void assertObjectsAreReadable(ExtObjectContainer objectContainer) {
		ParentItem parentItem = (ParentItem) retrieveInstance(objectContainer, ParentItem.class);
		Assert.isNotNull(parentItem._children);
		Assert.isNotNull(parentItem._children[0]);
		Assert.isNotNull(parentItem._children[1]);
		objectContainer.delete(parentItem);
		Assert.isFalse(_failed);
		store(objectContainer);
	}

	private Object retrieveInstance(ExtObjectContainer objectContainer,
			Class clazz) {
		return objectContainer.query(clazz).next();
	}

	protected String fileNamePrefix() {
		return "migrate_cascadedelete_" ;
	}

	protected void store(ExtObjectContainer objectContainer) {
		storeObject(objectContainer, ParentItem.newTestInstance());
	}

	protected String[] versionNames() {
		return new String[] { Db4o.version().substring(5) };	
	}

}
