package com.luminous.android.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar timerSeekBar;
    private TextView timerTextView;
    private boolean counterIsActive = false;
    private CountDownTimer countDownTimer;

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
//                Log.d("seekbar", "onProgressChanged: pressed");
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

    public void goClicked(View view) {
//        Log.d("gotClicked", "cliked" + counterIsActive);
        final Button clickedButton = (Button) view;

        if (counterIsActive) {
            updateTimerControl(clickedButton);
        } else {
            clickedButton.setText("STOP");
            int miliSeconds =  (timerSeekBar.getProgress() * 1000) + 100; // 100 is for fixing rounding errors
            switchStatus(false);

            countDownTimer = new CountDownTimer( miliSeconds, 1000) {
                @Override
                public void onTick(long remainingSeconds) {
                    updateTimerUI((int) remainingSeconds / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer bellSoundPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell);
                    bellSoundPlayer.start();
                    updateTimerControl(clickedButton);
                }
            }.start();
        }
    }

    private void updateTimerControl(Button button) {
        int defaultTime = 30;

        button.setText("GO!");
        timerSeekBar.setProgress(defaultTime);
        updateTimerUI(defaultTime);
        switchStatus(true);
        countDownTimer.cancel();
    }

    private void switchStatus(Boolean status) {
        timerSeekBar.setEnabled(status);
        counterIsActive = !status;

//        Log.d("Counter Status", Boolean.toString(counterIsActive));
    }
}