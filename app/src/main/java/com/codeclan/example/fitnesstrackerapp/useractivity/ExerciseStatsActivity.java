package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.MainActivity;
import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.activity.ExerciseActivity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExerciseStatsActivity extends AppCompatActivity {
    AppDatabase db;
    private List<UserExercise> allExercise;
    TableLayout layout;
    TableRow tableRow;
    Map<String, Long> exerciseCounts;
    TextView activityCount, activityKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.stats_activity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();


        db = AppDatabase.getInMemoryDatabase(getApplicationContext());
        User appUser = db.userDao().getAll().get(0);
        allExercise = db.userExerciseDao().findAllExerciseForUser(appUser.getId());
        exerciseCounts = getExerciseCounts(allExercise);
        layout = findViewById(R.id.activity_count);
        addDataToTable();

    }

    private void addDataToTable() {
        for (Map.Entry<String, Long> entry : exerciseCounts.entrySet()) {
            tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            activityKey = new TextView(this);
            activityCount = new TextView(this);
            activityKey.setText(entry.getKey());
            activityKey.setPadding(0, 0, 30, 10);
            tableRow.addView(activityKey);
            activityCount.setText(entry.getValue().toString());
            activityCount.setTextColor(Color.RED);
            tableRow.addView(activityCount);
            layout.addView(tableRow, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
//
    }


    @TargetApi(Build.VERSION_CODES.N)
    private Map<String, Long> getExerciseCounts(List<UserExercise> allExercise) {
        ArrayList<String> activityTypes = new ArrayList<>();
        for (UserExercise exercise : allExercise) {
            int activityId = exercise.getActivityId();
            Activity foundActivity = db.activityDao().findByID(activityId);
            activityTypes.add(foundActivity.getActivityType());
        }

        Map<String, Long> typeCount = activityTypes.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return typeCount;
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
