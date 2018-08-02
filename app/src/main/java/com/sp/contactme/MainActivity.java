package com.sp.contactme;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ezvcard.Ezvcard;
import ezvcard.VCard;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VCardProfileAdapter profileAdapter;
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
        profileAdapter = new VCardProfileAdapter(this, profileList);
        helper = new VCardStorageHelper(this);

        // LAYOUT SETUP
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
                startActivity(new Intent(MainActivity.this, EditActivity.class));
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
    private void initList() {
        if (model != null) {
            model.close();
        }

        model = helper.getAll();
    }

}
