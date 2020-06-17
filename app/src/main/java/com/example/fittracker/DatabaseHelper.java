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

//    private static final String colID = "id";
//    private static final String colWorkout = "workout";
//    private static final String colExercise = "exercise";
//    private static final String
//    private static final String
//    private static final String



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = "CREATE TABLE " + WORKOUTS_TABLE + "(id INTEGER PRIMARY KEY AUTOINCREMENT, workout TEXT)";
        String table2 = "CREATE TABLE " + EXERCISES_TABLE + "(id INTEGER, exercise TEXT, sets INTEGER, reps INTEGER, " +
                "weight INTEGER, PRIMARY KEY(id, exercise), FOREIGN KEY (id) REFERENCES "+ WORKOUTS_TABLE + "(id))";
        db.execSQL(table1);
        db.execSQL(table2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISES_TABLE);
        onCreate(db);
    }

    public boolean insert(String workout, String exercise, String sets, String reps, String weight){
        //Get Writeable DB
        SQLiteDatabase sqlDB = this.getWritableDatabase();

        //Create Content Values
        ContentValues values1 = new ContentValues();
        values1.put("workout", workout);
        sqlDB.insert(WORKOUTS_TABLE, null, values1);

        //Create Content Values
        ContentValues values2 = new ContentValues();
        values2.put("exercise", exercise);
        values2.put("sets", sets);
        values2.put("reps", reps);
        values2.put("weight", weight);
        sqlDB.insert(EXERCISES_TABLE, null, values2);
        return true;
    }

    public ArrayList getWorkout(){
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + WORKOUTS_TABLE, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("workout")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public ArrayList getExercises(){
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + EXERCISES_TABLE, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("exercise")));
            cursor.moveToNext();
        }
        return arrayList;
    }
}
