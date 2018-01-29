package com.codeclan.example.fitnesstrackerapp.db.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;
import com.codeclan.example.fitnesstrackerapp.user.User;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by graemebrown on 26/01/2018.
 */

public class DatabaseInitializer {

    public static void populateAsync(final AppDatabase db) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.doInBackground();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static UserExercise addUserExercise(final AppDatabase db, final User user, final Activity activity,
                                                final Date startDate, final Long duration,
                                                final Equipment equipment, final String description) {
        UserExercise exercise = new UserExercise();
        exercise.setUserId(user.getId());
        exercise.setActivityId(activity.getId());
        exercise.setStartDateAndTime(startDate);
        exercise.setDuration(duration);
        exercise.setEquipmentId(equipment.getId());
        exercise.setDescription(description);
        db.userExerciseDao().insertUserExercise(exercise);
        return exercise;
    }

    private static Equipment addEquipment(final AppDatabase db, final String equipmentMake,
                                          final String equipmentType, final String equipmentModel,
                                          final User user) {
        Equipment equipment = new Equipment();
        equipment.setEquipmentMake(equipmentMake);
        equipment.setEquipmentType(equipmentType);
        equipment.setEquipmentModel(equipmentModel);
        equipment.setUserId(user.getId());
        Long rowId = db.equipmentDao().insertEquipment(equipment);
        equipment.setId(rowId.intValue());
        return equipment;
    }

    private static User addUser(final AppDatabase db, final String name,
                                final String lastName, final Integer age) {
        User user = new User();
        user.setFirstName(name);
        user.setLastName(lastName);
        user.setAge(age);
        Long rowId = db.userDao().insertUser(user);
        user.setId(rowId.intValue());
        return user;
    }

    private static Activity addActivity(final AppDatabase db, final String name,
                                        final String type) {
        Activity activity = new Activity();
        activity.setActivityName(name);
        activity.setActivityType(type);
        Long rowId = db.activityDao().insertActivity(activity);
        activity.setId(rowId.intValue());
        return activity;
    }

    private static void populateWithTestData(AppDatabase db) {
        db.userExerciseDao().deleteAll();
        db.equipmentDao().deleteAll();
        db.activityDao().deleteAll();
        db.userDao().deleteAll();
        User user1 = addUser(db, "Lance", "Armstrong", 47);
        Activity roadBiking = addActivity(db, "Cycling", "Road biking");
        Activity mtnBiking = addActivity(db, "Cycling", "Mtn biking");
        Activity roadRunning = addActivity(db, "Running", "road running");
        Equipment equipment1 = addEquipment(db, "Giant", "Road bike", "TCR", user1);

        Date startTime = getToday();
        Log.d("start time:", String.valueOf(startTime));
        Date startTimePlusMins = getDatePlus(60);
        Log.d("start time plus:", String.valueOf(startTimePlusMins));

        addUserExercise(db, user1, roadBiking, startTime, 2L, equipment1, "This is an exercise...");
    }

    private static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    private static Date getDatePlus(long minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, (int) minutes);
        return calendar.getTime();
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }

}
