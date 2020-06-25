package com.example.fittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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

    public ArrayList getWorkout() {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + WORKOUTS_TABLE, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex("workout")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public int getID(String workoutName) {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + WORKOUTS_TABLE + " WHERE workout = '" + workoutName + "'", null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        return id;
    }

    public ArrayList getExercise(int workoutID) {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + EXERCISES_TABLE + " WHERE id = " + workoutID, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex("exercise")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public ArrayList getSets(int workoutID) {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + EXERCISES_TABLE + " WHERE id = " + workoutID, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex("sets")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public ArrayList getReps(int workoutID) {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + EXERCISES_TABLE + " WHERE id = " + workoutID, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex("reps")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public ArrayList getWeight(int workoutID) {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + EXERCISES_TABLE + " WHERE id = " + workoutID, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex("weight")));
            cursor.moveToNext();
        }
        return arrayList;
    }
}
