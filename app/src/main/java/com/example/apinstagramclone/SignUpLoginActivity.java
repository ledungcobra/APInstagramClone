package com.example.apinstagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtUserNameSignup,edtUserNameLogin,edtUserPasswordSignUp,
                        edtUserPasswordLogin;
    Button btnSignUp,btnLogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        edtUserNameLogin = findViewById(R.id.edtUserNameLogin);
        edtUserNameSignup = findViewById(R.id.edtUserNameSignUp);
        edtUserPasswordLogin = findViewById(R.id.edtUserPasswordLogin);
        edtUserPasswordSignUp = findViewById(R.id.edtUserPasswordSignUp);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnSignUp:

                final ParseUser appUser =  new ParseUser();

                appUser.setUsername(edtUserNameSignup.getText().toString());
                appUser.setPassword(edtUserPasswordSignUp.getText().toString());
                        appUser.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e == null)
                                {
                                    FancyToast.makeText(SignUpLoginActivity.this,appUser.get("username")+" signup successfully"
                                            ,FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false)
                                            .show();

                                }else{
                                    FancyToast.makeText(SignUpLoginActivity.this,"Sign Up failed"
                                            ,FancyToast.LENGTH_SHORT,FancyToast.ERROR,false)
                                            .show();

                                }

                    }
                });


                break;
            case R.id.btnLogin:

               ParseUser.logInInBackground(edtUserNameLogin.getText().toString(), edtUserPasswordLogin.getText().toString(), new LogInCallback() {
                   @Override
                   public void done(ParseUser user, ParseException e) {

                       if(e == null && user!=null){

                           FancyToast.makeText(SignUpLoginActivity.this,user.get("username")+" login successfully :)"
                                   ,FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false)
                                   .show();


                       }else{
                           FancyToast.makeText(SignUpLoginActivity.this,e.getMessage()
                                   ,FancyToast.LENGTH_SHORT,FancyToast.ERROR,false)
                                   .show();
                       }

                   }
               });


                break;

        }
    }
}
