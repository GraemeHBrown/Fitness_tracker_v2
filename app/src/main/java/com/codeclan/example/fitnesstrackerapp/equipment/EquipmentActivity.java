package com.codeclan.example.fitnesstrackerapp.equipment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.user.User;

import java.util.List;
import java.util.Locale;

public class EquipmentActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView equipmentTextView;
    private TextView userDetailsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        equipmentTextView = findViewById(R.id.equipment_text_view);
        userDetailsTextView = findViewById(R.id.user_details_text_view);
        db = AppDatabase.getInMemoryDatabase(getApplicationContext());
        fetchData();
    }

    private void fetchData() {
        // Note: this kind of logic should not be in an activity.
        StringBuilder sb = new StringBuilder();
        User user = db.userDao().findUserById(1);
        String userDetails = user.getFirstName() + "'s equipment";
        userDetailsTextView.setText(userDetails);
        List<Equipment> equipments = db.equipmentDao().findAllEquipmentForUser(user.getId());
        for (Equipment equipment : equipments) {
            sb.append(String.format(Locale.UK,
                    "%s, %s, %s, (user_id:%d) \n", equipment.getEquipmentMake(),
                    equipment.getEquipmentType(), equipment.getEquipmentModel(),
                    equipment.getUserId()));
        }
        equipmentTextView.setText(sb);
    }
}
