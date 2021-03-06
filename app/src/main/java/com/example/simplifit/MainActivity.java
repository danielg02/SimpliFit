package com.example.simplifit;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    //Declaring and Instantiating fragments
    final Fragment home = new TimerFragment();
    final Fragment workouts = new RecyclerWorkoutsFragment();

    final FragmentManager manager = getSupportFragmentManager();
    BottomNavigationView navView;
    Fragment current = home;    //Reference to whatever fragment is currently being shown

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navInstantiation();

        navView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_timer:
                        manager.beginTransaction()
                                .hide(current).show(home).commit();   //Hide the current fragment and show home

                        current = home; //Change current fragment to home
                        return true;
                    case R.id.navigation_workout:
                        manager.beginTransaction()
                                .hide(current).show(workouts).commit();   //Hide the current fragment and show workouts

                        current = workouts; //Change current fragment to workouts
                        return true;
                }
                return false;
            }
        });
    }
    //End of onCreate method

    public void navInstantiation(){
        navView = findViewById(R.id.nav_view);  //Assigning navigation bar view to navView

        //Adding fragments to activity state
        manager.beginTransaction().add(R.id.main_frame, workouts).hide(workouts).commit();  //Initially, hide the workouts fragment
        manager.beginTransaction().add(R.id.main_frame,home).commit();  //App begins on home fragment
    }

    @Override
    public void onBackPressed() {}
}
