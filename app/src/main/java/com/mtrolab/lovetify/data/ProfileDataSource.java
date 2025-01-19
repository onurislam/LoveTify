package com.mtrolab.lovetify.data;

import com.mtrolab.lovetify.models.UserProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileDataSource {
    // Test verileri için sabit diziler
    private static final String[] NAMES = {
        "Alexa Cornejo", "Sarah Johnson", "Emma Wilson", "Sophie Brown",
        "Isabella Martinez", "Olivia Davis", "Ava Thompson", "Mia Anderson"
    };

    private static final String[] OCCUPATIONS = {
        "Professional Model", "Photographer", "Artist", "Designer",
        "Software Engineer", "Doctor", "Architect", "Teacher"
    };

    private static final String[] PROFILE_IMAGES = {
        "https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e",
        "https://images.unsplash.com/photo-1494790108377-be9c29b29330",
        "https://images.unsplash.com/photo-1438761681033-6461ffad8d80",
        "https://images.unsplash.com/photo-1517841905240-472988babdf9",
        "https://images.unsplash.com/photo-1534528741775-53994a69daeb",
        "https://images.unsplash.com/photo-1521146764736-56c929d59c83",
        "https://images.unsplash.com/photo-1524504388940-b1c1722653e1",
        "https://images.unsplash.com/photo-1488426862026-3ee34a7d66df"
    };

    // Singleton instance
    private static ProfileDataSource instance;
    
    private ProfileDataSource() {}

    public static ProfileDataSource getInstance() {
        if (instance == null) {
            instance = new ProfileDataSource();
        }
        return instance;
    }

    // Test verileri için profil listesi oluştur
    public List<UserProfile> getTestProfiles() {
        List<UserProfile> profiles = new ArrayList<>();
        for (int i = 0; i < NAMES.length; i++) {
            profiles.add(new UserProfile(
                NAMES[i],
                20 + (int)(Math.random() * 15), // 20-35 yaş arası random
                OCCUPATIONS[i],
                PROFILE_IMAGES[i]
            ));
        }
        return profiles;
    }

    // API'den profilleri çekmek için hazır metod (ileride implement edilecek)
    public interface ProfileCallback {
        void onSuccess(List<UserProfile> profiles);
        void onError(String error);
    }

    public void fetchProfiles(ProfileCallback callback) {
        // TODO: API implementasyonu
        // Şimdilik test verilerini dönüyoruz
        callback.onSuccess(getTestProfiles());
    }

    // Profilleri karıştır
    public List<UserProfile> getShuffledProfiles() {
        List<UserProfile> profiles = getTestProfiles();
        java.util.Collections.shuffle(profiles);
        return profiles;
    }
} 