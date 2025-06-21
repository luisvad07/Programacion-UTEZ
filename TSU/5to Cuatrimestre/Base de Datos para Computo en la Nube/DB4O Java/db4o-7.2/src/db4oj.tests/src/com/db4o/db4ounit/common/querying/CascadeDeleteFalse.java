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
package com.db4o.db4ounit.common.querying;

import com.db4o.config.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class CascadeDeleteFalse extends AbstractDb4oTestCase {

    public static class CascadeDeleteFalseHelper{  
    }
    
    public CascadeDeleteFalseHelper h1;
    public CascadeDeleteFalseHelper h2;
    public CascadeDeleteFalseHelper h3;
    
    protected void configure(Configuration conf) {
        conf.objectClass(this).cascadeOnDelete(true);
        conf.objectClass(this).objectField("h3").cascadeOnDelete(false);
    }
    
    protected void store() {
    	CascadeDeleteFalse cdf = new CascadeDeleteFalse();
        cdf.h1 = new CascadeDeleteFalseHelper();
        cdf.h2 = new CascadeDeleteFalseHelper();
        cdf.h3 = new CascadeDeleteFalseHelper();
        db().store(cdf);
    }
    
    public void test() {
        checkHelperCount(3);
        
        CascadeDeleteFalse cdf = (CascadeDeleteFalse)retrieveOnlyInstance(getClass());
        db().delete(cdf);
        
        checkHelperCount(1);
    }
    
    private void checkHelperCount (int count){
        Assert.areEqual(count, countOccurences(CascadeDeleteFalseHelper.class));
    }
    
    public static void main(String[] args) {
    	new CascadeDeleteFalse().runSolo();
	}
    
}
