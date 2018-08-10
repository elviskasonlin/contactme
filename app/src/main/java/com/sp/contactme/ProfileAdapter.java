package com.sp.contactme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileHolder> {
    private Context ctxt;
    private List<Profile> profileList;

    // Initialiser – Profile Adapter
    public ProfileAdapter(Context ctxt, List<Profile> profileList) {
        this.ctxt = ctxt;
        this.profileList = profileList;
    }

    // ViewHolder – Named ProfileHolder (which extends RecyclerView's ViewHolder)
    public class ProfileHolder extends RecyclerView.ViewHolder {
        public TextView profileName;

        // Profile holder (individual items)
        public ProfileHolder(View itemView) {
            super(itemView);
            profileName = (TextView)itemView.findViewById(R.id.txtProfileName);
        }
    }

    // Function – Upon VH creation.
    @Override
    public ProfileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card, parent, false);
        return new ProfileHolder(itemView);
    }

    // Binding – Bind VH named ProfileHolder to View
    @Override
    public void onBindViewHolder(final ProfileHolder holder, int index) {
        Profile profile = profileList.get(index);
        holder.profileName.setText(profile.getProfileName());
    }

    // Function DEFAULT – RecyclerView onAttached
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Function DEFAULT – getItemCount()
    @Override
    public int getItemCount() {
        return profileList.size();
    }

}
