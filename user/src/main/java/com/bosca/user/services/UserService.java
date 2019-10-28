package com.bosca.user.services;

import com.bosca.user.shared.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDetails);
}
