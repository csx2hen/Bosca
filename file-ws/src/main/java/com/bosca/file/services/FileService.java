package com.bosca.file.services;

import com.amazonaws.util.StringInputStream;

import java.io.InputStream;

public interface FileService {
    void uploadFile(String fileName, InputStream fileInputStream);
    InputStream downloadFile(String fileName);
    void removeFile(String fileName);
}
