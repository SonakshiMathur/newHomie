package com.example.loginregister.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.loginregister.R;
import com.example.loginregister.helpers.InputValidation;
import com.example.loginregister.helpers.SharedPrefManager;
import com.example.loginregister.helpers.URLs;
import com.example.loginregister.model.SPDetails;
import com.example.loginregister.model.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Users_CreateAccount extends AppCompatActivity {

    private InputValidation inputValidation;
    private final AppCompatActivity activity = Users_CreateAccount.this;
    private AppCompatTextView Users_appCompatTextViewLoginLink;

    private TextInputLayout users_textInputLayoutName;
    private TextInputLayout users_textInputLayoutEmail;
    private TextInputLayout users_textInputLayoutCity;
    private TextInputLayout users_textInputLayoutPassword;
    private TextInputLayout users_textInputLayoutConfirmPassword;
    private TextInputLayout users_textInputLayoutMobile;


    private TextInputEditText Users_FullName;
    private TextInputEditText Users_UserEmail;
    private TextInputEditText Users_Number;
    private TextInputEditText Users_textInputEditTextPassword;
    private TextInputEditText Users_UserCity;
    private TextInputEditText Users_textInputEditTextConfirmPassword;
    private AppCompatButton Users_appCompatButtonRegister;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users__create_account);
        setTitle("Create an User account");

        if (SharedPrefManager.getInstance(this).isLoggedIn_users()) {
            finish();
            startActivity(new Intent(this, User_Navigation.class));
            return;
        }initViews();

        inputValidation = new InputValidation(activity);


        Users_appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();

            }
        });
        Users_appCompatTextViewLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Users_CreateAccount.this,Users_Login.class);
                startActivity(i);
            }
        });

    }

    private void initViews() {

        Users_appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.Users_appCompatTextViewLoginLink);

        users_textInputLayoutName = (TextInputLayout) findViewById(R.id.users_textInputLayoutName) ;
        users_textInputLayoutEmail = (TextInputLayout) findViewById(R.id.users_textInputLayoutEmail) ;
        users_textInputLayoutCity = (TextInputLayout) findViewById(R.id.users_textInputLayoutCity) ;
        users_textInputLayoutPassword = (TextInputLayout) findViewById(R.id.users_textInputLayoutPassword) ;
        users_textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.users_textInputLayoutConfirmPassword) ;
        users_textInputLayoutMobile = (TextInputLayout) findViewById(R.id.users_textInputLayoutMobile) ;

        Users_FullName = (TextInputEditText) findViewById(R.id.Users_FullName);
        Users_UserEmail = (TextInputEditText) findViewById(R.id.Users_UserEmail);
        Users_UserCity = (TextInputEditText) findViewById(R.id.Users_UserCity);
        Users_Number = (TextInputEditText) findViewById(R.id.Users_Number2);
        Users_textInputEditTextPassword = (TextInputEditText) findViewById(R.id.Users_textInputEditTextPassword);
        Users_textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.Users_textInputEditTextConfirmPassword);
        Users_appCompatButtonRegister = (AppCompatButton)findViewById(R.id.Users_appCompatButtonRegister) ;

    }

    private void validation() {

        if (!inputValidation.isInputEditTextFilled(Users_FullName, users_textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(Users_UserEmail, users_textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(Users_UserCity, users_textInputLayoutCity, getString(R.string.error_message_city))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(Users_textInputEditTextPassword, users_textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(Users_textInputEditTextConfirmPassword, users_textInputLayoutConfirmPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(Users_textInputEditTextPassword, Users_textInputEditTextConfirmPassword, users_textInputLayoutPassword, getString(R.string.error_message_passwordmatch))) {
            return;
        }
        if (!inputValidation.isInputEditTextphno(Users_Number, users_textInputLayoutMobile, getString(R.string.error_message_phno))) {
            return;
        }

        //IF validation is passed, user is registered else not registered.

        registerUser();

    }

    private void registerUser() {

        final String name = Users_FullName.getText().toString().trim();
        final String email = Users_UserEmail.getText().toString().trim();
        final String password = Users_textInputEditTextPassword.getText().toString().trim();
        final String phone = Users_Number.getText().toString().trim();


        class RegisterUser extends AsyncTask<Void, Void, String> {



            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
//                RequestHandler requestHandler = new RequestHandler();
//
//                //creating request parameters
//                HashMap<String, String> params = new HashMap<>();
//                params.put("username", name);
//                params.put("email", email);
//                params.put("password", password);
//                params.put("phone", phone);
//
//                //returing the response
                //        return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);

                String result="";
                try {
                    URL url = new URL(URLs.URL_REGISTER2);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    Uri.Builder builder = new Uri.Builder();
                    builder.appendQueryParameter("username", name);
                    builder.appendQueryParameter("email", email);
                    builder.appendQueryParameter("password", password);
                    builder.appendQueryParameter("phone", phone);
                    String query = builder.build().getEncodedQuery();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();

                    int response = conn.getResponseCode();
                    if (response == HttpURLConnection.HTTP_OK) {
                        InputStream bis = new BufferedInputStream(conn.getInputStream());
//                        JSONParsr jsonParser = new JSONParser();
//                        JSONObject jsonObject = (JSONObject)jsonParser.parse(
//                                new InputStreamReader(inputStream, "UTF-8"));
                        BufferedReader streamReader = new BufferedReader(new InputStreamReader(bis, "UTF-8"));
                        StringBuilder responseStrBuilder = new StringBuilder();

                        String inputStr;
                        while ((inputStr = streamReader.readLine()) != null)
                            responseStrBuilder.append(inputStr);

                        JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

                        result = jsonObject.toString();
                    }
                    conn.disconnect();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return result;
            }


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(Users_CreateAccount.this);
                dialog.setMessage("Registering..");
                dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                dialog.dismiss();

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        UserDetails userDetails = new UserDetails(
                                userJson.getInt("id"),
                                userJson.getString("username"),
                                userJson.getString("email"),
                                userJson.getString("phone")
                        );

                        Log.i("user", Integer.toString(userJson.getInt("id")));
                        Log.i("user1", userJson.getString("username"));
                        Log.i("user2",userJson.getString("email"));
                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).users_Login(userDetails);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), OtpVerfication_User.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        try {
            ru.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}