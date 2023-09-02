package carhire.layered.service.custom.impl;

import carhire.layered.dao.DaoFactory;
import carhire.layered.dao.custom.UserDao;
import carhire.layered.dto.UserDto;
import carhire.layered.entity.UserEntity;
import carhire.layered.service.custom.UserService;
import carhire.layered.util.SessionFactoryConfiguration;
import org.hibernate.Session;

import java.util.ArrayList;


public class UserServiceImpl implements UserService {
    UserDao userDao = (UserDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.USER);
    Session session = SessionFactoryConfiguration.getInstance().getSession();
    @Override
    public UserDto getUser(String id,boolean isAll) throws Exception {
        UserEntity userEntity = userDao.get(id,session,isAll);
        if (userEntity==null){
            return null;
        }else{
            return new UserDto(
                    userEntity.getUserId(),
                    userEntity.getFirstName(),
                    userEntity.getLastName(),
                    userEntity.getEmail(),
                    userEntity.getPassword(),
                    userEntity.getLevel(),
                    userEntity.getStatus());
        }
    }

    @Override
    public int addUser(UserDto userDto) throws Exception {
        UserEntity userEntity = new UserEntity(
                0,
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getLevel(),
                userDto.getStatus());
        int i = userDao.add(userEntity,session);
        return i;
    }

    @Override
    public int update(UserDto userDto) throws Exception {
        UserEntity userEntity = new UserEntity(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getLevel(),
                userDto.getStatus()
        );
        int i = userDao.update("",userEntity,session);
        return i;
    }

    @Override
    public ArrayList<UserDto> getAllUsers() throws Exception {
        ArrayList<UserEntity> userEntities = userDao.getAll(session);
        ArrayList<UserDto> users = new ArrayList<>();
        for (UserEntity u:userEntities) {
            users.add(new UserDto(
                    u.getUserId(),
                    u.getFirstName(),
                    u.getLastName(),
                    u.getEmail(),
                    u.getPassword(),
                    u.getLevel(),
                    u.getStatus()
            ));
        }
        return users;
    }

    @Override
    public int deleteUser(int id) throws Exception {
        int i = userDao.delete(id,session);
        return i;
    }

    @Override
    public int updateProfile(UserDto userDto) throws Exception {
        return userDao.updateProfile(new UserEntity(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getLevel(),
                userDto.getStatus()),session);
    }


}
