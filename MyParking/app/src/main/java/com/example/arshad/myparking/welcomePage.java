package com.example.arshad.myparking;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class welcomePage extends AppCompatActivity {
    private Timer timer;
    private ProgressBar mProgress;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.setProgress(0);

        final long period = 40;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //this repeats every 100ms
                if (i < 40) {
                    mProgress.setProgress(i);
                    i++;
                } else {
                    timer.cancel();
                    Intent homeIntent = new Intent(welcomePage.this, loginActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        }, 0, period);
    }
}
