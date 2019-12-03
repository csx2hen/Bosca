package com.bosca.metadata.controllers;


import com.bosca.metadata.models.*;
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


/**
 * This controller contains public API that should be used by users.
 */
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


    /**
     * Create folder. File cannot be upload by this API.
     * @param userId
     * @param request
     * @return fileId of created folder
     */
    @PostMapping("users/{userId}/folders")
    public ResponseEntity<CreateFileInfoResponse> createFolder(@PathVariable("userId") String userId,
                             @RequestBody CreateFileInfoRequest request) {
        FileInfoDto fileInfoDto = modelMapper.map(request, FileInfoDto.class);
        // check if the request is to create a folder
        if (!fileInfoDto.getIsFolder()) {
            // if this API is used to create file return Bad Request
            return ResponseEntity.badRequest().build();
        }
        fileInfoDto.setOwner(userId);
        FileInfoDto createdFileInfo = fileInfoService.createFileInfo(fileInfoDto);
        CreateFileInfoResponse returnValue = modelMapper.map(createdFileInfo, CreateFileInfoResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }


    /**
     * List folder (get contents of the folder).
     * @param userId
     * @param fileId
     * @return array of metadata of files contained in the folder
     */
    @GetMapping("users/{userId}/folders")
    public ResponseEntity<ListFolderResponse> listFolder(@PathVariable("userId") String userId,
                                                         @RequestParam("fileId") String fileId) {
        List<FileInfoDto> fileInfoDtos = fileInfoService.listFolder(fileId);
        List<FileInfoResponse> fileInfoRespons = fileInfoDtos
                .stream()
                .map(fileInfoDto -> modelMapper.map(fileInfoDto, FileInfoResponse.class))
                .collect(Collectors.toList());
        ListFolderResponse returnValue = new ListFolderResponse(fileInfoRespons);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }


    /**
     * Update file or folder.
     * @param userId
     * @param fileId
     * @param fileInfo
     * @return
     */
    @PutMapping("users/{userId}/files")
    public ResponseEntity updateFile(@PathVariable("userId") String userId,
                                     @RequestParam("fileId") String fileId,
                                     @RequestBody UpdateFileInfoRequest fileInfo) {
        FileInfoDto fileInfoDto = modelMapper.map(fileInfo, FileInfoDto.class);
        fileInfoService.updateFileInfo(fileId, fileInfoDto);
        return ResponseEntity.ok().build();
    }


    /**
     * Remove file or folder.
     * @param userId
     * @param fileId
     * @return
     */
    @DeleteMapping("users/{userId}/files")
    public ResponseEntity removeFile(@PathVariable("userId") String userId,
                             @RequestParam("fileId") String fileId) {
        fileInfoService.removeFileInfo(fileId);
        return ResponseEntity.noContent().build();
    }
}
