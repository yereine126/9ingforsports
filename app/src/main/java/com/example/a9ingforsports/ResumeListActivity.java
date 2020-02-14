package com.example.a9ingforsports;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResumeListActivity extends AppCompatActivity {

    ListView listview_apply;
    ArrayList<String> resumeList_apply = new ArrayList<String>();
    ArrayAdapter adapter_apply;
    private DatabaseReference mPostreference;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumelist);

        final Intent intent = getIntent();
        final String loginID = intent.getStringExtra("id");

        listview_apply = (ListView)findViewById(R.id.listview_apply);

        adapter_apply = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mPostreference = FirebaseDatabase.getInstance().getReference().child("resume");

        listview_apply.setAdapter(adapter_apply);
        getFirebaseDatabase();
        listview_apply.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String titleStr = (String)parent.getAdapter().getItem(position);
                mPostreference = FirebaseDatabase.getInstance().getReference();
                Map<String,Object> childUpdates = new HashMap<>();
                Map<String,Object> postValues = null;

                //joboffer job = new joboffer(category, contents, end_date, id, period, start_date, title, address, vnum);
                Apply apl = new Apply(titleStr);
                //postValues = job.toMap();
                postValues = apl.toMap();

                childUpdates.put("/applylist/"+ loginID, postValues);
                mPostreference.updateChildren(childUpdates);

                Toast.makeText(ResumeListActivity.this, "선택하신 이력서로 지원이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
    public void getFirebaseDatabase(){


        mPostreference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Resume res = new Resume();
                if (dataSnapshot.getValue(Resume.class) != null){
                    res = dataSnapshot.getValue(Resume.class);
                    Log.e("데이터를 주세요 ㅠㅠ 오네가이", dataSnapshot.getValue(Resume.class).toString());
                    resumeList_apply.clear();
                    resumeList_apply.add(res.title);
                }

                adapter_apply.addAll(resumeList_apply);
                adapter_apply.notifyDataSetChanged();
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