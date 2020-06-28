package com.example.fittracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class EditWorkout extends Activity {
    private EditText name;
    private Button edit;
    private int workoutID;
    DatabaseHelper db;
    private String wName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_workout);

        name = findViewById(R.id.edit_workout_name);
        edit = findViewById(R.id.complete_edit_workout_button);
        workoutID = getIntent().getIntExtra("workout_id", 0);
        db = new DatabaseHelper(this);
        name.setText(db.getWorkoutName(workoutID));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wName = name.getText().toString();
                db.editWorkout(workoutID, wName);
                Intent intent = new Intent(EditWorkout.this, MainActivity.class);
                overridePendingTransition(0,0);
                finish();
                startActivity(intent);
            }
        });

    }
}
