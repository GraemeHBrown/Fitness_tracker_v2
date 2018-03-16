package com.codeclan.example.fitnesstrackerapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.MainActivity;
import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.db.utils.DatabaseInitializer;
import com.codeclan.example.fitnesstrackerapp.user.User;
import com.codeclan.example.fitnesstrackerapp.useractivity.AddNewExerciseActivity;
import com.codeclan.example.fitnesstrackerapp.useractivity.ExerciseDetailsActivity;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExerciseListAdapter;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class ExerciseActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView exerciseTextView;
    List<UserExercise> exerciseForUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = AppDatabase.getInMemoryDatabase(getApplicationContext());

        setContentView(R.layout.exercise_list_view);

        User appUser = db.userDao().getAll().get(0);
        exerciseForUser = db.userExerciseDao().findAllExerciseForUser(appUser.getId());

        UserExerciseListAdapter exerciseListAdapter = new UserExerciseListAdapter(this, exerciseForUser);
        ListView exerciseListView = findViewById(R.id.exercise_list_view);
        exerciseListView.setAdapter(exerciseListAdapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.exercise_activity_toolbar);
        setSupportActionBar(myToolbar);


        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
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
