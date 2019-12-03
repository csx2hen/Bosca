package com.bosca.metadata.controllers;


import com.bosca.metadata.models.CreateFileInfoRequest;
import com.bosca.metadata.models.CreateFileInfoResponse;
import com.bosca.metadata.models.FileInfoResponse;
import com.bosca.metadata.models.UpdateFileInfoRequest;
import com.bosca.metadata.services.FileInfoService;
import com.bosca.metadata.shared.FileInfoDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * This controller contains all private API for inner user. These API should not be used by users.
 */
@RestController
public class PrivateApiController {

    private FileInfoService fileInfoService;
    private Environment environment;
    private ModelMapper modelMapper;

    {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }


    @Autowired
    public PrivateApiController(FileInfoService fileInfoService, Environment environment) {
        this.fileInfoService = fileInfoService;
        this.environment = environment;
    }


    @PostMapping("files")
    public ResponseEntity<CreateFileInfoResponse>
    createFileInfo(@Valid @RequestBody CreateFileInfoRequest fileInfo) {
        FileInfoDto fileInfoDto = modelMapper.map(fileInfo, FileInfoDto.class);
        FileInfoDto createdFileInfo = fileInfoService.createFileInfo(fileInfoDto);
        CreateFileInfoResponse returnValue = modelMapper.map(createdFileInfo, CreateFileInfoResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }


    @GetMapping("files/{fileId}")
    public ResponseEntity<FileInfoResponse> getFileInfo(@PathVariable("fileId") String fileId) {
        FileInfoDto fileInfo = fileInfoService.getFileInfo(fileId);
        FileInfoResponse returnValue = modelMapper.map(fileInfo, FileInfoResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }


    @DeleteMapping("files/{fileId}")
    public ResponseEntity removeFileInfo(@PathVariable("fileId") String fileId) {
        fileInfoService.removeFileInfo(fileId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("files/{fileId}")
    public ResponseEntity updateFileInfo(@PathVariable("fileId") String fileId,
                                         @RequestBody UpdateFileInfoRequest fileInfo) {
        FileInfoDto fileInfoDto = modelMapper.map(fileInfo, FileInfoDto.class);
        fileInfoService.updateFileInfo(fileId, fileInfoDto);
        return ResponseEntity.ok().build();
    }

}
