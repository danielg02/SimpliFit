package com.example.simplifit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecyclerWorkoutsFragment extends Fragment {
    private FloatingActionButton addWorkoutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);

        addWorkoutButton = view.findViewById(R.id.add_workout_button);

        addWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to EnterWorkout1 Activity
                Intent intent = new Intent(getActivity(), EnterWorkout1.class);
                getActivity().overridePendingTransition(0,0);
                getActivity().finish();
                startActivity(intent);
           }
        });


        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        List<String> workouts = dbHelper.getWorkout();  //Retrieves list of workouts from database


        RecyclerView recyclerView = view.findViewById(R.id.workouts_recycler_view);  //Instantiating recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));  //Positions the items
        recyclerView.setAdapter(new RecyclerViewAdapter(workouts)); //Binds list to the view
        return view;
    }

    private class RecyclerViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder{
        private TextView mTextView;     //TextView containing workout name
        private Button deleteWorkout;
        private Button editWorkout;

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.workout_card, container, false));
            mTextView = itemView.findViewById(R.id.text_holder);
            deleteWorkout = itemView.findViewById(R.id.delete_workout_button);
            editWorkout = itemView.findViewById(R.id.edit_workout_button);

            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Go to ExercisesActivity
                    DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                    int workoutID = dbHelper.getID(mTextView.getText().toString());
                    Intent intent = new Intent(getActivity(), ExercisesActivity.class);
                    //Transfer workoutID to the new activity
                    intent.putExtra("workout_id", workoutID);
                    getActivity().finish();
                    getActivity().overridePendingTransition(0,0);
                    startActivity(intent);

                }
            });

            deleteWorkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = mTextView.getText().toString();
                    DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                    dbHelper.deleteWorkout(mTextView.getText().toString());
                    //Reload fragment
                    getFragmentManager().beginTransaction().detach(RecyclerWorkoutsFragment.this).attach(RecyclerWorkoutsFragment.this).commit();
                }
            });

            editWorkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Go to EditWorkout Activity
                    Intent intent = new Intent(getActivity(), EditWorkout.class);
                    DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                    //Transfer workoutID to new activity
                    intent.putExtra("workout_id", dbHelper.getID(mTextView.getText().toString()));
                    getActivity().finish();
                    getActivity().overridePendingTransition(0,0);
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
            holder.mTextView.setText(mList.get(position));  //Sets text to workout name
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

}
