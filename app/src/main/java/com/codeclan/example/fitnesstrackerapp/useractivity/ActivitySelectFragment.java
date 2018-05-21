package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.viewmodel.ActivityViewModel;

import java.util.List;

public class ActivitySelectFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private ActivityViewModel activityViewModel;
    private OnActivitySelectedListener activitySelectedListener;

    public ActivitySelectFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_activity_select, container, false);
        Spinner spinner = view.findViewById(R.id.activity_select_spinner);
        spinner.setOnItemSelectedListener(this);
        activityViewModel.getLiveActivityList().observe(this, new Observer<List<Activity>>() {
            @Override
            public void onChanged(@Nullable List<Activity> activityList) {
                if (activityList != null) {
                    ArrayAdapter<Activity> activityAdapter = setUpActivityAdapter(activityList);
                    activityAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinner.setAdapter(activityAdapter);

                }
            }
        });

        return view;
    }

    private ArrayAdapter<Activity> setUpActivityAdapter(List<Activity> activityList) {
        ArrayAdapter<Activity> activityAdapter = new ArrayAdapter<Activity>(getContext(), R.layout.support_simple_spinner_dropdown_item, activityList);
        activityAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return activityAdapter;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActivitySelectedListener) {
            activitySelectedListener = (OnActivitySelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnActivitySelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activitySelectedListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Activity selectedActivity = (Activity) parent.getItemAtPosition(position);
        Log.d("Selected activity:", String.valueOf(selectedActivity.getId()));
        activitySelectedListener.onActivitySelected(selectedActivity);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnActivitySelectedListener {
        void onActivitySelected(Activity activity);
    }


}
