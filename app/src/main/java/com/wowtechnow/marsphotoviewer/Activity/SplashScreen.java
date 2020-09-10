package com.wowtechnow.marsphotoviewer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.wowtechnow.marsphotoviewer.R;

import static android.view.View.GONE;

public class SplashScreen extends AppCompatActivity {
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = findViewById(R.id.progress_circular);
        splashScreen splashScreen = new splashScreen();
        splashScreen.start();
    }
    public class splashScreen extends Thread{
        public void run(){
            try {
                sleep(1000*5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}