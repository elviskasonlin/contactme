package com.sp.contactme;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity {
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        mp = MediaPlayer.create(this, R.raw.ding);
        mp.start();

        Thread timer = new Thread() {
            public void run() {
                try { sleep(350); }
                catch (InterruptedException e) { e.printStackTrace(); }
                finally {
                    Intent mainActivity = new Intent(SplashScreen.this,
                            MainActivity.class);
                    startActivity(mainActivity); }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        mp.release();
    }

}
