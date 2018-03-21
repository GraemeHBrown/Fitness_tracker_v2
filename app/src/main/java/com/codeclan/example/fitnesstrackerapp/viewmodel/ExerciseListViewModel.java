package com.codeclan.example.fitnesstrackerapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;


import com.codeclan.example.fitnesstrackerapp.FitnessTrackerApp;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.user.User;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;

import java.util.List;

/**
 * Created by graemebrown on 18/03/2018.
 */

public class ExerciseListViewModel extends AndroidViewModel {

    private final AppDatabase db;
    public final List<UserExercise> allExerciseForUser;

    public ExerciseListViewModel(@NonNull Application application) {
        super(application);
        db = ((FitnessTrackerApp) application).getDatabase();
        User appUser = db.userModel().getAll().get(0);
        allExerciseForUser = db.userExerciseModel().findAllExerciseForUser(appUser.getId());
    }

    public Activity getActivityDetailsForCurrentExercise(int activityId){
        Activity currentActivity = db.activityModel().findByID(activityId);
        return currentActivity;
    }

}
