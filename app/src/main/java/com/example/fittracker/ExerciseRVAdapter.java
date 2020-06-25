package com.example.fittracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExerciseRVAdapter extends RecyclerView.Adapter<ExerciseRVAdapter.ExerciseViewHolder>{
    List<ExerciseItem> exercises;

    public ExerciseRVAdapter(List<ExerciseItem> exercises){
        this.exercises = exercises;
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        TextView numOfSets;
        TextView numOfReps;
        TextView weight;
        Button deleteExercise;

        ExerciseViewHolder(View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise);
            numOfSets = itemView.findViewById(R.id.sets);
            numOfReps = itemView.findViewById(R.id.reps);
            weight = itemView.findViewById(R.id.weight);
            deleteExercise = itemView.findViewById(R.id.delete_exercise_button);
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
    public void onBindViewHolder(ExerciseViewHolder evh, int i) {
        final TextView name = evh.exerciseName;
        evh.exerciseName.setText(exercises.get(i).getName());
        evh.numOfSets.setText(exercises.get(i).getSets());
        evh.numOfReps.setText(exercises.get(i).getReps());
        evh.weight.setText(exercises.get(i).getWeight());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

