package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert({"INSERT INTO CREDENTIALS (credentialid, url, username, key, password, userid)" +
            "VALUES(#{credentialId}, #{url}, #{userName}, #{key}, #{password}, #{userId})"})
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer addCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId} ORDER BY credentialid ASC")
    List<Credential> getCredentialList(User user);

    @Delete({"DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId} AND userid = #{userId}"})
    boolean deleteCredential(Integer credentialId, Integer userId);

    @Update("UPDATE CREDENTIALS SET " +
            "url = #{url}, " +
            "username = #{userName}, " +
            "key = #{key}, " +
            "password = #{password} " +
            "WHERE credentialid = #{credentialId} and userid = #{userId}")
    Integer editCredential(Credential credential);
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredentialById(Integer credentialId);
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId} AND userid = #{userId}")
    Credential getCredentialByIdAndUsername(Credential credential);
}
