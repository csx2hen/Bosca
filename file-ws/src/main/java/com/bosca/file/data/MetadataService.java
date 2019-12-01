package com.bosca.file.data;


import com.bosca.file.models.CreateFileInfoRequest;
import com.bosca.file.models.CreateFileInfoResponse;
import com.bosca.file.models.GetFileInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("metadata-ws")
public interface MetadataService {

    @PostMapping("files")
    public CreateFileInfoResponse createFileInfo(@RequestBody CreateFileInfoRequest fileInfoRequest);

    @GetMapping("files/{fileId}")
    public GetFileInfoResponse getFileInfo(@PathVariable("fileId") String fileId);

    @DeleteMapping("files/{fileId}")
    public void removeFileInfo(@PathVariable("fileId") String fileId);

}
