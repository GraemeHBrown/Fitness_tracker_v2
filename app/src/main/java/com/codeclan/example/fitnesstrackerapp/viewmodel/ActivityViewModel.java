package com.codeclan.example.fitnesstrackerapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.codeclan.example.fitnesstrackerapp.FitnessTrackerApp;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;

import java.util.List;

/**
 * Created by graemebrown on 17/03/2018.
 */

public class ActivityViewModel extends AndroidViewModel {
    public final List<Activity> activities;
//TODO use respository for db operations
    public ActivityViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = ((FitnessTrackerApp) application).getDatabase();
        activities = db.activityModel().getAll();
    }
}
