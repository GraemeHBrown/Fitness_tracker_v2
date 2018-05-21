package com.codeclan.example.fitnesstrackerapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.codeclan.example.fitnesstrackerapp.DataRepository;
import com.codeclan.example.fitnesstrackerapp.FitnessTrackerApp;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.user.User;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;

import java.util.concurrent.ExecutionException;

/**
 * Created by graemebrown on 19/03/2018.
 */

public class AddNewExerciseActivityViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;

    public AddNewExerciseActivityViewModel(@NonNull Application application) {
        super(application);
        dataRepository = ((FitnessTrackerApp) application).getRepository();
    }

    public void doInsertAndSetIds(UserExercise exerciseToAdd) {
        User appUser = dataRepository.getAppUser();
        exerciseToAdd.setUserId(appUser.getId());
        Long rowId = null;
        try {
            rowId = dataRepository.insertUserExercise(exerciseToAdd);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        exerciseToAdd.setId(rowId.intValue());

    }
}
