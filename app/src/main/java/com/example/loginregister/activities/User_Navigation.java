package com.example.loginregister.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregister.R;
import com.example.loginregister.helpers.SharedPrefManager;
import com.example.loginregister.model.UserDetails;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

public class User_Navigation extends AppCompatActivity {


    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    UserDetails userDetails;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG = "TAG";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__navigation);
        userDetails= SharedPrefManager.getInstance(getApplicationContext()).Users();


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_user);
        mDrawerList = (NavigationView) findViewById(R.id.nav_view_user_user);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);


        Fragment fragment = new UserDashFrag();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.content_frame_user, fragment).commit();

        mDrawerList.setCheckedItem(R.id.nav_userdashboard);
        setTitle("User Dashboard");

        mDrawerList.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                String title = "";
                Fragment fragment = null;
                Bundle bundle = new Bundle();
                // bundle.putString("token",token);
                Log.i("in ac", "in ac");

                switch (id) {
                    case R.id.nav_userdashboard:
                        fragment = new UserDashFrag();
                        Log.i("frag", "not null");
                        title = "Dashboard";
                        // fragment.setArguments(bundle);
                        break;
                    case R.id.nav_userongoing:
                        title = "On going requests";
                        // fragment.setArguments(bundle);
                        break;
                    case R.id.nav_userdetails:
//                        fragment = new Maps_Fragment();
                        title = "Details";
                        break;
                    case R.id.nav_logout:
                        SharedPrefManager.getInstance(getApplicationContext()).logout_users();
                        break;
                    default:
                        break;
                }

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction().replace(R.id.content_frame_user, fragment).commit();

                    mDrawerList.setCheckedItem(id);
                    setTitle(title);
                    mDrawerLayout.closeDrawer(mDrawerList);

                } else {
                    Log.i("Main2Activity", "Error in creating fragment");
                }


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_user);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_user);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();

    }


    void setupDrawerToggle() {
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_profile,menu);

        ((TextView) findViewById(R.id.usersPhns)).setText(userDetails.getPhoneNumber().toString());
        ((TextView) findViewById(R.id.usersEmails)).setText(userDetails.getEmail().toString());
        ((TextView) findViewById(R.id.usersNames)).setText(userDetails.getName().toString());

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.action_location)
        {
            Intent i = new Intent(User_Navigation.this,UsersLocation.class);
            startActivity(i);
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
