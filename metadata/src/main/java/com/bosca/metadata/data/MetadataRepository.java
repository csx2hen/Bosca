package com.bosca.metadata.data;

import org.springframework.data.repository.CrudRepository;

public interface MetadataRepository extends CrudRepository<MetadataEntity, Long> {
    MetadataEntity findByFileId(String fileId);
    //ArrayList<MetadataEntity> findByUserId(String userId);
    MetadataEntity findByUserIdAndFilename(String userId, String filename);
}
