package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;

import java.util.List;

public class AddNewExerciseActivity extends AppCompatActivity implements ActivitySelectFragment.OnActivitySelectedListener {
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exercise);
    }

    @Override
    public void onActivitySelected(Activity activity) {
        Log.d("Selected type*******:", activity.toString());
    }
}
