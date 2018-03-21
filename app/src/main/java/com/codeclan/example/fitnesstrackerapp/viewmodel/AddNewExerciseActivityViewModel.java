package com.codeclan.example.fitnesstrackerapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.codeclan.example.fitnesstrackerapp.FitnessTrackerApp;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.user.User;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;

/**
 * Created by graemebrown on 19/03/2018.
 */

public class AddNewExerciseActivityViewModel extends AndroidViewModel{
    private final AppDatabase db;

    public AddNewExerciseActivityViewModel(@NonNull Application application) {
        super(application);
        db = ((FitnessTrackerApp) application).getDatabase();


    }

    public void doInsertAndSetIds(UserExercise exerciseToAdd){
        User appUser = db.userModel().getAll().get(0);
        exerciseToAdd.setUserId(appUser.getId());
        Long rowId = db.userExerciseModel().insertUserExercise(exerciseToAdd);
        exerciseToAdd.setId(rowId.intValue());
    }
}
