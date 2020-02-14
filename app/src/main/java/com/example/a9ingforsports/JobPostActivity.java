package com.example.a9ingforsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JobPostActivity extends Activity implements View.OnClickListener{

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

    Button btn_insert;
    EditText edit_title;
    EditText edit_start;
    EditText edit_end;
    EditText edit_content;
    EditText edit_address;

    RadioGroup check_period;
    RadioGroup check_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobpost);

        btn_insert = (Button)findViewById(R.id.btn_insert);
        btn_insert.setOnClickListener(this);

        edit_title = (EditText)findViewById(R.id.edit_title);
        edit_content = (EditText)findViewById(R.id.edit_content);
        edit_end= (EditText)findViewById(R.id.edit_end);
        edit_start= (EditText)findViewById(R.id.edit_start);
        edit_address = (EditText)findViewById(R.id.edit_address);
        check_period = (RadioGroup)findViewById(R.id.check_period);
        check_category = (RadioGroup)findViewById(R.id.check_category);


        check_period.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.check_short) {
                    period = "단기";
                }
                else period = "장기";
            }
        });

        check_category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.check_yoga:
                        category = "yoga";
                        break;
                    case R.id.check_aerobic:
                        category = "aerobic";
                        break;
                    case R.id.check_swim:
                        category = "swim";
                        break;
                    case R.id.check_health:
                        category = "health";
                        break;
                    case R.id.check_etc:
                        category = "etc";
                        break;
                }
            }
        });

        btn_insert.setEnabled(true);

    }

    public void setInsertMode(){
        edit_start.setText("");
        edit_end.setText("");
        edit_content.setText("");
        edit_title.setText("");
        edit_address.setText("");
        btn_insert.setEnabled(true);
    }

    public void postFirebaseDatabase(boolean add){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String,Object> childUpdates = new HashMap<>();
        Map<String,Object> postValues = null;
        if(add){

            joboffer job = new joboffer(category, contents, end_date, id, period, start_date, title, address, vnum);
            postValues = job.toMap();
        }
        childUpdates.put("/joboffer/"+ id, postValues);
        mPostReference.updateChildren(childUpdates);
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_insert:

                Random rnd = new Random();

                int p = rnd.nextInt(500); // 0 <= p < 500

                vnum = p;
                String pp = String.valueOf(p);

                id = "tempUser" + "501" ;
                title = edit_title.getText().toString();
                start_date = edit_start.getText().toString();
                end_date = edit_end.getText().toString();
                contents = edit_content.getText().toString();
                address = edit_address.getText().toString();

                postFirebaseDatabase(true);

                Intent intent = new Intent(JobPostActivity.this, JobListActivity.class);
                startActivity(intent);


                finish();


        }
    }


}
