package com.codeclan.example.fitnesstrackerapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.codeclan.example.fitnesstrackerapp.activity.Activity;

import java.util.List;

/**
 * Created by graemebrown on 26/01/2018.
 */
@Dao
public interface ActivityDao {

    @Query("SELECT * FROM activity")
    List<Activity> getAll();

    @Query("SELECT * FROM activity WHERE id = :activityId")
    Activity findByID(int activityId);

    @Query("SELECT * FROM activity WHERE id IN (:activityIds)")
    List<Activity> loadAllByIds(int[] activityIds);

    @Query("SELECT * FROM activity WHERE activity_name LIKE :activityName")
    Activity findByActivityName(String activityName);

    @Insert
    Long insertActivity(Activity activity);

    @Delete
    void delete(Activity activity);

    @Query("DELETE FROM Activity")
    void deleteAll();
}
