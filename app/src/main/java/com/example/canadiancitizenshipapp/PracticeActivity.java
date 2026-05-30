package com.example.canadiancitizenshipapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.canadiancitizenshipapp.data.local.AppDatabase;
import com.example.canadiancitizenshipapp.databinding.ActivityPracticeBinding;
import com.example.canadiancitizenshipapp.databinding.ItemPracticeExamBinding;
import java.util.ArrayList;
import java.util.List;

public class PracticeActivity extends AppCompatActivity {
    private ActivityPracticeBinding binding;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPracticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefs = getSharedPreferences("practice_prefs", MODE_PRIVATE);

        setSupportActionBar(binding.practiceToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            int totalQuestions = AppDatabase.getDatabase(this).questionDao().getCount();
            int examCount = totalQuestions / 20;
            
            runOnUiThread(() -> {
                List<Integer> exams = new ArrayList<>();
                for (int i = 0; i < examCount; i++) exams.add(i);
                
                binding.rvPracticeExams.setLayoutManager(new LinearLayoutManager(this));
                binding.rvPracticeExams.setAdapter(new PracticeAdapter(exams));
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (binding.rvPracticeExams.getAdapter() != null) {
            binding.rvPracticeExams.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    class PracticeAdapter extends RecyclerView.Adapter<PracticeAdapter.ViewHolder> {
        private final List<Integer> examIndices;

        PracticeAdapter(List<Integer> indices) { this.examIndices = indices; }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ItemPracticeExamBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            int index = examIndices.get(position);
            holder.binding.tvExamTitle.setText("Practice Exam " + (index + 1));
            
            boolean isPassed = prefs.getBoolean("exam_passed_" + index, false);
            holder.binding.ivPassedCheck.setVisibility(isPassed ? View.VISIBLE : View.GONE);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(PracticeActivity.this, PracticeExamActivity.class);
                intent.putExtra("exam_index", index);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() { return examIndices.size(); }

        class ViewHolder extends RecyclerView.ViewHolder {
            ItemPracticeExamBinding binding;
            ViewHolder(ItemPracticeExamBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
