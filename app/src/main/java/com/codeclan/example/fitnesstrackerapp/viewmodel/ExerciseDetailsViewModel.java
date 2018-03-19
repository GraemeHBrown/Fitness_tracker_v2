package com.codeclan.example.fitnesstrackerapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;

/**
 * Created by graemebrown on 18/03/2018.
 */

public class ExerciseDetailsViewModel extends AndroidViewModel {

    AppDatabase db = AppDatabase.getInMemoryDatabase(this.getApplication());

    public ExerciseDetailsViewModel(@NonNull Application application) {
        super(application);

    }

    public Activity getActivityForExercise(int activityId) {
        Activity foundActivity = db.activityModel().findByID(activityId);
        return foundActivity;
    }

    public Equipment getEquipmentForExercise(Integer equipmentId) {
        Equipment foundEquipment = db.equipmentModel().findByID(equipmentId);
        return foundEquipment;
    }


}

