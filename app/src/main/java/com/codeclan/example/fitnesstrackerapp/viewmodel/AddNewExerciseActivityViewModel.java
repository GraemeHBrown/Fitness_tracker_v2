package com.codeclan.example.fitnesstrackerapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.user.User;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;

/**
 * Created by graemebrown on 19/03/2018.
 */

public class AddNewExerciseActivityViewModel extends AndroidViewModel{

    AppDatabase db = AppDatabase.getInMemoryDatabase(this.getApplication());

    public AddNewExerciseActivityViewModel(@NonNull Application application) {
        super(application);


    }

    public void doInsertAndSetIds(UserExercise exerciseToAdd){
        User appUser = db.userDao().getAll().get(0);
        exerciseToAdd.setUserId(appUser.getId());
        Long rowId = db.userExerciseDao().insertUserExercise(exerciseToAdd);
        exerciseToAdd.setId(rowId.intValue());
    }
}
