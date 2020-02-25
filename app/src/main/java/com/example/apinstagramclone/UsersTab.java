package com.example.apinstagramclone;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment {

    private ListView listView;
    private ArrayList arrayList;
    private ArrayAdapter arrayAdapter;





    public UsersTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user_tab, container, false);

        listView  = v.findViewById(R.id.listView);

        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arrayList);

        final TextView txtLoadingUsers = v.findViewById(R.id.txtLoadingUsers);



        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {


                if(e == null){
                    if(objects.size()>0){
                        for(ParseUser user:objects){

                            arrayList.add(user.getUsername());



                        }
                        listView.setAdapter(arrayAdapter);
                        txtLoadingUsers.animate().setDuration(1500).alpha(0.0f);
                        final CountDownTimer countDownTimer = new CountDownTimer(1500,500){

                            @Override
                            public void onTick(long millisUntilFinished) {



                            }

                            @Override
                            public void onFinish() {
                                txtLoadingUsers.setVisibility(View.INVISIBLE);
                                listView.setVisibility(View.VISIBLE);
                                listView.setRotation(180);

                                listView.setAlpha(0f);

                                listView.animate().setDuration(1000).alpha(1.0f).rotation(360);





                            }
                        }.start();


                    }
                }
            }
        });



        return v;

    }

}
