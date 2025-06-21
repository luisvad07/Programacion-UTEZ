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

import java.io.*;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.db4ounit.util.*;
import com.db4o.defragment.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.foundation.io.*;

import db4ounit.*;
import db4ounit.extensions.fixtures.*;


public abstract class FormatMigrationTestCaseBase implements TestLifeCycle, OptOutNoFileSystemData {
    
    private String _db4oVersion;
    
    public void configure(){
        Configuration config = Db4o.configure();
        config.allowVersionUpdates(true);
        configureForTest(config);
    }
    
    protected static final String TEMP_PATH = Path4.combine(Path4.getTempPath(), "test/db4oVersions");

    protected String fileName(){
        _db4oVersion = Db4oVersion.NAME;
        return fileName(_db4oVersion);
    }
    
    protected String fileName(String versionName){
        return oldVersionFileName(versionName) + ".yap";
    }
    
    protected String oldVersionFileName(String versionName){
        return Path4.combine(TEMP_PATH, fileNamePrefix() + versionName.replace(' ', '_'));
    }
    
    public void createDatabase() {
        createDatabase(fileName());
    }
    
    public void createDatabaseFor(String versionName) {
        _db4oVersion = versionName;
        Configuration config = Db4o.configure();
        try{
        	configureForStore(config);
        } catch(Throwable t){
        	// Some old database engines may throw NoSuchMethodError
        	// for configuration methods they don't know yet. Ignore,
        	// but tell the implementor:
        	
        	// System.out.println("Exception in configureForStore for " + versionName + " in " + getClass().getName());
        }
    	createDatabase(fileName(versionName));
    }

	private void createDatabase(String file) {
		File4.mkdirs(TEMP_PATH);
        if(File4.exists(file)){
            File4.delete(file);
        }
        ExtObjectContainer objectContainer = Db4o.openFile(file).ext();
        try {
            store(objectContainer);
        } finally {
            objectContainer.close();
        }
	}
    
    public void setUp() throws Exception {
        configure();
        createDatabase();
    }
    
    public void test() throws IOException{
        for(int i = 0; i < versionNames().length; i ++){
            final String versionName = versionNames()[i];
			test(versionName);
        }
    }

	public void test(final String versionName) throws IOException {
	    _db4oVersion = versionName;
		String testFileName = fileName(versionName); 
		if(File4.exists(testFileName)){
//		    System.out.println("Check database: " + testFileName);
			
		    investigateFileHeaderVersion(testFileName);
		    
			runDefrag(testFileName);

		    checkDatabaseFile(testFileName);
		    // Twice, to ensure everything is fine after opening, converting and closing.
		    checkDatabaseFile(testFileName);
		    
		    updateDatabaseFile(testFileName);
		    
		    checkUpdatedDatabaseFile(testFileName);
		    
		    
		}else{
		    
		    System.out.println("Version upgrade check failed. File not found:" + testFileName);
		    
		    
		    // FIXME: The following fails the CC build since not all files are there on .NET.
		    //        Change back when we have all files.
		    // Assert.fail("Version upgrade check failed. File not found:" + testFileName);
		}
	}

	private void runDefrag(String testFileName) throws IOException {
		Db4o.configure().allowVersionUpdates(true);
		ObjectContainer oc = Db4o.openFile(testFileName);
		oc.close();
		
		String backupFileName = Path4.getTempFileName();
		try{
			DefragmentConfig defragConfig = new DefragmentConfig(testFileName, backupFileName);
			defragConfig.forceBackupDelete(true);
			Defragment.defrag(defragConfig);
		} finally{
			File4.delete(backupFileName);
		}
	}
    
    public void tearDown() throws Exception {
        // do nothing
    }
    
    private void checkDatabaseFile(String testFile) {
        withDatabase(testFile, new Function4() {
            public Object apply(Object objectContainer) {
                assertObjectsAreReadable((ExtObjectContainer) objectContainer);
                return null;
            }
        });
    }
    
    private void updateDatabaseFile(String testFile) {
        withDatabase(testFile, new Function4() {
            public Object apply(Object objectContainer) {
                update((ExtObjectContainer) objectContainer);
                return null;
            }

        });
    }
    
    private void checkUpdatedDatabaseFile(String testFile) {
        withDatabase(testFile, new Function4() {
            public Object apply(Object objectContainer) {
                assertObjectsAreUpdated((ExtObjectContainer) objectContainer);
                return null;
            }
        });
    }
    

    
    private void withDatabase(String file, Function4 function){
        configure();
        ExtObjectContainer objectContainer = Db4o.openFile(file).ext();
        try {
            function.apply(objectContainer);
        } finally {
            objectContainer.close();
        }
    }
    
    private void investigateFileHeaderVersion(String testFile) throws IOException{
        _db4oHeaderVersion = VersionServices.fileHeaderVersion(testFile); 
    }
    
    protected int db4oMajorVersion(){
        return new Integer (_db4oVersion.substring(0, 1)).intValue();
    }
    
    private byte _db4oHeaderVersion;
    
    protected abstract String[] versionNames();
    
    protected abstract String fileNamePrefix();

    protected void configureForTest(Configuration config){
    	// Override for special testing configuration.
    }
    
    protected void configureForStore(Configuration config){
    	// Override for special storage configuration.
    }
    
    protected abstract void store(ExtObjectContainer objectContainer);
    
    protected void storeObject(ExtObjectContainer objectContainer, Object obj){
    	// code MUST use the deprecated API here
    	// because it will be run against old db4o versions
    	objectContainer.set(obj);
    }
    
    protected abstract void assertObjectsAreReadable(ExtObjectContainer objectContainer);

    protected byte db4oHeaderVersion() {
        return _db4oHeaderVersion;
    }
    
    protected void update(ExtObjectContainer objectContainer) {
        // Override to do updates also
    }
    
    protected void assertObjectsAreUpdated(ExtObjectContainer objectContainer) {
        // Override to check updates also
    }
    
}
