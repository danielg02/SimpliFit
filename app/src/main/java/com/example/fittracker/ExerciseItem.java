package com.example.fittracker;

public class ExerciseItem {
    private String exerciseName, sets, reps, weight;

    public ExerciseItem(String exerciseName, String sets, String reps, String weight){
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public String getName(){
        return exerciseName;
    }
    public String getReps(){
        return reps;
    }
    public String getSets(){
        return sets;
    }
    public String getWeight(){
        return weight;
    }

}