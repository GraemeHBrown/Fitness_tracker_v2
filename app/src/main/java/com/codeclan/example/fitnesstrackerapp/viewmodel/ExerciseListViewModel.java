package com.codeclan.example.fitnesstrackerapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.codeclan.example.fitnesstrackerapp.DataRepository;
import com.codeclan.example.fitnesstrackerapp.FitnessTrackerApp;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.user.User;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by graemebrown on 18/03/2018.
 */

public class ExerciseListViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;
    private LiveData<List<UserExercise>> liveExerciseList;
    private final User appUser;

    public ExerciseListViewModel(@NonNull Application application) throws ExecutionException, InterruptedException {
        super(application);
        dataRepository = ((FitnessTrackerApp) application).getRepository();
        appUser = dataRepository.getAppUser();
        liveExerciseList = dataRepository.getAllExerciseForUserLiveData(appUser.getId());
    }

    public Activity getActivityDetailsForCurrentExercise(int activityId) {
        Activity currentActivity = null;
        try {
            currentActivity = dataRepository.getActivityDetailsForExercise(activityId);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return currentActivity;
    }

    public LiveData<List<UserExercise>> getLiveExerciseList() {
        if (liveExerciseList == null) {
            liveExerciseList = dataRepository.getAllExerciseForUserLiveData(appUser.getId());
        }
        return liveExerciseList;
    }

}
