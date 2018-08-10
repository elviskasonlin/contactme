package com.sp.contactme;

import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProfileAdapter profileAdapter;
    private List<Profile> profileList;
    private VCardStorageHelper helper;

    private Cursor model = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TOOLBAR SETUP
        // Sets toolbar as appbar for activity
        Toolbar toolbarMain = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbarMain);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        // SETUP
        profileList = new ArrayList<>();
        helper = new VCardStorageHelper(this);

        profileAdapter = new ProfileAdapter(this, profileList);

        // LAYOUT SETUP
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView)findViewById(R.id.main_rv);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(profileAdapter);
    }

    // TOOLBAR
    // Inflate the toolbar for this view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_main, menu);
        return true;
    }

    // TOOLBAR ITEMS
    // On select, change to target views Edit or Scan.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
                break;
            case R.id.action_scan:
                startActivity(new Intent(MainActivity.this, ScanActivity.class));
                break;
            default:
                break;
        }
        return true;
    }

    // INITIALISE DATA POPULATION
    private void initProfileList() {
        if (model != null) {
            model.close();
        }
        model = helper.getAll();

        while(model.moveToNext()) {
            Profile profile = new Profile();
            profile.setProfile(
                    model.getString(model.getColumnIndex(VCardStorageHelper.COLUMN_PROFILE)),
                    model.getString(model.getColumnIndex(VCardStorageHelper.COLUMN_DATA)));
            profileList.add(profile);
        }
        profileAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initProfileList();
    }
}
