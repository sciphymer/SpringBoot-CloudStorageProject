package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLConnection;
import java.security.Principal;
import java.util.List;
import java.util.Map;



@RequestMapping("/home/file")
@Controller
public class FileController {

    FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/fileUpload")
    public String uploadFile(@RequestParam("fileUpload")MultipartFile fileUpload, Model model, Authentication auth, RedirectAttributes redirectAttributes){

        if(!fileUpload.isEmpty()) {
            try {
                File file = fileService.getFileByUserIdAndFileName(auth,fileUpload.getOriginalFilename());
                if(file==null) {
                    fileService.save(fileUpload, auth);
                    model.addAttribute("msg", "Your file was uploaded. ");
                    model.addAttribute("status", "success");
                } else{
                    model.addAttribute("msg", "Duplicate filenames. Please upload another file or rename the file.");
                    model.addAttribute("status", "error");
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage() + " " + e.getCause());
                model.addAttribute("status", "error");
            } finally{
                model.addAttribute("navType","file");
                return "result";
            }
        } else {
            return "redirect:/home";
        }
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             Authentication auth){

        Boolean successDeleted = fileService.deleteFileByFileIdAndUserId(auth,fileId);

        if(successDeleted){
            model.addAttribute("status","success");
            model.addAttribute("msg","The file is deleted successfully.");
        } else {
            model.addAttribute("status", "error");
            model.addAttribute("msg", "There is some problem, file cannot be found.");
        }
        model.addAttribute("navType","file");
        return "result";
    }

    @GetMapping(value="/download/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> downloadFile(@PathVariable("fileId") Integer fileId,
                                        Model model,
                                        Authentication auth){
        File file = fileService.downloadFileByFileIdAndUserId(auth,fileId);
        if(file != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+file.getFileName()+"\"")
                    .body(file.getFileData());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
