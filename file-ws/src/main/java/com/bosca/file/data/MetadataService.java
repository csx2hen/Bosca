package com.bosca.file.data;


import com.bosca.file.models.CreateFileInfoRequest;
import com.bosca.file.models.CreateFileInfoResponse;
import com.bosca.file.models.GetFileInfoResponse;
import com.bosca.file.models.UpdateFileInfoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("metadata-ws")
public interface MetadataService {

    @PostMapping("files")
    public CreateFileInfoResponse createFileInfo(@RequestParam("userId") String userId,
                                                 @RequestBody CreateFileInfoRequest request);

    @GetMapping("files/{fileId}")
    public GetFileInfoResponse getFileInfo(@RequestParam("userId") String userId,
                                           @PathVariable("fileId") String fileId);

    @DeleteMapping("files/{fileId}")
    public void removeFileInfo(@RequestParam("userId") String userId,
                               @PathVariable("fileId") String fileId);

    @PutMapping("files/{fileId}")
    public void updateFileInfo(@RequestParam("userId") String userId,
                               @PathVariable("fileId") String fileId,
                               @RequestBody UpdateFileInfoRequest request);

}
