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
package com.db4o.test;

import com.db4o.*;
import com.db4o.ext.*;


public class InvalidUUID {
    
    public String name;
    
    public void configure(){
        Db4o.configure().objectClass(this.getClass()).generateUUIDs(true);
    }
    
    public void storeOne(){
        name = "theOne";
    }
    
    public void testOne(){
        ExtObjectContainer oc = Test.objectContainer();
        
        Db4oUUID myUuid = oc.getObjectInfo(this).getUUID();
        
        Test.ensure(myUuid != null);
        
        byte[] mySignature = myUuid.getSignaturePart();
        long myLong = myUuid.getLongPart();
        
        long unknownLong = Long.MAX_VALUE - 100;  
        byte[] unknownSignature = new byte[]{1,2,4,99,33,22};
       
        Db4oUUID unknownLongPart= new Db4oUUID(unknownLong, mySignature);
        Db4oUUID unknownSignaturePart = new Db4oUUID(myLong, unknownSignature);
        Db4oUUID unknownBoth = new Db4oUUID(unknownLong, unknownSignature);
        
        Test.ensure(oc.getByUUID(unknownLongPart) == null);
        Test.ensure(oc.getByUUID(unknownSignaturePart) == null);
        Test.ensure(oc.getByUUID(unknownBoth) == null);
        
        
        Test.ensure(oc.getByUUID(unknownLongPart) == null);
        
        Test.delete(this);
        Test.commit();
        
        Test.ensure(oc.getByUUID(myUuid) == null);
    }

}
