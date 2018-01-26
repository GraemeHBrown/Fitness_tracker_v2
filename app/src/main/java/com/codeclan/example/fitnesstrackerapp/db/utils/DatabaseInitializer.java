package com.codeclan.example.fitnesstrackerapp.db.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.user.User;

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

    private static void populateWithTestData(AppDatabase db) {
        db.userDao().deleteAll();
        addUser(db, "Fred", "Brookes", 54);
        addUser(db, "Ted", "Highes", 104);
        addActivity(db, "Cycling", "Mtn biking");

    }

    private static User addUser(final AppDatabase db, final String name,
                                final String lastName, final Integer age) {
        User user = new User();
        user.setFirstName(name);
        user.setLastName(lastName);
        user.setAge(age);
        db.userDao().insertUser(user);
        return user;
    }

    private static Activity addActivity(final AppDatabase db, final String name,
                                final String type) {
        Activity activity = new Activity();
        activity.setActivityName(name);
        activity.setActivityType(type);
        db.activityDao().insertActivity(activity);
        return activity;
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
