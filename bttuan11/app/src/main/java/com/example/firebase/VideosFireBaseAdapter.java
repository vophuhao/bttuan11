package com.example.firebase;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class VideosFireBaseAdapter extends FirebaseRecyclerAdapter<Video1Model, VideosFireBaseAdapter.MyHolder> {
    public VideosFireBaseAdapter(@NonNull FirebaseRecyclerOptions<Video1Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull Video1Model model) {
        String videoKey = getRef(position).getKey(); // Lấy key của video từ Firebase
        holder.textVideoDesc.setText(model.getDesc());
        holder.textVideoTitle.setText(model.getTitle());
        holder.tvEmail.setText(model.getEmail());
        holder.progressBar.setVisibility(View.VISIBLE);
        DatabaseReference likeRef = FirebaseDatabase.getInstance().getReference("likes");
        Uri videoUri = Uri.parse(model.getUrl());

        holder.videoView.setVideoURI(videoUri);

        Glide.with(holder.imPerson.getContext())
                .load(model.getAvatarUrl())
                .placeholder(R.drawable.ic_person_pin)
                .into(holder.imPerson);

        holder.videoView.setOnPreparedListener(mp -> {
            holder.progressBar.setVisibility(View.GONE);

            mp.setLooping(true); // Lặp video thay vì dùng onCompletion
            mp.start();

            // Fix scale video đúng tỷ lệ màn hình
            float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
            float screenRatio = holder.videoView.getWidth() / (float) holder.videoView.getHeight();
            float scale = videoRatio / screenRatio;

            if (scale >= 1f) {
                holder.videoView.setScaleX(scale);
                holder.videoView.setScaleY(1f);
            } else {
                holder.videoView.setScaleX(1f);
                holder.videoView.setScaleY(1f / scale);
            }
        });

        holder.videoView.setOnErrorListener((mp, what, extra) -> {
            holder.progressBar.setVisibility(View.GONE);
            return true; // Đã xử lý lỗi
        });

        /*holder.favorites.setOnClickListener(v -> {
            boolean isFav = (boolean) holder.favorites.getTag();
            if (!isFav) {
                holder.favorites.setImageResource(R.drawable.ic_fill_favorite);
                holder.favorites.setTag(true);
            } else {
                holder.favorites.setImageResource(R.drawable.ic_favorite);
                holder.favorites.setTag(false);
            }
        });

        // Gán mặc định icon favorite ban đầu và trạng thái false
        holder.favorites.setImageResource(R.drawable.ic_favorite);
        holder.favorites.setTag(false);*/
        holder.txtLikeCount.setText(String.valueOf(model.getLikeCount()));
        handleLikes(holder, model, videoKey);

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_row, parent, false);
        return new MyHolder(view);
    }
    public static class MyHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        ProgressBar progressBar;
        TextView textVideoTitle, textVideoDesc, tvEmail, txtLikeCount;
        ImageView imPerson, favorites, imShare, imMore;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            progressBar = itemView.findViewById(R.id.videoProgressBar);
            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);
            textVideoDesc = itemView.findViewById(R.id.textVideoDescription);
            imPerson = itemView.findViewById(R.id.imPerson);
            favorites = itemView.findViewById(R.id.favorites);
            imShare = itemView.findViewById(R.id.imShare);
            imMore = itemView.findViewById(R.id.imMore);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            txtLikeCount = itemView.findViewById(R.id.txtLikeCount);
        }
    }
    private void handleLikes(@NonNull MyHolder holder, @NonNull Video1Model model, String videoKey) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Lấy ID user hiện tại

        // Hiển thị số lượt thích ban đầu
        holder.txtLikeCount.setText(String.valueOf(model.getLikeCount()));

        // Truy vấn xem user hiện tại đã like chưa
        DatabaseReference likeRef = FirebaseDatabase.getInstance().getReference("likes").child(videoKey);
        likeRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean liked = snapshot.exists();
                if (liked) {
                    holder.favorites.setImageResource(R.drawable.ic_fill_favorite);
                    holder.favorites.setTag(true);
                } else {
                    holder.favorites.setImageResource(R.drawable.ic_favorite);
                    holder.favorites.setTag(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Xử lý khi nhấn nút like
        holder.favorites.setOnClickListener(v -> {
            boolean isLiked = (boolean) holder.favorites.getTag();
            DatabaseReference videoRef = FirebaseDatabase.getInstance().getReference("videos").child(videoKey);

            if (!isLiked) {
                // Thêm like
                likeRef.child(userId).setValue(true);
                videoRef.child("likeCount").setValue(model.getLikeCount() + 1);
                model.setLikeCount(model.getLikeCount() + 1);
                holder.favorites.setImageResource(R.drawable.ic_fill_favorite);
                holder.txtLikeCount.setText(String.valueOf(model.getLikeCount()));
                holder.favorites.setTag(true);
            } else {
                // Bỏ like
                likeRef.child(userId).removeValue();
                int newCount = Math.max(0, model.getLikeCount() - 1);
                videoRef.child("likeCount").setValue(newCount);
                model.setLikeCount(newCount);
                holder.favorites.setImageResource(R.drawable.ic_favorite);
                holder.txtLikeCount.setText(String.valueOf(newCount));
                holder.favorites.setTag(false);
            }
        });
    }
}