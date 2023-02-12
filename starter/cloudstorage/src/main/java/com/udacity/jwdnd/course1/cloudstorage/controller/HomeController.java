package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@RequestMapping("/home")
@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;
    @Autowired
    NoteService noteService;
    @Autowired
    CredentialService credentialService;

    @GetMapping
    public String homeView(@RequestParam(defaultValue="file") String navType,
                           Authentication auth,
                           Model model,
                           RedirectAttributes redirectAttributes){
        List<File> fileList = fileService.getFileList(auth);
        List<Note> noteList = noteService.getNotes(auth);
        List<Credential> credentialList = credentialService.getDecryptedCredentialList(auth);
        model.addAttribute("fileList", fileList);
        model.addAttribute("notes", noteList);
        model.addAttribute("credentials", credentialList);
        model.addAttribute("navType",navType);
        Map<String, ?> attributes = redirectAttributes.getFlashAttributes();
        return "home";
    }

}
