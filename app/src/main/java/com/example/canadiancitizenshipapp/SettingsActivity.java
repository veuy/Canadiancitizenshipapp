package com.example.canadiancitizenshipapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.canadiancitizenshipapp.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);

        setSupportActionBar(binding.settingsToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setupSettings();
    }

    private void setupSettings() {
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);
        binding.switchDarkMode.setChecked(isDarkMode);

        binding.switchDarkMode.setOnClickListener(v -> {
            boolean isChecked = binding.switchDarkMode.isChecked();
            prefs.edit().putBoolean("dark_mode", isChecked).commit();
            
            int desiredMode = isChecked ? 
                    AppCompatDelegate.MODE_NIGHT_YES : 
                    AppCompatDelegate.MODE_NIGHT_NO;

            if (AppCompatDelegate.getDefaultNightMode() != desiredMode) {
                AppCompatDelegate.setDefaultNightMode(desiredMode);
            }
        });

        binding.cardResetProgress.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Reset Progress")
                .setMessage("Are you sure you want to clear all passed exams?")
                .setPositiveButton("Reset", (dialog, which) -> {
                    SharedPreferences practicePrefs = getSharedPreferences("practice_prefs", MODE_PRIVATE);
                    practicePrefs.edit().clear().apply();
                    Toast.makeText(this, "Progress Reset", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
