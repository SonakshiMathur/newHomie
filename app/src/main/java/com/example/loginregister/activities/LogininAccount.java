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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.loginregister.R;
import com.example.loginregister.helpers.InputValidation;
import com.example.loginregister.helpers.RequestHandler;
import com.example.loginregister.helpers.SharedPrefManager;
import com.example.loginregister.helpers.URLs;
import com.example.loginregister.model.SPDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LogininAccount extends AppCompatActivity {

    private InputValidation inputValidation;
    private final AppCompatActivity activity = LogininAccount.this;
    private AppCompatTextView loginappCompatTextViewLoginLink;

//    private TextInputLayout logintextInputLayoutName;
    private TextInputLayout logintextInputLayoutEmail;
    private TextInputLayout logintextInputLayoutPassword;

//    private TextInputEditText Login_FullName;
    private TextInputEditText Login_UserEmail;
    private TextInputEditText Login_textInputEditTextPassword;

    private AppCompatButton Login_appCompatButtonRegister;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginin_account);
        setTitle("Log-in into Account");

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, SP_MainScreen.class));
            return;
        }

        initViews();


        Login_appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        loginappCompatTextViewLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LogininAccount.this,CreateAccount.class);
                startActivity(i);
            }
        });



        inputValidation = new InputValidation(activity);



    }

    private void validation() {

//        if (!inputValidation.isInputEditTextFilled(Login_FullName, logintextInputLayoutName, getString(R.string.error_message_name))) {
//            return;
//        }

        if (!inputValidation.isInputEditTextEmail(Login_UserEmail, logintextInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(Login_textInputEditTextPassword, logintextInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }

        userLogin();

    }



    private void userLogin() {
        //first getting the values
        final String email = Login_UserEmail.getText().toString();
        final String password = Login_textInputEditTextPassword.getText().toString();


        //if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(LogininAccount.this);
                dialog.setMessage("Logging in..");
                dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                dialog.dismiss();
               Log.i("resp",Integer.toString(s.length()));

                try {

                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        SPDetails spDetails = new SPDetails(
                                userJson.getInt("id"),
                                userJson.getString("username"),
                                userJson.getString("email"),
                                userJson.getString("phone"),
                                userJson.getString("award"),
                                userJson.getString("idproof"),
                                userJson.getString("references"),
                                userJson.getString("work"),
                                userJson.getString("ratechart"),
                                userJson.getString("propic")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(spDetails);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), Navigation.class));
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

                //returning the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

    private void initViews() {

        Login_appCompatButtonRegister = (AppCompatButton)findViewById(R.id.Login_appCompatButtonRegister) ;

        loginappCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.loginappCompatTextViewLoginLink);

//        logintextInputLayoutName = (TextInputLayout) findViewById(R.id.logintextInputLayoutName) ;
        logintextInputLayoutEmail = (TextInputLayout) findViewById(R.id.logintextInputLayoutEmail) ;
        logintextInputLayoutPassword = (TextInputLayout) findViewById(R.id.logintextInputLayoutPassword) ;

//        Login_FullName = (TextInputEditText) findViewById(R.id.Login_FullName);
        Login_UserEmail = (TextInputEditText) findViewById(R.id.Login_UserEmail);
        Login_textInputEditTextPassword = (TextInputEditText) findViewById(R.id.Login_textInputEditTextPassword);
    }
}
