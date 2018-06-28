package com.example.loginregister.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.loginregister.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class UsersLocation extends AppCompatActivity {

    AppCompatButton addloc;

    String[] cityArray = {"Location 1","Location 2","Location 3","Location 4",
            "Location 5","Location 6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_location);

        addloc = (AppCompatButton)findViewById(R.id.User_addloc);


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_user);

        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_COUNTRY)
                .setCountry("IN") // NP for Nepal, US for USA, CA for Canada, JP for JAPAN, etc
                .build();
        autocompleteFragment.setFilter(autocompleteFilter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
//                Log.i(TAG, "Place: " + place.getName());
                String placename = place.getName().toString();
                Toast.makeText(UsersLocation.this, "Location selected "+placename, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UsersLocation.this,User_Navigation.class);
                startActivity(i);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
//                Log.i(TAG, "An error occurred: " + status);
            }
        });



        addloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UsersLocation.this,User_AddlocationActivity.class);
                startActivity(i);
            }
        });

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, cityArray);

        ListView listView = (ListView) findViewById(R.id.city_list);
        listView.setAdapter(adapter);


    }
}
