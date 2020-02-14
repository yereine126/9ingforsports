package com.example.a9ingforsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FreeBoardListActivity extends Activity {

    Button btn_post_free;
    ListView listView_free;
    ListViewAdapter adapter_free;
    static int i = 1;


    private DatabaseReference mPostreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelist);

        adapter_free = new ListViewAdapter() ;

        //글 작성하기 액티비티로 전환
        btn_post_free = (Button) findViewById(R.id.btn_post_free);

        btn_post_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FreeBoardListActivity.this, FreeBoardPostActivity.class);
                startActivity(intent);


                //finish();
            }
        });


        //파이어베이스에서 글 제목들 불러와 글목록 작성
        mPostreference = FirebaseDatabase.getInstance().getReference().child("eboard");
        i=1;

        //리스트뷰 부분
        //arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView_free = (ListView) findViewById(R.id.listview_free);
        //listView.setAdapter(arrayAdapter);

        getFirebaseDatabase();

        listView_free.setAdapter(adapter_free);

        listView_free.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle() ;

                Intent intent = new Intent(view.getContext(), FreeBoardShowActivity.class);
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
                //total = start + "  ~  " +end;
                total = "";

                String iStr = Integer.toString(i++);

                adapter_free.addItem(job.title, total, iStr, job.vnum);
                adapter_free.notifyDataSetChanged();

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