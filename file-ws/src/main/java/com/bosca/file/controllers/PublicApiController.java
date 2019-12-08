package com.bosca.file.controllers;

import com.bosca.file.data.MetadataService;
import com.bosca.file.models.*;
import com.bosca.file.services.FileService;
import com.bosca.file.shared.MetadataDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;


/**
 * This controller is mainly for public API of file service
 * (including uploading and downloading file).
 */
@RestController
public class PublicApiController {

    private Environment environment;
    private FileService fileService;
    private MetadataService metadataService;
    private ModelMapper modelMapper;

    {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }


    @Autowired
    public PublicApiController(MetadataService metadataService, FileService fileService, Environment environment) {
        this.metadataService = metadataService;
        this.fileService = fileService;
        this.environment = environment;
    }


    /**
     * Upload file data to cloud.
     * @param file file data
     * @param userId owner's userId
     * @param parentDir
     * @return fileId of uploaded file
     */
    @PostMapping("users/{userId}/files")
    public ResponseEntity<UploadResponse> uploadFile(@RequestParam("file") MultipartFile file,
                                                     @PathVariable("userId") String userId,
                                                     @RequestParam("parentDir") String parentDir) {
        // first create file info on metadata service and retrieve fileId
        CreateFileInfoResponse response = metadataService
                .createFileInfo(new CreateFileInfoRequest(userId, file.getOriginalFilename(), parentDir));
        String fileId = response.getFileId();
        // upload file to cloud using fileService and retrieve metadata on cloud
        // (including: size, createdTime, lastModifiedTime)
        MetadataDto metadata = null;
        try {
            metadata = fileService.uploadFile(fileId, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert metadata != null;
        // upload file info on metadata service using metadata retrieved from cloud
        metadataService.updateFileInfo(fileId, modelMapper.map(metadata, UpdateFileInfoRequest.class));
        // compose return value
        UploadResponse returnValue = new UploadResponse(fileId);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }


    /**
     * Download file data from cloud.
     * @param userId
     * @param fileId
     * @param request
     * @return file data as resource
     */
    @GetMapping("users/{userId}/files")
    public ResponseEntity<Resource> downloadFile(@PathVariable("userId") String userId,
                                                 @RequestParam("fileId") String fileId,
                                                 HttpServletRequest request) {
        // retrieve file info (mainly filename) from metadata service
        GetFileInfoResponse response = metadataService.getFileInfo(fileId);
        String filename = response.getFilename();
        // compose return value
        Resource resource = new InputStreamResource(fileService.downloadFile(fileId));
        String contentType = request.getServletContext().getMimeType(filename);
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

}
