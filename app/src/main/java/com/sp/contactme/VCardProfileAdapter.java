package com.sp.contactme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/*


REMEMBER TO CREATE A NEW CLASS
This class named (Profile) has to act as an intermediary between the adapter for a view
and the SQLiteHelper (VCardStorageHelper).

basically something like this

public class Profile {
    private String profile;

    Public Profile(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }
}

 */


public class VCardProfileAdapter extends RecyclerView.Adapter {
    private Context ctxt;
    private List<VCardStorageHelper> profileList;


    public class ProfileHolder extends RecyclerView.ViewHolder {
        public TextView profileName;

        public ProfileHolder(View view) {
            super(view);
            profileName = (TextView) view.findViewById(R.id.txtProfileName);

        }
    }

    public VCardProfileAdapter(Context ctxt, List<VCardStorageHelper> profileList) {
        this.ctxt = ctxt;
        this.profileList = profileList;
    }

    @Override
    public ProfileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_card, parent, false);
        return new ProfileHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProfileHolder holder, int position) {

    }

    /*

    // SAMPLE CODE

    @Override
    public void onBindViewHolder(final ProfileHolder holder, int position) {
        Album album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.count.setText(album.getNumOfSongs() + " songs");

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    // Showing popup menu when tapping on 3 dots
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }



    // Click listener for popup menu items
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    */
}
