package com.codeclan.example.fitnesstrackerapp;

import android.app.Application;

import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.db.utils.DatabaseInitializer;

/**
 * Created by graemebrown on 20/03/2018.
 */

public class FitnessTrackerApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseInitializer.populateAsync(getDatabase());
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
