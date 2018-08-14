package com.sp.contactme;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.Locale;

public class SplashScreen extends Activity implements TextToSpeech.OnInitListener {
    MediaPlayer mp;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        textToSpeech = new TextToSpeech(this, this);

        mp = MediaPlayer.create(this, R.raw.ding);
        mp.start();

        speakOut();

        Thread timer = new Thread() {
            public void run() {
                try { sleep(450); }
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
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language not supported");
            } else {
                speakOut();
            }
        } else {
            Log.e("TTS", "TTS Initialisation Failure");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        mp.release();
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    private void speakOut() {
        String text = "Contact Me";
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}
