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
    private Button create;
    private String wName;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_workout1);

        workoutName = findViewById(R.id.workout_name);
        create = findViewById(R.id.create_button);
        dbHelper = new DatabaseHelper(this);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wName = workoutName.getText().toString();
                if (!wName.isEmpty()){
                    dbHelper.insertWorkout(wName);
                    int wID = dbHelper.getID(wName);
                    Intent intent = new Intent(EnterWorkout1.this, EnterWorkout2.class);
                    intent.putExtra("workout_id", wID);
                    finish();
                    overridePendingTransition(0,0);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(EnterWorkout1.this, "Enter Workout Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(EnterWorkout1.this, MainActivity.class);
        finish();
        overridePendingTransition(0,0);
        startActivity(i);
    }
}
