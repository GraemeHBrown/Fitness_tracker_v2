package com.codeclan.example.fitnesstrackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.activity.ExerciseActivity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.db.utils.DatabaseInitializer;
import com.codeclan.example.fitnesstrackerapp.user.User;
import com.codeclan.example.fitnesstrackerapp.useractivity.AddNewExerciseActivity;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    AppDatabase db;

    private TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void onButtonClick(View button) {

        Intent intent = new Intent(this, ExerciseActivity.class);

        startActivity(intent);
    }


    public void onAddNewActivityButtonClick(View button) {
        Intent intent = new Intent(this, AddNewExerciseActivity.class);
        startActivity(intent);
    }


    private void populateDb() {
        DatabaseInitializer.populateSync(db);
    }

    private void fetchData() {
        // Note: this kind of logic should not be in an activity.
        StringBuilder sb = new StringBuilder();
        User user = db.userDao().findUserById(1);
        sb.append(String.format(Locale.UK,
                "%s's Exercise Tracker", user.getFirstName()));

        welcomeMessage.setText(sb);
    }
}
