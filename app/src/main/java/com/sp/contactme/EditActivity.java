package com.sp.contactme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.TimeoutException;

public class EditActivity extends AppCompatActivity {
    String profileId;
    String profileName;
    String data;
    VCardStorageHelper helper = null;

    EditText edit_profileName;
    TextView edit_dataDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // TOOLBAR SETUP
        // Sets toolbar as appbar for activity
        Toolbar toolbarMain = findViewById(R.id.toolbarEdit);
        setSupportActionBar(toolbarMain);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        helper = new VCardStorageHelper(this);
        edit_profileName = (EditText) findViewById(R.id.edit_profileNameField);
        edit_dataDisplay = (TextView) findViewById(R.id.edit_contactData);

        profileId = getIntent().getStringExtra("PID");
        data = getIntent().getStringExtra("DATA");
        profileName = getIntent().getStringExtra("PNAME");

        edit_profileName.setText(profileName, TextView.BufferType.EDITABLE);
        edit_dataDisplay.setText(data);

    }

    // TOOLBAR
    // Inflate the toolbar for this view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                updateRecords();
                finish();
                break;
            case R.id.action_delete:
                helper.delete(profileId);
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    private void updateRecords() {
        String newProfileName = edit_profileName.getText().toString();
        helper.updateAtId(profileId, newProfileName, data);
    }
}
