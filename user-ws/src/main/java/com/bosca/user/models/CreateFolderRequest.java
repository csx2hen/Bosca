package com.bosca.user.models;

public class CreateFolderRequest {

    private String owner;
    private final String filename = "/";
    private final String parentDir ="";
    private final boolean isFolder = true;

    public CreateFolderRequest(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFilename() {
        return filename;
    }

    public String getParentDir() {
        return parentDir;
    }

    public boolean getIsFolder() {
        return isFolder;
    }
}
