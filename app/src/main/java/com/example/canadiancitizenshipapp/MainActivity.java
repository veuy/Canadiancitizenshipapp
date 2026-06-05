package com.example.canadiancitizenshipapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.canadiancitizenshipapp.databinding.ActivityMainBinding;
import com.example.canadiancitizenshipapp.viewmodel.DashboardViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DashboardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        setupDashboardListeners();
        observeViewModel();
    }

    private void setupDashboardListeners() {
        binding.btnSettings.setOnClickListener(v -> viewModel.onSettingsClicked());
        binding.cardStudy.setOnClickListener(v -> viewModel.onStudyMaterialsClicked());
        binding.cardPractice.setOnClickListener(v -> viewModel.onPracticeExamClicked());
        binding.cardMock.setOnClickListener(v -> viewModel.onMockExamClicked());
    }

    private void observeViewModel() {
        viewModel.getNavigationEvent().observe(this, event -> {
            if (event != null) {
                handleNavigation(event);
                viewModel.clearNavigationEvent();
            }
        });
    }

    private void handleNavigation(String event) {
        switch (event) {
            case "SETTINGS": 
                startActivity(new android.content.Intent(this, SettingsActivity.class));
                break;
            case "STUDY": 
                startActivity(new android.content.Intent(this, StudyActivity.class));
                break;
            case "PRACTICE": 
                startActivity(new android.content.Intent(this, PracticeActivity.class));
                break;
            case "MOCK": 
                android.content.Intent intent = new android.content.Intent(this, PracticeExamActivity.class);
                intent.putExtra("is_mock", true);
                startActivity(intent);
                break;
        }
    }
}
