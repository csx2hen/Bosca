package com.bosca.user.data;


import com.bosca.user.models.CreateFolderRequest;
import com.bosca.user.models.CreateFolderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("metadata-ws")
public interface MetadataService {

    @PostMapping("files")
    public CreateFolderResponse createFolder(@RequestParam("userId") String userId,
                                             @RequestBody CreateFolderRequest request);

}
