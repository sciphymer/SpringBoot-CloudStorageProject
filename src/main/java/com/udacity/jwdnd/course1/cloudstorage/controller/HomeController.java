package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
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
    @Autowired
    EncryptionService encryptionService;


    @GetMapping
    public String homeView(@RequestParam(defaultValue="file") String navType,
                           Authentication auth,
                           Model model,
                           RedirectAttributes redirectAttributes,
                           HttpSession session){
        List<File> fileList = fileService.getFileList(auth);
        List<Note> noteList = noteService.getNotes(auth);
        List<Credential> credentialList = credentialService.getCredentialList(auth);
        User user = userService.getUser((String)auth.getPrincipal());
        model.addAttribute("fileList", fileList);
        model.addAttribute("notes", noteList);
        model.addAttribute("credentials", credentialList);
        model.addAttribute("navType",navType);
        model.addAttribute("encryptionService",encryptionService);
        model.addAttribute("userFirstName",user.getFirstName());
        Map<String, ?> attributes = redirectAttributes.getFlashAttributes();
        return "home";
    }

}
