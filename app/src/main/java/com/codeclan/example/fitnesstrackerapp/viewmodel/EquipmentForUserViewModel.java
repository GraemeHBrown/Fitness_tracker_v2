package com.codeclan.example.fitnesstrackerapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.codeclan.example.fitnesstrackerapp.FitnessTrackerApp;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;
import com.codeclan.example.fitnesstrackerapp.user.User;

import java.util.List;

/**
 * Created by graemebrown on 16/03/2018.
 */

public class EquipmentForUserViewModel extends AndroidViewModel {
    private final AppDatabase db;
    public final List<Equipment> userEquipment;

    public EquipmentForUserViewModel(@NonNull Application application) {
        super(application);
        db = ((FitnessTrackerApp) application).getDatabase();
        //TODO look at how to handle current app user - saved instance state??
        User appUser = db.userModel().getAll().get(0);
        userEquipment = db.equipmentModel().findAllEquipmentForUser(appUser.getId());
    }


}
