package com.codeclan.example.fitnesstrackerapp.db;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.codeclan.example.fitnesstrackerapp.AppExecutors;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.converter.Converters;
import com.codeclan.example.fitnesstrackerapp.db.utils.DatabaseInitializer;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;
import com.codeclan.example.fitnesstrackerapp.user.User;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;

import java.util.List;

/**
 * Created by graemebrown on 26/01/2018.
 */

@Database(entities = {User.class, Activity.class, Equipment.class, UserExercise.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    public static final String DATABASE_NAME = "fitness-tracker-db";
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public abstract UserDao userModel();

    public abstract ActivityDao activityModel();

    public abstract EquipmentDao equipmentModel();

    public abstract UserExerciseDao userExerciseModel();

    public static AppDatabase getInstance(Context context) {
        Log.d("In get instance", "inside");

        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context.getApplicationContext());
            INSTANCE.updateDatabaseCreated(context.getApplicationContext());
        }

        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        AsyncTask.execute(() -> {
                            Log.d("in build db", "build db");
                            // Generate the data for pre-population
                            AppDatabase database = AppDatabase.getInstance(appContext);

                            DatabaseInitializer.loadPrepopulatedData(database);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        Log.d("In setdb created:", "db created");
        mIsDatabaseCreated.postValue(true);
    }

    public static void destroyInstance() {
        Log.d("In destroy instancee;", "in");
        INSTANCE = null;
    }
}
