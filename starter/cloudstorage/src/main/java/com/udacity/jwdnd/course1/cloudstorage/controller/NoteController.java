package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/note")
public class NoteController {
    @Autowired
    NoteService noteService;
    @Autowired
    UserService userService;
    @PostMapping("/add")
    public String addNewNote(@ModelAttribute Note note,
                             Model model,
                             Authentication auth){
        boolean noteExisted = noteService.noteExisted(note);
        if (!noteExisted) {
            Integer noteId = noteService.addNote(note, auth);
            if (noteId >= 0) {
                model.addAttribute("msg", "Your note is added. ");
                model.addAttribute("status", "success");
            } else {
                model.addAttribute("msg", "There is error adding your note. Please try again.");
                model.addAttribute("status", "error");
            }
        } else {
            Integer noteId = noteService.editNote(note, auth);
            if (noteId >= 0) {
                model.addAttribute("msg", "Your note is edited. ");
                model.addAttribute("status", "success");
            } else {
                model.addAttribute("msg", "There is error editing your note. Please try again.");
                model.addAttribute("status", "error");
            }
        }
        model.addAttribute("navType","note");
        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Model model, Authentication auth){
        boolean isDeleted = noteService.deleteNote(noteId,auth);
        if(isDeleted){
            model.addAttribute("msg", "Your note is deleted. ");
            model.addAttribute("status", "success");
        } else {
            model.addAttribute("msg", "There is error adding your note. Please try again.");
            model.addAttribute("status", "error");
        }
        model.addAttribute("navType","note");
        return "result";
    }
}
