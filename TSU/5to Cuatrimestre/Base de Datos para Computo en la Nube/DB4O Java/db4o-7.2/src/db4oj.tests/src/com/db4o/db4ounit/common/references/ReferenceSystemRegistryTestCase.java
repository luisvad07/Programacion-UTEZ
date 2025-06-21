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
package com.db4o.db4ounit.common.references;

import com.db4o.internal.*;

import db4ounit.*;


public class ReferenceSystemRegistryTestCase implements TestLifeCycle {

    private ReferenceSystemRegistry _registry;
    private ReferenceSystem _referenceSystem1;
    private ReferenceSystem _referenceSystem2;
    
    private static int TEST_ID = 5;

    public void setUp() throws Exception {
        _registry = new ReferenceSystemRegistry();
        _referenceSystem1 = new TransactionalReferenceSystem();
        _referenceSystem2 = new TransactionalReferenceSystem();
        _registry.addReferenceSystem(_referenceSystem1);
        _registry.addReferenceSystem(_referenceSystem2);
    }
    
    public void tearDown() throws Exception {
        
    }
    
    public void testRemoveId(){
        addTestReference();
        _registry.removeId(TEST_ID);
        assertTestReferenceNotPresent();
    }

    public void testRemoveNull(){
        _registry.removeObject(null);
    }

    public void testRemoveObject(){
        ObjectReference testReference = addTestReference();
        _registry.removeObject(testReference.getObject());
        assertTestReferenceNotPresent();
    }
    
    public void testRemoveReference(){
        ObjectReference testReference = addTestReference();
        _registry.removeReference(testReference);
        assertTestReferenceNotPresent();
    }
    
    public void testRemoveReferenceSystem(){
        addTestReference();
        _registry.removeReferenceSystem(_referenceSystem1);
        _registry.removeId(TEST_ID);
        Assert.isNotNull(_referenceSystem1.referenceForId(TEST_ID));
        Assert.isNull(_referenceSystem2.referenceForId(TEST_ID));
    }

    private void assertTestReferenceNotPresent() {
        Assert.isNull(_referenceSystem1.referenceForId(TEST_ID));
        Assert.isNull(_referenceSystem2.referenceForId(TEST_ID));
    }

    private ObjectReference addTestReference() {
        ObjectReference ref = new ObjectReference(TEST_ID);
        ref.setObject(new Object());
        _referenceSystem1.addExistingReference(ref);
        _referenceSystem2.addExistingReference(ref);
        return ref;
    }
    
    

}
