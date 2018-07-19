package com.sp.contactme;

import android.os.Bundle;
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
import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class ScanActivity extends AppCompatActivity implements BarcodeRetriever {

    private static final String TAG = "ScanActivity";

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
                // new ContextThemeWrapper to enable special theme

                // Current test : testing vcard parsing and value.
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
                String message = "Code selected : " + closetToClick.displayValue + "\n\nother " +
                        "codes in frame include : \n";
                for (int index = 0; index < barcodeGraphics.size(); index++) {
                    Barcode barcode = barcodeGraphics.get(index).getBarcode();
                    message += (index + 1) + ". " + barcode.displayValue + "\n";
                }
                /*AlertDialog.Builder builder = new AlertDialog.Builder(ScanActivity.this)
                        .setTitle("code retrieved")
                        .setMessage(message);
                builder.show();*/

                Toast.makeText(ScanActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
        // when image is scanned and processed
    }

    @Override
    public void onRetrievedFailed(String reason) {
        // in case of failure
    }

    @Override
    public void onPermissionRequestDenied() {
        // in case permission request is denied
    }

}

/*

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        cameraView = (SurfaceView) findViewById(R.id.scanCameraView);
        scanSuccessIndicatorText = (TextView) findViewById(R.id.scanSuccessIndicatorText);
        qrDetector = new BarcodeDetector()
                .Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource()
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(720,480)
                .setAutoFocusEnabled(true)
                .build;

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        qrDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray qrCodes = detections.getDetectedItems();
                if (qrCodes.size() != 0) {
                    scanSuccessIndicatorText.setText(qrCodes.valueAt(0));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.release();
        qrDetector.release();
    }
 */

/*
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        if (!qrDetector.isOperational()) {
            Log.e("QR Detector cannot be set up.");
        } else {
            startQrDetect();
        }
    }

    void startQrDetect() {
        final SparseArray qrCodes;
        Frame frame = new Frame.Builder().setImageData(ByteBuffer dataBuffer, )


    }
 */
/*
qrDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                // qrCode sparseArray will not change
                final SparseArray qrCodes = detections.getDetectedItems();
                if (qrCodes.size() != 0) {
                    // Using post method for a text view as receive detections do not run on UI thread.
                    scanSuccessIndicatorText.post(new Runnable() {
                        @Override
                        public void run() {
                            scanSuccessIndicatorText.setText(qrCodes.valueAt(0));
                        }
                    });
                }
            }
        });

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    camSource.start(cameraView.getHolder());
                } catch (IOException err) {
                    Log.e("CAM SRC", err.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                camSource.stop();
            }
        });
 */
