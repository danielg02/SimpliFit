package com.example.simplifit;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class TimerFragment extends Fragment {
    private TextView countText;
    private FloatingActionButton start;
    private FloatingActionButton restart;
    private FloatingActionButton pause;
    private MaterialProgressBar progressBar;
    private SeekBar seekBar;

    private CountDownTimer timer;
    private long timeLeft;  //in milliseconds
    private boolean running = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        countText = view.findViewById(R.id.textView_countdown);
        start = view.findViewById(R.id.fab_start);
        pause = view.findViewById(R.id.fab_pause);
        restart = view.findViewById(R.id.fab_restart);
        progressBar = view.findViewById(R.id.progress_countdown);
        seekBar = view.findViewById(R.id.seekBar);

        //Setting up the circular progress bar
        progressBar.getProgressDrawable().setColorFilter(Color.rgb(0, 191, 255), android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setMax(seekBar.getMax());
        progressBar.setProgress(seekBar.getMax());

        //Default time will be set to 1 min or 60000 milliseconds
        timeLeft = 60000;
        //Seek bar will have 9 incremented settings
        seekBar.setMax(9);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    setButtonColours(R.color.dark_blue, R.color.dark_blue, R.color.colorDeepSkyBlue);
                }
                restartTimer();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Reset colours of floating action buttons
                setButtonColours(R.color.dark_blue, R.color.dark_blue, R.color.dark_blue);

                //Obtain the minutes left from the seek bar and convert to milliseconds by * by 60000
                //Add 60000 in order to restrict the user from setting it to 0
                timeLeft = seekBar.getProgress() * 60000 + 60000;
                if (timer != null) {
                    timer.cancel();
                }
                running = false;
                updateTimer();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                timeLeft = seekBar.getProgress() * 60000 + 60000;
                if (timer != null) {
                    timer.cancel();
                }
                running = false;
                updateTimer();
            }
        });
        updateTimer();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //Runs when start button is pressed
    public void startTimer() {
        if (!running && timeLeft != 0) {
            setButtonColours(R.color.colorDeepSkyBlue, R.color.dark_blue, R.color.dark_blue);
            running = true;
            timer = new CountDownTimer(timeLeft, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeft = millisUntilFinished;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    //Reset colours of floating action buttons
                    setButtonColours(R.color.dark_blue, R.color.dark_blue, R.color.dark_blue);
                    timeLeft = seekBar.getProgress() * 60000 + 60000;
                    timer.cancel();
                    running = false;
                    updateTimer();
                    restartTimer();
                }
            }.start();
        }
    }

    //Runs when pause button is pressed
    public void pauseTimer() {
        if (running) {
            setButtonColours(R.color.dark_blue, R.color.colorDeepSkyBlue, R.color.dark_blue);
            running = false;
            timer.cancel();
        }
    }

    //Runs when restart button is pressed
    public void restartTimer() {
        if (timer != null) {
            timeLeft = seekBar.getProgress() * 60000 + 60000;
            updateTimer();
            timer.cancel();
            running = false;
        }
    }

    //Updates the timer text and the progress bar
    public void updateTimer() {
        //Time left is converted to minutes:seconds
        int minutes = (int) timeLeft / 60000;
        int seconds = (int) timeLeft % 60000 / 1000;
        String timeText = minutes + ":";

        if (seconds < 10) {
            timeText += "0" + seconds;
        } else {
            timeText += seconds;
        }

        countText.setText(timeText);
        progressBar.setProgress((int) timeLeft);
    }

    //Sets the colours of the action buttons
    public void setButtonColours(int start, int pause, int restart) {
        this.start.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(start))));
        this.pause.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(pause))));
        this.restart.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(restart))));
    }


}