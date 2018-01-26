package com.codeclan.example.fitnesstrackerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.db.utils.DatabaseInitializer;
import com.codeclan.example.fitnesstrackerapp.user.User;

import java.util.List;
import java.util.Locale;


class MainActivity extends AppCompatActivity {

    private AppDatabase db;

    private TextView usersTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersTextView = findViewById(R.id.userView);

        db = AppDatabase.getInMemoryDatabase(getApplicationContext());

        populateDb();
        fetchData();
    }

    private void populateDb() {

        DatabaseInitializer.populateAsync(db);
    }

    private void fetchData() {
        // Note: this kind of logic should not be in an activity.
        StringBuilder sb = new StringBuilder();
        List<User> users = db.userDao().getAll();
        for (User user : users) {
            sb.append(String.format(Locale.UK,
                    "%s, %s, (ID:%d), %s \n", user.getFirstName(), user.getLastName(), user.getId(),user.getAge()));
        }
        usersTextView.setText(sb);
    }
}
