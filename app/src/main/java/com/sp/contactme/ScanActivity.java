
package com.sp.contactme;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
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

    private static final String TAG = "ScanActivity";
    // Permission type
    private static final String cameraPermission = Manifest.permission.CAMERA;
    // Permission request code
    private static int REQUEST_CODE_CAMERA = 2;


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
                // Store vcard data into vcard object
                VCard vcard = Ezvcard.parse(barcode.rawValue).first();

                // DEBUG Invoke a dialog with QR Data
                // Current test : testing vcard parsing and value.
                // ```new ContextThemeWrapper```to enable special theme for dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScanActivity.this, R.style.DebugDialogue))
                        .setTitle("DEBUG QR DATA")
                        .setMessage(vcard.getFormattedName().getValue());
                builder.show();
            }
        });

    }

    // For multiple scans
    @Override
    public void onRetrievedMultiple(final Barcode closetToClick, final List<BarcodeGraphic> barcodeGraphics) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < barcodeGraphics.size(); i++) {
                    Barcode barcode = barcodeGraphics.get(i).getBarcode();
                    VCardReader vCardReader = null;
                }
            }
        });
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
        // When the image is scanned and processed.
        Toast.makeText(ScanActivity.this, "onBitmapScanned", Toast.LENGTH_LONG).show();

        //DEBUG
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScanActivity.this, R.style.DebugDialogue))
                .setTitle("onBitmapScanned")
                .setMessage("SparseArray data : " + sparseArray.valueAt(0));
        builder.show();
    }

    @Override
    public void onRetrievedFailed(String reason) {
        // Should retrieving fail
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

    // Validates whether barcode is in vcard format
    private Boolean isDataVcard(Barcode barcode) {
        //Vcard vcard = Ezvcard.validate(barcode);
    }

}

/*

// Own custom code. Gave up halfway. Leave it here in case I need it again.
package com.sp.contactme;

import android.Manifest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

public class ScanActivity extends AppCompatActivity {

    // Permission type
    private static final String cameraPermission = Manifest.permission.CAMERA;
    // Permission request code
    private static int REQUEST_CODE_CAMERA = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // Initialise toolbar
        android.support.v7.widget.Toolbar toolbarMain = findViewById(R.id.toolbarScan);
        setSupportActionBar(toolbarMain);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        startCamera();
    }

    private void startCamera() {
        // Check whether permission is granted
        if (checkPermission(cameraPermission) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(ScanActivity.this, "Permission denied", Toast.LENGTH_LONG).show();

            // Show rationale for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(ScanActivity.this, cameraPermission)) {
                new AlertDialog.Builder(new ContextThemeWrapper(ScanActivity.this, R.style.DebugDialogue))
                        .setTitle("Scan requires access to camera")
                        .setMessage("The QR Code scanner requires access to the camera to operate. Are you sure you do not want to allow this?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(ScanActivity.this, MainActivity.class));
                                Toast.makeText(ScanActivity.this, "Dialog YES", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermission(cameraPermission, REQUEST_CODE_CAMERA);
                                Toast.makeText(ScanActivity.this, "Dialog NO", Toast.LENGTH_LONG).show();

                            }
                        });
            } else {
                requestPermission(cameraPermission, REQUEST_CODE_CAMERA);
            }

        } else {
            //Continue
            Toast.makeText(ScanActivity.this, "Permission granted", Toast.LENGTH_LONG).show();
        }
    }

    private void openCamera() {}

    private int checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(ScanActivity.this, permission);
    }

    // Rmb : Function only allows single permission
    private void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(ScanActivity.this, new String[] {permission}, requestCode);
    }

}
*/
