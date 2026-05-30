package com.example.canadiancitizenshipapp.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ChapterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Chapter> chapters);

    @Query("SELECT * FROM chapters")
    LiveData<List<Chapter>> getAllChapters();

    @Query("SELECT * FROM chapters WHERE title LIKE :searchQuery")
    LiveData<List<Chapter>> searchChapters(String searchQuery);

    @Update
    void update(Chapter chapter);

    @Query("SELECT COUNT(*) FROM chapters")
    int getCount();

    @Query("DELETE FROM chapters")
    void deleteAll();
}
