package com.codeclan.example.fitnesstrackerapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.user.User;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;

import java.util.List;

/**
 * Created by graemebrown on 19/03/2018.
 */

public class ExerciseStatsViewModel extends AndroidViewModel {

    AppDatabase db = AppDatabase.getInMemoryDatabase(this.getApplication());

    public ExerciseStatsViewModel(@NonNull Application application) {
        super(application);

    }


    public List<UserExercise> getAllExerciseForUser() {
        User appUser = db.userDao().getAll().get(0);
        List<UserExercise> allExercise = db.userExerciseDao().findAllExerciseForUser(appUser.getId());
        return allExercise;
    }

    public Activity getActivityForExercise(int activityId) {
        Activity foundActivity = db.activityModel().findByID(activityId);
        return foundActivity;
    }
}
