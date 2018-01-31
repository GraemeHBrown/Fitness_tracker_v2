package com.codeclan.example.fitnesstrackerapp.equipment;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;

import com.codeclan.example.fitnesstrackerapp.user.User;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by graemebrown on 27/01/2018.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = CASCADE
        )})
public class Equipment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "equipment_make")
    private String equipmentMake;

    @ColumnInfo(name = "equipment_type")
    private String equipmentType;

    @ColumnInfo(name = "equipment_model")
    private String equipmentModel;

    @ColumnInfo(name = "user_id")
    private Integer userId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquipmentMake() {
        return equipmentMake;
    }

    public void setEquipmentMake(String equipmentMake) {
        this.equipmentMake = equipmentMake;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(String equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString(){
        return this.equipmentModel;
    }

    public String getFullEquipmentName() {
        return equipmentMake+ ", " + equipmentModel+", "+ equipmentType;
    }
}
