package com.example.apinstagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");


        edtEmail = findViewById(R.id.edtEmailLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        btnSignUp = findViewById(R.id.btnSignUp_LoginActivity);
        btnLogin = findViewById(R.id.btnLogin_LoginActivity);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(btnLogin);


                }
                return false;
            }
        });


        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {

           transitionToSocialMediaActivity();
           finish();


        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin_LoginActivity:
                if (edtEmail.getText().toString().equals("") || edtPassword.getText().toString().equals("")) {
                    FancyToast.makeText(LoginActivity.this
                            , "Username and password must be required", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false)
                            .show();
                }

                ParseUser.logInInBackground(edtEmail.getText().toString(), edtPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
                            FancyToast.makeText(LoginActivity.this, "User name: " + user.getUsername()
                                    , FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            transitionToSocialMediaActivity();
                        }
                    }
                });


                break;
            case R.id.btnSignUp_LoginActivity:
                Intent intent = new Intent(LoginActivity.this,SignUp.class);
                startActivity(intent);

                break;
        }

    }

    public void rootLayoutOnTapped(View v){

        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

        }catch (Exception e){

            e.printStackTrace();

        }

    }

    private void transitionToSocialMediaActivity(){

        Intent intent = new Intent(LoginActivity.this,SocialMediaActivity.class);
        startActivity(intent);

    }
}
