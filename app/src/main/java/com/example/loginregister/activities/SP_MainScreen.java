package com.example.loginregister.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.loginregister.R;
import com.example.loginregister.helpers.SharedPrefManager;
import com.example.loginregister.model.SPDetails;

public class SP_MainScreen extends AppCompatActivity {

    TextView textViewId,textViewUsername,textViewEmail,textViewGender;

    Button logggout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp__main_screen);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Who_are_you.class));
        }

        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewGender = (TextView) findViewById(R.id.textViewGender);
        logggout = (Button)findViewById(R.id.buttonLogout);

        SPDetails spDetails = SharedPrefManager.getInstance(this).User();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(spDetails.getId()));
        textViewUsername.setText(spDetails.getName());
        textViewEmail.setText(spDetails.getEmail());
        textViewGender.setText(spDetails.getPhoneNumber());

        logggout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });

    }
}
