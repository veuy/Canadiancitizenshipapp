package com.example.canadiancitizenshipapp;

import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.canadiancitizenshipapp.databinding.ActivityChapterDetailBinding;

public class ChapterDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_CONTENT = "extra_content";

    private ActivityChapterDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChapterDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String content = getIntent().getStringExtra(EXTRA_CONTENT);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        binding.collapsingToolbar.setTitle(title);
        
        // Use Html.fromHtml to render bold and bullets correctly
        if (content != null) {
            binding.tvDetailContent.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
        }

        binding.btnDetailQuiz.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(this, QuizActivity.class);
            intent.putExtra(QuizActivity.EXTRA_CHAPTER_TITLE, title);
            startActivity(intent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
