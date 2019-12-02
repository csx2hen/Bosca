package com.bosca.metadata.models;

import java.util.List;

public class ListFolderResponse {

    private List<GetFileInfoResponse> files;


    public ListFolderResponse(List<GetFileInfoResponse> files) {
        this.files = files;
    }

    public List<GetFileInfoResponse> getFiles() {
        return files;
    }

    public void setFiles(List<GetFileInfoResponse> files) {
        this.files = files;
    }
}
