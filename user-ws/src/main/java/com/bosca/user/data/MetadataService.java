package com.bosca.user.data;


import com.bosca.user.models.CreateFolderRequest;
import com.bosca.user.models.CreateFolderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("metadata-ws")
public interface MetadataService {

    @PostMapping("files")
    public CreateFolderResponse createFolder(@RequestBody CreateFolderRequest request);

    @DeleteMapping("files/{fileId}")
    public void removeFolder(@PathVariable("fileId") String fileId);

}
