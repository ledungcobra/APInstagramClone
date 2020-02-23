package com.example.apinstagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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



                break;
            case R.id.btnLogin:



                break;

        }
    }
}
