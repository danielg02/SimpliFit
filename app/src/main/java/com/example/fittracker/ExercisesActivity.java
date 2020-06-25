package com.example.fittracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ExercisesActivity extends Activity {
//    final Fragment exercises = new RecyclerExercisesFragment();
//    final FragmentManager manager = getSupportFragmentManager();
//    Fragment current = exercises;    //Reference to whatever fragment is currently being shown

    private List<ExerciseItem> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        int workoutID = getIntent().getIntExtra("workout_id", -1);
        exercises = new ArrayList<>();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<String> exerciseNames = dbHelper.getExercise(workoutID);
        List<String> sets = dbHelper.getSets(workoutID);
        List<String> reps = dbHelper.getReps(workoutID);
        List<String> weight = dbHelper.getWeight(workoutID);

        for (int i = 0; i < dbHelper.getExercise(workoutID).size(); i++){
            exercises.add(new ExerciseItem(exerciseNames.get(i), sets.get(i), reps.get(i), weight.get(i)));
        }

        RecyclerView rv = findViewById(R.id.exercise_recycler_view2);
        rv.setLayoutManager(new LinearLayoutManager(this));  //Positions the items
        rv.setAdapter(new com.example.fittracker.ExerciseRVAdapter(exercises));

    }
}

