package com.bosca.file.models;

public class UploadResponse {

    private String fileId;

    public UploadResponse(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
