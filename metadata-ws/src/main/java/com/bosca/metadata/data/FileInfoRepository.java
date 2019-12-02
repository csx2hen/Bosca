package com.bosca.metadata.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileInfoRepository extends CrudRepository<FileInfoEntity, Long> {
    FileInfoEntity findByFileId(String fileId);
    List<FileInfoEntity> findByOwnerAndParentDir(String owner, String parentDir);

}
