package com.mtrolab.lovetify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.mtrolab.lovetify.R;
import com.mtrolab.lovetify.models.UserProfile;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<UserProfile> profiles = new ArrayList<>();
    private Context context;

    public CardAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        UserProfile profile = profiles.get(position);
        holder.userName.setText(profile.getName());
        holder.userInfo.setText(profile.getAge() + " yaş, " + profile.getInfo());

        Glide.with(context)
                .load(profile.getImageUrl())
                .placeholder(R.drawable.profile_placeholder)
                .error(R.drawable.profile_error)
                .into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    // Profilleri ayarlamak için metod
    public void setProfiles(List<UserProfile> profiles) {
        this.profiles = profiles;
        notifyDataSetChanged();
    }

    // Profil listesini almak için yeni metod
    public List<UserProfile> getProfiles() {
        return profiles;
    }

    public void setCardStackView(CardStackView cardStackView, CardStackLayoutManager manager) {
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName;
        TextView userInfo;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.userName);
            userInfo = itemView.findViewById(R.id.userInfo);
        }
    }
} 