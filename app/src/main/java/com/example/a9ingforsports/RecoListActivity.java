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

import java.util.ArrayList;

public class RecoListActivity extends Activity {

    ListView listView_reco;
    ListViewAdapter adapter_reco;

    static int reco_i = 1;

    private DatabaseReference mPostreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recolist);

        adapter_reco = new ListViewAdapter() ;

        //파이어베이스에서 글 제목들 불러와 글목록 작성
        mPostreference = FirebaseDatabase.getInstance().getReference().child("joboffer");
        reco_i=1;

        //리스트뷰 부분
        listView_reco = (ListView) findViewById(R.id.listview_reco);
        getFirebaseDatabase();
        listView_reco.setAdapter(adapter_reco);

        listView_reco.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle() ;

                Intent intent = new Intent(RecoListActivity.this, JobShowActivity.class);
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
                if(job.category.equals("yoga")) {

                    String start, end, total;
                    start = job.start_date;
                    end = job.end_date;
                    total = start + "  ~  " + end;

                    String iStr = Integer.toString(reco_i++);

                    adapter_reco.addItem(job.title, total, iStr, job.vnum);
                    adapter_reco.notifyDataSetChanged();
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
