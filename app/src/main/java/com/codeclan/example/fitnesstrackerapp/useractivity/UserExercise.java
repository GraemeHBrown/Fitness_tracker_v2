package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;
import com.codeclan.example.fitnesstrackerapp.user.User;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by graemebrown on 27/01/2018.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = CASCADE),
        @ForeignKey(entity = Activity.class,
                parentColumns = "id",
                childColumns = "activity_id",
                onDelete = CASCADE),
        @ForeignKey(entity = Equipment.class,
                parentColumns = "id",
                childColumns = "equipment_id",
                onDelete = CASCADE)
        })
public class UserExercise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "activity_id")
    private int activityId;

    @ColumnInfo(name = "start_date")
    private Date startDateAndTime;

    private Long duration;

    private String description;

    @ColumnInfo(name = "equipment_id")
    private int equipmentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Date getStartDateAndTime() {
        return startDateAndTime;
    }

    public void setStartDateAndTime(Date startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
