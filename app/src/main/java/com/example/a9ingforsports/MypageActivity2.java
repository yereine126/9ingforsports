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

public class MypageActivity2 extends Activity {
    private TextView textID;
    private Intent myintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_main2);
        myintent=getIntent();
        textID=findViewById(R.id.myid_View2);
        textID.setText(myintent.getStringExtra("id"));
        Button myoffer = (Button) findViewById(R.id.offerview_button);
        myoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageActivity2.this, OfferViewActivity2.class);
                intent.putExtra("id",myintent.getStringExtra("id"));
                startActivity(intent);
            }
        });
    }
}
