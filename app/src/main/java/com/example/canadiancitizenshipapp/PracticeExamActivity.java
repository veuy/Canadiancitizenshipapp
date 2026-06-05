package com.example.canadiancitizenshipapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RadioButton;
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
    private final List<String> userAnswers = new ArrayList<>();
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
            public void onFinish() { showResults(); }
        }.start();
    }

    private void loadQuestions() {
        QuestionRepository.RepositoryCallback<List<Question>> callback = result -> {
            if (result != null) {
                runOnUiThread(() -> {
                    questionList = result;
                    displayQuestion();
                });
            }
        };
        if (isMock) repository.getRandomMockQuestions(callback);
        else repository.getPracticeExamQuestions(examIndex, callback);
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
        binding.tvQuestionCount.setText(String.format(Locale.getDefault(), "Question %d of 20", currentQuestionIndex + 1));
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
        if (selectedId == -1) return;

        RadioButton selectedRb = findViewById(selectedId);
        String selectedAnswer = selectedRb.getText().toString();
        userAnswers.add(selectedAnswer);
        Question q = questionList.get(currentQuestionIndex);

        if (selectedAnswer.equals(q.correctAnswer)) score++;

        if (isMock) {
            if (currentQuestionIndex < 19) {
                currentQuestionIndex++;
                displayQuestion();
            } else {
                showResults();
            }
        } else {
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
        while (userAnswers.size() < questionList.size()) userAnswers.add("Not Answered");
        binding.tvScore.setText(String.format(Locale.getDefault(), "Score: %d/20", score));
        boolean passed = score >= 15;
        binding.tvStatus.setText(passed ? "PASSED" : "FAILED");
        binding.tvStatus.setTextColor(passed ? Color.parseColor("#4CAF50") : Color.parseColor("#F44336"));
        if (passed) {
            if (!isMock) {
                SharedPreferences p = getSharedPreferences("practice_prefs", MODE_PRIVATE);
                p.edit().putBoolean("exam_passed_" + examIndex, true).apply();
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
