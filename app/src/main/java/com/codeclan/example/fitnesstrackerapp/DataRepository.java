package com.codeclan.example.fitnesstrackerapp;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;
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

    public Long insertUserExercise(UserExercise exerciseToAdd) throws ExecutionException, InterruptedException {
        InsertUserExerciseAsyncTask task = new InsertUserExerciseAsyncTask(exerciseToAdd, mDatabase);
        Long rowId = task.execute().get();
        return rowId;
    }

    public LiveData<List<UserExercise>> getAllExerciseForUserLiveData(int userId) {
        LiveData<List<UserExercise>> liveExerciseList = mDatabase.userExerciseModel().findAllExerciseForUserLiveData(userId);
        return liveExerciseList;
    }

    public User getAppUser() {
        AppUserAsyncTask task = new AppUserAsyncTask(mDatabase);
        User appUser = null;
        try {
            appUser = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return appUser;
    }

    public Activity getActivityDetailsForExercise(int activityId) throws ExecutionException, InterruptedException {
        ActivityForExerciseAsyncTask task = new ActivityForExerciseAsyncTask(activityId, mDatabase);
        Activity loadedActivity = task.execute().get();
        return loadedActivity;
    }

    public Equipment getEquipmentDetailsForExercise(Integer equipmentId) throws ExecutionException, InterruptedException {
        EquipmentForExerciseAsyncTask task = new EquipmentForExerciseAsyncTask(equipmentId, mDatabase);
        Equipment foundEquiment = task.execute().get();
        return foundEquiment;
    }

    public LiveData<List<Activity>> getActivityListLiveData() {
        LiveData<List<Activity>> liveActivityList = mDatabase.activityModel().getAllActivitiesLiveData();
        return liveActivityList;
    }

    public LiveData<List<Equipment>> getUserEquipmentLiveData(User appUser) {
        LiveData<List<Equipment>> liveUserEquipmentList = mDatabase.equipmentModel().findAllEquipmentForUserLiveData(appUser.getId());
        return liveUserEquipmentList;
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

        AppUserAsyncTask(AppDatabase mDatabase) {
            this.mDatabase = mDatabase;
        }

        @Override
        protected User doInBackground(Void... params) {
            User appUser = mDatabase.userModel().getAll().get(0);
            return appUser;
        }
    }

    private static class EquipmentForExerciseAsyncTask extends AsyncTask<Void, Void, Equipment> {

        private final Integer equipmentId;
        private final AppDatabase mDatabase;

        EquipmentForExerciseAsyncTask(Integer equipmentId, AppDatabase mDatabase) {
            this.equipmentId = equipmentId;
            this.mDatabase = mDatabase;

        }

        @Override
        protected Equipment doInBackground(Void... voids) {
            Equipment foundEquipment = mDatabase.equipmentModel().findByID(equipmentId);
            return foundEquipment;
        }
    }

    private static class InsertUserExerciseAsyncTask extends AsyncTask<Void, Void, Long> {
        private final AppDatabase mDatabase;
        private final UserExercise exerciseToAdd;

        InsertUserExerciseAsyncTask(UserExercise exerciseToAdd, AppDatabase mDatabase){
            this.exerciseToAdd = exerciseToAdd;
            this.mDatabase = mDatabase;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            Long rowID = mDatabase.userExerciseModel().insertUserExercise(exerciseToAdd);
            return rowID;
        }

    }
}
