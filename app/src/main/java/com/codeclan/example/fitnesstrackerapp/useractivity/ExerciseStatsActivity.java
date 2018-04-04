package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.MainActivity;
import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.activity.ExerciseActivity;
import com.codeclan.example.fitnesstrackerapp.viewmodel.ExerciseStatsViewModel;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class ExerciseStatsActivity extends AppCompatActivity {
    private List<UserExercise> allExercise;
    private ExerciseStatsViewModel statsViewModel;
    TableLayout layout;
    TableRow tableRow;
    Map<String, Long> exerciseCounts;
    Map<String, Double> exerciseDistanceTotals;
    TextView activityValue, activityKey;
    ArrayList<String> labels;
    ArrayList<Double> values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statsViewModel = ViewModelProviders.of(this).get(ExerciseStatsViewModel.class);
        setContentView(R.layout.activity_exercise_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.stats_activity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        //TODO look at making allExercise live data
        allExercise = statsViewModel.getAllExerciseForUser();
        exerciseCounts = getExerciseCounts(allExercise);
        exerciseDistanceTotals = getExerciseDistanceTotals(allExercise);
        layout = findViewById(R.id.activity_count);
//        addDataToTable();
        addActivityDistanceDataToTable();

        //begin graph setup
//        values = getValuesFromCount(exerciseCounts);
//        labels = getLabelFromCount(exerciseCounts);
        values = getValuesForDistance(exerciseDistanceTotals);
        labels = getLabelsForDistance(exerciseDistanceTotals);
        String[] horizLabels = labels.toArray(new String[0]);
        GraphView graph = (GraphView) findViewById(R.id.graph);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(horizLabels);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>();
        for (int i = 0; i < values.size(); i++) {
            DataPoint point = new DataPoint(i, values.get(i));
            series.appendData(point, false, 200, true);

        }

        series.setSpacing(40);
        graph.getGridLabelRenderer().setHumanRounding(true);
        graph.addSeries(series);
        graph.getViewport().setMinY(0);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setTextSize(35);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
            }
        });

    }

    private Map<String, Double> getExerciseDistanceTotals(List<UserExercise> allExercise) {
        Map<String, Double> distanceMap = new HashMap<>();
        for (UserExercise exercise : allExercise) {
            Activity foundActivity = statsViewModel.getActivityForExercise(exercise.getActivityId());
            Double totalDistanceForActivity = statsViewModel.getTotalDistanceForExerciseType(exercise.getActivityId());
            distanceMap.put(foundActivity.getActivityType(), totalDistanceForActivity);
        }

        for (Map.Entry<String, Double> entry : distanceMap.entrySet()) {
            Log.d(entry.getKey(), entry.getValue().toString());
        }

        return distanceMap;
    }

    private ArrayList<Double> getValuesForDistance(Map<String, Double> exerciseDistanceTotals) {
        ArrayList<Double> values = new ArrayList<>();
        values.addAll(exerciseDistanceTotals.values());
        return values;
    }

    private ArrayList<String> getLabelsForDistance(Map<String, Double> exerciseDistanceTotals) {
        ArrayList<String> labels = new ArrayList<>();
        labels.addAll(exerciseDistanceTotals.keySet());
        return labels;
    }

    private ArrayList<Long> getValuesFromCount(Map<String, Long> exerciseCounts) {
        ArrayList<Long> values = new ArrayList<>();
        values.addAll(exerciseCounts.values());
        return values;
    }

    private ArrayList<String> getLabelFromCount(Map<String, Long> exerciseCounts) {
        ArrayList<String> labels = new ArrayList<>();
        labels.addAll(exerciseCounts.keySet());
        return labels;
    }

    private void addDataToTable() {
        for (Map.Entry<String, Long> entry : exerciseCounts.entrySet()) {
            tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            activityKey = new TextView(this);
            activityValue = new TextView(this);
            activityKey.setText(entry.getKey());
            activityKey.setPadding(0, 0, 30, 10);
            tableRow.addView(activityKey);
            activityValue.setText(entry.getValue().toString());
            activityValue.setTextColor(Color.RED);
            tableRow.addView(activityValue);
            layout.addView(tableRow, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
//
    }

    private void addActivityDistanceDataToTable() {
        for (Map.Entry<String, Double> entry : exerciseDistanceTotals.entrySet()) {
            tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            activityKey = new TextView(this);
            activityValue = new TextView(this);
            activityKey.setText(entry.getKey());
            activityKey.setPadding(0, 0, 30, 10);
            tableRow.addView(activityKey);
            StringBuilder sb = new StringBuilder();
            sb.append(String.format(Locale.UK,
                    "%.2f", entry.getValue()));
            activityValue.setText(sb);
//            activityValue.setText(entry.getValue().toString());
            activityValue.setTextColor(Color.RED);
            tableRow.addView(activityValue);
            layout.addView(tableRow, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
//
    }


    @TargetApi(Build.VERSION_CODES.N)
    private Map<String, Long> getExerciseCounts(List<UserExercise> allExercise) {
        ArrayList<String> activityTypes = new ArrayList<>();
        Map<String, Long> typeMap = new HashMap<>();
        for (UserExercise exercise : allExercise) {
            Activity foundActivity = statsViewModel.getActivityForExercise(exercise.getActivityId());
            activityTypes.add(foundActivity.getActivityType());
        }

        for (String type : activityTypes) {
            if (typeMap.containsKey(type)) {
                typeMap.put(type, typeMap.get(type) + 1);
            } else {
                typeMap.put(type, 1L);
            }
        }

        return typeMap;
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
