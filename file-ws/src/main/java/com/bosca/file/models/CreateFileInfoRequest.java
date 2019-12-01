package com.bosca.file.models;

public class CreateFileInfoRequest {

    private String filename;
    private String owner;

    public CreateFileInfoRequest(String filename, String owner) {
        this.filename = filename;
        this.owner = owner;
    }

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
