package carhire.layered.service.custom.impl;

import carhire.layered.dao.DaoFactory;
import carhire.layered.dao.custom.UserDao;
import carhire.layered.dto.UserDto;
import carhire.layered.entity.UserEntity;
import carhire.layered.service.custom.UserService;
import carhire.layered.util.SessionFactoryConfiguration;
import org.hibernate.Session;


public class UserServiceImpl implements UserService {
    UserDao userDao = (UserDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.USER);
    Session session = SessionFactoryConfiguration.getInstance().getSession();
    @Override
    public UserDto getUser(String id) throws Exception {
        UserEntity userEntity = userDao.get(id,session);
        if (userEntity==null){
            return null;
        }else{
            return new UserDto(
                    userEntity.getUserId(),
                    userEntity.getFirstName(),
                    userEntity.getLastName(),
                    userEntity.getEmail(),
                    userEntity.getPassword(),
                    userEntity.getLevel());
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
                userDto.getLevel());
        int i = userDao.add(userEntity,session);
        return i;
    }
}
