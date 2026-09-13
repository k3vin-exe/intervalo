package com.devmobile.intervalo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.devmobile.intervalo.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public interface OnPostActionListener {
        void onEdit(Post post);
        void onDelete(Post post);
    }

    private final List<Post> posts;
    private final OnPostActionListener listener;

    public PostAdapter(List<Post> posts, OnPostActionListener listener) {
        this.posts = posts;
        this.listener = listener;
    }


    @Override
    public PostViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.tvAuthorName.setText(post.getAuthorName());
        holder.tvAuthorCourse.setText(post.getAuthorCourse());
        holder.tvPostText.setText(post.getText());
        holder.tvLikes.setText("♡ " + post.getLikes());
        holder.tvComments.setText("▢ " + post.getComments());


        if (post.getAuthorName() != null && !post.getAuthorName().isEmpty()) {
            holder.tvAvatar.setText(post.getAuthorName().substring(0, 1).toUpperCase());
        }

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(post));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(post));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvAvatar, tvAuthorName, tvAuthorCourse, tvPostText, tvLikes, tvComments, btnEdit, btnDelete;

        PostViewHolder(View itemView) {
            super(itemView);
            tvAvatar = itemView.findViewById(R.id.tvAvatar);
            tvAuthorName = itemView.findViewById(R.id.tvAuthorName);
            tvAuthorCourse = itemView.findViewById(R.id.tvAuthorCourse);
            tvPostText = itemView.findViewById(R.id.tvPostText);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvComments = itemView.findViewById(R.id.tvComments);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
