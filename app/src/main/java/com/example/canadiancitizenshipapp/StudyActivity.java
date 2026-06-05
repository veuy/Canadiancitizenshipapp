package com.example.canadiancitizenshipapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.canadiancitizenshipapp.data.local.Chapter;
import com.example.canadiancitizenshipapp.databinding.ActivityStudyBinding;
import com.example.canadiancitizenshipapp.ui.ChapterAdapter;
import com.example.canadiancitizenshipapp.viewmodel.StudyViewModel;

public class StudyActivity extends AppCompatActivity {

    private ActivityStudyBinding binding;
    private StudyViewModel viewModel;
    private ChapterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(StudyViewModel.class);
        setupRecyclerView();
        setupSearch();
        observeViewModel();
    }

    private void setupRecyclerView() {
        adapter = new ChapterAdapter(new ChapterAdapter.OnChapterClickListener() {
            @Override
            public void onChapterClick(Chapter chapter) {
                android.content.Intent intent = new android.content.Intent(StudyActivity.this, ChapterDetailActivity.class);
                intent.putExtra(ChapterDetailActivity.EXTRA_TITLE, chapter.title);
                intent.putExtra(ChapterDetailActivity.EXTRA_CONTENT, chapter.content);
                startActivity(intent);
            }

            @Override
            public void onBookmarkClick(Chapter chapter) {
                viewModel.toggleBookmark(chapter);
            }

            @Override
            public void onQuizClick(Chapter chapter) {
                android.content.Intent intent = new android.content.Intent(StudyActivity.this, QuizActivity.class);
                intent.putExtra(QuizActivity.EXTRA_CHAPTER_TITLE, chapter.title);
                startActivity(intent);
            }
        });
        binding.rvChapters.setLayoutManager(new LinearLayoutManager(this));
        binding.rvChapters.setAdapter(adapter);
    }

    private void setupSearch() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setSearchQuery(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void observeViewModel() {
        viewModel.getChapters().observe(this, chapters -> {
            if (chapters != null) {
                adapter.setChapters(chapters);
            }
        });
    }
}
