package com.example.apinstagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNameKickBoxer, edtThePunchSpeed, edtPunchPower, edtKickSpeed, edtKickPower;
    private Button btnSave;

    private TextView txtGetData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtNameKickBoxer = findViewById(R.id.edtNameKickBoxer);
        edtThePunchSpeed = findViewById(R.id.edtThePunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickPower = findViewById(R.id.edtKickPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        btnSave = findViewById(R.id.btnSave);

        txtGetData = findViewById(R.id.txtGetData);

//        txtGetData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Boxer");
//                parseQuery.getInBackground("ZHDymXFgME", new GetCallback<ParseObject>() {
//                    @Override
//                    public void done(ParseObject object, ParseException e) {
//                        if(object!=null&&e==null){
//                            txtGetData.setText(object.get("name")+"");
//
//                        }
//
//                    }
//                });
//            }
//        });


        btnSave.setOnClickListener(SignUp.this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                try {
                    final ParseObject boxer = new ParseObject("Boxer");

                    boxer.put("theName", edtNameKickBoxer.getText().toString());
                    boxer.put("thePunchPower", Integer.parseInt(edtThePunchSpeed.getText().toString()));
                    boxer.put("theKickPower", Integer.parseInt(edtKickPower.getText().toString()));
                    boxer.put("theKickSpeed", Integer.parseInt(edtKickSpeed.getText().toString()));
                    boxer.put("thePunchPower", Integer.parseInt(edtPunchPower.getText().toString()));

                    boxer.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this, "Done save to" + boxer.get("theName").toString(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            }else{
                              



                            }
                        }
                    });
                } catch (Exception e) {
                    FancyToast.makeText(SignUp.this, "Some thing wrong " + e.toString(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }


    }
}
