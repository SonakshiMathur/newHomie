package com.example.loginregister.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.loginregister.R;
import com.example.loginregister.adapters.GridAdapter;

public class UserDashFrag extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.customer_screen, container, false);
        int width = rootView.getWidth();
        CardView cv = rootView.findViewById(R.id.mycard1);
        cv.setMinimumWidth(width/2);
        CardView cv1 = rootView.findViewById(R.id.mycardv1);
        cv1.setMinimumWidth(width/2);

        return rootView;
    }
}