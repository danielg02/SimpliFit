package com.example.fittracker;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fittracker.ExerciseItem;
import com.example.fittracker.R;

import java.util.List;

public class ExerciseRVAdapter extends RecyclerView.Adapter<ExerciseRVAdapter.PersonViewHolder>{
    List<ExerciseItem> exercises;

    public ExerciseRVAdapter(List<ExerciseItem> exercises){
        this.exercises = exercises;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        TextView numOfSets;
        TextView numOfReps;
        TextView weight;

        PersonViewHolder(View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise);
            numOfSets = itemView.findViewById(R.id.sets);
            numOfReps = itemView.findViewById(R.id.reps);
            weight = itemView.findViewById(R.id.weight);
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exercise_card, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.exerciseName.setText(exercises.get(i).getName());
        personViewHolder.numOfSets.setText(exercises.get(i).getSets());
        personViewHolder.numOfReps.setText(exercises.get(i).getReps());
        personViewHolder.weight.setText(exercises.get(i).getWeight());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

