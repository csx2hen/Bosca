package controller;

import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.io.*;

import org.springframework.web.multipart.MultipartFile;
import services.S3Service;
import shared.S3FileDto;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/s3")
public class AmazonController {

    private S3Service s3Service;

    @Autowired
    public AmazonController(){

        this.s3Service = new S3Service();
    }

    @PostMapping(value = "/upload")
    private ResponseEntity<UploadResponseModel> upload(@RequestParam("file") MultipartFile file, @RequestParam("fileId") String fileId){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        S3FileDto s3FileDto = new S3FileDto();
        s3FileDto.setFileID(fileId);
        s3FileDto.setFile(file);
        String fileID = s3Service.upload(s3FileDto);
        UploadResponseModel uploadResponseModel = new UploadResponseModel();
        uploadResponseModel.setFileID(fileID);
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadResponseModel);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    private ResponseEntity<ListResponseModel> listObjects(){
        ListResponseModel listResponseModel = new ListResponseModel();
        listResponseModel.setObjects(s3Service.listObjects());
        return ResponseEntity.status(HttpStatus.CREATED).body(listResponseModel);
    }

    @RequestMapping(value = "/download/", method = RequestMethod.POST)
    private ResponseEntity<DownloadResponseModel> download(@RequestBody DownloadRequestModel downloadRequestModel) throws IOException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        S3FileDto s3FileDto = new S3FileDto();
        s3FileDto.setFileID(downloadRequestModel.getFileID());

        byte[] bytes = s3Service.amazonS3Downloading(s3FileDto);
        DownloadResponseModel downloadResponseModel = new DownloadResponseModel();
        downloadResponseModel.setBytes(bytes);
        return ResponseEntity.status(HttpStatus.CREATED).body(downloadResponseModel);
    }
    @RequestMapping(value = "/delete/", method = RequestMethod.POST)
    private ResponseEntity<DeleteResponseModel> delete(@RequestBody DeleteRequestModel deleteRequestModel) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        S3FileDto s3FileDto = modelMapper.map(deleteRequestModel, S3FileDto.class);
        String message = s3Service.deleteFile(s3FileDto);
        DeleteResponseModel deleteResponseModel = new DeleteResponseModel();
        deleteResponseModel.setMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(deleteResponseModel);
    }
}