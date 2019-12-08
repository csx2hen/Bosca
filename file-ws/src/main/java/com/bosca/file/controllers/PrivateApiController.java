package com.bosca.file.controllers;


import com.bosca.file.data.MetadataService;
import com.bosca.file.services.FileService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * This controller is mainly for private API of file service
 * (including removing file data).
 */
@RestController
public class PrivateApiController {

    private Environment environment;
    private FileService fileService;
    private ModelMapper modelMapper;

    {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Autowired
    public PrivateApiController(FileService fileService, Environment environment) {
        this.fileService = fileService;
        this.environment = environment;
    }


    /**
     * Remove file data from cloud.
     * @param fileId
     * @return request result
     */
    @DeleteMapping("files/{fileId}")
    public ResponseEntity removeFile(@PathVariable("fileId") String fileId) {
        fileService.removeFile(fileId);
        return ResponseEntity.noContent().build();
    }
}
