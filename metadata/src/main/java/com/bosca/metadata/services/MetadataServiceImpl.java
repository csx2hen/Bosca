package com.bosca.metadata.services;

import com.bosca.metadata.data.MetadataEntity;
import com.bosca.metadata.data.MetadataRepository;
import com.bosca.metadata.shared.MetadataDto;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
//import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;

//import java.util.ArrayList;
import java.util.UUID;

@Service
public class MetadataServiceImpl implements MetadataService {

    private MetadataRepository metadataRepository;

    @Autowired
    public MetadataServiceImpl(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }


    @Override
    public MetadataDto uploadRequest(MetadataDto metadataDetails) {

        //metadataDetails.setFileID(UUID.randomUUID().toString()+metadataDetails.getFilename());
        metadataDetails.setIsExist("1");
        //metadataDetails.setCreateTime();  //获取时间稍后，测试可以带数据
        //metadataDetails.setModifyTime();
        //metadataDetails.setFilename();
        //metadataDetails.setFileType();
        //metadataDetails.setIsFolder();
        //metadataDetails.setUserId();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        MetadataEntity metadataEntity = modelMapper.map(metadataDetails, MetadataEntity.class);
        metadataRepository.save(metadataEntity);

        return modelMapper.map(metadataEntity, MetadataDto.class);
    }

/*    public MetadataDto downloadRequest(String userId, String filename){
        MetadataEntity metadataEntity = metadataRepository.findByUserIdAndFilename(userId, filename);

        ModelMapper modelMapper = new ModelMapper();
        //modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper.map(metadataEntity, MetadataDto.class);
    }*/

    public String deleteRequest(String userId, String filename){
        MetadataEntity metadataEntity = metadataRepository.findByUserIdAndFilename(userId, filename);
        if(metadataEntity == null)
            System.out.println("NULL!!");
        metadataEntity.setIsExist("0");
        metadataRepository.save(metadataEntity);
        return metadataEntity.getFileId();
    }

    public String modifyFileName(String userId, String filename, String newFilename){
        MetadataEntity metadataEntity = metadataRepository.findByUserIdAndFilename(userId, filename);
        metadataEntity.setFilename(newFilename);
        metadataRepository.save(metadataEntity);
        return "OK";
    }

}
