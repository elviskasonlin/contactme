package com.sp.contactme;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ShareActivity extends AppCompatActivity {

    private ImageView qrView;
    private TextView textView;
    private String data;
    private String name;

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

        qrView = (ImageView)findViewById(R.id.qrImageView);
        textView = (TextView)findViewById(R.id.share_profileName);
        data = getIntent().getStringExtra("DATA");
        name = getIntent().getStringExtra("PNAME");

        generateQrCode(data, name);
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

    // Generating QR Code for Sharing
    private void generateQrCode(String data, String name) {
        String contactData = data; // Whatever you need to encode in the QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(contactData, BarcodeFormat.QR_CODE,800,800);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrView.setImageBitmap(bitmap);
            textView.setText(name);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

}
