package com.example.canadiancitizenshipapp.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.example.canadiancitizenshipapp.data.local.Chapter;
import com.example.canadiancitizenshipapp.data.repository.ChapterRepository;
import java.util.List;

public class StudyViewModel extends AndroidViewModel {
    private final ChapterRepository repository;
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>("");
    private final LiveData<List<Chapter>> chapters;

    public StudyViewModel(@NonNull Application application) {
        super(application);
        repository = new ChapterRepository(application);
        chapters = Transformations.switchMap(searchQuery, query -> {
            if (query.isEmpty()) {
                return repository.getAllChapters();
            } else {
                return repository.searchChapters(query);
            }
        });
    }

    public LiveData<List<Chapter>> getChapters() {
        return chapters;
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    public void toggleBookmark(Chapter chapter) {
        chapter.isBookmarked = !chapter.isBookmarked;
        repository.updateChapter(chapter);
    }
}
