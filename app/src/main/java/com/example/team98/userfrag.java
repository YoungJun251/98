package com.example.team98;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.team98.info.userinfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class userfrag extends Fragment {
    private String id;
    private String name;
    private String pnum;
    DataSnapshot m;
    userinfo user ;
    private DatabaseReference mDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.mypage, container, false);

        Bundle bundle = getArguments();
//        id = bundle.getString("id");
        name = bundle.getString("name");


        mDatabase = FirebaseDatabase.getInstance().getReference("User");

        //user = readUser();
        ((TextView)rootView.findViewById(R.id.nameText)).setText(name);
        ((TextView)rootView.findViewById(R.id.u_idtext)).setText(bundle.getString("ID"));
        ((TextView)rootView.findViewById(R.id.authorizationText)).setText(bundle.getString("author"));
        ((TextView)rootView.findViewById(R.id.u_numberText)).setText(bundle.getString("snum"));
        ((TextView)rootView.findViewById(R.id.u_grouptext)).setText(bundle.getString("UNI"));
        ((TextView)rootView.findViewById(R.id.authenticationText)).setText(bundle.getString("pass"));
        ((TextView)rootView.findViewById(R.id.cdateText)).setText(bundle.getString("cdate"));
        return rootView;
    }

        public static userfrag newInstance() {
        userfrag fragment = new userfrag();

        /*Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        */
        return fragment;

    }


}