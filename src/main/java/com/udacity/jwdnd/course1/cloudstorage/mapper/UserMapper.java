package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO " +
            "USERS (username, salt,password, firstname, lastname) " +
            "VALUES(#{userName}, #{salt}, #{password}, #{firstName}, #{lastName}) ")
    @Options(useGeneratedKeys = true, keyProperty="userId")
    int createUser(User user);

    @Select("SELECT * FROM USERS WHERE username = #{userName}")
    User getUser(String userName);

    @Delete("DELETE FROM USERS WHERE userid = #{userId}")
    boolean deleteUser(Integer userId);

    @Select("SELECT * FROM USERS")
    List<User> getAllUser();

}
