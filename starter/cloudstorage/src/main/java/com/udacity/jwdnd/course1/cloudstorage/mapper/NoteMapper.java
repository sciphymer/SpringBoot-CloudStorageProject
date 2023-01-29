package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {

    @Insert({"INSERT INTO NOTES (notetitle, notedescription, userid)" +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})"})
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    boolean addNote(Note note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    Note getNote(User user);

    @Update("UPDATE NOTES SET" +
            "notetitle = #{noteTitle}," +
            "notedescription = #{noteDescription}" +
            "WHERE userid = #{userId}")
    Note editNote(Note note);

    @Delete({"DELETE FROM NOTES WHERE noteid = #{noteId}"})
    boolean deleteNote(Note note);
}
