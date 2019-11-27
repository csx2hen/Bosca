package com.bosca.metadata.controllers;

import com.bosca.metadata.models.*;
import com.bosca.metadata.models.MetadataDeleteRequestModel;
import com.bosca.metadata.services.MetadataService;
import com.bosca.metadata.shared.MetadataDto;
import models.*;
import com.bosca.metadata.models.MetadataUploadResponseModel;
import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("files")
public class MetadataController {

    private MetadataService metadataService;
    private Environment environment;

    @Autowired
    public MetadataController(MetadataService metadataService, Environment environment) {
        this.metadataService = metadataService;
        this.environment = environment;
    }


    /*// just for test
    @GetMapping
    public String getUsers() {
        return environment.getProperty("token.secret");
    }*/

    @PostMapping
    public ResponseEntity<MetadataUploadResponseModel> createMetadata(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId,
                                                                      @RequestParam("parentPath") String parentPath,@RequestParam("createTime") String createTime,
                                                                      @RequestParam("modifyTime") String modifyTime, @RequestParam("isFolder") String isFolder) throws IOException, JSONException {

        String uri="http://localhost:8081/s3/upload";


        String fileId = UUID.randomUUID().toString();

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", file.getResource());
        params.add("fileId", fileId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params, headers);


        MetadataUploadResponseModel metadataUploadResponseModel = new MetadataUploadResponseModel();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UploadResponseModel> fileIdResponse = restTemplate.postForEntity(uri,httpEntity,UploadResponseModel.class);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        metadataUploadResponseModel.setFileID(fileId);
        metadataUploadResponseModel.setFilename(file.getOriginalFilename());
        metadataUploadResponseModel.setIsExist("1");
        metadataUploadResponseModel.setIsFolder(isFolder);
        metadataUploadResponseModel.setCreateTime(createTime);
        metadataUploadResponseModel.setModifyTime(modifyTime);
        metadataUploadResponseModel.setUserId(userId);
        metadataUploadResponseModel.setParentPath(parentPath);

        MetadataDto metadataDto = modelMapper.map(metadataUploadResponseModel, MetadataDto.class);
        //metadataDto.setFileID(fileId);
        MetadataDto createdMetadata = metadataService.uploadRequest(metadataDto);

        /*MetadataUploadResponseModel returnValue = new MetadataUploadResponseModel();
        returnValue.setCreateTime(createdMetadata.getCreateTime());
        returnValue.setFileID(createdMetadata.getFileId());
        returnValue.setFilename(createdMetadata.getFilename());
        returnValue.setFileType(createdMetadata.getFileType());
        returnValue.setIsExist(createdMetadata.getIsExist());
        returnValue.setIsFolder(createdMetadata.getIsFolder());
        returnValue.setModifyTime(createdMetadata.getModifyTime());
        returnValue.setParentPath(createdMetadata.getParentPath());
        returnValue.setUserId(createdMetadata.getUserId());*/

        return ResponseEntity.status(HttpStatus.CREATED).body(metadataUploadResponseModel);     //?
    }

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void getDownloadInfo(@RequestBody MetadataDownloadRequestModel metadataDownloadRequestModel, HttpServletResponse res) throws IOException {
        String uri="http://localhost:8081/s3/download/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        DownloadRequestModel downloadRequestModel = new DownloadRequestModel();

        downloadRequestModel.setFileID(metadataDownloadRequestModel.getFileId());

        HttpEntity<DownloadRequestModel> entity = new HttpEntity<DownloadRequestModel>(downloadRequestModel,headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DownloadResponseModel> downloadResponse = restTemplate.postForEntity(uri,entity,DownloadResponseModel.class);

        byte[] result = downloadResponse.getBody().getBytes();
        InputStream inputStream = new ByteArrayInputStream(result);
        OutputStream os = new ByteArrayOutputStream();
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = inputStream.read(buf, 0, 1024)) != -1) {
            assert os != null;
            os.write(buf, 0, len);
        }
        assert os != null;
        os.flush();
        if(inputStream != null){
            inputStream.close();
        }
        if(os != null){
            os.close();
        }


        /*byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(Objects.requireNonNull(downloadResponse.getBody()).getInputStream());
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<DeleteResponseModel> getDeleteInfo(@RequestBody MetadataDeleteRequestModel metadataDeleteRequestModel) {

        String fileId = metadataService.deleteRequest(metadataDeleteRequestModel.getUserId(),metadataDeleteRequestModel.getFilename());

        String uri="http://localhost:8081/s3/delete/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        DeleteRequestModel deleteRequestModel = new DeleteRequestModel();
        deleteRequestModel.setFileID(fileId);
        HttpEntity<DeleteRequestModel> entity = new HttpEntity<DeleteRequestModel>(deleteRequestModel,headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DeleteResponseModel> message = restTemplate.postForEntity(uri,entity,DeleteResponseModel.class);
        String deleteMessage = Objects.requireNonNull(message.getBody()).getMessage();

        return message;
        //return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @PostMapping(value="/modifyname")
    public ResponseEntity<ModifyNameResponseModel> modifyName(@RequestBody ModifyNameRequestModel modifyNameRequestModel) {

        String message = metadataService.modifyFileName(modifyNameRequestModel.getUserId(), modifyNameRequestModel.getFilename(), modifyNameRequestModel.getNewFilename());
        ModifyNameResponseModel returnValue = new ModifyNameResponseModel();
        returnValue.setMessage(message);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
