package com.bosca.metadata.models;

public class CreateFileInfoRequestModel {

    private String filename;
    private String owner;


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
