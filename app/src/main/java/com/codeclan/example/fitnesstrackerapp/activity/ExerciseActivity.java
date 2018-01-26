package com.codeclan.example.fitnesstrackerapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.db.utils.DatabaseInitializer;

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

        populateDb();
        fetchData();
    }

    private void populateDb() {

        DatabaseInitializer.populateAsync(db);
    }

    private void fetchData() {
        // Note: this kind of logic should not be in an activity.
        StringBuilder sb = new StringBuilder();
        List<Activity> activities = db.activityDao().getAll();
        for (Activity activity : activities) {
            sb.append(String.format(Locale.UK,
                    "%s, %s, (ID:%d) \n", activity.getActivityName(), activity.getActivityType(), activity.getId()));
        }
        exerciseTextView.setText(sb);
    }
}
