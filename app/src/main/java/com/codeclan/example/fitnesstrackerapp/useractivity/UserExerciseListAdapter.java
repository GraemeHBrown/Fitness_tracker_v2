package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.activity.ActivityImages;
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
        String typeString = currentActivity.getActivityType();

        activityType.setText(typeString);

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

        ImageView imageView = listItemView.findViewById(R.id.list_item_image_view);
        int resId = ActivityImages.getImagesResourceIdForActivityType(typeString);
        imageView.setImageResource(resId);

        listItemView.setTag(currentExercise);
        return listItemView;
    }
}
