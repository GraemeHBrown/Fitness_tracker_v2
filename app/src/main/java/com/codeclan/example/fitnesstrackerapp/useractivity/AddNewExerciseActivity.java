package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class AddNewExerciseActivity extends AppCompatActivity {
    private AppDatabase db;
    private TextView exerciseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exercise);
        exerciseText = findViewById(R.id.exercise_text);

        db = AppDatabase.getInMemoryDatabase(getApplicationContext());

//        populateDb();
        fetchData();
    }

    private void fetchData() {
        // Note: this kind of logic should not be in an activity.
        StringBuilder sb = new StringBuilder();
        List<UserExercise> exercises = db.userExerciseDao().getAll();
        for (UserExercise exercise : exercises) {
            sb.append(String.format(Locale.UK,
                    "%s, %s", exercise.getDescription(), exercise.getStartDate()));
        }
        exerciseText.setText(sb);
    }
}
