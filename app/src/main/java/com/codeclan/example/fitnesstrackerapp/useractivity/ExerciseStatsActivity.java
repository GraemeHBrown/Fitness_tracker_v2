package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.codeclan.example.fitnesstrackerapp.viewmodel.ExerciseStatsViewModel;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExerciseStatsActivity extends AppCompatActivity {
    private ExerciseStatsViewModel statsViewModel;
    TableLayout layout;
    TableRow tableRow;
    TextView activityCount, activityKey;
    ArrayList<String> labels;
    ArrayList<Long> values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statsViewModel = ViewModelProviders.of(this).get(ExerciseStatsViewModel.class);
        setContentView(R.layout.activity_exercise_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.stats_activity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        layout = findViewById(R.id.activity_count);


        //LiveData

        statsViewModel.getLiveExerciseList().observe(this, new Observer<List<UserExercise>>() {
            @Override
            public void onChanged(@Nullable List<UserExercise> userExercises) {
                if (userExercises != null) {
                    Map<String, Long> exerciseCounts = getExerciseCounts(userExercises);
                    addDataToTable(exerciseCounts);
                    drawGraph(exerciseCounts);
                }
            }
        });

    }

    private void drawGraph(Map<String, Long> exerciseCounts) {
        values = getValuesFromCount(exerciseCounts);
        labels = getLabelFromCount(exerciseCounts);
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

    private void addDataToTable(Map<String, Long> exerciseCounts) {
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

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
