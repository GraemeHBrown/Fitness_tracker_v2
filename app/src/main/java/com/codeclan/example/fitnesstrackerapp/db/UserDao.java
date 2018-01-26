package com.codeclan.example.fitnesstrackerapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.codeclan.example.fitnesstrackerapp.user.User;

import java.util.List;

/**
 * Created by graemebrown on 26/01/2018.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertUser(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM User")
    void deleteAll();

}
