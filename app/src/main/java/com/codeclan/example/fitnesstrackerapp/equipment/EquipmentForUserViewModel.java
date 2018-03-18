package com.codeclan.example.fitnesstrackerapp.equipment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.user.User;

import java.util.List;

/**
 * Created by graemebrown on 16/03/2018.
 */

public class EquipmentForUserViewModel extends AndroidViewModel {

    public final List<Equipment> userEquipment;

    public EquipmentForUserViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInMemoryDatabase(this.getApplication());
        //TODO look at how to handle current app user - saved instance state??
        User appUser = db.userDao().getAll().get(0);
        userEquipment = db.equipmentModel().findAllEquipmentForUser(appUser.getId());
    }


}
