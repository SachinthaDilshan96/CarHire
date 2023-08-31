package carhire.layered.service.custom;

import carhire.layered.dto.UserDto;
import carhire.layered.service.SuperService;

import java.io.IOException;

public interface UserService extends SuperService {
    UserDto getUser(String id) throws Exception;
    int addUser(UserDto userDto) throws Exception;
}
