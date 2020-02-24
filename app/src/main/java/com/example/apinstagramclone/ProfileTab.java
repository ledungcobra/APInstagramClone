package com.example.apinstagramclone;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {
    private EditText edtName,edtBio,edtProfession,edtHobbies,
            edtFavoriteSport;

    private Button btnUpdateInfo;




    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        edtName = view.findViewById(R.id.edtProfileName);
        edtBio = view.findViewById(R.id.edtProfileBio);
        edtFavoriteSport = view.findViewById(R.id.edtProfileFavoriteSport);
        edtProfession = view.findViewById(R.id.edtProfileProfession);
        edtHobbies = view.findViewById(R.id.edtProfileHobbies);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        setContentToEditText(edtBio,"profileBio");
        setContentToEditText(edtName,"profileName");
        setContentToEditText(edtProfession,"profileProfession");
        setContentToEditText(edtHobbies,"profileHobbies");
        setContentToEditText(edtFavoriteSport,"profileFavSport");



        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);


        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parseUser.put("profileName",edtName.getText()+"");
                parseUser.put("profileBio",edtBio.getText()+"");
                parseUser.put("profileProfession",edtProfession.getText()+"");
                parseUser.put("profileHobbies",edtHobbies.getText()+"");
                parseUser.put("profileFavSport",edtFavoriteSport.getText()+"");



                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            FancyToast.makeText(getContext(),
                                    "Info updated",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();

                        }else{
                            FancyToast.makeText(getContext(),
                                    e.getMessage(),FancyToast.LENGTH_SHORT
                                    ,FancyToast.ERROR,false).show();


                        }
                    }
                });

            }
        });

        return view;

    }
    private void setContentToEditText(EditText edt,String columnName){

        final ParseUser parseUser = ParseUser.getCurrentUser();
        if(parseUser.get(columnName)!=null) {
            edt.setText(parseUser.get(columnName).toString());
        }else{

            edt.setText("");

        }

    }


}
