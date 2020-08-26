package com.luminous.android.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar timerSeekBar = findViewById(R.id.timerSeekBar);
        final TextView timerTextView = findViewById(R.id.timerText);

        timerSeekBar.setMax(720);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int minutes = i / 60;
                int seconds = i - (minutes * 60);
                String minutesString;
                String secondsString;

                if (0 == seconds) {
                    secondsString = seconds + "0";
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

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void goClicked(View view) {

    }
}