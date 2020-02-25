package com.example.apinstagramclone;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

public class SocialMediaActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

        setTitle("Social Media App!!");

        toolBar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolBar);

        viewPager = findViewById(R.id.viewPager);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.postImageItem) {
            if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3000);


            } else {

                captureImage();


            }


        } else if (item.getItemId() == R.id.logoutUserItem) {

            ParseUser.logOut();
            finish();
            Intent intent = new Intent(SocialMediaActivity.this,SignUp.class);
            startActivity(intent);
            


        }

        return super.onOptionsItemSelected(item);
    }

    private void captureImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 4000);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 3000) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                captureImage();

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4000 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage;

            if (data != null) {


                try {

                    selectedImage = data.getData();
                    Bitmap receivedBitmapImage = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    receivedBitmapImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();

                    ParseFile parseFile = new ParseFile("pic.png", bytes);
                    ParseObject parseObject = new ParseObject("Photo");
                    parseObject.put("picture", parseFile);
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());

                    final ProgressDialog progressDialog = new ProgressDialog(SocialMediaActivity.this);
                    progressDialog.setMessage("Uploading....");
                    progressDialog.show();

                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {
                                FancyToast.makeText(SocialMediaActivity.this
                                        , "Uploading being completed"
                                        , FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();


                            } else {

                                FancyToast.makeText(SocialMediaActivity.this
                                        , e.getMessage()
                                        , FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                            }

                            progressDialog.dismiss();

                        }
                    });


                } catch (Exception e) {

                    e.printStackTrace();
                }

            }


        }

    }
}
