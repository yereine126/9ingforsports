package com.example.a9ingforsports;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferViewActivity2 extends Activity {


    private DatabaseReference mPostreference;
    TextView offertextView5, offertextView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offerview_mypage2);


        offertextView5 = (TextView)findViewById(R.id.offertextView5);
        offertextView6 = (TextView)findViewById(R.id.offertextView6);


        mPostreference = FirebaseDatabase.getInstance().getReference().child("applylist");

        getFirebaseDatabase();




    }
    public void getFirebaseDatabase(){


        mPostreference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String id = "user1";
                Apply apl = dataSnapshot.getValue(Apply.class);

                offertextView5.setText(id);
                offertextView6.setText(apl.title);



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}