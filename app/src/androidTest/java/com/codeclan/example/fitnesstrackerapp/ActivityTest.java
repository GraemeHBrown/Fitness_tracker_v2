package com.codeclan.example.fitnesstrackerapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.ActivityDao;
import com.codeclan.example.fitnesstrackerapp.db.TestDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by graemebrown on 26/01/2018.
 */
@RunWith(AndroidJUnit4.class)
public class ActivityTest {

    private ActivityDao testActivityDao;
    private TestDatabase testDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        testDb = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        testActivityDao = testDb.activityDao();
    }

    @After
    public void closeDb() throws IOException {
        testDb.close();
    }

    @Test
    public void activityHasName(){
        testActivityDao.deleteAll();
        Activity activity = TestUtil.addActivity(testDb, "Cycling", "Mtn biking");
        Activity foundActivity = testActivityDao.findByActivityName("Cycling");
        assertEquals(activity.getActivityName(), foundActivity.getActivityName());
    }

    @Test
    public void activityHasType(){
        testActivityDao.deleteAll();
        Activity activity = TestUtil.addActivity(testDb, "Cycling", "Mtn biking");
        Activity foundActivity = testActivityDao.findByActivityName("Cycling");
        assertEquals(activity.getActivityType(), foundActivity.getActivityType());
    }
}
