package com.example.loginregister.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.loginregister.activities.Who_are_you;
import com.example.loginregister.model.SPDetails;
import com.example.loginregister.model.UserDetails;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "sharedpref";
    private static final String SHARED_PREF_NAME_USER = "sharedprefuser";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_ID = "keyid";
    private static final String KEY_award = "award";
    private static final String KEY_idproof = "idproof";
    private static final String KEY_references = "references";
    private static final String KEY_work = "work";
    private static final String KEY_ratechart = "ratechart";
    private static final String KEY_propic="propic";
    private static final String KEY_Adress="adress";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

//    public void users_address()
//    {SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt(KEY_Adress, userDetails.getId());
//
//    }

    public void users_Login(UserDetails userDetails) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, userDetails.getId());
        editor.putString(KEY_USERNAME, userDetails.getName());
        editor.putString(KEY_EMAIL, userDetails.getEmail());
        editor.putString(KEY_PHONE, userDetails.getPhoneNumber());
//        editor.putString(KEY_award, spDetails.getAward());
//        editor.putString(KEY_idproof, spDetails.getIdproof());
//        editor.putString(KEY_references, spDetails.getReferences());
//        editor.putString(KEY_work, spDetails.getWorking());
//        editor.putString(KEY_ratechart, spDetails.getRatechart());
//        editor.putString(KEY_propic,spDetails.getProPic());
        editor.apply();
    }

    public UserDetails Users() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        return new UserDetails(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PHONE, null)
//                sharedPreferences.getString(KEY_award, null),
//                sharedPreferences.getString(KEY_idproof, null),
//                sharedPreferences.getString(KEY_references,null),
//                sharedPreferences.getString(KEY_work, null),
//                sharedPreferences.getString(KEY_ratechart, null),
//                sharedPreferences.getString(KEY_propic,null)

        );
    }

    public boolean isLoggedIn_users() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public void logout_users() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, Who_are_you.class));
    }




    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(SPDetails spDetails) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, spDetails.getId());
        editor.putString(KEY_USERNAME, spDetails.getName());
        editor.putString(KEY_EMAIL, spDetails.getEmail());
        editor.putString(KEY_PHONE, spDetails.getPhoneNumber());
        editor.putString(KEY_award, spDetails.getAward());
        editor.putString(KEY_idproof, spDetails.getIdproof());
        editor.putString(KEY_references, spDetails.getReferences());
        editor.putString(KEY_work, spDetails.getWorking());
        editor.putString(KEY_ratechart, spDetails.getRatechart());
        editor.putString(KEY_propic,spDetails.getProPic());
        editor.apply();
    }



    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public SPDetails User() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new SPDetails(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_award, null),
        sharedPreferences.getString(KEY_idproof, null),
        sharedPreferences.getString(KEY_references,null),
        sharedPreferences.getString(KEY_work, null),
        sharedPreferences.getString(KEY_ratechart, null),
                sharedPreferences.getString(KEY_propic,null)

        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, Who_are_you.class));
    }
}

