package com.codeclan.example.fitnesstrackerapp;

import android.os.AsyncTask;

import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.user.User;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by graemebrown on 20/03/2018.
 */

public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;

    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    public List<UserExercise> loadAllExerciseForUser() throws ExecutionException, InterruptedException {
        AllExerciseQueryAsyncTask task = new AllExerciseQueryAsyncTask(mDatabase);
        List<UserExercise> loadedExercise = task.execute().get();
        return loadedExercise;

    }

    public User getAppUser() throws ExecutionException, InterruptedException {
        AppUserAsyncTask task = new AppUserAsyncTask(mDatabase);
        User appUser = task.execute().get();
        return appUser;
    }

    public Activity getActivityDetailsForExercise(int activityId) throws ExecutionException, InterruptedException {
        ActivityForExerciseAsyncTask task = new ActivityForExerciseAsyncTask(activityId, mDatabase);
        Activity loadedActivity = task.execute().get();
        return loadedActivity;
    }

    private static class AllExerciseQueryAsyncTask extends AsyncTask<Void, Void, List<UserExercise>> {

        private final AppDatabase mDatabase;

        AllExerciseQueryAsyncTask(AppDatabase mDatabase) {
            this.mDatabase = mDatabase;
        }

        @Override
        protected List<UserExercise> doInBackground(final Void... params) {
            User appUser = mDatabase.userModel().getAll().get(0);
            List<UserExercise> exerciseForUser = mDatabase.userExerciseModel().findAllExerciseForUser(appUser.getId());
            return exerciseForUser;
        }

    }

    private static class ActivityForExerciseAsyncTask extends AsyncTask<Void, Void, Activity> {

        private final AppDatabase mDatabase;
        private int activityId;

        ActivityForExerciseAsyncTask(int activityId, AppDatabase mDatabase) {
            this.activityId = activityId;
            this.mDatabase = mDatabase;
        }

        @Override
        protected Activity doInBackground(final Void... params) {
            Activity foundActivity = mDatabase.activityModel().findByID(activityId);
            return foundActivity;
        }
    }

    private static class AppUserAsyncTask extends AsyncTask<Void, Void, User> {
        private final AppDatabase mDatabase;

        public AppUserAsyncTask(AppDatabase mDatabase) {
            this.mDatabase = mDatabase;
        }

        @Override
        protected User doInBackground(Void... params) {
            User appUser = mDatabase.userModel().getAll().get(0);
            return appUser;
        }
    }
}
