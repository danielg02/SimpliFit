package com.example.simplifit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class EditWorkout extends Activity {
    private EditText name;
    private Button edit;
    private int workoutID;
    DatabaseHelper db;
    private String wName;
    private String origName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_workout);

        name = findViewById(R.id.edit_workout_name);
        edit = findViewById(R.id.complete_edit_workout_button);

        //Get workoutID and set text with known workout details
        workoutID = getIntent().getIntExtra("workout_id", 0);
        db = new DatabaseHelper(this);
        origName = db.getWorkoutName(workoutID);
        name.setText(origName);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wName = name.getText().toString();
                if (!wName.isEmpty()){
                    if (db.ifWorkoutExists(wName) && !wName.equals(origName)){
                        Toast.makeText(EditWorkout.this, "Workout Already Exists", Toast.LENGTH_SHORT).show();
                    } else {
                        db.editWorkout(workoutID, wName);
                        Intent intent = new Intent(EditWorkout.this, MainActivity.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(EditWorkout.this, "Fill in all data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(EditWorkout.this, MainActivity.class);
        finish();
        overridePendingTransition(0,0);
        startActivity(i);
    }
}
