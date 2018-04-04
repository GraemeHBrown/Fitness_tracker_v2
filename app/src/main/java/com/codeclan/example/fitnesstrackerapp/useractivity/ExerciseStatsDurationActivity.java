package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codeclan.example.fitnesstrackerapp.MainActivity;
import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.activity.ExerciseActivity;
import com.codeclan.example.fitnesstrackerapp.viewmodel.ExerciseStatsViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseStatsDurationActivity extends AppCompatActivity {
    private ExerciseStatsViewModel statsViewModel;
    private List<UserExercise> allExercise;
    private Map<Integer, Long> exerciseDurationTotals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statsViewModel = ViewModelProviders.of(this).get(ExerciseStatsViewModel.class);
        setContentView(R.layout.activity_exercise_stats_duration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.stats_duration_toolbar);
        setSupportActionBar(toolbar);
        allExercise = statsViewModel.getAllExerciseForUser();
        exerciseDurationTotals = getTotalDurationsForExerciseTypes(allExercise);

        //Pie chart setup
        PieChart chart = (PieChart) findViewById(R.id.chart);
        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<Integer, Long> entry : exerciseDurationTotals.entrySet()) {
            Activity foundActivity = statsViewModel.getActivityForExercise(entry.getKey());
            entries.add(new PieEntry(entry.getValue(), foundActivity.getActivityType()));

        }
        PieDataSet dataset = new PieDataSet(entries,"");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        dataset.setValueTextSize(24);

        PieData data = new PieData(dataset);
        chart.setData(data);

        Description desc = new Description();
        desc.setText("Total duration (mins) for each activity type");
        desc.setTextSize(18);
        desc.setTextColor(R.color.spinner_colour);
        desc.setPosition(1010,250);
        chart.setDescription(desc);
    }

    private Map<Integer, Long> getTotalDurationsForExerciseTypes(List<UserExercise> allExercise) {
        Map<Integer, Long> durationMap = new HashMap<>();
        for (UserExercise exercise : allExercise) {
            Activity foundActivity = statsViewModel.getActivityForExercise(exercise.getActivityId());
            Long totalDurationForExerciseType = statsViewModel.getTotalDurationForExerciseType(exercise.getActivityId());
            durationMap.put(foundActivity.getId(), totalDurationForExerciseType);
        }

        for (Map.Entry<Integer, Long> entry : durationMap.entrySet()) {
            Log.d(entry.getKey().toString(), entry.getValue().toString());
        }
        return durationMap;
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

            case R.id.stats_home_page:
                Intent intentStatsHome = new Intent(this, ExerciseStatsHomePageActivity.class);
                startActivity(intentStatsHome);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
