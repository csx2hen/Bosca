package com.bosca.user.services;

import com.bosca.user.data.MetadataService;
import com.bosca.user.data.UserEntity;
import com.bosca.user.data.UserRepository;
import com.bosca.user.models.CreateFolderRequest;
import com.bosca.user.models.CreateFolderResponse;
import com.bosca.user.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private MetadataService metadataService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Autowired
    public UserServiceImpl(MetadataService metadataService,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.metadataService = metadataService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
        // create root dir for new user on metadata service
        CreateFolderResponse response = metadataService.createFolder(new CreateFolderRequest(userDetails.getUserId()));
        userDetails.setRootDir(response.getFileId());
        // persist user info
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        try {
            userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            metadataService.removeFolder(response.getFileId());
            throw e;
        }
        return modelMapper.map(userEntity, UserDto.class);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(s);
        if (userEntity == null) throw new UsernameNotFoundException(s);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                true, true, true, true,
                new ArrayList<>());
    }


    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        return new ModelMapper().map(userEntity, UserDto.class);
    }


    @Override
    public UserDto getUserDetailsByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) throw new UsernameNotFoundException(userId);
        return new ModelMapper().map(userEntity, UserDto.class);
    }
}
