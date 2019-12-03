package com.bosca.metadata.models;

import java.util.List;

public class ListFolderResponse {

    private List<FileInfoResponse> files;


    public ListFolderResponse(List<FileInfoResponse> files) {
        this.files = files;
    }

    public List<FileInfoResponse> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfoResponse> files) {
        this.files = files;
    }
}
