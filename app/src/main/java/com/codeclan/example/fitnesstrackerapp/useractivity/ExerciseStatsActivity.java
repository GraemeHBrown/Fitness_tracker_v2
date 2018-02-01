package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codeclan.example.fitnesstrackerapp.MainActivity;
import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.activity.ExerciseActivity;

public class ExerciseStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.stats_activity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stats_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.stats_home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.stats_add_new_exercise:
                Intent intentAdd = new Intent(this, AddNewExerciseActivity.class);
                startActivity(intentAdd);
                return true;

            case R.id.stats_view_exercise:
                Intent intentView = new Intent(this, ExerciseActivity.class);
                startActivity(intentView);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
