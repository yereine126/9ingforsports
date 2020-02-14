package com.example.a9ingforsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResumeSettingActivity extends Activity {

    private ListView listView_resume;
    ArrayList<String> resumeList = new ArrayList<String>();
    ArrayAdapter adapter_resume;
    private DatabaseReference mPostreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myoffer_mypage);

        adapter_resume = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mPostreference = FirebaseDatabase.getInstance().getReference().child("resume");

        Button makenew = (Button) findViewById(R.id.makenewoffer_button);
        makenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResumeSettingActivity.this, ResumeActivity.class);
                startActivity(intent);
            }
        });

        listView_resume = (ListView) findViewById(R.id.resumelist);
        listView_resume.setAdapter(adapter_resume);
        getFirebaseDatabase();
        listView_resume.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String titleStr = (String)parent.getAdapter().getItem(position);
                Intent intent = new Intent(view.getContext(), ResumeShowActivity.class);
                intent.putExtra("key", titleStr);
                startActivity(intent);
            }
        });
    }
    public void getFirebaseDatabase(){

        mPostreference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Resume res = new Resume();
                res = dataSnapshot.getValue(Resume.class);
                resumeList.clear();
                resumeList.add(res.title);

                adapter_resume.addAll(resumeList);
                adapter_resume.notifyDataSetChanged();
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