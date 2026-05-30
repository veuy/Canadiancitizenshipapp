package com.example.canadiancitizenshipapp.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.canadiancitizenshipapp.data.local.Question;
import com.example.canadiancitizenshipapp.data.repository.QuestionRepository;
import java.util.List;

public class QuizViewModel extends AndroidViewModel {
    private final QuestionRepository repository;

    public QuizViewModel(Application application) {
        super(application);
        repository = new QuestionRepository(application);
    }

    public LiveData<List<Question>> getQuestionsForChapter(String chapterTitle) {
        return repository.getQuestionsByChapter(chapterTitle);
    }
}
