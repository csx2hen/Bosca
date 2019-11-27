package com.bosca.metadata.models;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

//import javax.validation.constraints.Size;

public class MetadataUploadRequestModel {

    private String fileId;

    @NotNull(message = "user id cannot be null")
    private String userId;

    @NotNull(message = "file name cannot be null")
    private String filename;

    private String parentPath;

    private String createTime;
    private String modifyTime;

    @NotNull(message = "is folder cannot be null")
    private String isFolder;

    //@NotNull(message = "file cannot be null")
    //private MultipartFile file;

    public String getFileId() {
        return fileId;
    }

    public void setFileID(String fileId) {
        this.fileId = fileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(String isFolder) {
        this.isFolder = isFolder;
    }
}
