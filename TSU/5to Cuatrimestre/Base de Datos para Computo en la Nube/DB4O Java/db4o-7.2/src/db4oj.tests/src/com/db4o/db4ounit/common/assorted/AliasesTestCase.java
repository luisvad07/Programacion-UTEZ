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
package com.db4o.db4ounit.common.assorted;

import com.db4o.config.*;
import com.db4o.db4ounit.util.Strings;

import db4ounit.*;
import db4ounit.extensions.*;
import db4ounit.extensions.fixtures.*;


public class AliasesTestCase extends AbstractDb4oTestCase implements OptOutDefragSolo {
	
	public static void main(String[] args) {
		new AliasesTestCase().runSolo();
	}
	
	
	private int id;
	
	private Alias alias;

	
	public static class AFoo{
		public String foo;
	}
	
	public static class ABar extends AFoo {
		public String bar;
	}
	
	public static class BFoo {
		public String foo;
	}
	
	public static class BBar extends BFoo {
		public String bar;
	}
	
	public static class CFoo{
		public String foo;
	}
	
	public static class CBar extends CFoo {
		public String bar;
	}
	
	protected void store(){
		addACAlias();
		CBar bar = new CBar();
		bar.foo = "foo";
		bar.bar = "bar";
		store(bar);
		id = (int)db().getID(bar);
	}
	
	public void testAccessByChildClass() throws Exception{
		addABAlias();
		BBar bar = (BBar) retrieveOnlyInstance(BBar.class);
		assertInstanceOK(bar);
	}
	
	public void testAccessByParentClass() throws Exception{
		addABAlias();
		BBar bar = (BBar) retrieveOnlyInstance(BFoo.class);
		assertInstanceOK(bar);
	}
	
	public void testAccessById() throws Exception{
		addABAlias();
		BBar bar = (BBar) db().getByID(id);
		db().activate(bar, 2);
		assertInstanceOK(bar);
	}
	
	public void testAccessWithoutAlias() throws Exception{
		removeAlias();
		ABar bar = (ABar) retrieveOnlyInstance(ABar.class);
		assertInstanceOK(bar);
	}
	
	private void assertInstanceOK (BBar bar) {
		Assert.areEqual("foo", bar.foo);
		Assert.areEqual("bar", bar.bar);
	}
	
	private void assertInstanceOK (ABar bar) {
		Assert.areEqual("foo", bar.foo);
		Assert.areEqual("bar", bar.bar);
	}
	
	private void addABAlias() throws Exception{
		addAlias("A", "B");
	}
	
	private void addACAlias(){
		addAlias("A", "C");
	}
	
	private void addAlias(String storedLetter, String runtimeLetter){
		removeAlias();
		alias = createAlias(storedLetter, runtimeLetter);
		fixture().configureAtRuntime(new RuntimeConfigureAction() {
			public void apply(Configuration config) {
				config.addAlias(alias);	
			}
		});
	}
	
	private void removeAlias(){
		if(alias != null){
			fixture().configureAtRuntime(new RuntimeConfigureAction() {
				public void apply(Configuration config) {
					config.removeAlias(alias);
				}
			});
			alias = null;
		}
	}
	
	private WildcardAlias createAlias(String storedLetter, String runtimeLetter){
		String className = reflector().forObject(new ABar()).getName();
		String storedPattern = Strings.replace(className, "ABar", storedLetter + "*");
		String runtimePattern = Strings.replace(className, "ABar", runtimeLetter + "*");
		return new WildcardAlias(storedPattern, runtimePattern);
	}

}
