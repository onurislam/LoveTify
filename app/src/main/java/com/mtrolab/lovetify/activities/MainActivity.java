package com.mtrolab.lovetify.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yuyakaido.android.cardstackview.*;
import com.mtrolab.lovetify.R;
import com.mtrolab.lovetify.adapters.CardAdapter;
import com.mtrolab.lovetify.models.UserProfile;
import com.mtrolab.lovetify.data.ProfileDataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MainActivity extends AppCompatActivity implements CardStackListener {
    
    private final WeakHashMap<String, View> viewCache = new WeakHashMap<>();
    private CardStackView cardStackView;
    private CardAdapter adapter;
    private CardStackLayoutManager manager;
    private View emptyStateView;
    
    private static final int ANIMATION_DURATION = 200;
    private static final int ANIMATION_DELAY = 200;
    private static final float INITIAL_SCALE = 0.5f;
    private static final float FINAL_SCALE = 2f;

    private ProfileDataSource profileDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        profileDataSource = ProfileDataSource.getInstance();
        initializeViews();
        setupUI();
    }

    private void initializeViews() {
        viewCache.put("mainLayout", findViewById(R.id.mainLayout));
        cardStackView = findViewById(R.id.cardStackView);
        emptyStateView = findViewById(R.id.emptyStateView);
        if (emptyStateView != null) {
            emptyStateView.setVisibility(View.GONE);
        }
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void setupUI() {
        setupCardStackView();
        setupBottomNavigation();
        loadInitialProfiles();
    }

    private void setupCardStackView() {
        initializeCardStackManager();
        adapter = new CardAdapter(this);
        adapter.setCardStackView(cardStackView, manager);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
    }

    private void initializeCardStackManager() {
        manager = new CardStackLayoutManager(this, this);
        configureCardStackManager();
    }

    private void configureCardStackManager() {
        manager.setStackFrom(StackFrom.Top);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        manager.setOverlayInterpolator(new LinearInterpolator());
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                // Ana sayfa işlemleri
                return true;
            } else if (itemId == R.id.nav_explore) {
                // Keşfet sayfası işlemleri
                return true;
            } else if (itemId == R.id.nav_likes) {
                // Beğeniler sayfası işlemleri
                return true;
            } else if (itemId == R.id.nav_profile) {
                // Profil sayfası işlemleri
                return true;
            }
            return false;
        });
    }

    private void loadInitialProfiles() {
        profileDataSource.fetchProfiles(new ProfileDataSource.ProfileCallback() {
            @Override
            public void onSuccess(List<UserProfile> profiles) {
                adapter.setProfiles(profiles);
            }

            @Override
            public void onError(String error) {
                // Hata durumunda empty state göster
                showEmptyState();
                // TODO: Hata mesajını kullanıcıya göster
            }
        });
    }

    private void showOverlayAnimation(boolean isLike) {
        RelativeLayout mainLayout = (RelativeLayout) viewCache.get("mainLayout");
        if (mainLayout == null) return;

        View overlay = createOverlayView(isLike);
        mainLayout.addView(overlay, createOverlayParams());
        animateOverlay(overlay);
    }

    private View createOverlayView(boolean isLike) {
        View overlay = getLayoutInflater().inflate(R.layout.overlay_view, null);
        ImageView icon = overlay.findViewById(isLike ? R.id.likeOverlay : R.id.dislikeOverlay);
        icon.setVisibility(View.VISIBLE);
        configureInitialOverlayState(overlay);
        return overlay;
    }

    private void configureInitialOverlayState(View overlay) {
        overlay.setAlpha(0f);
        overlay.setScaleX(INITIAL_SCALE);
        overlay.setScaleY(INITIAL_SCALE);
    }

    private RelativeLayout.LayoutParams createOverlayParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        return params;
    }

    private void animateOverlay(View overlay) {
        overlay.animate()
                .alpha(1f)
                .scaleX(FINAL_SCALE)
                .scaleY(FINAL_SCALE)
                .setDuration(ANIMATION_DURATION)
                .withEndAction(() -> fadeOutOverlay(overlay))
                .start();
    }

    private void fadeOutOverlay(View overlay) {
        overlay.animate()
                .alpha(0f)
                .setDuration(ANIMATION_DURATION)
                .setStartDelay(ANIMATION_DELAY)
                .withEndAction(() -> removeOverlay(overlay))
                .start();
    }

    private void removeOverlay(View overlay) {
        RelativeLayout mainLayout = (RelativeLayout) viewCache.get("mainLayout");
        if (mainLayout != null) {
            mainLayout.removeView(overlay);
        }
    }

    private void showEmptyState() {
        if (cardStackView != null && emptyStateView != null) {
            cardStackView.setVisibility(View.GONE);
            emptyStateView.setVisibility(View.VISIBLE);

            // Yeniden yükleme butonu için click listener
            View reloadButton = findViewById(R.id.btnReload);
            if (reloadButton != null) {
                reloadButton.setOnClickListener(v -> reloadProfiles());
            }
        }
    }

    private void reloadProfiles() {
        if (cardStackView != null && emptyStateView != null) {
            // Karıştırılmış profilleri yükle
            adapter.setProfiles(profileDataSource.getShuffledProfiles());
            cardStackView.setVisibility(View.VISIBLE);
            emptyStateView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {}

    @Override
    public void onCardSwiped(Direction direction) {
        if (direction == Direction.Right) {
            // Sağa kaydırma (like) işlemi
            showOverlayAnimation(true);
            
            // Basit bir simülasyon: %30 ihtimalle eşleşme olsun
            //if (Math.random() < 0.3) {
                // Eşleşme oldu, Match aktivitesini başlat
                UserProfile currentProfile = adapter.getProfiles().get(manager.getTopPosition() - 1);
                showMatch(currentProfile);
           // }
        } else if (direction == Direction.Left) {
            showOverlayAnimation(false);
        }

        // Kartlar bittiğinde empty state'i göster
        if (manager != null && adapter != null && 
            manager.getTopPosition() == adapter.getItemCount()) {
            showEmptyState();
        }
    }

    @Override
    public void onCardRewound() {}

    @Override
    public void onCardCanceled() {}

    @Override
    public void onCardAppeared(View view, int position) {}

    @Override
    public void onCardDisappeared(View view, int position) {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewCache.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            // TODO: Ayarlar sayfasına yönlendir
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Yeni eklenen metod
    private void showMatch(UserProfile matchedProfile) {
        Intent matchIntent = new Intent(this, Match.class);
        matchIntent.putExtra("matchImageUrl", matchedProfile.getImageUrl());
        matchIntent.putExtra("userImageUrl", "https://example.com/user_profile.jpg");
        startActivity(matchIntent);
    }
} 