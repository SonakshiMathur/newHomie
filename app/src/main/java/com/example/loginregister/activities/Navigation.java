package com.example.loginregister.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.loginregister.R;
import com.example.loginregister.helpers.SharedPrefManager;
import com.example.loginregister.model.SPDetails;

public class Navigation extends AppCompatActivity {

    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    Intent intent;
    String username;
    String userPhone;
    String userEmail;
    SPDetails spDetails;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);
        spDetails= SharedPrefManager.getInstance(getApplicationContext()).User();
        intent=getIntent();
       // username=intent.getStringExtra("username");
        //userPhone=intent.getStringExtra("phone");
        //userEmail=intent.getStringExtra("email");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (NavigationView) findViewById(R.id.nav_view);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);



        //Bundle bundle=new Bundle();
        //bundle.putString("token",token);
        Fragment fragment = new ProfileFrag();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        mDrawerList.setCheckedItem(R.id.nav_dash);
        setTitle("Profile");

        mDrawerList.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                String title="";
                Fragment fragment = null;
                Bundle bundle=new Bundle();
               // bundle.putString("token",token);
                Log.i("in ac","in ac");

                switch (id) {
                    case R.id.nav_dash:
                        fragment = new ProfileFrag();
                        Log.i("frag","not null");
                        title="Profile";
                       // fragment.setArguments(bundle);
                        break;
                    case R.id.nav_payment:
                        fragment = new PaymentFrag();
                        title="Payment Details";
                       // fragment.setArguments(bundle);

                        break;
                    case R.id.nav_new:
                        fragment = new Maps_Fragment();
                        title="Map";
                        break;
                    case R.id.nav_ongoing:
                        break;
                    case R.id.nav_logout:
                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                        break;

                    case R.id.nav_hired:
                        break;
                        default:
                        break;
                }

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                    mDrawerList.setCheckedItem(id);
                    setTitle(title);
                    mDrawerLayout.closeDrawer(mDrawerList);

                } else {
                    Log.i("Main2Activity", "Error in creating fragment");
                }


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        ((TextView)findViewById(R.id.usersPhn)).setText(spDetails.getPhoneNumber().toString());
        ((TextView)findViewById(R.id.usersEmail)).setText(spDetails.getEmail().toString());
        ((TextView)findViewById(R.id.usersName)).setText(spDetails.getName().toString());

        return super.onCreateOptionsMenu(menu);
    }


}

