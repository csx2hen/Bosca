package com.bosca.metadata.controllers;


import com.bosca.metadata.models.CreateFileInfoRequestModel;
import com.bosca.metadata.models.CreateFileInfoResponseModel;
import com.bosca.metadata.models.GetFileInfoResponseModel;
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


@RestController
public class MetadataController {

    private FileInfoService fileInfoService;
    private Environment environment;
    private ModelMapper modelMapper;

    {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Autowired
    public MetadataController(FileInfoService fileInfoService, Environment environment) {
        this.fileInfoService = fileInfoService;
        this.environment = environment;
    }

    @PostMapping("files")
    public ResponseEntity<CreateFileInfoResponseModel>
    createFileInfo(@Valid @RequestBody CreateFileInfoRequestModel fileInfo) {
        FileInfoDto fileInfoDto = modelMapper.map(fileInfo, FileInfoDto.class);
        FileInfoDto createdFileInfo = fileInfoService.createFileInfo(fileInfoDto);
        CreateFileInfoResponseModel returnValue = modelMapper.map(createdFileInfo, CreateFileInfoResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping("files/{fileId}")
    public ResponseEntity<GetFileInfoResponseModel> getFileInfo(@PathVariable("fileId") String fileId) {
        FileInfoDto fileInfo = fileInfoService.getFileInfo(fileId);
        GetFileInfoResponseModel returnValue = modelMapper.map(fileInfo, GetFileInfoResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @DeleteMapping("files/{fileId}")
    public ResponseEntity removeFileInfo(@PathVariable("fileId") String fileId) {
        fileInfoService.removeFileInfo(fileId);
        return ResponseEntity.noContent().build();
    }



}
