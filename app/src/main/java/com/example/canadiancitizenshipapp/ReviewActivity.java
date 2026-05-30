package com.example.canadiancitizenshipapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.canadiancitizenshipapp.data.local.Question;
import com.example.canadiancitizenshipapp.databinding.ActivityReviewBinding;
import com.example.canadiancitizenshipapp.databinding.ItemReviewBinding;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    public static List<Question> questionList;
    public static List<String> userAnswers;

    private ActivityReviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.reviewToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        binding.rvReview.setLayoutManager(new LinearLayoutManager(this));
        binding.rvReview.setAdapter(new ReviewAdapter());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Question q = questionList.get(position);
            String ans = userAnswers.get(position);

            holder.binding.tvReviewQuestion.setText((position + 1) + ". " + q.text);
            holder.binding.tvReviewAnswer.setText("Your Answer: " + ans);

            if (ans.equals(q.correctAnswer)) {
                holder.binding.tvReviewAnswer.setTextColor(android.graphics.Color.parseColor("#4CAF50"));
                holder.binding.tvReviewCorrect.setVisibility(View.GONE);
            } else {
                holder.binding.tvReviewAnswer.setTextColor(android.graphics.Color.parseColor("#F44336"));
                holder.binding.tvReviewCorrect.setText("Correct Answer: " + q.correctAnswer);
                holder.binding.tvReviewCorrect.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() { return questionList != null ? questionList.size() : 0; }

        class ViewHolder extends RecyclerView.ViewHolder {
            ItemReviewBinding binding;
            ViewHolder(ItemReviewBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
