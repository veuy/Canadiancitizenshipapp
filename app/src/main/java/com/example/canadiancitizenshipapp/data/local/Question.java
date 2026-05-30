package com.example.canadiancitizenshipapp.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a Citizenship Exam Question.
 * Scalable for chapter-specific quizzes and full exams.
 */
@Entity(tableName = "questions")
public class Question {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String text;
    public String optionA;
    public String optionB;
    public String optionC;
    public String optionD;
    public String correctAnswer;
    public String explanation;
    public String chapterTitle; // Links question to a specific chapter
    public boolean isTrueFalse;

    public Question(String text, String optionA, String optionB, String optionC, String optionD, String correctAnswer, String explanation, String chapterTitle, boolean isTrueFalse) {
        this.text = text;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.chapterTitle = chapterTitle;
        this.isTrueFalse = isTrueFalse;
    }
}
