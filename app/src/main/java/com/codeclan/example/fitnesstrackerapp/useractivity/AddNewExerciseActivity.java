package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.codeclan.example.fitnesstrackerapp.MainActivity;
import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.activity.ExerciseActivity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;
import com.codeclan.example.fitnesstrackerapp.user.User;

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
    private Long minutesConvertedFromHours = 0L;
    private Long minutesEntered =0L;
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

        Toolbar myToolbar = (Toolbar) findViewById(R.id.add_activity_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.view_exercise:
                Intent intentView = new Intent(this, ExerciseActivity.class);
                startActivity(intentView);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
        Toast toast = Toast.makeText(getApplicationContext(), "Total duration: " + minutesForCalc + " (mins)", Toast.LENGTH_SHORT);
        toast.show();
        exerciseToAdd.setDuration(minutesForCalc);
    }

    @Override
    public void onEquipmentSelected(Equipment equipment) {
        exerciseToAdd.setEquipmentId(equipment.getId());
    }

    public void onSubmitActivityClicked(View view){
        User appUser = db.userDao().getAll().get(0);
        exerciseToAdd.setUserId(appUser.getId());
        Long rowId = db.userExerciseDao().insertUserExercise(exerciseToAdd);
        exerciseToAdd.setId(rowId.intValue());
        Toast toast = Toast.makeText(getApplicationContext(), "New exercise added", Toast.LENGTH_SHORT);
        toast.show();
        Intent intentView = new Intent(this, ExerciseActivity.class);
        startActivity(intentView);
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

                if (s.length() != 0 && s!=null) {
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
