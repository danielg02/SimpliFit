package com.example.fittracker;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ExercisesActivity extends Activity {
    DatabaseHelper dbHelper;
    private List<ExerciseItem> exercises;
    private FloatingActionButton add;
    int workoutID;
    int numOfExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        add = findViewById(R.id.new_exercise_button);
        workoutID = getIntent().getIntExtra("workout_id", 0);
        exercises = new ArrayList<>();

        dbHelper = new DatabaseHelper(this);
        List<String> exerciseNames = dbHelper.getExercise(workoutID);
        List<String> sets = dbHelper.getSets(workoutID);
        List<String> reps = dbHelper.getReps(workoutID);
        List<String> weight = dbHelper.getWeight(workoutID);

        numOfExercises = exerciseNames.size();

        if (numOfExercises < 1){
            dbHelper.deleteWorkout(dbHelper.getWorkoutName(workoutID));
            Intent i = new Intent(ExercisesActivity.this, MainActivity.class);
            finish();
            overridePendingTransition(0,0);
            startActivity(i);
        }

        for (int i = 0; i < exerciseNames.size(); i++) {
            exercises.add(new ExerciseItem(exerciseNames.get(i), sets.get(i), reps.get(i), weight.get(i)));
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExercisesActivity.this, EnterWorkout2.class);
                intent.putExtra("workout_id", workoutID);
                finish();
                overridePendingTransition(0,0);
                startActivity(intent);
            }
        });

        RecyclerView rv = findViewById(R.id.exercise_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));  //Positions the items
        rv.setAdapter(new ExerciseRVAdapter(exercises));
    }


    private class ExerciseRVAdapter extends RecyclerView.Adapter<ExerciseRVAdapter.ExerciseViewHolder> {
        List<ExerciseItem> exercises;

        public ExerciseRVAdapter(List<ExerciseItem> exercises) {
            this.exercises = exercises;
        }

        public class ExerciseViewHolder extends RecyclerView.ViewHolder {
            TextView exerciseName;
            TextView numOfSets;
            TextView numOfReps;
            TextView weight;
            Button deleteExercise;
            Button editExercise;

            ExerciseViewHolder(View itemView) {
                super(itemView);
                exerciseName = itemView.findViewById(R.id.exercise);
                numOfSets = itemView.findViewById(R.id.sets);
                numOfReps = itemView.findViewById(R.id.reps);
                weight = itemView.findViewById(R.id.weight);
                deleteExercise = itemView.findViewById(R.id.delete_exercise_button);
                editExercise = itemView.findViewById(R.id.edit_exercise_button);
            }
        }

        @Override
        public int getItemCount() {
            return exercises.size();
        }

        @Override
        public ExerciseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exercise_card, viewGroup, false);
            ExerciseViewHolder exerciseViewHolder = new ExerciseViewHolder(view);
            return exerciseViewHolder;
        }

        @Override
        public void onBindViewHolder(final ExerciseViewHolder evh, int i) {
            evh.exerciseName.setText(exercises.get(i).getName());
            evh.numOfSets.setText(exercises.get(i).getSets());
            evh.numOfReps.setText(exercises.get(i).getReps());
            evh.weight.setText(exercises.get(i).getWeight());

            evh.deleteExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.deleteExercise(evh.exerciseName.getText().toString());
                    finish();
                    overridePendingTransition(0,0);
                    startActivity(getIntent());
                }
            });

            evh.editExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ExercisesActivity.this, EditExercise.class);
                    intent.putExtra("exercise_name", evh.exerciseName.getText().toString());
                    intent.putExtra("workout_id", workoutID);
                    finish();
                    overridePendingTransition(0,0);
                    startActivity(intent);
                }
            });
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

    }

}

