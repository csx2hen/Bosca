package com.bosca.user.models;

public class CreateFolderRequest {

    private final String filename = "/";
    private final String parentDir ="";
    private final boolean isFolder = true;

    public String getFilename() {
        return filename;
    }

    public String getParentDir() {
        return parentDir;
    }

    public boolean isFolder() {
        return isFolder;
    }
}
