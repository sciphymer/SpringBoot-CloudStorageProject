package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {
    @Insert({"INSERT INTO CREDENTIALS (credentialid, url, username, key, password, userid)" +
            "VALUES(#{credentialId}, #{url}, #{userName}, #{key}, #{password}, #{userId})"})
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    boolean storeCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    Credential viewCredential(User user);

    @Delete({"DELETE FROM CREDENTIAL WHERE credentialId = #{credentialId}"})
    boolean deleteCredential(Credential credential);
}
