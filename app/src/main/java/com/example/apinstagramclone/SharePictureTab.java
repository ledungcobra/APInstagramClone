package com.example.apinstagramclone;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharePictureTab extends Fragment implements View.OnClickListener {

    private ImageView imgShare;
    private Button btnShareImage;
    private EditText edtDescription;
    private Bitmap receivedImageBitmap;



    public SharePictureTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        imgShare = v.findViewById(R.id.imgShare);
        btnShareImage = v.findViewById(R.id.btnShareImage);
        edtDescription = v.findViewById(R.id.edtDescription);

        imgShare.setOnClickListener(this);
        btnShareImage.setOnClickListener(this);


        return v;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imgShare:
                
                accessReadExternalPermission();
                
                break;

            case R.id.btnShareImage:

                if(receivedImageBitmap!=null){

                    if(!edtDescription.getText().equals("")){

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte [] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("pic.png",bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("image_des",edtDescription.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername().toString());

                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Uploading ....");
                        progressDialog.show();

                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e == null){

                                FancyToast.makeText(getContext(),"Uploading is completed"
                                        ,FancyToast.LENGTH_SHORT
                                        ,FancyToast.SUCCESS
                                        ,false).show();



                                }else{

                                    FancyToast.makeText(getContext(),e.getMessage()
                                            ,FancyToast.LENGTH_SHORT
                                            ,FancyToast.ERROR
                                            ,false).show();


                                }

                                progressDialog.dismiss();

                            }
                        });


                    }else{

                        FancyToast.makeText(getContext(),"You must enter description",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();

                    }


                }else{
                    //Toast.makeText(getContext(),"Must select image",Toast.LENGTH_SHORT).show();
                    FancyToast.makeText(getContext(),"You must select the image", FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();

                }


                break;
            default:


        }

    }

    private void accessReadExternalPermission() {
        if(android.os.Build.VERSION.SDK_INT>=23&&
                //Already granted permission
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
        ){


            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },1000);



        }else{

            getChosenImage();
            

        }
    }

    private void getChosenImage() {
        FancyToast.makeText(getContext(),"Success access",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2000){
             if(resultCode == Activity.RESULT_OK){
                 try{

                     Uri selectedImage = data.getData();
//                     String [] fileColumnPath = {MediaStore.Images.Media.DATA};
//                     Cursor cursor = getActivity().getContentResolver().query(selectedImage,fileColumnPath,null,null,null);
//
//                     cursor.moveToFirst();
//                     int columnIndex = cursor.getColumnIndex(fileColumnPath[0]);
//                     String picturePath = cursor.getString(columnIndex);
//                     Log.i("picturePath",picturePath);
//
//                     cursor.close();
                     receivedImageBitmap = BitmapFactory.
                             decodeStream(getActivity().
                                     getContentResolver().openInputStream(selectedImage));


                     imgShare.setImageBitmap(receivedImageBitmap);



                 }catch (Exception e){

                     e.printStackTrace();

                 }
             }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000){
            if(grantResults.length>0&&PackageManager.PERMISSION_GRANTED == grantResults[0]){

                getChosenImage();

            }
        }
    }
}
