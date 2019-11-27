package com.bosca.metadata.services;

import com.bosca.metadata.shared.MetadataDto;
//import org.springframework.security.core.userdetails.UserDetailsService;

public interface MetadataService{
    //MetadataDto loadFileList(String userId);
    MetadataDto uploadRequest(MetadataDto metadataDetails);
    //MetadataDto downloadRequest(String userid, String filename);
    String deleteRequest(String userId, String filename);
    String modifyFileName(String userid, String filename, String newFilename);
}
