package com.codeclan.example.fitnesstrackerapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.EquipmentDao;
import com.codeclan.example.fitnesstrackerapp.db.TestDatabase;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;
import com.codeclan.example.fitnesstrackerapp.user.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by graemebrown on 27/01/2018.
 */
@RunWith(AndroidJUnit4.class)
public class EquipmentTest {

    private EquipmentDao testEquipmentDao;
    private TestDatabase testDb;
    private User user1, user2;
    private Equipment equipment, equipment2;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        testDb = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        testEquipmentDao = testDb.equipmentDao();
        user1 = TestUtil.addUser(testDb, "Lance", "Armstrong", 47);
        user2 = TestUtil.addUser(testDb, "Graeme", "Brown", 46);
        equipment = TestUtil.addEquipment(testDb,"Trek","Road Bike","TCR", user1);
        equipment2 = TestUtil.addEquipment(testDb,"Trek","Mtn Bike","Fuel", user2);

    }

    @After
    public void closeDb() throws IOException {
        testDb.close();
    }

    @Test
    public void canGetListOfEquipmentForUser(){
        List<Equipment> foundEquipment = testEquipmentDao.findAllEquipmentForUser(user1.getId());
        assertEquals(1, foundEquipment.size());
    }

    @Test
    public void canFindEquipmentForUserByType(){
        List<Equipment> foundEquipment = testEquipmentDao.findAllUserEquipmentByType("Road Bike", user1.getId());
        assertEquals(equipment.getEquipmentType(), foundEquipment.get(0).getEquipmentType());
    }
}
