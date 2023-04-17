package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    FileMapper fileMapper;
    UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public Integer save(MultipartFile fileData, Authentication auth) throws IOException{
        User user = userMapper.getUser((String)auth.getPrincipal());

        try {
            File file = new File(
                    null,
                    fileData.getOriginalFilename(),
                    fileData.getContentType(),
                    String.valueOf(fileData.getSize()),
                    user.getUserId(),
                    fileData.getBytes()
            );
            return fileMapper.uploadFile(file);
        } catch (IOException e) {
            System.out.println("Fail to upload file: " + e.getMessage());
            throw new IOException(e);
        }
    }

    public List<File> getFileList(Authentication auth){
        User user = userMapper.getUser((String)auth.getPrincipal());
        return fileMapper.getFileListByUserId(user.getUserId());
    }

    public Boolean deleteFileByFileIdAndUserId(Authentication auth, Integer fileId) {
        User user = userMapper.getUser((String) auth.getPrincipal());
        Boolean successDeleted = false;
        File file = fileMapper.getFileByFileIdAndUserId(user.getUserId(),fileId);
        if(file!=null) {
            successDeleted = fileMapper.deleteFileByFileId(fileId);
        }
        return successDeleted;
    }

    public File getFileByUserIdAndFileName(Authentication auth, String fileName){
        User user = userMapper.getUser((String)auth.getPrincipal());
        return fileMapper.getFileByUserIdAndFileName(user.getUserId(),fileName);
    }

//    public Boolean deleteFileById (Integer fileId){
//        return fileMapper.deleteFileByFileId(fileId);
//    }

    public File downloadFileByFileIdAndUserId (Authentication auth, Integer fileId){
        User user = userMapper.getUser((String)auth.getPrincipal());
        File file = fileMapper.getFileByFileIdAndUserId(user.getUserId(),fileId);
        if(file!=null) {
            return fileMapper.downloadFileByUserIdAndFileId(user.getUserId(), fileId);
        } else{
            return null;
        }
    }
}
