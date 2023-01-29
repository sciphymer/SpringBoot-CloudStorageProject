package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Insert({"INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)" +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})"})
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    boolean uploadFile(File file);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    File viewFiles(User user);

    @Delete({"DELETE FROM FILES WHERE fileId = #{fileId}"})
    boolean deleteFile(File file);
}
