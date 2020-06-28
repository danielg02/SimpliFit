package com.example.fittracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class EditExercise extends Activity {
    private EditText name;
    private EditText sets;
    private EditText reps;
    private EditText weight;
    private Button edit;
    private DatabaseHelper db;
    private String exerciseName;
    private int workoutID;
    private ExerciseItem exercise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_exercise);

        name = findViewById(R.id.edit_exercise_name);
        sets = findViewById(R.id.edit_number_of_sets);
        reps = findViewById(R.id.edit_number_of_reps);
        weight = findViewById(R.id.edit_weight);
        edit = findViewById(R.id.complete_edit_exercise_button);

        db = new DatabaseHelper(this);
        exerciseName = getIntent().getStringExtra("exercise_name");
        workoutID = getIntent().getIntExtra("workout_id", 0);
        exercise = db.getExerciseItem(workoutID, exerciseName);
        setFields();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExerciseItem e = getFieldText();
                db.editExercise(e, workoutID, exerciseName);
                Intent intent = new Intent(EditExercise.this, MainActivity.class);
                overridePendingTransition(0,0);
                finish();
                startActivity(intent);
            }
        });
    }

    private void setFields(){
        name.setText(exercise.getName());
        sets.setText(exercise.getSets());
        reps.setText(exercise.getReps());
        weight.setText(exercise.getWeight());
    }

    private ExerciseItem getFieldText(){
        String eName = name.getText().toString();
        String eSets = sets.getText().toString();
        String eReps = reps.getText().toString();
        String eWeight = weight.getText().toString();
        return new ExerciseItem(eName, eSets, eReps, eWeight);
    }

}
