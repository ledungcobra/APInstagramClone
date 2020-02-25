package com.example.apinstagramclone;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharePictureTab extends Fragment implements View.OnClickListener {

    private ImageView imgShare;
    private Button btnShareImage;
    private EditText edtDescription;


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
        FancyToast.makeText(getContext(),"Succes access",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
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
