package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by graemebrown on 29/01/2018.
 */

public class TimeSelectFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private OnTimeSelectListener callbackListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTimeSelectListener) {
            callbackListener = (OnTimeSelectListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTimeSelectListener");
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d("Hour picked:", String.valueOf(hourOfDay));
        Log.d("Minute picked:", String.valueOf(minute));
        if(callbackListener !=null){
            callbackListener.onTimeSet(view,hourOfDay, minute);
        }
    }


    public interface OnTimeSelectListener {
        void onTimeSet(TimePicker view, int hourOfDay, int minute);
    }

}
