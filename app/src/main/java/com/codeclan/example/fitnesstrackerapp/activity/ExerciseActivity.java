package com.codeclan.example.fitnesstrackerapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.db.utils.DatabaseInitializer;
import com.codeclan.example.fitnesstrackerapp.useractivity.ExerciseDetailsActivity;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExerciseListAdapter;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class ExerciseActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView exerciseTextView;
    List<UserExercise> exerciseForUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInMemoryDatabase(getApplicationContext());

        setContentView(R.layout.exercise_list_view);
        exerciseForUser = db.userExerciseDao().findAllExerciseForUser(1);
        UserExerciseListAdapter exerciseListAdapter = new UserExerciseListAdapter(this, exerciseForUser);
        ListView exerciseListView = findViewById(R.id.exercise_list_view);
        exerciseListView.setAdapter(exerciseListAdapter);
    }

    public void getExercise(View listItem) {
        UserExercise exercise = (UserExercise) listItem.getTag();
        Intent intent = new Intent(this, ExerciseDetailsActivity.class); //NEW
        intent.putExtra("exercise", exercise);
        startActivity(intent); //NEW

    }
}
