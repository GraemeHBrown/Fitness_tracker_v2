package com.codeclan.example.fitnesstrackerapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.codeclan.example.fitnesstrackerapp.DataRepository;
import com.codeclan.example.fitnesstrackerapp.FitnessTrackerApp;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;

import java.util.concurrent.ExecutionException;

/**
 * Created by graemebrown on 18/03/2018.
 */

public class ExerciseDetailsViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;

    public ExerciseDetailsViewModel(@NonNull Application application) {
        super(application);
        dataRepository = ((FitnessTrackerApp) application).getRepository();

    }

    public Activity getActivityForExercise(int activityId) {
        Activity activityForExercise = null;
        try {
            activityForExercise = dataRepository.getActivityDetailsForExercise(activityId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return activityForExercise;
    }

    public Equipment getEquipmentForExercise(Integer equipmentId) {
        Equipment foundEquipment = null;
        try {
            foundEquipment = dataRepository.getEquipmentDetailsForExercise(equipmentId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return foundEquipment;
    }


}

