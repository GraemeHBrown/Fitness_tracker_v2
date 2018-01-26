package com.codeclan.example.fitnesstrackerapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.codeclan.example.fitnesstrackerapp.db.TestDatabase;
import com.codeclan.example.fitnesstrackerapp.db.UserDao;
import com.codeclan.example.fitnesstrackerapp.user.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;


import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UserTest {

    private UserDao testUserDao;
    private TestDatabase testDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        testDb = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        testUserDao = testDb.userDao();
    }

    @After
    public void closeDb() throws IOException {
        testDb.close();
    }

    @Test
    public void insertedUserHasFirstName() throws Exception {
        testUserDao.deleteAll();
        User user = TestUtil.addUser(testDb, "Fred", "Brookes", 54);
        User foundUser = testUserDao.findByName("Fred","Brookes");
        assertThat(foundUser.getFirstName(), equalTo(user.getFirstName()));
    }

    @Test
    public void insertedUserHasLastName() throws Exception {
        testUserDao.deleteAll();
        User user = TestUtil.addUser(testDb, "Fred", "Brookes", 54);
        User foundUser = testUserDao.findByName("Fred","Brookes");
        assertThat(foundUser.getLastName(), equalTo(user.getLastName()));
    }

    @Test
    public void userHasAge()throws Exception{
        testUserDao.deleteAll();
        User user = TestUtil.addUser(testDb, "Fred", "Brookes",54);
        User foundUser = testUserDao.findByName("Fred", "Brookes");
        assertEquals(user.getAge(), foundUser.getAge(),0);

    }
}



