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
package com.db4o.db4ounit.util.test;

import java.io.*;

import com.db4o.db4ounit.util.*;
import com.db4o.db4ounit.util.IOServices.*;

import db4ounit.*;

public class JavaServicesTestCase implements TestCase {
    
    public static class ShortProgram {
        
        public static final String OUTPUT = "XXshortXX";  
        
        public static void main(String[] arguments) {
            System.out.println(OUTPUT);
        }
    }
    
    public static class LongProgram {
        
        public static final String OUTPUT = "XXlongXX";
        
        public static void main(String[] arguments) {
            System.out.println(OUTPUT);
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
            }
        }
    }
    
    /**
     * @sharpen.remove
     */
    public void _testJava() throws IOException, InterruptedException{
        String output = JavaServices.java(ShortProgram.class.getName());
        Assert.isTrue(output.indexOf(ShortProgram.OUTPUT) >=0 );
    }
    
    /**
     * @sharpen.remove
     */
    public void _testStartAndKillJavaProcess() throws IOException{
        Assert.expect(DestroyTimeoutException.class, new CodeBlock() {
            public void run() throws Throwable {
                JavaServices.startAndKillJavaProcess(LongProgram.class.getName(), ShortProgram.OUTPUT, 50);
            }
        });
        JavaServices.startAndKillJavaProcess(LongProgram.class.getName(), LongProgram.OUTPUT, 5000);
    }

}
