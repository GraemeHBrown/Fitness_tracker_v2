package com.codeclan.example.fitnesstrackerapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codeclan.example.fitnesstrackerapp.DataRepository;
import com.codeclan.example.fitnesstrackerapp.FitnessTrackerApp;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.user.User;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by graemebrown on 19/03/2018.
 */

public class ExerciseStatsViewModel extends AndroidViewModel {
    private final AppDatabase db;
    private final DataRepository dataRepository;
    private LiveData<List<UserExercise>> liveExerciseList;
    private final User appUser;

    public ExerciseStatsViewModel(@NonNull Application application) throws ExecutionException, InterruptedException {
        super(application);
        db = ((FitnessTrackerApp) application).getDatabase();
        dataRepository = ((FitnessTrackerApp) application).getRepository();
        appUser = dataRepository.getAppUser();
        //TODO move this to repository
        liveExerciseList = db.userExerciseModel().findAllExerciseForUserLiveData(appUser.getId());

    }

    public List<UserExercise> getAllExerciseForUser() {
        List<UserExercise> allExercise = null;
        try {
            allExercise = dataRepository.loadAllExerciseForUser();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return allExercise;
    }


    public Activity getActivityForExercise(int activityId) {
        Activity activityForExercise = null;
        try {
            activityForExercise = dataRepository.getActivityDetailsForExercise(activityId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return activityForExercise;
    }


    public LiveData<List<UserExercise>> getLiveExerciseList() {
        if (liveExerciseList == null) {
            //TODO move the call to repository
            liveExerciseList = db.userExerciseModel().findAllExerciseForUserLiveData(appUser.getId());
        }
        return liveExerciseList;
    }
}
