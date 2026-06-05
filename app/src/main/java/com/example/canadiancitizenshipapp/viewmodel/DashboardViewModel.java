package com.example.canadiancitizenshipapp.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.canadiancitizenshipapp.data.local.Question;
import com.example.canadiancitizenshipapp.data.repository.QuestionRepository;
import java.util.List;

/**
 * ViewModel for the Dashboard.
 * Uses AndroidViewModel to access application context for the database.
 */
public class DashboardViewModel extends AndroidViewModel {

    private final QuestionRepository repository;
    private final LiveData<List<Question>> allQuestions;
    private final MutableLiveData<String> _navigationEvent = new MutableLiveData<>();

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        repository = new QuestionRepository(application);
        allQuestions = repository.getAllQuestions();
    }

    public LiveData<String> getNavigationEvent() {
        return _navigationEvent;
    }

    public LiveData<List<Question>> getAllQuestions() {
        return allQuestions;
    }

    public void onStudyMaterialsClicked() {
        _navigationEvent.setValue("STUDY");
    }

    public void onPracticeExamClicked() {
        _navigationEvent.setValue("PRACTICE");
    }

    public void onMockExamClicked() {
        _navigationEvent.setValue("MOCK");
    }

    public void onSettingsClicked() {
        _navigationEvent.setValue("SETTINGS");
    }

    public void clearNavigationEvent() {
        _navigationEvent.setValue(null);
    }
}
