package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;

public class ExerciseDetailsActivity extends AppCompatActivity {

    private AppDatabase db;

    Integer[] imgid = {R.drawable.road_bike, R.drawable.mtn_bike, R.drawable.running, R.drawable.swimming

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInMemoryDatabase(getApplicationContext());
        setContentView(R.layout.activity_exercise_details);

        Intent intent = getIntent();
        UserExercise exercise = (UserExercise) intent.getSerializableExtra("exercise");

        TextView exerciseType = findViewById(R.id.exercise_details_type);
        Activity foundActivity = db.activityDao().findByID(exercise.getActivityId());
        String activityType = foundActivity.getActivityType();
        exerciseType.setText(foundActivity.getActivityType());

        TextView dateAndTime = findViewById(R.id.date_and_time);
        dateAndTime.setText(exercise.getStartDateAndTime().toString());

        TextView duration = findViewById(R.id.exercise_duration);
        duration.setText(String.valueOf(exercise.getDuration()));

        TextView distance = findViewById(R.id.exercise_distance);
        distance.setText(exercise.getDistance().toString());

        TextView description = findViewById(R.id.exercise_description);
        description.setText(exercise.getDescription());

        TextView equipment = findViewById(R.id.equipment_description);
        Equipment foundEquioment = db.equipmentDao().findByID(exercise.getEquipmentId());

        equipment.setText(foundEquioment.getFullEquipmentName());

        ImageView image = findViewById(R.id.activity_image_view);


        if (activityType.equals("Road Biking")) {
            image.setImageResource(imgid[0]);
        } else if (activityType.equals("Mtn Biking")) {
            image.setImageResource(imgid[1]);
        } else if (activityType.equals("Road Running")) {
            image.setImageResource(imgid[2]);
        } else if (activityType.equals("Swimming")) {
            image.setImageResource(imgid[3]);

        }

    }
}
