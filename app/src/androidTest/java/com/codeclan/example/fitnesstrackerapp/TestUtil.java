package com.codeclan.example.fitnesstrackerapp;

import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.db.TestDatabase;
import com.codeclan.example.fitnesstrackerapp.user.User;

/**
 * Created by graemebrown on 26/01/2018.
 */

class TestUtil {
    public static User addUser(final TestDatabase db, final String name,
                               final String lastName, final Integer age) {
        User user = new User();
        user.setFirstName(name);
        user.setLastName(lastName);
        user.setAge(age);
        db.userDao().insertUser(user);
        return user;
    }

    public static Activity addActivity(final TestDatabase db, final String activityName,
                               final String activityType) {
        Activity activity = new Activity();
        activity.setActivityName(activityName);
        activity.setActivityType(activityType);
        db.activityDao().insertActivity(activity);
        return activity;
    }
}
