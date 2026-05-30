package com.example.canadiancitizenshipapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.canadiancitizenshipapp.data.local.Question;
import com.example.canadiancitizenshipapp.databinding.ActivityQuizBinding;
import com.example.canadiancitizenshipapp.viewmodel.QuizViewModel;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    public static final String EXTRA_CHAPTER_TITLE = "extra_chapter_title";
    
    private ActivityQuizBinding binding;
    private QuizViewModel viewModel;
    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private boolean isAnswered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String chapterTitle = getIntent().getStringExtra(EXTRA_CHAPTER_TITLE);
        
        setSupportActionBar(binding.quizToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quiz: " + chapterTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        viewModel.getQuestionsForChapter(chapterTitle).observe(this, questions -> {
            if (questions != null && !questions.isEmpty()) {
                this.questionList = questions;
                displayQuestion();
            } else {
                Toast.makeText(this, "No questions found for this chapter.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        binding.btnSubmit.setOnClickListener(v -> {
            if (!isAnswered) {
                checkAnswer();
            } else {
                nextQuestion();
            }
        });
    }

    private void displayQuestion() {
        isAnswered = false;
        binding.btnSubmit.setText("Submit");
        binding.tvExplanation.setVisibility(View.GONE);
        binding.rgOptions.clearCheck();
        
        // Reset colors
        for (int i = 0; i < binding.rgOptions.getChildCount(); i++) {
            binding.rgOptions.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
            binding.rgOptions.getChildAt(i).setEnabled(true);
        }

        Question q = questionList.get(currentQuestionIndex);
        binding.tvQuestionCount.setText("Question " + (currentQuestionIndex + 1) + " of " + questionList.size());
        binding.quizProgress.setProgress((int) (((float) (currentQuestionIndex + 1) / questionList.size()) * 100));
        binding.tvQuestionText.setText(q.text);

        if (q.isTrueFalse) {
            binding.rbOptionA.setText(q.optionA);
            binding.rbOptionB.setText(q.optionB);
            binding.rbOptionC.setVisibility(View.GONE);
            binding.rbOptionD.setVisibility(View.GONE);
        } else {
            binding.rbOptionA.setText(q.optionA);
            binding.rbOptionB.setText(q.optionB);
            binding.rbOptionC.setText(q.optionC);
            binding.rbOptionD.setText(q.optionD);
            binding.rbOptionC.setVisibility(View.VISIBLE);
            binding.rbOptionD.setVisibility(View.VISIBLE);
        }
    }

    private void checkAnswer() {
        int selectedId = binding.rgOptions.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        isAnswered = true;
        RadioButton selectedRb = findViewById(selectedId);
        String selectedAnswer = selectedRb.getText().toString();
        Question q = questionList.get(currentQuestionIndex);

        // Disable options
        for (int i = 0; i < binding.rgOptions.getChildCount(); i++) {
            binding.rgOptions.getChildAt(i).setEnabled(false);
        }

        if (selectedAnswer.equals(q.correctAnswer)) {
            selectedRb.setBackgroundColor(Color.parseColor("#C8E6C9")); // Light Green
        } else {
            selectedRb.setBackgroundColor(Color.parseColor("#FFCDD2")); // Light Red
            // Highlight correct answer
            highlightCorrectAnswer(q.correctAnswer);
        }

        binding.tvExplanation.setText(q.explanation);
        binding.tvExplanation.setVisibility(View.VISIBLE);
        binding.btnSubmit.setText(currentQuestionIndex == questionList.size() - 1 ? "Finish" : "Next");
    }

    private void highlightCorrectAnswer(String correct) {
        if (binding.rbOptionA.getText().toString().equals(correct)) binding.rbOptionA.setBackgroundColor(Color.parseColor("#C8E6C9"));
        else if (binding.rbOptionB.getText().toString().equals(correct)) binding.rbOptionB.setBackgroundColor(Color.parseColor("#C8E6C9"));
        else if (binding.rbOptionC.getText().toString().equals(correct)) binding.rbOptionC.setBackgroundColor(Color.parseColor("#C8E6C9"));
        else if (binding.rbOptionD.getText().toString().equals(correct)) binding.rbOptionD.setBackgroundColor(Color.parseColor("#C8E6C9"));
    }

    private void nextQuestion() {
        if (currentQuestionIndex < questionList.size() - 1) {
            currentQuestionIndex++;
            displayQuestion();
        } else {
            Toast.makeText(this, "Quiz Completed!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
