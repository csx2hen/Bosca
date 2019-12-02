package com.bosca.metadata.controllers;


import com.bosca.metadata.models.CreateFileInfoRequest;
import com.bosca.metadata.models.CreateFileInfoResponse;
import com.bosca.metadata.models.GetFileInfoResponse;
import com.bosca.metadata.models.ListFolderResponse;
import com.bosca.metadata.services.FileInfoService;
import com.bosca.metadata.shared.FileInfoDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class PublicApiController {

    private FileInfoService fileInfoService;
    private Environment environment;
    private ModelMapper modelMapper;

    {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Autowired
    public PublicApiController(FileInfoService fileInfoService, Environment environment) {
        this.fileInfoService = fileInfoService;
        this.environment = environment;
    }


    @PostMapping("users/{userId}/folders")
    public ResponseEntity<CreateFileInfoResponse> createFolder(@PathVariable("userId") String userId,
                             @RequestBody CreateFileInfoRequest request) {
        FileInfoDto fileInfoDto = modelMapper.map(request, FileInfoDto.class);
        fileInfoDto.setOwner(userId);
        fileInfoDto.setFolder(true);
        FileInfoDto createdFileInfo = fileInfoService.createFileInfo(fileInfoDto);
        CreateFileInfoResponse returnValue = modelMapper.map(createdFileInfo, CreateFileInfoResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }


    @GetMapping("users/{userId}/folders")
    public ResponseEntity<ListFolderResponse> listFolder(@PathVariable("userId") String userId,
                                                         @RequestParam("fileId") String fileId) {
        List<FileInfoDto> fileInfoDtos = fileInfoService.listFolder(userId, fileId);
        List<GetFileInfoResponse> getFileInfoResponses = fileInfoDtos
                .stream()
                .map(fileInfoDto -> modelMapper.map(fileInfoDto, GetFileInfoResponse.class))
                .collect(Collectors.toList());
        ListFolderResponse returnValue = new ListFolderResponse(getFileInfoResponses);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }


    @DeleteMapping("users/{userId}/folders")
    public ResponseEntity removeFolder(@PathVariable("userId") String userId,
                             @RequestParam("fileId") String fileId) {
        fileInfoService.removeFileInfo(fileId);
        return ResponseEntity.noContent().build();
    }
}
