package com.codeclan.example.fitnesstrackerapp.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.codeclan.example.fitnesstrackerapp.MainActivity;
import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.useractivity.AddNewExerciseActivity;
import com.codeclan.example.fitnesstrackerapp.useractivity.ExerciseDetailsActivity;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExerciseListAdapter;
import com.codeclan.example.fitnesstrackerapp.viewmodel.ExerciseListViewModel;

import java.util.List;

public class ExerciseActivity extends AppCompatActivity {

    private ExerciseListViewModel exerciseListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_list_view);

        exerciseListViewModel = ViewModelProviders.of(this).get(ExerciseListViewModel.class);

        exerciseListViewModel.getLiveExerciseList().observe(this, new Observer<List<UserExercise>>() {
            @Override
            public void onChanged(@Nullable List<UserExercise> userExercises) {
                if (userExercises != null) {
                    setUpListAdapter(userExercises);
                }
            }
        });


        Toolbar myToolbar = (Toolbar) findViewById(R.id.exercise_activity_toolbar);
        setSupportActionBar(myToolbar);


        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setUpListAdapter(List<UserExercise> userExercises) {
        UserExerciseListAdapter exerciseListAdapter = new UserExerciseListAdapter(this, userExercises, exerciseListViewModel);
        ListView exerciseListView = findViewById(R.id.exercise_list_view);
        exerciseListView.setAdapter(exerciseListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exercise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exercise_action_home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.add_new_exercise:
                Intent intentAdd = new Intent(this, AddNewExerciseActivity.class);
                startActivity(intentAdd);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void getExercise(View listItem) {
        UserExercise exercise = (UserExercise) listItem.getTag();
        Intent intent = new Intent(this, ExerciseDetailsActivity.class); //NEW
        intent.putExtra("exercise", exercise);
        startActivity(intent); //NEW

    }
}
