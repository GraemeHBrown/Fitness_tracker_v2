package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.MainActivity;
import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.activity.ActivityImages;
import com.codeclan.example.fitnesstrackerapp.activity.ExerciseActivity;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;

import java.text.SimpleDateFormat;

public class ExerciseDetailsActivity extends AppCompatActivity {

    private ActivityImages actImages;
    private ExerciseDetailsViewModel exerciseDetailsViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exerciseDetailsViewModel = ViewModelProviders.of(this).get(ExerciseDetailsViewModel.class);


        setContentView(R.layout.activity_exercise_details);

        Intent intent = getIntent();
        UserExercise exercise = (UserExercise) intent.getSerializableExtra("exercise");

        TextView exerciseType = findViewById(R.id.exercise_details_type);
        Activity foundActivity = exerciseDetailsViewModel.getActivityForExercise(exercise.getActivityId());
        String activityType = foundActivity.getActivityType();
        exerciseType.setText(foundActivity.getActivityType());

        TextView dateAndTime = findViewById(R.id.date_and_time);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy h:mm aa");
        String formattedDate = df.format(exercise.getStartDateAndTime());
        dateAndTime.setText(formattedDate);


        TextView duration = findViewById(R.id.exercise_duration);
        duration.setText(String.valueOf(exercise.getDuration()));

        TextView distance = findViewById(R.id.exercise_distance);
        if(exercise.getDistance()!=null){
            distance.setText(exercise.getDistance().toString());
        } else {
            distance.setText(R.string.no_distance_selected);
        }


        TextView description = findViewById(R.id.exercise_description);
        description.setText(exercise.getDescription());

        TextView equipment = findViewById(R.id.equipment_description);

        if (exercise.getEquipmentId() != null) {
            Equipment foundEquipment = exerciseDetailsViewModel.getEquipmentForExercise(exercise.getEquipmentId());
            equipment.setText(foundEquipment.getFullEquipmentName());
        } else {
            equipment.setText(R.string.no_equipment);
        }

        ImageView image = findViewById(R.id.activity_image_view);
        int resId = ActivityImages.getImagesResourceIdForActivityType(activityType);
        image.setImageResource(resId);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.details_view_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detail_home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.detail_view_exercise:
                Intent intentView = new Intent(this, ExerciseActivity.class);
                startActivity(intentView);
                return true;

            case R.id.detail_add_new_exercise:
                Intent intentAdd = new Intent(this, AddNewExerciseActivity.class);
                startActivity(intentAdd);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


}
