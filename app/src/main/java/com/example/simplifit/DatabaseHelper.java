package com.example.simplifit;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "workouts_database";
    private static final String WORKOUTS_TABLE = "workouts_table";
    private static final String EXERCISES_TABLE = "exercises_table";

    private static final String colID = "id";
    private static final String colWorkout = "workout";
    private static final String colExercise = "exercise";
    private static final String colSets = "sets";
    private static final String colReps = "reps";
    private static final String colWeight = "weight";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE1 = "CREATE TABLE " + WORKOUTS_TABLE + "(" + colID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + colWorkout + " TEXT);";
        String CREATE_TABLE2 = "CREATE TABLE " + EXERCISES_TABLE + "(" + colID + " INTEGER, " + colExercise +
                " TEXT, " + colSets + " TEXT, " + colReps + " TEXT, " + colWeight + " TEXT, PRIMARY KEY(" + colID + ", " + colExercise + "), FOREIGN KEY (" + colID +
                ") REFERENCES " + WORKOUTS_TABLE + "(" + colID + "));";
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISES_TABLE);
        onCreate(db);
    }

    public boolean insertWorkout(String workout) {
        //Get Writeable DB
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        //Create Content Values
        ContentValues values1 = new ContentValues();
        values1.put(colWorkout, workout);
        sqlDB.insert(WORKOUTS_TABLE, null, values1);
        return true;
    }

    public boolean insertExercise(int workoutID, String exercise, String sets, String reps, String weight) {
        //Get Writeable DB
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        //Create Content Values
        ContentValues values2 = new ContentValues();
        values2.put(colID, workoutID);
        values2.put(colExercise, exercise);
        values2.put(colSets, sets);
        values2.put(colReps, reps);
        values2.put(colWeight, weight);
        sqlDB.insert(EXERCISES_TABLE, null, values2);
        return true;
    }

    //Returns a list of all workouts in DB
    public ArrayList getWorkout() {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + WORKOUTS_TABLE, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(colWorkout)));
            cursor.moveToNext();
        }
        return arrayList;
    }

    //Returns the id of a given workout name
    public int getID(String workoutName) {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + WORKOUTS_TABLE + " WHERE " + colWorkout + " = ?", new String[]{workoutName});
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex(colID));
        return id;
    }

    //Returns the workout name for a given id
    public String getWorkoutName(int workoutID) {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        String name = "";
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + WORKOUTS_TABLE + " WHERE " + colID + " = ?", new String[]{Integer.toString(workoutID)});
        cursor.moveToFirst();
        name = cursor.getString(cursor.getColumnIndex(colWorkout));
        return name;
    }

    //Returns a list of exercises in a given workout
    public ArrayList getExercise(int workoutID) {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + EXERCISES_TABLE + " WHERE id = ?", new String[]{Integer.toString(workoutID)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(colExercise)));
            cursor.moveToNext();
        }
        return arrayList;
    }

    //Returns a list of sets in a given workout
    public ArrayList getSets(int workoutID) {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + EXERCISES_TABLE + " WHERE id = " + workoutID, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(colSets)));
            cursor.moveToNext();
        }
        return arrayList;
    }

    //Returns a list of reps in a given workout
    public ArrayList getReps(int workoutID) {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + EXERCISES_TABLE + " WHERE id = " + workoutID, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(colReps)));
            cursor.moveToNext();
        }
        return arrayList;
    }

    //Returns a list of weights in a given workout
    public ArrayList getWeight(int workoutID) {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + EXERCISES_TABLE + " WHERE id = " + workoutID, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(colWeight)));
            cursor.moveToNext();
        }
        return arrayList;
    }

    //Deletes a given exercise from DB
    public void deleteExercise(String exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + EXERCISES_TABLE + " WHERE " + colExercise + " = ?", new String[]{exercise});
    }

    //Deletes a given workout and its exercises from DB
    public void deleteWorkout(String workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + EXERCISES_TABLE + " WHERE " + colID + " = ?", new String[]{Integer.toString(getID(workout))});
        db.execSQL("DELETE FROM " + WORKOUTS_TABLE + " WHERE " + colWorkout + " = ?", new String[]{workout});
    }

    public String editExercise(ExerciseItem e, int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colExercise, e.getName());
        cv.put(colSets, e.getSets());
        cv.put(colReps, e.getReps());
        cv.put(colWeight, e.getWeight());
        db.update(EXERCISES_TABLE, cv, colID + " = ? AND " + colExercise + " = ?" , new String[]{Integer.toString(id), name});
        return e.getName();
    }

    public void editWorkout(int workoutID, String workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colWorkout, workout);
        db.update(WORKOUTS_TABLE, cv, colID + " = ?", new String[]{Integer.toString(workoutID)});
    }

    //Returns an ExerciseItem object with values from a specific row in DB
    public ExerciseItem getExerciseItem(int id, String name) {
        ExerciseItem eItem;
        String eName = "", eSets = "", eReps = "", eWeight = "";
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + EXERCISES_TABLE + " WHERE " + colID + " = ? AND " + colExercise + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{Integer.toString(id), name});
        cursor.moveToFirst();
        do {
            if (cursor != null) {
                //setting related user info in User Object
                eName = cursor.getString(cursor.getColumnIndex(colExercise));
                eSets = cursor.getString(cursor.getColumnIndex(colSets));
                eReps = cursor.getString(cursor.getColumnIndex(colReps));
                eWeight = cursor.getString(cursor.getColumnIndex(colWeight));
            }
        } while (cursor.moveToNext());

        eItem = new ExerciseItem(eName, eSets, eReps, eWeight);
        return eItem;
    }

    //Checks if workout already exists in DB
    public boolean ifWorkoutExists(String wName){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT " + colWorkout + " FROM " + WORKOUTS_TABLE + " WHERE " + colWorkout + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{wName});
        if (cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

    //Check if exercise already exists in a certain workout in DB
    public boolean ifExerciseExists(String eName, int id){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT " + colExercise + " FROM " + EXERCISES_TABLE + " WHERE " + colExercise
                + " = ? AND " + colID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{eName, Integer.toString(id)});
        if (cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

}
