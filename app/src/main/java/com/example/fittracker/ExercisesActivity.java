package com.example.fittracker;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ExercisesActivity extends Activity {

    private List<ExerciseItem> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        int workoutID = getIntent().getIntExtra("workout_id", 0);
        exercises = new ArrayList<>();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<String> exerciseNames = dbHelper.getExercise(workoutID);
        List<String> sets = dbHelper.getSets(workoutID);
        List<String> reps = dbHelper.getReps(workoutID);
        List<String> weight = dbHelper.getWeight(workoutID);

        for (int i = 0; i < dbHelper.getExercise(workoutID).size(); i++){
            exercises.add(new ExerciseItem(exerciseNames.get(i), sets.get(i), reps.get(i), weight.get(i)));
        }

        RecyclerView rv = findViewById(R.id.exercise_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));  //Positions the items
        rv.setAdapter(new com.example.fittracker.ExerciseRVAdapter(exercises));

    }
}

