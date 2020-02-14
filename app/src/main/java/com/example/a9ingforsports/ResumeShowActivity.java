package com.example.a9ingforsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResumeShowActivity extends Activity {
    TextView title;
    TextView name;
    TextView contact;
    TextView address;
    TextView email;
    TextView career_place;
    TextView career_data;
    TextView career_place2;
    TextView career_data2;
    TextView introduction;

    Resume_userinfo userinfo = new Resume_userinfo();
    Resume resume = new Resume();

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.resume_show);

        title = (TextView)findViewById(R.id.show_title);
        name = (TextView)findViewById(R.id.name_offerview);
        contact = (TextView)findViewById(R.id.contact_offerview);
        address = (TextView)findViewById(R.id.address_offerview);
        email = (TextView)findViewById(R.id.email_offerview);
        career_place = (TextView)findViewById(R.id.career_place);
        career_data = (TextView)findViewById(R.id.career_data);
        career_place2 = (TextView)findViewById(R.id.career_place2);
        career_data2 = (TextView)findViewById(R.id.career_data2);
        introduction = (TextView)findViewById(R.id.show_introduction);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("userinfo").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                userinfo = dataSnapshot.getValue(Resume_userinfo.class);
                if(key.equals("yogauser")){
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

        Intent intent = getIntent();
        final String keytitle = intent.getStringExtra("key");

        database.child("resume").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                resume = dataSnapshot.getValue(Resume.class);
                if(resume.title.equals(keytitle)){
                    title.setText(resume.title);
                    introduction.setText(resume.introduction);
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
    }

}