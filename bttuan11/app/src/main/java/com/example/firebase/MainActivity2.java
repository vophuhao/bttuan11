package com.example.firebase;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private VideosAdapter videosAdapter;
    private List<VideoModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.vpager2);
        list = new ArrayList<>();
        getVideos();
    }

    private void getVideos() {
        APIService.servieapi.getVideos().enqueue(new Callback<MessageVideoModel>() {
            @Override
            public void onResponse(Call<MessageVideoModel> call, Response<MessageVideoModel> response) {
                list = response.body().getResult();
//Log.d("TAG", list.get(8).getUrl());
                videosAdapter = new VideosAdapter(getApplicationContext(), list);
                viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                viewPager2.setAdapter(videosAdapter);
            }

            @Override
            public void onFailure(Call<MessageVideoModel> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }
}