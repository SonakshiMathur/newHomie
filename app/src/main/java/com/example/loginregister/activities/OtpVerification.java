package com.example.loginregister.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.loginregister.R;

public class OtpVerification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        setTitle("Verification");
    }
    public void select(View view){
        Intent intent=new Intent(this,ListCategory.class);
        startActivity(intent);
    }
}
