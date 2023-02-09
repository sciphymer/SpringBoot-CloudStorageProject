package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)" +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer uploadFile(File file);

    @Select("SELECT fileId, filename, contenttype, filesize, userid, NULL FROM FILES WHERE userId = #{userId}")
    List<File> getFileListByUserId(Integer userId);

    @Select("SELECT fileId, filename, contenttype, filesize, userid, NULL FROM FILES WHERE userId = #{userId} AND filename = #{fileName}")
    File getFileByUserIdAndFileName(Integer userId,String fileName);

    @Select("SELECT fileId, filename, contenttype, filesize, userid, NULL FROM FILES WHERE userId = #{userId} AND fileId = #{fileId}")
    File getFileByFileIdAndUserId(Integer userId, Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} AND fileId = #{fileId}")
    File downloadFileByUserIdAndFileId(Integer userId, Integer fileId);

    @Delete({"DELETE FROM FILES WHERE fileId = #{fileId}"})
    boolean deleteFileByFileId(Integer fileId);
}
