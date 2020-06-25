package com.example.fittracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RecyclerWorkoutsFragment extends Fragment {
    private FloatingActionButton addWorkoutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);

        addWorkoutButton = (FloatingActionButton) view.findViewById(R.id.add_workout_button);

        addWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();    //Testing Purposes
                Intent intent = new Intent(getActivity(), EnterWorkout1.class);
                startActivity(intent);
           }
        });

        //For Testing Purposes
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        List<String> workouts = dbHelper.getWorkout();


        RecyclerView recyclerView = view.findViewById(R.id.workouts_recycler_view);  //Instantiating recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));  //Positions the items
        recyclerView.setAdapter(new RecyclerViewAdapter(workouts)); //Binds list to the view
        return view;
    }

    private class RecyclerViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder{
        private TextView mTextView;

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.workout_card, container, false));
            mTextView = itemView.findViewById(R.id.text_holder);

            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                    int workoutID = dbHelper.getID(mTextView.getText().toString());
                    Toast.makeText(getActivity(), mTextView.getText().toString(), Toast.LENGTH_SHORT);  //Testing
                    Intent intent = new Intent(getActivity(), ExercisesActivity.class);
                    intent.putExtra("workout_id", workoutID);
                    startActivity(intent);
                }
            });
        }
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
        private List<String> mList;

        public RecyclerViewAdapter(List<String> list) {
            this.mList = list;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RecyclerViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {
            holder.mTextView.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

}
