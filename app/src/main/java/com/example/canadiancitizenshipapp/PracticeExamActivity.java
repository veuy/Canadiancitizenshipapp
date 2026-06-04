package com.example.canadiancitizenshipapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.canadiancitizenshipapp.data.local.Question;
import com.example.canadiancitizenshipapp.data.repository.QuestionRepository;
import com.example.canadiancitizenshipapp.databinding.ActivityPracticeExamBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PracticeExamActivity extends AppCompatActivity {
    private ActivityPracticeExamBinding binding;
    private QuestionRepository repository;
    private List<Question> questionList;
    private List<String> userAnswers = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int examIndex;
    private boolean isAnswered = false;
    private boolean isMock = false;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPracticeExamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        examIndex = getIntent().getIntExtra("exam_index", 0);
        isMock = getIntent().getBooleanExtra("is_mock", false);
        repository = new QuestionRepository(getApplication());

        setSupportActionBar(binding.examToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(isMock ? "Mock Exam" : "Exam " + (examIndex + 1));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loadQuestions();
        startTimer();

        if (isMock) {
            binding.btnSubmit.setVisibility(View.GONE);
            binding.rgOptions.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId != -1) {
                    // Slight delay so user can see their selection
                    group.postDelayed(this::checkAnswer, 200);
                }
            });
        } else {
            binding.btnSubmit.setOnClickListener(v -> {
                if (!isAnswered) checkAnswer();
                else nextQuestion();
            });
        }

        binding.btnRetake.setOnClickListener(v -> recreate());
        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnReview.setOnClickListener(v -> {
            ReviewActivity.questionList = questionList;
            ReviewActivity.userAnswers = userAnswers;
            startActivity(new android.content.Intent(this, ReviewActivity.class));
        });
    }

    private void startTimer() {
        timer = new CountDownTimer(30 * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                binding.tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                showResults();
            }
        }.start();
    }

    private void loadQuestions() {
        if (isMock) {
            repository.getRandomMockQuestions(result -> {
                if (result != null) {
                    runOnUiThread(() -> {
                        questionList = result;
                        displayQuestion();
                    });
                }
            });
        } else {
            repository.getPracticeExamQuestions(examIndex, result -> {
                if (result != null) {
                    runOnUiThread(() -> {
                        questionList = result;
                        displayQuestion();
                    });
                }
            });
        }
    }

    private void displayQuestion() {
        isAnswered = false;
        binding.btnSubmit.setText("Submit");
        binding.rgOptions.clearCheck();
        for (int i = 0; i < binding.rgOptions.getChildCount(); i++) {
            binding.rgOptions.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
            binding.rgOptions.getChildAt(i).setEnabled(true);
        }

        Question q = questionList.get(currentQuestionIndex);
        binding.tvQuestionCount.setText("Question " + (currentQuestionIndex + 1) + " of 20");
        binding.examProgress.setProgress((int) (((float) (currentQuestionIndex + 1) / 20) * 100));
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

        RadioButton selectedRb = findViewById(selectedId);
        String selectedAnswer = selectedRb.getText().toString();
        userAnswers.add(selectedAnswer);
        Question q = questionList.get(currentQuestionIndex);

        if (selectedAnswer.equals(q.correctAnswer)) {
            score++;
        }

        if (isMock) {
            // In Mock Exam, go to next question immediately without feedback
            if (currentQuestionIndex < 19) {
                currentQuestionIndex++;
                displayQuestion();
            } else {
                showResults();
            }
        } else {
            // In Practice Exam, show feedback
            isAnswered = true;
            for (int i = 0; i < binding.rgOptions.getChildCount(); i++) binding.rgOptions.getChildAt(i).setEnabled(false);

            if (selectedAnswer.equals(q.correctAnswer)) {
                selectedRb.setBackgroundColor(Color.parseColor("#C8E6C9"));
            } else {
                selectedRb.setBackgroundColor(Color.parseColor("#FFCDD2"));
                highlightCorrectAnswer(q.correctAnswer);
            }
            binding.btnSubmit.setText(currentQuestionIndex == 19 ? "Show Results" : "Next");
        }
    }

    private void highlightCorrectAnswer(String correct) {
        if (binding.rbOptionA.getText().toString().equals(correct)) binding.rbOptionA.setBackgroundColor(Color.parseColor("#C8E6C9"));
        else if (binding.rbOptionB.getText().toString().equals(correct)) binding.rbOptionB.setBackgroundColor(Color.parseColor("#C8E6C9"));
        else if (binding.rbOptionC.getText().toString().equals(correct)) binding.rbOptionC.setBackgroundColor(Color.parseColor("#C8E6C9"));
        else if (binding.rbOptionD.getText().toString().equals(correct)) binding.rbOptionD.setBackgroundColor(Color.parseColor("#C8E6C9"));
    }

    private void nextQuestion() {
        if (currentQuestionIndex < 19) {
            currentQuestionIndex++;
            displayQuestion();
        } else {
            showResults();
        }
    }

    private void showResults() {
        if (timer != null) timer.cancel();
        binding.layoutResult.setVisibility(View.VISIBLE);
        
        // Ensure userAnswers size matches questionList for Review screen
        while (userAnswers.size() < questionList.size()) {
            userAnswers.add("Not Answered");
        }

        binding.tvScore.setText("Score: " + score + "/20");
        
        boolean passed = score >= 15;
        binding.tvStatus.setText(passed ? "PASSED" : "FAILED");
        binding.tvStatus.setTextColor(passed ? Color.parseColor("#4CAF50") : Color.parseColor("#F44336"));

        // Passing logic: Show dashboard button more clearly
        if (passed) {
            if (!isMock) {
                SharedPreferences prefs = getSharedPreferences("practice_prefs", MODE_PRIVATE);
                prefs.edit().putBoolean("exam_passed_" + examIndex, true).apply();
                binding.btnBack.setText("Return to Exams");
            } else {
                binding.btnBack.setText("Return to Dashboard");
            }
            binding.btnRetake.setVisibility(View.GONE);
            binding.btnBack.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#FF0000")));
            binding.btnBack.setTextColor(Color.WHITE);
        } else {
            binding.btnRetake.setVisibility(View.VISIBLE);
            binding.btnBack.setText("Back to Dashboard");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (timer != null) timer.cancel();
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        if (timer != null) timer.cancel();
        super.onDestroy();
    }
}
