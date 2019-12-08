package com.bosca.file.models;

public class CreateFileInfoRequest {

    private String owner;
    private String filename;
    private final boolean isFolder = false;
    private String parentDir;

    public CreateFileInfoRequest(String owner, String filename, String parentDir) {
        this.owner = owner;
        this.filename = filename;
        this.parentDir = parentDir;
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

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean getIsFolder() {
        return isFolder;
    }

    public String getParentDir() {
        return parentDir;
    }

    public void setParentDir(String parentDir) {
        this.parentDir = parentDir;
    }
}
