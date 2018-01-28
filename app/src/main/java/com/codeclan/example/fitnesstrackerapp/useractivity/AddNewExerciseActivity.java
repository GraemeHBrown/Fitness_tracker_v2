package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewExerciseActivity extends AppCompatActivity implements ActivitySelectFragment.OnActivitySelectedListener, DateSelectFragment.OnDateSetListener {
    private AppDatabase db;
    private Locale locale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exercise);
        locale = getResources().getConfiguration().locale;
    }

    @Override
    public void onActivitySelected(Activity activity) {
        Log.d("Selected type*******:", activity.toString());
        Toast toast = Toast.makeText(getApplicationContext(), activity.getActivityType() + " selected", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showDatePickerDialog(View view) {
        DateSelectFragment newFragment = new DateSelectFragment();
        android.support.v4.app.FragmentManager man = getSupportFragmentManager();
        newFragment.show(man, "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance(locale);
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        calendar.set(year, month, dayOfMonth);
        String dateStringToDisplay = dateFormat.format(calendar.getTime());
        Toast toast = Toast.makeText(getApplicationContext(), dateStringToDisplay + " selected", Toast.LENGTH_SHORT);
        toast.show();
    }
}
