package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;


import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.user.User;

import java.util.List;

/**
 * Created by graemebrown on 18/03/2018.
 */

public class ExerciseListViewModel extends AndroidViewModel {

    public final List<UserExercise> allExerciseForUser;

    public ExerciseListViewModel(@NonNull Application application) {
        super(application);

        AppDatabase db = AppDatabase.getInMemoryDatabase(this.getApplication());
        User appUser = db.userDao().getAll().get(0);
        allExerciseForUser = db.userExerciseDao().findAllExerciseForUser(appUser.getId());
    }
}
