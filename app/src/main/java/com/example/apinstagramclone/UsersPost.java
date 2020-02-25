package com.example.apinstagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UsersPost extends AppCompatActivity {

    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);

        linearLayout = findViewById(R.id.linearLayout);



        Intent intent = getIntent();
        String userName = intent.getStringExtra("username");
        setTitle(userName + " 's Post");

        FancyToast.makeText(this, userName, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username", userName);

        parseQuery.orderByDescending("createdAt");
        final ProgressDialog progressDialog = new ProgressDialog(UsersPost.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    for (ParseObject post : objects) {
                        final TextView imageDescription = new TextView(UsersPost.this);
                        imageDescription.setText(post.get("image_des") + "");

                        ParseFile postPicture = (ParseFile) post.get("picture");

                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if (data != null && e == null) {

                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postImageView = new ImageView(UsersPost.this);
                                    LinearLayout.LayoutParams image_layout_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,   ViewGroup.LayoutParams.WRAP_CONTENT);
                                    image_layout_params.setMargins(10,10,10,10);

                                    postImageView.setLayoutParams(image_layout_params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
                                    textViewParams.setMargins(5,5,5,15);
                                    imageDescription.setLayoutParams(textViewParams);
                                    imageDescription.setGravity(Gravity.CENTER);
                                    imageDescription.setBackgroundColor(Color.YELLOW);
                                    imageDescription.setTextColor(Color.WHITE);
                                    imageDescription.setTextSize(30f);

                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(imageDescription);





                                }

                            }
                        });

                    }
                }
                progressDialog.dismiss();

            }
        });
    }
}
