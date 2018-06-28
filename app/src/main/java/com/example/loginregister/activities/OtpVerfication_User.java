package com.example.loginregister.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.loginregister.R;

public class OtpVerfication_User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verfication__user);
        setTitle("Verification");
    }


    public void select_user(View view){
        Intent intent=new Intent(this,User_Navigation.class);
        startActivity(intent);
    }
}
