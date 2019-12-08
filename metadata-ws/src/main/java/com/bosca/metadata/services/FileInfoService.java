package com.bosca.metadata.services;

import com.bosca.metadata.shared.FileInfoDto;

import java.util.List;

public interface FileInfoService {

    FileInfoDto createFileInfo(FileInfoDto fileInfo);
    FileInfoDto getFileInfo(String fileId);
    void removeFileInfo(String fileId);
    void updateFileInfo(String fileId, FileInfoDto fileInfo);
    List<FileInfoDto> listFolder(String fileId);
}
