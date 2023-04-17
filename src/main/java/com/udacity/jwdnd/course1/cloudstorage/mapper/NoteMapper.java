package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert({"INSERT INTO NOTES (notetitle, notedescription, userid)" +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})"})
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer addNote(Note note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId} ORDER BY noteid ASC")
    List<Note> getNotes(User user);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Note getNoteId(Note note);

    @Update("UPDATE NOTES SET " +
            "notetitle = #{noteTitle}, " +
            "notedescription = #{noteDescription} " +
            "WHERE userid = #{userId} and noteid = #{noteId}")
    Integer editNote(Note note);

    @Delete({"DELETE FROM NOTES WHERE noteid = #{noteId} AND userid = #{userId}"})
    boolean deleteNote(Integer noteId, Integer userId);
}
