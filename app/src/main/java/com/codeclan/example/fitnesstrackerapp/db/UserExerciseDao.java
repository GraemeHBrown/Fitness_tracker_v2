package com.codeclan.example.fitnesstrackerapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;
import com.codeclan.example.fitnesstrackerapp.user.User;
import com.codeclan.example.fitnesstrackerapp.useractivity.UserExercise;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by graemebrown on 27/01/2018.
 */
@Dao
public interface UserExerciseDao {

    @Query("SELECT * FROM userexercise")
    List<UserExercise> getAll();

    @Query("SELECT * FROM userexercise WHERE user_id = :userId")
    List<UserExercise> findAllExerciseForUser(int userId);

    @Query("SELECT * FROM userexercise INNER JOIN Activity ON userexercise.activity_id = Activity.id WHERE Activity.activity_type LIKE :activityType AND userexercise.user_id = :userId  ")
    List<UserExercise> findAllExerciseForUserByActivity(String activityType, int userId);

    @Insert
    void insertUserExercise(UserExercise exercise);

    @Delete
    void delete(UserExercise exercise);

    @Query("DELETE FROM userexercise")
    void deleteAll();

    @Update(onConflict = REPLACE)
    void updateUserExercise(UserExercise exercise);

}
