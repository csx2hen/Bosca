package com.bosca.file.models;

public class UploadResponseModel {

    private String fileId;

    public UploadResponseModel(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
