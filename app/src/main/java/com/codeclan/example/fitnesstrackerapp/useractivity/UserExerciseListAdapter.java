package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by graemebrown on 30/01/2018.
 */

public class UserExerciseListAdapter extends ArrayAdapter<UserExercise> {

    private AppDatabase db;

    public UserExerciseListAdapter(@NonNull Context context, List<UserExercise> exercise) {
        super(context, 0, exercise);
        db = AppDatabase.getInMemoryDatabase(context);
    }

    public View getView(int position, View listItemView, ViewGroup parent) {
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_item, parent, false);
        }

        UserExercise currentExercise = getItem(position);
        TextView activityType = listItemView.findViewById(R.id.activity_type_text_view);
        Activity currentActivity = db.activityDao().findByID(currentExercise.getActivityId());
        activityType.setText(currentActivity.getActivityType());

        TextView startDateAndTime = listItemView.findViewById(R.id.start_date_and_time_text_view);
        SimpleDateFormat df = new SimpleDateFormat("DD-MM-YYYY h:mm aa");
        String formattedDate = df.format(currentExercise.getStartDateAndTime());
        startDateAndTime.setText(formattedDate);

        TextView distance = listItemView.findViewById(R.id.distance_text_view);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(Locale.UK,
                "%.2f", currentExercise.getDistance()));
        sb.append(" KM's");
        distance.setText(sb);

        TextView description = listItemView.findViewById(R.id.description_text_view);
        description.setText(currentExercise.getDescription());

        listItemView.setTag(currentExercise);
        return listItemView;
    }
}
