package com.sp.contactme;

import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ezvcard.Ezvcard;
import ezvcard.VCard;

public class MainActivity extends AppCompatActivity {

    private VCardStorageHelper helper;
    private Cursor model = null;
    private ListView listView;

    private TextView dataEmptyAlert = null;
    private ProfileAdapter adapter;

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

        // HELPER
        helper = new VCardStorageHelper(this);

        // MAIN LIST
        adapter = new ProfileAdapter(model);
        listView = (ListView) findViewById(R.id.MainListView);
        listView.setAdapter(adapter);

        // MISC
        dataEmptyAlert = findViewById(R.id.DataEmptyAlert);
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
        adapter.swapCursor(model);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initProfileList();

        if (model.getCount() > 0 ) {
            dataEmptyAlert.setVisibility(View.INVISIBLE);
        }
    }

    // ADAPTER FOR LIST VIEW

    class ProfileAdapter extends CursorAdapter {
        ProfileAdapter(Cursor c) {
            super(MainActivity.this, c);
        }

        @Override
        public void bindView(View row, Context context, Cursor c) {
            ProfileHolder holder = (ProfileHolder) row.getTag();
            holder.populateFrom(c, helper);
        }

        @Override
        public View newView(Context context, Cursor c, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.list_row, parent, false);
            final ProfileHolder holder = new ProfileHolder(row);

            // Get needed data
            final String data = helper.getData(c);
            final String profileName = helper.getProfile(c);
            final String profileId = helper.getProfileId(c);
            final VCard vCard = Ezvcard.parse(data).first();

            // CLICK LISTENER - BTN SHARE
            // Put data into intent and pass it to share view for QR generation
            holder.btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "BtnShare Clicked " + vCard.getTelephoneNumbers().get(0).getText(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, ShareActivity.class));
                }
            });

            // CLICK LISTENER - BTN EDIT
            // Put data into intent and pass it to detail view
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "BtnEdit Clicked " +vCard.getOrganization().getValues().get(0).toString(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, EditActivity.class));
                }
            });

            row.setTag(holder);
            return (row);
        }
    }


}
