package com.bosca.file.models;

public class CreateFileInfoRequest {

    private String filename;
    private final boolean isFolder = false;
    private String parentDir;

    public CreateFileInfoRequest(String filename, String parentDir) {
        this.filename = filename;
        this.parentDir = parentDir;
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
