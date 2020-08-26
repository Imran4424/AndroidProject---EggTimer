package com.luminous.android.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar timerSeekBar;
    private TextView timerTextView;
    private Boolean counterIsActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerText);

        timerSeekBar.setMax(720);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimerUI(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updateTimerUI(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);
        String minutesString;
        String secondsString;

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = Integer.toString(seconds);
        }

        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = Integer.toString(minutes);
        }

        timerTextView.setText(minutesString + ":" + secondsString);
    }

    public void goClicked(final View view) {

        if (counterIsActive) {
            ((Button)view).setText("GO!");
            timerSeekBar.setProgress(30);
            updateTimerUI(30);
            timerSeekBar.setEnabled(true);
        }


        int miliSeconds =  (timerSeekBar.getProgress() * 1000) + 100; // 100 is for fixing rounding errors

        new CountDownTimer( miliSeconds, 1000) {
            @Override
            public void onTick(long remainingSeconds) {
                view.setEnabled(false);
                updateTimerUI((int) remainingSeconds / 1000);
            }

            @Override
            public void onFinish() {
                MediaPlayer bellSoundPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell);
                bellSoundPlayer.start();
                view.setEnabled(true);
            }
        }.start();
    }
}