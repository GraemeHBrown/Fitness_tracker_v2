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
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNewExerciseActivity extends AppCompatActivity implements ActivitySelectFragment.OnActivitySelectedListener,
        DateSelectFragment.OnDateSetListener, TimeSelectFragment.OnTimeSelectListener, EquipmentSelectFragment.OnEquipmentSelectedListener {

    private AppDatabase db;
    private Locale locale;
    final Calendar calendar = Calendar.getInstance(locale);
    private TextView hourDurationTextView;
    UserExercise exerciseToAdd;
    Long minutesForCalc;
    Double distance;
    private TextView minutesDurationTextView;
    private Long minutesConvertedFromHours;
    private Long minutesEntered;
    String description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInMemoryDatabase(getApplicationContext());
        exerciseToAdd = new UserExercise();
        setContentView(R.layout.activity_add_new_exercise);
        locale = getResources().getConfiguration().locale;
        hourDurationTextView = findViewById(R.id.duration_hours_input);
        minutesDurationTextView = findViewById(R.id.duration_input_minutes);
        TextView distanceInput = findViewById(R.id.distance_input);
        TextView descriptionInput = findViewById(R.id.description_text_view);
        addTextChangedListenerForDescriptionInput(descriptionInput);
        addTextChangeListenerForDistance(distanceInput);
        addTextChangedListenerForHours(hourDurationTextView);
        addTextChangedListenerForMinutes(minutesDurationTextView);
    }


    @Override
    public void onActivitySelected(Activity activity) {
        Log.d("Selected type*******:", activity.toString());
        exerciseToAdd.setActivityId(activity.getId());
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
        exerciseToAdd.setStartDateAndTime(calendar.getTime());

    }

    public void onDurationSubmitButtonClick(View view) {
        minutesForCalc = minutesConvertedFromHours + minutesEntered;
        Log.d("Minutes for calc:", minutesForCalc.toString());
        Toast toast = Toast.makeText(getApplicationContext(), "Total duration: " + minutesForCalc + " (mins)", Toast.LENGTH_SHORT);
        toast.show();
        exerciseToAdd.setDuration(minutesForCalc);
    }

    @Override
    public void onEquipmentSelected(Equipment equipment) {
        Log.d("Equipment selected:", equipment.toString());
        exerciseToAdd.setEquipmentId(equipment.getId());
    }

    public void onSubmitActivityClicked(View view){
        exerciseToAdd.setUserId(1);
        Long rowId = db.userExerciseDao().insertUserExercise(exerciseToAdd);
        exerciseToAdd.setId(rowId.intValue());
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

    private void addTextChangedListenerForDescriptionInput(TextView descriptionInput) {
        descriptionInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    description = String.valueOf(s);
                    Log.d("Description entered:", description);
                    exerciseToAdd.setDescription(description);
                }
            }
        });
    }

    private void addTextChangeListenerForDistance(final TextView distanceInput) {
        distanceInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    distance = Double.valueOf(String.valueOf(s));
                    Log.d("Distance entered:", distance.toString());
                    exerciseToAdd.setDistance(distance);
                }
            }
        });
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


    private Long calculateMinutesFromInput(Integer hours) {
        int mins = hours * 60;
        return Long.valueOf(mins);
    }


}
