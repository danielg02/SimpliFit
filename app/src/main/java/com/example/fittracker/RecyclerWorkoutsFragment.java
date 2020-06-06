package com.example.fittracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerWorkoutsFragment extends Fragment {

    public static Fragment newInstance() {
        return new RecyclerWorkoutsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);

        //For Testing Purposes
        List<String> workouts = new ArrayList<>();
        workouts.add("ONE");
        workouts.add("TWO");
        workouts.add("THREE");
        workouts.add("FOUR");
        workouts.add("FOUR");
        workouts.add("FOUR");
        workouts.add("FOUR");

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);  //Instantiating recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));  //Positions the items
        recyclerView.setAdapter(new RecyclerViewAdapter(workouts)); //Binds list to the view
        return view;
    }

    private class RecyclerViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder{
        private CardView mCardView;
        private TextView mTextView;

        public RecyclerViewHolder(View itemView){
            super(itemView);
        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.workout_card, container, false));

            mCardView = itemView.findViewById(R.id.fragment_container);
            mTextView = itemView.findViewById(R.id.text_holder);
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
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
            holder.mTextView.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
