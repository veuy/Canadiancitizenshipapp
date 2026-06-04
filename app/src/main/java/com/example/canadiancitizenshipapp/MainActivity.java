package com.example.canadiancitizenshipapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.canadiancitizenshipapp.databinding.ActivityMainBinding;
import com.example.canadiancitizenshipapp.viewmodel.DashboardViewModel;

/**
 * Dashboard for the Canadian Citizenship App.
 * Design: Red and White themed (Canadian Flag style).
 * Architecture: Scalable MVVM pattern using ViewBinding and ViewModel.
 * Security: UI logic is separated from data; prepared for secure backend integration.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DashboardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Initialize View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Setup the dashboard interactions
        setupDashboardListeners();
        observeViewModel();
    }

    private void setupDashboardListeners() {
        // Gear icon (Settings) at top left
        binding.btnSettings.setOnClickListener(v -> viewModel.onSettingsClicked());

        // Study Materials Card
        binding.cardStudy.setOnClickListener(v -> viewModel.onStudyMaterialsClicked());

        // Practice Exam Card
        binding.cardPractice.setOnClickListener(v -> viewModel.onPracticeExamClicked());

        // Mock Exam Card
        binding.cardMock.setOnClickListener(v -> viewModel.onMockExamClicked());
    }

    private void observeViewModel() {
        viewModel.getNavigationEvent().observe(this, event -> {
            if (event != null) {
                handleNavigation(event);
            }
        });
    }

    private void handleNavigation(String event) {
        switch (event) {
            case "SETTINGS": showToast("Settings (Offline Mode Active)"); break;
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

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
