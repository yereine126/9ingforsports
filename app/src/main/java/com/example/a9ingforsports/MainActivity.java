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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private EditText editTextID;
    private EditText editTextPW;
    private Button btnMakeAccount;
    private Button btnLogin;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference().child("userinfo");
    private boolean login_flag=false;

    private String t_pw;
    private String t_id;
    private String t_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        btnMakeAccount = (Button) findViewById(R.id.Newaccount_button);
        btnLogin = (Button) findViewById(R.id.Login_button);
        editTextID=(EditText)findViewById(R.id.Input_email);
        editTextPW=(EditText)findViewById(R.id.Input_password);
        btnMakeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MakeAccountActivity.class);
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        t_pw=dataSnapshot.child(editTextID.getText().toString()).child("passwd").getValue(String.class);
                        t_id=dataSnapshot.child(editTextID.getText().toString()).child("id").getValue(String.class);
                        //editTextID.setText("-"+t_id);
                        if(editTextID.getText().toString().equals(t_id)&&editTextPW.getText().toString().equals(t_pw)) {
                            login_flag = true;
                            if(dataSnapshot.child(editTextID.getText().toString()).child("type").getValue(String.class).equals("개인"))
                                t_type="개인";
                            else
                                t_type="기업";
                        }

                        if (login_flag) {
                            if(t_type.equals("개인")){
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                intent.putExtra("id",editTextID.getText().toString());
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else if(t_type.equals("기업")){
                                Intent intent = new Intent(MainActivity.this, HomeActivity2.class);
                                intent.putExtra("id",editTextID.getText().toString());
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        } else
                            Toast.makeText(MainActivity.this, "아이디 또는 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }
}
