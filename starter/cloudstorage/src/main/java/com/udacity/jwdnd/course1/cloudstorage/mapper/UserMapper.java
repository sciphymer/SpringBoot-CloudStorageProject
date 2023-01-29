package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO " +
            "USERS (username, salt,password, firstname, lastname)" +
            "VALUES(#{username}, #{salt}, #{password}, #{firstname}, #{lastname}")
    @Options(useGeneratedKeys = true, keyProperty="userId")
    boolean createUser(User user);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Delete("DELETE FROM USERS WHERE userid = #{userId}")
    boolean deleteUser(String username);
}
