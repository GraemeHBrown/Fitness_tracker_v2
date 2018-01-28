package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;


public class DateSelectFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private OnDateSetListener callbackListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Log.d("Day picked:", String.valueOf(day));
        Log.d("Month picked:", String.valueOf(month));
        if(callbackListener !=null){
            callbackListener.onDateSet(view,year,month,day);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDateSetListener) {
            callbackListener = (OnDateSetListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDateSetListener");
        }
    }


    public interface OnDateSetListener {
        void onDateSet(DatePicker view, int year, int month, int dayOfMonth);
    }
}
