package com.example.canadiancitizenshipapp.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Question> questions);

    @Query("SELECT * FROM questions")
    LiveData<List<Question>> getAllQuestions();

    @Query("SELECT * FROM questions WHERE chapterTitle = :chapterTitle")
    LiveData<List<Question>> getQuestionsByChapter(String chapterTitle);

    @Query("SELECT * FROM questions")
    List<Question> getAllQuestionsList();

    @Query("SELECT COUNT(*) FROM questions")
    int getCount();

    @Query("DELETE FROM questions")
    void deleteAll();
}
