package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    NoteMapper noteMapper;
    @Autowired
    UserService userService;
    public Integer addNote(Note note, Authentication auth){
        User user = userService.getUser((String)auth.getPrincipal());
        note.setUserId(user.getUserId());
        return noteMapper.addNote(note);
    }

    public Integer editNote(Note note, Authentication auth){
        User user = userService.getUser((String)auth.getPrincipal());
        return noteMapper.editNote(note);
    }

    public boolean noteExisted(Note note){
        return noteMapper.getNoteId(note)!=null;
    }

    public List<Note> getNotes(Authentication auth){
        User user = userService.getUser((String)auth.getPrincipal());
        return noteMapper.getNotes(user);
    }

    public boolean deleteNote(Integer noteId, Authentication auth){
        User user = userService.getUser((String)auth.getPrincipal());
        return noteMapper.deleteNote(noteId, user.getUserId());
    }
}
