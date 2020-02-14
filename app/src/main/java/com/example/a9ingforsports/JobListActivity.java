package com.example.a9ingforsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.service.autofill.Dataset;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JobListActivity extends Activity{

    Button btn_post;
    ListView listView;
    ListViewAdapter adapter;
    static int i = 1;

    private DatabaseReference mPostreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joblist);

        final Intent intent = getIntent();

        adapter = new ListViewAdapter() ;

        //글 작성하기 액티비티로 전환
        btn_post = (Button) findViewById(R.id.btn_post);

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobListActivity.this, JobPostActivity.class);
                startActivity(intent);
            }
        });


        //파이어베이스에서 글 제목들 불러와 글목록 작성
        mPostreference = FirebaseDatabase.getInstance().getReference().child("joboffer");
        i=1;

        //리스트뷰 부분
        listView = (ListView) findViewById(R.id.listview);

        getFirebaseDatabase();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle() ;

                Intent intent = new Intent(view.getContext(), JobShowActivity.class);
                intent.putExtra("id",intent.getStringExtra("id"));
                intent.putExtra("key", titleStr);        //intent로 전송 (key로 받음)
                startActivity(intent);

            }
        });
    }

    public void getFirebaseDatabase(){

        mPostreference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                joboffer job = new joboffer();
                job = dataSnapshot.getValue(joboffer.class);

                String start, end, total;
                start = job.start_date;
                end = job.end_date;
                total = start + "  ~  " +end;

                String iStr = Integer.toString(i++);

                adapter.addItem(job.title, total, iStr, job.vnum);
                adapter.notifyDataSetChanged();

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
