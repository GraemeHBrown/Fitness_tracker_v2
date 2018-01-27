package com.codeclan.example.fitnesstrackerapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;
import com.codeclan.example.fitnesstrackerapp.user.User;

/**
 * Created by graemebrown on 26/01/2018.
 */

@Database(entities = {User.class, Activity.class, Equipment.class}, version = 1)
public abstract class TestDatabase extends RoomDatabase {

    private static TestDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract ActivityDao activityDao();
    public abstract EquipmentDao equipmentDao();

//    public static TestDatabase getInMemoryDatabase(Context context) {
//        if (INSTANCE == null) {
//            INSTANCE =
//                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), TestDatabase.class)
//                            // To simplify the codelab, allow queries on the main thread.
//                            // Don't do this on a real app! See PersistenceBasicSample for an example.
////                            .allowMainThreadQueries()
//                            .build();
//        }
//        return INSTANCE;
//    }
//
//    public static void destroyInstance() {
//        INSTANCE = null;
//    }
}

