package carhire.layered.service.custom;

import carhire.layered.dto.UserDto;
import carhire.layered.service.SuperService;

import java.io.IOException;
import java.util.ArrayList;

public interface UserService extends SuperService {
    UserDto getUser(String id, boolean isAll) throws Exception;
    int addUser(UserDto userDto) throws Exception;
    int update(UserDto userDto) throws Exception;
    ArrayList<UserDto> getAllUsers() throws Exception;
    int deleteUser(int id) throws Exception;
    int updateProfile(UserDto userDto) throws Exception;
}
