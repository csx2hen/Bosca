package com.bosca.file.controllers;

import com.bosca.file.models.UploadResponseModel;
import com.bosca.file.services.FileService;
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

@RestController
@RequestMapping("/files")
public class FileController {

    private Environment environment;
    private FileService fileService;

    @Autowired
    public FileController(FileService fileService, Environment environment) {
        this.fileService = fileService;
        this.environment = environment;
    }

    @PostMapping
    public ResponseEntity<UploadResponseModel> uploadFile(@RequestParam("file") MultipartFile file,
                                                          @RequestParam("userId") String userId) {

        UUID fileId = UUID.randomUUID();
        try {
            fileService.uploadFile(fileId.toString(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        UploadResponseModel returnValue = new UploadResponseModel(fileId.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping("{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, HttpServletRequest request) {
        Resource resource = new InputStreamResource(fileService.downloadFile(fileId));

        String filename = "test.png";
        String contentType = request.getServletContext().getMimeType(filename);

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @DeleteMapping("{fileId}")
    public ResponseEntity removeFile(@PathVariable String fileId) {
        fileService.removeFile(fileId);
        return ResponseEntity.noContent().build();
    }

}
