package com.codeclan.example.fitnesstrackerapp.activity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by graemebrown on 26/01/2018.
 */
@Entity
public class Activity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "activity_name")
    private String activityName;

    @ColumnInfo(name = "activity_type")
    private String activityType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
}
