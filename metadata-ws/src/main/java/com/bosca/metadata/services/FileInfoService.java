package com.bosca.metadata.services;

import com.bosca.metadata.shared.FileInfoDto;

public interface FileInfoService {

    FileInfoDto createFileInfo(FileInfoDto fileInfo);
    FileInfoDto getFileInfo(String fileId);
    void removeFileInfo(String fileId);
    void updateFileInfo(String fileId, FileInfoDto fileInfo);

}
