package com.example.a9ingforsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import static android.support.constraint.ConstraintLayoutStates.TAG;

public class MypageActivity extends Activity {

    private TextView textID;
    private TextView textApply;
    private TextView textOffer;
    private TextView textQuali;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference().child("apply");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_main);

        Intent intent=getIntent();
        textID=(TextView)findViewById(R.id.myid_View);
        textApply=(TextView)findViewById(R.id.apply_View);
        textOffer=(TextView)findViewById(R.id.offer_View);
        textQuali=(TextView)findViewById(R.id.myq_View);
        String str=intent.getStringExtra("id");
        textID.setText(str);

        databaseReference.child(str).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long apply=dataSnapshot.child("apply_num").getValue(Long.class);
                Long resume=dataSnapshot.child("resume_num").getValue(Long.class);
                Long cert=dataSnapshot.child("cert_num").getValue(Long.class);
                textApply.setText(apply.toString());
                textOffer.setText(resume.toString());
                textQuali.setText(cert.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button myq = (Button) findViewById(R.id.myq_button);

        myq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageActivity.this, MyqActivity.class);
                startActivity(intent);
            }
        });

        Button myoffer = (Button) findViewById(R.id.myoffer_button);
        myoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageActivity.this, ResumeSettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
