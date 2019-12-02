package com.bosca.file.services;

import com.bosca.file.shared.MetadataDto;

import java.io.InputStream;

public interface FileService {
    MetadataDto uploadFile(String filename, InputStream fileInputStream);
    InputStream downloadFile(String filename);
    void removeFile(String filename);
}
