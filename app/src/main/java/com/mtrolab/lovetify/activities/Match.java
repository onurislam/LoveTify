package com.mtrolab.lovetify.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mtrolab.lovetify.R;

public class Match extends AppCompatActivity {
    private ImageView profileImage1;
    private ImageView profileImage2;
    private TextView matchText;
    private Button messageButton;
    private Button keepSwipingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        initializeViews();
        loadImages();
        setupClickListeners();
    }

    private void initializeViews() {
        profileImage1 = findViewById(R.id.profileImage1);
        profileImage2 = findViewById(R.id.profileImage2);
        matchText = findViewById(R.id.matchText);
        messageButton = findViewById(R.id.messageButton);
        keepSwipingButton = findViewById(R.id.keepSwipingButton);
    }

    private void loadImages() {
        String userImageUrl = getIntent().getStringExtra("userImageUrl");
        String matchImageUrl = getIntent().getStringExtra("matchImageUrl");

        // Basit Glide kullanımı
        Glide.with(this)
            .load(userImageUrl)
            .circleCrop()
            .placeholder(R.drawable.profile_placeholder)
            .into(profileImage1);

        Glide.with(this)
            .load(matchImageUrl)
            .circleCrop()
            .placeholder(R.drawable.profile_placeholder)
            .into(profileImage2);

        // Basit fade-in animasyonu
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeIn.setDuration(500);
        profileImage1.startAnimation(fadeIn);
        profileImage2.startAnimation(fadeIn);
        matchText.startAnimation(fadeIn);
    }

    private void setupClickListeners() {
        messageButton.setOnClickListener(v -> {
            Toast.makeText(this, "Mesajlaşma özelliği yakında aktif olacak!", Toast.LENGTH_SHORT).show();
            finish();
        });

        keepSwipingButton.setOnClickListener(v -> finish());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
} 