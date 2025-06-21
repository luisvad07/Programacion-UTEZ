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

import com.db4o.test.cluster.*;
import com.db4o.test.conjunctions.*;
import com.db4o.test.constraints.*;
import com.db4o.test.java.*;
import com.db4o.test.nativequery.*;
import com.db4o.test.performance.*;

public class AllTestsConfAll extends TestSuite{

	static protected final String TEST_CONFIGURATION = "AllTestsConfAll";
	
    protected void addTestSuites(TestSuite suites) {
        suites.add(this);
        suites.add(new ConstraintsTestSuite());
        suites.add(new ConjunctionsTestSuite());
        suites.add(new JavaTestSuite());
        suites.add(new NativeQueryTestSuite());
	}
    
    public Class[] tests(){
        return new Class[] {
	    	Backup.class,
            BasicClusterTest.class,
	    	BindFileSize.class,
	        CascadeToHashtable.class,
	        CascadeToVector.class,
	        CascadeToExistingVectorMember.class,
            CallbackCanDelete.class,
	    	CallbacksTestCase.class,
	        CaseInsensitive.class,
	        Circular1.class,
	        Circular2.class,
	        // takes too long in JDK1.1 setup due to locking timeout
            // CrashSimulatingTest.class,
	        CreateIndexInherited.class,
			CustomActivationDepth.class,
            DeleteDeep.class,
			DeepSet.class,
	        DifferentAccessPaths.class,
	        DualDelete.class,
            EncryptionWrongPassword.class,
			ExtMethods.class,
			ExtendsDate.class,
			FileSizeOnReopen.class,
	        GetAll.class,
            GetAllSoda.class,
			GreaterOrEqual.class,
			IndexedByIdentity.class,
			IndexCreateDrop.class,
            IndexQueryingIsFast.class,
			IndexedUpdatesWithNull.class,
			InternStrings.class,
            InvalidUUID.class,
	        IsStored.class,
	        Isolation.class,
			Messaging.class,
            MultiLevelIndex.class,
            NeverAnObjectStored.class,
			NoInstanceStored.class,
	        NoInternalClasses.class,
	        NullWrapperQueries.class,
	        ObjectContainerIsTransient.class,
			ObjectSetIDs.class,
			ParameterizedEvaluation.class,
            
            // disabled because it fails due to fix
            // See comments in: YapClass.deleteEmbedded1()
            
	        // PrimitiveArrayFileSize.class,
            
			QueryDeleted.class,
            QueryForUnknownField.class,
	        QueryNonExistant.class,
            ReadAs.class,
            ReferenceThis.class,
	        Refresh.class,
	        Rename.class,
			SameSizeOnReopen.class,
	        SerializableTranslator.class,
	    	SetDeactivated.class,
	    	SetSemaphore.class,
			SmallerOrEqual.class,
	    	SodaNoDuplicates.class,
	    	//SortResult.class,
	    	StoredClassInformation.class,
	    	StoredFieldValue.class,
	    	//StoreObject.class,
            StorePrimitiveDirectly.class,
			SwitchingFilesFromClient.class,
			TestDescend.class,
	        TestHashTable.class,
	        TwoClients.class,
            UuidAware.class,
        };
    }
    

    /**
      * the number of test runs 
      */
    public int RUNS = 1;

	/**
	 * delete the database files
	 */
	public boolean DELETE_FILE = true;

    /**
      * run the tests stand-alone 
      */
    public boolean SOLO = true;

    /**
      * run the tests in client/server mode 
      */
    public boolean CLIENT_SERVER = true;
    
    /**
     * use ObjectServer#openClient() instead of Db4o.openClient()
     */
    public static boolean EMBEDDED_CLIENT = false;
    
    /**
     * run the test against a memory file instead of disc file
     */
    public static boolean MEMORY_FILE = false;

    /**
      * run the client/server test against a remote server. 
      * This requires AllTestsServer to be started on the other machine and 
      * SERVER_HOSTNAME to be set correctly.
      */
    final boolean REMOTE_SERVER = false;

    /**
     * the database file to be used for the server.
     */
    public static String FILE_SERVER = "xt_serv.yap";

    /**
     * the database file to be used stand-alone.
     */
    public static String FILE_SOLO = "xt_solo.yap";
    
    /**
     * the server host name.
     */
    public static String SERVER_HOSTNAME = "localhost";

    /**
     * the server port.
     */
    public static int SERVER_PORT = 4448;

    /**
     * the db4o user.
     */
    public static String DB4O_USER = "db4o";

    /**
     * the db4o password.
     */
    public static String DB4O_PASSWORD = "db4o";
    
    /**
     * path to blobs held externally
     */
	public static String BLOB_PATH = "test/TEMP/db4oTestBlobs";

}
