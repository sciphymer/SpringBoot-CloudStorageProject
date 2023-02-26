package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RequestMapping("/home/credential")
@Controller
public class CredentialController {
    @Autowired
    CredentialService credentialService;
    @Autowired
    EncryptionService encryptionService;

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Model model, Authentication auth){
        boolean isDeleted = credentialService.deleteCredential(credentialId, auth);
        if(isDeleted){
            model.addAttribute("msg", "Your credential record is deleted. ");
            model.addAttribute("status", "success");
        } else {
            model.addAttribute("msg", "There is error deleting your credential record. Please try again.");
            model.addAttribute("status", "error");
        }
        model.addAttribute("navType","credential");
        return "result";
    }

    @PostMapping("/add")
    public String addCredential(@ModelAttribute Credential credential, Model model, Authentication auth){
        boolean credentialExisted = credentialService.credentialExisted(credential);
        if (!credentialExisted) {
            Integer noteId = credentialService.addCredential(credential, auth);
            if (noteId >= 0) {
                model.addAttribute("msg", "Your credential record is added. ");
                model.addAttribute("status", "success");
            }
            else {
                model.addAttribute("msg", "There is error adding your credential record. Please try again.");
                model.addAttribute("status", "error");
            }
        } else {
            Integer noteId = credentialService.editCredential(credential, auth);
            if (noteId >= 0) {
                model.addAttribute("msg", "Your credential record is edited. ");
                model.addAttribute("status", "success");
            } else {
                model.addAttribute("msg", "There is error editing your credential record. Please try again.");
                model.addAttribute("status", "error");
            }
        }
        model.addAttribute("navType","credential");
        return "result";
    }

    @PostMapping("/decryptPassword")
    public @ResponseBody String decryptPassword(@RequestParam(name="id") Integer credentialId, Authentication auth) throws UnsupportedEncodingException {
        Credential credential = credentialService.getCredentialById(credentialId,auth);
        String decryptedPassword = "";
        try {
            decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        } catch (UnsupportedEncodingException e){

        } finally {
            return decryptedPassword;
        }
    }
}
