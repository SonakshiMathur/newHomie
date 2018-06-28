package com.example.loginregister.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregister.R;

public class User_AddlocationActivity extends AppCompatActivity {

    TextView ed1,ed2,ed3;

    AppCompatButton Addsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__addlocation);

        ed1 = (TextView) findViewById(R.id.textView5);
        ed2 = (TextView) findViewById(R.id.textView6);
        ed3 = (TextView) findViewById(R.id.textView7);

        Addsave = (AppCompatButton)findViewById(R.id.Addsave) ;

        Addsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(User_AddlocationActivity.this, "Address Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(User_AddlocationActivity.this,User_Navigation.class);
                startActivity(i);
                            }
        });

        ed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed1.setBackgroundColor(Color.CYAN);
            }
        });

        ed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed2.setBackgroundColor(Color.CYAN);
            }
        });

        ed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed3.setBackgroundColor(Color.CYAN);
            }
        });

    }

}
