package carhire.layered.util;

import carhire.layered.dto.UserDto;

public class UserHolder {
    private static UserDto userDto;
    private UserHolder(){}

    public static void setUserDto(UserDto user){
        if((userDto==null) & (user!=null)){
            userDto = user;
        }
    }

    public static void removeCustomerDto(){
        if (userDto!=null){
            userDto = null;
        }
    }

    public static UserDto getUserDto(){
        return (userDto==null)?null:userDto;
    }
}
