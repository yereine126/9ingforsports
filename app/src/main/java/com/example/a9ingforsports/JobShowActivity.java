package com.example.a9ingforsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JobShowActivity extends Activity {


    TextView show_title;
    TextView show_howlong;
    TextView show_start;
    TextView show_end;
    TextView show_content;
    TextView show_category;
    TextView show_vnum;
    TextView show_address;
    TextView show_apply;

    joboffer job = new joboffer();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobshow);

        show_title = (TextView)findViewById(R.id.show_title);
        show_howlong = (TextView)findViewById(R.id.show_howlong);
        show_start = (TextView)findViewById(R.id.show_start);
        show_end = (TextView)findViewById(R.id.show_end);
        show_content = (TextView)findViewById(R.id.show_content);
        show_category = (TextView)findViewById(R.id.show_category);
        show_vnum = (TextView)findViewById(R.id.show_vnum);
        show_address = (TextView)findViewById(R.id.show_address);
        show_apply = (TextView)findViewById(R.id.show_apply);


        //글제목 받기~
        final Intent intent = getIntent();
        final String keytitle = intent.getStringExtra("key");
        final String loginID = intent.getStringExtra("id");

        show_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobShowActivity.this, ResumeListActivity.class);
                intent.putExtra("id", loginID);
                startActivity(intent);
            }
        });

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("joboffer").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



                job = dataSnapshot.getValue(joboffer.class);
                if(job.title.equals(keytitle)){

                    show_title.setText(job.title);
                    show_howlong.setText(job.period);
                    show_category.setText(job.category);
                    show_start.setText(job.start_date);
                    show_end.setText(job.end_date);
                    show_content.setText(job.contents);
                    String vnumStr = Integer.toString(job.vnum);
                    show_vnum.setText("조회수 : " + vnumStr);
                    show_address.setText(job.address);

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