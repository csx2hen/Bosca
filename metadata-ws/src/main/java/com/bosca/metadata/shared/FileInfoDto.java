package com.bosca.metadata.shared;

import java.io.Serializable;

public class FileInfoDto implements Serializable {
    private static final long serialVersionUID = -3823898974277328936L;

    private String fileId;
    private String filename;
    private String owner;


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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
