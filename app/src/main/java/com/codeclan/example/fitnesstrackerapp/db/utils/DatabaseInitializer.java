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
    private static int dbPopulationCount = 0;
    public static void populateAsync(final AppDatabase db) {
//
        AsyncTask.execute(() -> {

            Log.d("In pop Async:", "run");
            loadPrepopulatedData(db);
        });
//        PopulateDbAsync task = new PopulateDbAsync(db);
//        task.doInBackground();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        if(dbPopulationCount<1){
            Log.i("In PopSync", "populating");
            loadPrepopulatedData(db);
            dbPopulationCount++;
        }

    }

    private static UserExercise addUserExercise(final AppDatabase db, final User user, final Activity activity,
                                                final Date startDate, final Long duration,
                                                final Equipment equipment, final String description, final Double distance) {
        UserExercise exercise = new UserExercise();
        exercise.setUserId(user.getId());
        exercise.setActivityId(activity.getId());
        exercise.setStartDateAndTime(startDate);
        exercise.setDuration(duration);
        if (equipment != null) {
            exercise.setEquipmentId(equipment.getId());
        }
        exercise.setDescription(description);
        exercise.setDistance(distance);
        db.userExerciseModel().insertUserExercise(exercise);
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
        Long rowId = db.equipmentModel().insertEquipment(equipment);
        equipment.setId(rowId.intValue());
        return equipment;
    }

    private static User addUser(final AppDatabase db, final String name,
                                final String lastName, final Integer age) {
        User user = new User();
        user.setFirstName(name);
        user.setLastName(lastName);
        user.setAge(age);
        Long rowId = db.userModel().insertUser(user);
        user.setId(rowId.intValue());
        return user;
    }

    private static Activity addActivity(final AppDatabase db, final String name,
                                        final String type) {
        Activity activity = new Activity();
        activity.setActivityName(name);
        activity.setActivityType(type);
        Long rowId = db.activityModel().insertActivity(activity);
        activity.setId(rowId.intValue());
        return activity;
    }

    public static void loadPrepopulatedData(AppDatabase db) {
        Log.d("In Load prepop:","inside");
        db.userExerciseModel().deleteAll();
        db.equipmentModel().deleteAll();
        db.activityModel().deleteAll();
        db.userModel().deleteAll();
        User user1 = addUser(db, "Lance", "Armstrong", 47);
        Activity roadBiking = addActivity(db, "Cycling", "Road Biking");
        Activity mtnBiking = addActivity(db, "Cycling", "Mtn Biking");
        Activity roadRunning = addActivity(db, "Running", "Road Running");
        Activity swimming = addActivity(db, "Swimming", "Swimming");
        Equipment equipment1 = addEquipment(db, "Trek", "Road bike", "TCR", user1);
        Equipment equipment2 = addEquipment(db, "Trek", "Road bike", "Madone", user1);
        Equipment equipment3 = addEquipment(db, "Trek", "Mountain Bike", "Fuel", user1);
        Equipment equipment4 = addEquipment(db, "Trek", "CX bike", "Crocket", user1);
        Equipment equipment5 = addEquipment(db, "Addidas", "Running shoe", "Boost", user1);
        Equipment equipment6 = addEquipment(db, "Swimovate", "Swimming watch", "Poolmate 2", user1);

        Date threeWeeksAgo = getTodayPlusDays(-21);
        Date oneWeekAgo = getTodayPlusDays(-7);
        Date yesterday = getTodayPlusDays(-1);
        Date twoDaysAgo = getTodayPlusDays(-2);
        Date twoWeeksAgo = getTodayPlusDays(-14);
//        Log.d("start time:", String.valueOf(startTime));
        Date startTimePlusMins = getDatePlusMinutes(60);
//        Log.d("start time plus:", String.valueOf(startTimePlusMins));

        addUserExercise(db, user1, roadBiking, oneWeekAgo, 120L, equipment1, "This is an exercise...", 45.0);
        addUserExercise(db, user1, roadRunning, threeWeeksAgo, 40L, null, "This is a road run...", 10.3);
        addUserExercise(db, user1, swimming, yesterday, 50L, equipment6, "This is a swim...", 4.0);
        addUserExercise(db, user1, mtnBiking, twoDaysAgo, 60L, equipment3, "Mtn biking", 45.7);
        addUserExercise(db, user1, mtnBiking, twoWeeksAgo, 120L, equipment3, "Mtn biking x1", 56.7);
    }

    private static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    private static Date getDatePlusMinutes(long minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, (int) minutes);
        return calendar.getTime();
    }

    private static Date getTodayPlusDays(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysAgo);
        return calendar.getTime();
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            loadPrepopulatedData(mDb);
            return null;
        }


    }

}
