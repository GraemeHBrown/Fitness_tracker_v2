package com.codeclan.example.fitnesstrackerapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.codeclan.example.fitnesstrackerapp.DataRepository;
import com.codeclan.example.fitnesstrackerapp.FitnessTrackerApp;
import com.codeclan.example.fitnesstrackerapp.activity.Activity;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;
import com.codeclan.example.fitnesstrackerapp.user.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by graemebrown on 16/03/2018.
 */

public class EquipmentForUserViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;
    private LiveData<List<Equipment>> liveUserEquipment;

    public EquipmentForUserViewModel(@NonNull Application application) throws ExecutionException, InterruptedException {
        super(application);
        dataRepository = ((FitnessTrackerApp) application).getRepository();
    }

    public LiveData<List<Equipment>> getLiveUserEquipment() {
        User appUser = dataRepository.getAppUser();
        if (liveUserEquipment == null) {
            liveUserEquipment = dataRepository.getUserEquipmentLiveData(appUser);
        }
        return liveUserEquipment;
    }


}
