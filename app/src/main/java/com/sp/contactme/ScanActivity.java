
package com.sp.contactme;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.io.text.VCardReader;
import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class ScanActivity extends AppCompatActivity implements BarcodeRetriever {

    // Tag for Log
    private static final String TAG = "ScanActivity";
    // Permission type
    private static final String cameraPermission = Manifest.permission.CAMERA;
    // Permission request code
    private static int REQUEST_CODE_CAMERA = 2;

    private VCardStorageHelper helper = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // Initialise barcode scanner
        BarcodeCapture barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);
        barcodeCapture.setRetrieval(this);
        barcodeCapture.setBarcodeFormat(256);
        barcodeCapture.setSupportMultipleScan(false);
        barcodeCapture.setShowDrawRect(true);

        // Initialise toolbar
        android.support.v7.widget.Toolbar toolbarMain = findViewById(R.id.toolbarScan);
        setSupportActionBar(toolbarMain);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Initialise helper & adapter
        helper = new VCardStorageHelper(this);

    }

    // TOOLBAR
    // For back button on toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // SCANNING VIEW
    // For single scan
    @Override
    public void onRetrieved(final Barcode barcode) {
        Log.d(TAG, "SUCCESS: Barcode Read");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            // Parse data and write only if in vcard format
            final VCard vcard = Ezvcard.parse(barcode.rawValue).first();

            if (vcard != null) {
                String message = vcard.getFormattedName().getValue();
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScanActivity.this, R.style.DebugDialogue))
                        .setTitle("Add this contact to list?")
                        .setMessage(message)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int itemCounter;
                                try {
                                    itemCounter = helper.getItemCount() + 1;
                                } catch (Exception e) {
                                    itemCounter = 0;
                                }
                                String outputData = vcard.write();
                                helper.insert(vcard.getFormattedName().getValue() + " " + Integer.toString(itemCounter), outputData);
                                finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                builder.show();
            }


/*
            // DEBUG Invoke a dialog with QR Data
            // `new ContextThemeWrapper` to enable special theme for dialog box
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScanActivity.this, R.style.DebugDialogue))
                    .setTitle("DEBUG QR DATA")
                    .setMessage(vcard.getFormattedName().getValue());
            builder.show();
*/
            }
        });

    }

    // For multiple scans
    @Override
    public void onRetrievedMultiple(final Barcode closetToClick, final List<BarcodeGraphic> barcodeGraphics) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // For each barcode identified, parse data and write only if in vcard format
                for (int i = 0; i < barcodeGraphics.size(); i++) {
                    Barcode barcode = barcodeGraphics.get(i).getBarcode();
                    final VCard vcard = Ezvcard.parse(barcode.rawValue).first();
                    if (vcard != null) {
                        String message = vcard.getFormattedName().getValue();
                        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScanActivity.this, R.style.DebugDialogue))
                                .setTitle("Add this contact to list?")
                                .setMessage(message)
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int itemCounter;
                                        try {
                                            itemCounter = helper.getItemCount() + 1;
                                        } catch (Exception e) {
                                            itemCounter = 0;
                                        }
                                        String outputData = vcard.write();
                                        helper.insert(vcard.getFormattedName().getValue() + " " + Integer.toString(itemCounter), outputData);
                                        finish();
                                    }
                                })
                                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                        builder.show();
                    }
                }
            }
        });
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
        // UNSURE ABOUT THIS FUNCTION
        // When the image is scanned and processed.
        /*
        Toast.makeText(ScanActivity.this, "onBitmapScanned", Toast.LENGTH_LONG).show();

        //DEBUG
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScanActivity.this, R.style.DebugDialogue))
                .setTitle("onBitmapScanned")
                .setMessage("SparseArray data : " + sparseArray.valueAt(0));
        builder.show();
        */
    }

    @Override
    public void onRetrievedFailed(String reason) {
        // UNSURE ABOUT THIS FUNCTION
        // Should retrieving fail.
        Toast.makeText(ScanActivity.this, "onRetrievedFailed", Toast.LENGTH_LONG).show();

        // DEBUG
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScanActivity.this, R.style.DebugDialogue))
                .setTitle("onRetreivedFailed")
                .setMessage("Reason : " + reason);
        builder.show();
    }

    @Override
    public void onPermissionRequestDenied() {
        // In case permission request for camera is denied, show rationale & appeal to user

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScanActivity.this, R.style.DebugDialogue))
                .setTitle("Scan requires access to camera")
                .setMessage("The QR Code scanner requires access to the camera to operate. Are you sure you do not want to allow this?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Send user back to main screen
                        startActivity(new Intent(ScanActivity.this, MainActivity.class));
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Request permission again
                        requestPermission(cameraPermission, REQUEST_CODE_CAMERA);
                        //Toast.makeText(ScanActivity.this, "Dialog NO", Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    // Rmb : Function only allows single permission
    private void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(ScanActivity.this, new String[] {permission}, requestCode);
    }

}