package com.example.fittracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterWorkout1 extends AppCompatActivity {
    private EditText workoutName;
    private Button next;
    private String wName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_workout1);

        workoutName = findViewById(R.id.workout_name);
        next = findViewById(R.id.next_button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wName = workoutName.getText().toString();

                if (!wName.isEmpty()){
                    Toast.makeText(EnterWorkout1.this, wName, Toast.LENGTH_SHORT).show();    //Testing Purposes
                    Intent intent = new Intent(EnterWorkout1.this, EnterWorkout2.class);
                    intent.putExtra("workout_name", wName);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(EnterWorkout1.this, "Enter Workout Name", Toast.LENGTH_SHORT).show();    //Testing Purposes
                }
            }
        });
    }
}
