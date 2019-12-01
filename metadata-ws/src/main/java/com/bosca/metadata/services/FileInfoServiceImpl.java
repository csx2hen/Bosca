package com.bosca.metadata.services;

import com.bosca.metadata.data.FileInfoEntity;
import com.bosca.metadata.data.FileInfoRepository;
import com.bosca.metadata.shared.FileInfoDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class FileInfoServiceImpl implements FileInfoService {

    private FileInfoRepository fileInfoRepository;
    private ModelMapper modelMapper;

    {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Autowired
    public FileInfoServiceImpl(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
    }

    @Override
    public FileInfoDto createFileInfo(FileInfoDto fileInfo) {
        fileInfo.setFileId(UUID.randomUUID().toString());
        FileInfoEntity fileInfoEntity = modelMapper.map(fileInfo, FileInfoEntity.class);
        fileInfoRepository.save(fileInfoEntity);
        return modelMapper.map(fileInfoEntity, FileInfoDto.class);
    }

    @Override
    public FileInfoDto getFileInfo(String fileId) {
        FileInfoEntity fileInfoEntity = fileInfoRepository.findByFileId(fileId);
        if (fileInfoEntity == null) throw new RuntimeException("No such file with fileId: " + fileId);
        return modelMapper.map(fileInfoEntity, FileInfoDto.class);
    }

    @Override
    public void removeFileInfo(String fileId) {
        FileInfoEntity fileInfoEntity = fileInfoRepository.findByFileId(fileId);
        fileInfoRepository.delete(fileInfoEntity);
    }

    @Override
    public void updateFileInfo(String fileId, FileInfoDto fileInfo) {
        FileInfoEntity fileInfoEntity = fileInfoRepository.findByFileId(fileId);
        updateFileInfoHelper(fileInfoEntity, fileInfo);
        fileInfoRepository.save(fileInfoEntity);
    }

    private void updateFileInfoHelper(FileInfoEntity fileInfoEntity, FileInfoDto fileInfo) {
        if (fileInfo.getFilename() != null)
            fileInfoEntity.setFilename(fileInfo.getFilename());
        if (fileInfo.getSize() != 0)
            fileInfoEntity.setSize(fileInfo.getSize());
        if (fileInfo.getCreatedTime() != null)
            fileInfoEntity.setCreatedTime(fileInfo.getCreatedTime());
        if (fileInfo.getLastModifiedTime() != null)
            fileInfoEntity.setLastModifiedTime(fileInfo.getLastModifiedTime());
    }
}
