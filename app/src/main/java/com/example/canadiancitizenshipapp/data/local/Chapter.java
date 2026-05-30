package com.example.canadiancitizenshipapp.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chapters")
public class Chapter {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String title;
    public String content;
    public boolean isBookmarked;

    public Chapter(String title, String content) {
        this.title = title;
        this.content = content;
        this.isBookmarked = false;
    }
}
