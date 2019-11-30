package com.bosca.file.services;

import com.amazonaws.util.StringInputStream;

import java.io.InputStream;

public interface FileService {
    void uploadFile(String filename, InputStream fileInputStream);
    InputStream downloadFile(String filename);
    void removeFile(String filename);
}
