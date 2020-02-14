package com.example.a9ingforsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FreeBoardPostActivity extends Activity implements View.OnClickListener{

    private DatabaseReference mPostReference;

    //joboffer 요소
    String category;
    String contents;
    String end_date;
    String id;
    String period;
    String start_date;
    String title;
    String address;
    int vnum;

    Button btn_insert_free;
    EditText edit_title_free;
    EditText edit_content_free;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freepost);

        btn_insert_free = (Button) findViewById(R.id.btn_insert_free);
        btn_insert_free.setOnClickListener(this);

        edit_title_free = (EditText) findViewById(R.id.edit_title_free);
        edit_content_free = (EditText) findViewById(R.id.edit_content_free);

    }


    public void postFirebaseDatabase(boolean add){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String,Object> childUpdates = new HashMap<>();
        Map<String,Object> postValues = null;
        if(add){

            joboffer job = new joboffer(category, contents, end_date, id, period, start_date, title, address, vnum);
            postValues = job.toMap();
        }
        childUpdates.put("/eboard/"+ id, postValues);
        mPostReference.updateChildren(childUpdates);
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_insert_free:

                Random rnd = new Random();

                int p = rnd.nextInt(500); // 0 <= p < 500

                vnum = p;
                String pp = String.valueOf(p);

                period="";
                category="";
                id = "tempUser" + "501" ;
                title = edit_title_free.getText().toString();
                start_date = "";
                end_date = "";
                contents = edit_content_free.getText().toString();
                address = "";

                postFirebaseDatabase(true);

                Intent intent = new Intent(FreeBoardPostActivity.this, FreeBoardListActivity.class);
                startActivity(intent);


                finish();
        }
    }
}