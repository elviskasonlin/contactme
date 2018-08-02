package com.sp.contactme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class VCardProfileAdapter extends RecyclerView.Adapter<VCardProfileAdapter.ProfileHolder> {
    private Context ctxt;
    private List<Profile> profileList;

    // Constructor
    public VCardProfileAdapter(Context ctxt, List<Profile> profileList) {
        this.ctxt = ctxt;
        this.profileList = profileList;
    }

    // Profile holder
    static class ProfileHolder extends RecyclerView.ViewHolder {
        public TextView profileName;

        public ProfileHolder(View itemView) {
            super(itemView);
            profileName = (TextView)itemView.findViewById(R.id.txtProfileName);
        }
    }

    // Profile holder, onCreateViewHolder
    @Override
    public ProfileHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_card, viewGroup, false);
        return new ProfileHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProfileHolder holder, int index) {
        Profile profile = profileList.get(index);
        holder.profileName.setText(profile.getProfileName());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

}
