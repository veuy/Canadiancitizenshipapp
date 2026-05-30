package com.example.canadiancitizenshipapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.canadiancitizenshipapp.R;
import com.example.canadiancitizenshipapp.data.local.Chapter;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    private List<Chapter> chapters = new ArrayList<>();
    private final OnChapterClickListener listener;

    public interface OnChapterClickListener {
        void onChapterClick(Chapter chapter);
        void onBookmarkClick(Chapter chapter);
        void onQuizClick(Chapter chapter);
    }

    public ChapterAdapter(OnChapterClickListener listener) {
        this.listener = listener;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        Chapter chapter = chapters.get(position);
        holder.tvTitle.setText(chapter.title);
        holder.tvContent.setText(chapter.content);
        
        int icon = chapter.isBookmarked ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off;
        holder.ivBookmark.setImageResource(icon);
            
        holder.ivBookmark.setOnClickListener(v -> listener.onBookmarkClick(chapter));
        holder.btnQuiz.setOnClickListener(v -> listener.onQuizClick(chapter));
        holder.itemView.setOnClickListener(v -> listener.onChapterClick(chapter));
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    static class ChapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent;
        ImageView ivBookmark;
        MaterialButton btnQuiz;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvChapterTitle);
            tvContent = itemView.findViewById(R.id.tvChapterContent);
            ivBookmark = itemView.findViewById(R.id.ivBookmark);
            btnQuiz = itemView.findViewById(R.id.btnChapterQuiz);
        }
    }
}
