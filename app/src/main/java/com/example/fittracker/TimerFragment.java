package com.example.fittracker;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class TimerFragment extends Fragment {
    private TextView countText;
    private FloatingActionButton start;
    private FloatingActionButton restart;
    private FloatingActionButton pause;
    private MaterialProgressBar bar;

    private CountDownTimer timer;
    private int maxTime;
    private long timeLeft = 60000;  //in milliseconds
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
        bar = view.findViewById(R.id.progress_countdown);

        maxTime = (int) timeLeft;

        bar.getProgressDrawable().setColorFilter(Color.rgb(0,191,255), android.graphics.PorterDuff.Mode.SRC_IN);
        bar.setMax(maxTime);
        bar.setProgress(maxTime);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.colorDeepSkyBlue))));
                pause.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.dark_blue))));
                restart.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.dark_blue))));
                startTimer();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.dark_blue))));
                pause.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.colorDeepSkyBlue))));
                restart.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.dark_blue))));
                pauseTimer();
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.dark_blue))));
                pause.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.dark_blue))));
                restart.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.colorDeepSkyBlue))));
                restartTimer();
            }
        });

        updateTimer();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void startTimer() {
        if (!running){
            running = true;
            timer = new CountDownTimer(timeLeft, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeft = millisUntilFinished;
                    bar.setProgress((int)timeLeft);
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    restartTimer();
                }
            }.start();
        }
    }

    public void pauseTimer() {
        if (running){
            running = false;
            timer.cancel();
        }
    }

    public void restartTimer() {
        running = false;
        timer.cancel();
        timeLeft = maxTime;
        bar.setProgress(maxTime);
        updateTimer();
    }

    public void updateTimer() {
        int minutes = (int) timeLeft / 60000;
        int seconds = (int) timeLeft % 60000 / 1000;

        String timeText = minutes + ":";

        if (seconds < 10) {
            timeText += "0" + seconds;
        } else {
            timeText += seconds;
        }
        countText.setText(timeText);
    }


}