package com.example.apinstagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUserName, edtPassword, edtEmail;

    private Button btnLogIn, btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up");

        edtUserName = findViewById(R.id.edtUserNameSignUp);
        edtPassword = findViewById(R.id.edtPasswordSignUp);
        edtEmail = findViewById(R.id.edtEmailSignUp);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    onClick(btnSignUp);


                }
                return false;
            }
        });

        btnLogIn = findViewById(R.id.btnLogin_SignUpActivity);
        btnSignUp = findViewById(R.id.btnSignUp_SignUpActivity);

        btnLogIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            Log.i("Login", "Executed");

            transitionToSocialMediaActivity();
            finish();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin_SignUpActivity:

                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);


                break;
            case R.id.btnSignUp_SignUpActivity:

                if (edtUserName.getText().toString().equals("") ||
                        edtEmail.getText().toString().equals("") ||
                        edtPassword.getText().toString().equals("")) {
                    FancyToast.makeText(SignUp.this, "Missing value"
                            , FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    return;

                }

                final ParseUser appUser = new ParseUser();
                appUser.setEmail(edtEmail.getText().toString());
                appUser.setUsername(edtUserName.getText().toString());
                appUser.setPassword(edtPassword.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setMessage("Signing up " + edtUserName.getText());


                progressDialog.show();


                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null) {

                            FancyToast.makeText(SignUp.this, appUser.getUsername(), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            transitionToSocialMediaActivity();

                        } else {
                            FancyToast.makeText(SignUp.this, "There was an error: " + e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();


                        }
                        progressDialog.dismiss();

                    }
                });


                break;


        }

    }

    public void rootLayoutTapped(View v) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }


    }

    private void transitionToSocialMediaActivity() {
        try {
            Intent intent = new Intent(SignUp.this, SocialMediaActivity.class);
            startActivity(intent);
        } catch (Exception e) {

        }


    }
}
