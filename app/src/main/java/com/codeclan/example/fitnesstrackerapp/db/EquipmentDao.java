package com.codeclan.example.fitnesstrackerapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by graemebrown on 27/01/2018.
 */
@Dao
public interface EquipmentDao {

    @Query("SELECT * FROM equipment")
    List<Equipment> getAll();

    @Query("SELECT * FROM equipment WHERE user_id = :userId")
    List<Equipment> findAllEquipmentForUser(int userId);

    @Query("SELECT * FROM equipment WHERE equipment_type = :equipmentType AND user_id = :userId")
    List<Equipment> findAllUserEquipmentByType(String equipmentType, int userId);

    @Insert
    Long insertEquipment(Equipment equipment);

    @Delete
    void delete(Equipment equipment);

    @Query("DELETE FROM equipment")
    void deleteAll();

    @Update(onConflict = REPLACE)
    void updateEquipment(Equipment equipment);

    @Query("SELECT * FROM equipment WHERE id = :equipmentId")
    Equipment findByID(int equipmentId);
}
