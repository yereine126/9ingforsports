package com.example.a9ingforsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FreeBoardShowActivity extends Activity {


    TextView show_title_free;
    TextView show_content_free;
    TextView show_vnum_free;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeshow);

        show_title_free = (TextView)findViewById(R.id.show_title_free);
        show_content_free = (TextView)findViewById(R.id.show_content_free);
        show_vnum_free = (TextView)findViewById(R.id.show_vnum_free);


        //글제목 받기~
        Intent intent = getIntent();
        final String keytitle = intent.getStringExtra("key");


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("eboard").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                joboffer job_free = new joboffer();

                job_free = dataSnapshot.getValue(joboffer.class);
                if(job_free.title.equals(keytitle)){

                    show_title_free.setText(job_free.title);
                    show_content_free.setText(job_free.contents);
                    String vnumStr = Integer.toString(job_free.vnum);
                    show_vnum_free.setText("조회수 : " + vnumStr);
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