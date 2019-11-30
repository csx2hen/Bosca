package com.bosca.metadata.data;

import org.springframework.data.repository.CrudRepository;

public interface FileInfoRepository extends CrudRepository<FileInfoEntity, Long> {
    FileInfoEntity findByFileId(String fileId);
}
