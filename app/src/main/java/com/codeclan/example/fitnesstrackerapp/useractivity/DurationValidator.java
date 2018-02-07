package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.widget.TextView;

/**
 * Created by graemebrown on 07/02/2018.
 */

public class DurationValidator extends TextValidator {

    public DurationValidator(TextView textView) {
        super(textView);
    }


    @Override
    public boolean validate(TextView textView) {
        if (textView.getText().toString().isEmpty()) {
            textView.setError(textView.getHint() + " is required!");
            return false;
        } else {
            textView.setError(null);
            return true;
        }

    }


}
