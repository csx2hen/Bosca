package com.bosca.metadata.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("file-ws")
public interface FileService {

    @DeleteMapping("files/{fileId}")
    public void removeFile(@PathVariable("fileId") String fileId);

}
