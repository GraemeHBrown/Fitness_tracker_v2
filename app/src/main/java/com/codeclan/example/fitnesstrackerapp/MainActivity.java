package com.codeclan.example.fitnesstrackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.activity.ExerciseActivity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.db.utils.DatabaseInitializer;
import com.codeclan.example.fitnesstrackerapp.user.User;
import com.codeclan.example.fitnesstrackerapp.useractivity.AddNewExerciseActivity;
import com.codeclan.example.fitnesstrackerapp.useractivity.ExerciseStatsActivity;

import java.util.Locale;

//TODO look at putting current user into saved instance?
public class MainActivity extends AppCompatActivity {

    AppDatabase db;

    private TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        welcomeMessage = findViewById(R.id.welcome_text_view);

        db = AppDatabase.getInMemoryDatabase(getApplicationContext());

        populateDb();
        fetchData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.add_new_activity:
                Intent intent = new Intent(this, AddNewExerciseActivity.class);
                startActivity(intent);
                return true;

            case R.id.view_activities:
                Intent intentView = new Intent(this, ExerciseActivity.class);
                startActivity(intentView);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void onButtonClick(View button) {

        Intent intent = new Intent(this, ExerciseActivity.class);

        startActivity(intent);
    }


    public void onAddNewActivityButtonClick(View button) {
        Intent intent = new Intent(this, AddNewExerciseActivity.class);
        startActivity(intent);
    }

    public void onViewStatsButtonClick(View button) {
        Intent intent = new Intent(this, ExerciseStatsActivity.class);
        startActivity(intent);
    }


    private void populateDb() {
        DatabaseInitializer.populateSync(db);
    }

    private void fetchData() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(Locale.UK,
                "Graeme's Exercise Tracker"));
        welcomeMessage.setText(sb);
    }
}
