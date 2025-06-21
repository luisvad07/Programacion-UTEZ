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
package com.db4o.db4ounit.common.ta.events;

import com.db4o.activation.*;
import com.db4o.db4ounit.common.ta.*;
import com.db4o.events.*;
import com.db4o.ta.*;

import db4ounit.*;

public class ActivationEventsTestCase extends TransparentActivationTestCaseBase {
	
	public static class NonActivatableItem {
		public String name;
		
		public NonActivatableItem(String name_) {
			name = name_;
		}
		
		public NonActivatableItem() {
		}
	}
	
	public static class ActivatableItem implements Activatable {
		public String name;
		public NonActivatableItem child;
		private transient Activator _activator;
		
		public ActivatableItem(String name_, NonActivatableItem child_) {
			name = name_;
			child = child_;
		}
		
		public ActivatableItem() {
		}

		public void activate(ActivationPurpose purpose) {
			_activator.activate(purpose);
		}

		public void bind(Activator activator) {
			_activator = activator;
		}
	}
	
	protected void store() throws Exception {
		final NonActivatableItem nonActivatable = new NonActivatableItem("Eric Idle");
		store(nonActivatable);
		store(new ActivatableItem("John Cleese", nonActivatable));
	}
	
	public void testActivatingCancelNonActivatableDepth0() {
		
		addCancelAnyListener();
		NonActivatableItem item = queryNonActivatableItem();
		Assert.isNull(item.name);
	}
	
	public void testActivatingCancelActivatableDepth0() {
		
		addCancelAnyListener();
		ActivatableItem item = queryActivatableItem();
		item.activate(ActivationPurpose.READ);
		Assert.isNull(item.name);
	}
	
	public void testActivatingCancelDepth1() {
		addCancelNonActivatableListener();
		
		ActivatableItem item = queryActivatableItem();
		item.activate(ActivationPurpose.READ);
		Assert.isNotNull(item.name);
		Assert.isNotNull(item.child);
		Assert.isNull(item.child.name);
	}

	private void addCancelNonActivatableListener() {
		eventRegistry().activating().addListener(new EventListener4() {
			public void onEvent(Event4 e, EventArgs args) {
				final Object obj = ((ObjectEventArgs)args).object();
				if (obj instanceof NonActivatableItem) {
					((CancellableEventArgs)args).cancel();
				}
			}
		});
	}

	private void addCancelAnyListener() {
		eventRegistry().activating().addListener(new EventListener4() {
			public void onEvent(Event4 e, EventArgs args) {
				((CancellableEventArgs)args).cancel();
			}
		});
	}
	
	private NonActivatableItem queryNonActivatableItem() {
		return (NonActivatableItem)retrieveOnlyInstance(NonActivatableItem.class);
	}
	
	private ActivatableItem queryActivatableItem() {
		return (ActivatableItem)retrieveOnlyInstance(ActivatableItem.class);
	}
}
