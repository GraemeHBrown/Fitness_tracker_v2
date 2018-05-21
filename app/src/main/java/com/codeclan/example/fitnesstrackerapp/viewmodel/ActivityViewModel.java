package com.codeclan.example.fitnesstrackerapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.codeclan.example.fitnesstrackerapp.DataRepository;
import com.codeclan.example.fitnesstrackerapp.FitnessTrackerApp;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;

import java.util.List;

/**
 * Created by graemebrown on 17/03/2018.
 */

public class ActivityViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;
    private LiveData<List<Activity>> liveActivityList;

    public ActivityViewModel(@NonNull Application application) {
        super(application);
        dataRepository = ((FitnessTrackerApp) application).getRepository();
    }

    public LiveData<List<Activity>> getLiveActivityList() {
        if (liveActivityList == null) {
            liveActivityList = dataRepository.getActivityListLiveData();
        }
        return liveActivityList;
    }
}
