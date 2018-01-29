package com.codeclan.example.fitnesstrackerapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.db.utils.DatabaseInitializer;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;

import java.util.List;
import java.util.Locale;

public class ExerciseActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView exerciseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        exerciseTextView = findViewById(R.id.exercise_activity_view);

        db = AppDatabase.getInMemoryDatabase(getApplicationContext());

//        populateDb();
        fetchData();
    }

    private void populateDb() {

        DatabaseInitializer.populateAsync(db);
    }

    private void fetchData() {
        // Note: this kind of logic should not be in an activity.
        StringBuilder sb = new StringBuilder();
        List<UserExercise> activities = db.userExerciseDao().getAll();
        for (UserExercise activity : activities) {
            String activityType = db.activityDao().findByID(activity.getActivityId()).getActivityType();
            sb.append(String.format(Locale.UK,
                    "Description: %s, Activity Type: %s, Start date and time: %s, Duration: %s minutes \n", activity.getDescription(),
                    activityType, activity.getStartDateAndTime(),
                    activity.getDuration()));
        }
        exerciseTextView.setText(sb);
    }
}
