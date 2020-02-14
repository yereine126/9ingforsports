package com.example.a9ingforsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ResumeActivity extends Activity {
    public String id = "oooo0004";
    String titleStr;
    String introStr;

    TextView name;
    TextView contact;
    TextView address;
    TextView email;
    TextView career_place;
    TextView career_data;
    TextView career_place2;
    TextView career_data2;

    EditText edit_title;
    EditText edit_introduction;
    Button btn_ok;

    private DatabaseReference mPostReference;

    Resume_userinfo userinfo = new Resume_userinfo();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makenewoffer_mypage);

        edit_title = (EditText)findViewById(R.id.editText);
        name = (TextView)findViewById(R.id.name_offerview);
        contact = (TextView)findViewById(R.id.contact_offerview);
        address = (TextView)findViewById(R.id.address_offerview);
        email = (TextView)findViewById(R.id.email_offerview);
        career_place = (TextView)findViewById(R.id.career_place);
        career_data = (TextView)findViewById(R.id.career_data);
        career_place2 = (TextView)findViewById(R.id.career_place2);
        career_data2 = (TextView)findViewById(R.id.career_data2);
        edit_introduction = (EditText)findViewById(R.id.editText4);
        btn_ok = (Button)findViewById(R.id.makeoffer_button);



        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("userinfo").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                userinfo = dataSnapshot.getValue(Resume_userinfo.class);
                if(key.equals("yogauser")) {
                    name.setText(userinfo.name);
                    contact.setText(userinfo.contact);
                    address.setText(userinfo.address);
                    email.setText(userinfo.email);
                    career_place.setText(userinfo.career_place);
                    career_data.setText(userinfo.career_data);
                    career_place2.setText(userinfo.career_place2);
                    career_data2.setText(userinfo.career_data2);
                }
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


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postFirebaseDatabase(true);

                Intent intent = new Intent(ResumeActivity.this, ResumeSettingActivity.class);
                startActivity(intent);

                finish();
            }
        });

    }
    public void postFirebaseDatabase(boolean add){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add){

            titleStr = edit_title.getText().toString();
            introStr = edit_introduction.getText().toString();
            Resume resume = new Resume(id, titleStr, introStr);
            postValues = resume.toMap();
        }
        childUpdates.put("/resume/" + id, postValues);
        mPostReference.updateChildren(childUpdates);
    }
}