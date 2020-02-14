package com.example.a9ingforsports;

import android.support.annotation.NonNull;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MakeAccountActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newaccount_login);

        Button make_employee = (Button) findViewById(R.id.make_employee_button);
        make_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeAccountActivity.this, CreateEmployeeActivity.class);
                startActivity(intent);
            }
        });

        Button make_employer = (Button) findViewById(R.id.make_employer_button);
        make_employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeAccountActivity.this, CreateEmployerActivity.class);
                startActivity(intent);
            }
        });
    }
}