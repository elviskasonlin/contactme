package com.sp.contactme;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        // Initialise toolbar
        android.support.v7.widget.Toolbar toolbarMain = findViewById(R.id.toolbarShare);
        setSupportActionBar(toolbarMain);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // TOOLBAR
    // For back button on toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
