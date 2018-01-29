package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNewExerciseActivity extends AppCompatActivity implements ActivitySelectFragment.OnActivitySelectedListener,
        DateSelectFragment.OnDateSetListener, TimeSelectFragment.OnTimeSelectListener {

    private AppDatabase db;
    private Locale locale;
    final Calendar calendar = Calendar.getInstance(locale);
    private TextView hourDurationTextView;
    UserExercise exerciseToAdd;
    Long minutesForCalc;
    private TextView minutesDurationTextView;
    private Long minutesConvertedFromHours;
    private Long minutesEntered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exercise);
        locale = getResources().getConfiguration().locale;
        hourDurationTextView = findViewById(R.id.duration_hours_input);
        minutesDurationTextView = findViewById(R.id.duration_input_minutes);
        addTextChangedListenerForHours(hourDurationTextView);
        addTextChangedListenerForMinutes(minutesDurationTextView);
    }

    private void addTextChangedListenerForMinutes(TextView view) {
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    minutesEntered = Long.valueOf(String.valueOf(s));
                    Log.d("Minutes entered:", minutesEntered.toString());
                }
            }
        });
    }


    @Override
    public void onActivitySelected(Activity activity) {
        Log.d("Selected type*******:", activity.toString());
    }

    public void showDatePickerDialog(View view) {
        DateSelectFragment newFragment = new DateSelectFragment();
        android.support.v4.app.FragmentManager man = getSupportFragmentManager();
        newFragment.show(man, "datePicker");
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
        showTimePickerDialog(view);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        Date selected = calendar.getTime();
        Log.d("Selected date and time:", String.valueOf(selected));
        Toast toast = Toast.makeText(getApplicationContext(), selected + " selected date and time", Toast.LENGTH_SHORT);
        toast.show();

    }

    public void onDurationSubmitButtonClick(View view){
        minutesForCalc = minutesConvertedFromHours+minutesEntered;
        Log.d("Minutes for calc:", minutesForCalc.toString());
        Toast toast = Toast.makeText(getApplicationContext(), "Total duration: "+minutesForCalc + " (mins)", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showTimePickerDialog(View view) {
        TimeSelectFragment newFragment = new TimeSelectFragment();
        android.support.v4.app.FragmentManager man = getSupportFragmentManager();
        newFragment.show(man, "timePicker");
    }

    private void addTextChangedListenerForHours(TextView view) {
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    Log.d("Hours entered: ", s.toString());
                    Integer hours = Integer.valueOf(String.valueOf(s));
                    minutesConvertedFromHours = calculateMinutesFromInput(hours);
                    Log.d("Converted minutes: ", minutesConvertedFromHours.toString());
                }

            }

        });
    }

    private Long calculateMinutesFromInput(Integer hours) {
        int mins = hours * 60;
        return Long.valueOf(mins);
    }
}
