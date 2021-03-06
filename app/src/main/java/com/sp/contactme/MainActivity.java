package com.sp.contactme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

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

        // CARD SETUP
        CardView cardView = findViewById(R.id.card_view);

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

    // MAIN LAYOUT SHOW
}
