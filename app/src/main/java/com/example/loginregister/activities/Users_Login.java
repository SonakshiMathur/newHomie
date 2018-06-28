package com.example.loginregister.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.example.loginregister.R;
import com.example.loginregister.helpers.InputValidation;
import com.example.loginregister.helpers.RequestHandler;
import com.example.loginregister.helpers.SharedPrefManager;
import com.example.loginregister.helpers.URLs;
import com.example.loginregister.model.SPDetails;
import com.example.loginregister.model.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Users_Login extends AppCompatActivity {

    private InputValidation inputValidation;
    private final AppCompatActivity activity = Users_Login.this;
    private AppCompatTextView UserappCompatTextViewLoginLink;

    //    private TextInputLayout logintextInputLayoutName;
    private TextInputLayout usertextInputLayoutEmail;
    private TextInputLayout usertextInputLayoutPassword;

    //    private TextInputEditText Login_FullName;
    private TextInputEditText User_UserEmail;
    private TextInputEditText User_textInputEditTextPassword;

    private AppCompatButton User_appCompatButtonRegister;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users__login); setTitle("Log-in into Account");

        if (SharedPrefManager.getInstance(this).isLoggedIn_users()) {
            finish();
            startActivity(new Intent(this, User_Navigation.class));
            return;
        }

        initViews();


        User_appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        UserappCompatTextViewLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Users_Login.this,Users_CreateAccount.class);
                startActivity(i);
            }
        });



        inputValidation = new InputValidation(activity);



    }

    private void validation() {

//        if (!inputValidation.isInputEditTextFilled(Login_FullName, logintextInputLayoutName, getString(R.string.error_message_name))) {
//            return;
//        }

        if (!inputValidation.isInputEditTextEmail(User_UserEmail, usertextInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(User_textInputEditTextPassword, usertextInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }

        userLogin();

    }



    private void userLogin() {
        //first getting the values
        final String email = User_UserEmail.getText().toString();
        final String password = User_textInputEditTextPassword.getText().toString();


        //if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(Users_Login.this);
                dialog.setMessage("Loggin in..");
                dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
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

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).users_Login(userDetails);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), OtpVerfication_User.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN2, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

    private void initViews() {

        User_appCompatButtonRegister = (AppCompatButton)findViewById(R.id.User_appCompatButtonRegister) ;

        UserappCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.UserappCompatTextViewLoginLink);

//        logintextInputLayoutName = (TextInputLayout) findViewById(R.id.logintextInputLayoutName) ;
        usertextInputLayoutEmail = (TextInputLayout) findViewById(R.id.usertextInputLayoutEmail) ;
        usertextInputLayoutPassword = (TextInputLayout) findViewById(R.id.usertextInputLayoutPassword) ;

//        Login_FullName = (TextInputEditText) findViewById(R.id.Login_FullName);
        User_UserEmail = (TextInputEditText) findViewById(R.id.User_UserEmail);
        User_textInputEditTextPassword = (TextInputEditText) findViewById(R.id.User_textInputEditTextPassword);
    }
}
