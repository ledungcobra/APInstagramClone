package com.example.apinstagramclone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }

    public void tappedOnHelloWorld(View v) {


        final ParseObject boxer = new ParseObject("boxer");
        boxer.put("speed", 100);
        boxer.put("hands", 2);
        boxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                    Log.i("Call This","called");

                FancyToast
                        .makeText(SignUp.this,"Hello World !",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false)
                        .show();


            }
        });

    }
}
