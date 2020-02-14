package com.example.a9ingforsports;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeActivity extends Activity {
    Button offer_button;
    Button recommand_button;
    Button board_button;
    ListView home_listview;
    ListViewAdapter adapter_home;
    static int i_home = 1;
    private DatabaseReference mPostreference;
    ViewFlipper powercall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        final Intent myintent=getIntent();
        Button shopping = (Button) findViewById(R.id.shopping_button);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ShoppingActivity.class);
                startActivity(intent);
            }
        });

        Button mypage = (Button) findViewById(R.id.mypage_button);
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MypageActivity.class);
                intent.putExtra("id",myintent.getStringExtra("id"));
                startActivity(intent);
            }
        });

        Button.OnClickListener onClickListener = new Button.OnClickListener(){

            @Override
            public void onClick(View view) {

                switch (view.getId()){
                    case R.id.offer_button :
                        Intent intent = new Intent(HomeActivity.this, JobListActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.recommand_button :
                        Intent intent2 = new Intent(HomeActivity.this, RecoListActivity.class);
                        startActivity(intent2);
                        break;

                }

            }

        };
        offer_button = (Button) findViewById(R.id.offer_button);
        offer_button.setOnClickListener(onClickListener);

        recommand_button = (Button) findViewById(R.id.recommand_button);
        recommand_button.setOnClickListener(onClickListener);

        board_button = (Button)findViewById(R.id.board_button);
        board_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FreeBoardListActivity.class);
                startActivity(intent);
            }
        });


        adapter_home = new ListViewAdapter() ;

        //파이어베이스에서 글 제목들 불러와 글목록 작성
        mPostreference = FirebaseDatabase.getInstance().getReference().child("joboffer");
        i_home=1;

        home_listview = (ListView) findViewById(R.id.home_listview);

        getFirebaseDatabase();

        home_listview.setAdapter(adapter_home);
        home_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle() ;

                Intent intent = new Intent(view.getContext(), JobShowActivity.class);
                intent.putExtra("key", titleStr);        //intent로 전송 (key로 받음)
                startActivity(intent);

            }
        });

        int images[] = {
                R.drawable.ad1,
                R.drawable.ad2,
                R.drawable.ad3
        };

        powercall = findViewById(R.id.image_slide);

        for(int image : images){
            flipperImages(image);
        }
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

                String iStr = Integer.toString(i_home++);

                adapter_home.addItem(job.title, total, iStr, job.vnum);
                adapter_home.notifyDataSetChanged();

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

    private void flipperImages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        powercall.addView(imageView); //이미지 추가
        powercall.setFlipInterval(3000); //시간 4초
        powercall.setAutoStart(true); //자동시작

        //애니메이션
        powercall.setInAnimation(this, android.R.anim.slide_in_left);
        powercall.setOutAnimation(this, android.R.anim.slide_out_right);
    }
}
