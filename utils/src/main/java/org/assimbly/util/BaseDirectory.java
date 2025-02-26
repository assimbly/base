package org.assimbly.util;

import java.io.File;

public class BaseDirectory {

    private static final BaseDirectory INSTANCE = new BaseDirectory();

    private volatile String baseDirectory = System.getProperty("user.home") + "/.assimbly";

    private static BaseDirectory getInstance() {
        return INSTANCE;
    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public void setBaseDirectory(String baseDirectory) {
        File directory = new File(baseDirectory);
        if (! directory.exists()){
            boolean dirsCreated = directory.mkdirs();
            if(!dirsCreated){
                System.out.println("Base Directory: " + baseDirectory + " couldn't be created.");
            }
        }
        this.baseDirectory = baseDirectory;
    }

    //example usage
    //BaseDirectory.getInstance().setBaseDirectory(10);
    //System.out.println(BaseDirectory.getInstance().getBaseDirectory());

}


