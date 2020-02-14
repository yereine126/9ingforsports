package com.example.a9ingforsports;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.lang.String;

import java.util.ArrayList;
import java.util.List;


public class CreateEmployerActivity extends Activity  {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextID;
    private EditText editTextName;
    private EditText editTextContact;
    private EditText editTextNum;
    private EditText editTextAddress;
    private EditText pw2;
    private Button create_employer;
    private Button cert_employer;
    private RadioGroup radioGroup;
    private Button check_id;


    private boolean id_flag=true;
    private boolean cert_flag=false;
    private boolean regis=true;
    private String favorite="";

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference().child("userinfo");



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newaccount_employer);

        editTextName=(EditText)findViewById(R.id.name_employer);
        editTextID=(EditText)findViewById(R.id.id_employer);
        editTextPassword=(EditText)findViewById(R.id.pw_employer);
        editTextContact=(EditText)findViewById(R.id.contact_employer);
        editTextNum=(EditText)findViewById(R.id.num_employer);
        editTextAddress=(EditText)findViewById(R.id.address_employer);
        pw2=(EditText)findViewById(R.id.pw_employer2);
        create_employer = (Button) findViewById(R.id.create_employer);
        cert_employer=(Button)findViewById(R.id.numcert_button);
        radioGroup=(RadioGroup)findViewById(R.id.f_radio);
        editTextEmail=(EditText)findViewById(R.id.email_employer);

        create_employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                if(regis&&id_flag&&cert_flag) {
                    Intent intent = new Intent(CreateEmployerActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        cert_employer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                cert_employer();


            }
        });

        findViewById(R.id.checkid_button2).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(editTextID.getText().toString().trim().length()>=8&&editTextID.getText().toString().trim().length()<=15){
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> child=dataSnapshot.getChildren().iterator();
                            while(child.hasNext()){
                                if(editTextID.getText().toString().trim().equals(child.next().getKey())){
                                    id_flag=false;
                                    Toast.makeText(CreateEmployerActivity.this,"이미 존재하는 아이디입니다",Toast.LENGTH_SHORT).show();
                                    databaseReference.removeEventListener(this);
                                    return;
                                }
                            }
                            makeDialog();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });}
                else{
                    id_flag=false;
                    Toast.makeText(CreateEmployerActivity.this, "올바른 아이디 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)findViewById(checkedId);
                switch (checkedId){
                    case R.id.yoga_radioemployer :
                        favorite="요가,필라테스";
                        break;
                    case R.id.aerobic_radioemployer:
                        favorite="에어로빅";
                        break;
                    case R.id.swim_radioemployer:
                        favorite="수영";
                        break;
                    case R.id.health_radioemployer:
                        favorite="헬스";
                        break;
                    case R.id.etc_radioemployer:
                        favorite="기타";
                        break;
                    default:
                        favorite="없음";
                        break;



                }
            }
        });
    }

    public void registerUser(){
        String id=editTextID.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String email=editTextEmail.getText().toString().trim();
        String password2=pw2.getText().toString().trim();

        String name=editTextName.getText().toString().trim();
        String contact=editTextContact.getText().toString().trim();
        String num=editTextNum.getText().toString().trim();
        String address=editTextAddress.getText().toString().trim();

        if(email.getBytes().length<=0||password.getBytes().length<=0||
                id.getBytes().length<=0||name.getBytes().length<=0||contact.getBytes().length<=0||
                num.getBytes().length<=0||address.getBytes().length<=0) {
            Toast.makeText(CreateEmployerActivity.this, "모두 입력해주세요", Toast.LENGTH_SHORT).show();
            regis=false;
        }
        else if(!Pattern.matches("^[a-zA-Z0-9]*$",password)){
            Toast.makeText(CreateEmployerActivity.this, "올바른 비밀번호 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
            regis=false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            regis=false;
            Toast.makeText(CreateEmployerActivity.this, "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
        }

        else if(!(password.equals(password2))){
            regis=false;
            Toast.makeText(CreateEmployerActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        }
        else if(!Pattern.matches("^[0-9]*$",contact)){
            Toast.makeText(CreateEmployerActivity.this, "대표번호 숫자만 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else if(!Pattern.matches("^[0-9]*$",num)){
            Toast.makeText(CreateEmployerActivity.this, "사업자번호 숫자만 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else{
            regis=true;
            if(regis&&id_flag) {
                User user = new User(id, password, name, contact, num, address, email,favorite);
                databaseReference.child(id).setValue(user);
            }
        }


    }
    public void makeDialog(){
        id_flag=true;
        Toast.makeText(CreateEmployerActivity.this, "사용 가능한 아이디입니다", Toast.LENGTH_SHORT).show();
        check_id=(Button)findViewById(R.id.checkid_button2);
        check_id.setText("사용가능");
        check_id.setEnabled(false);
    }

    public void cert_employer() {

        StrictMode.enableDefaults();
        String num=editTextNum.getText().toString();
        String cert_num=num.substring(0,6);
        String bzowrRgstNo=null;
        boolean inbzowrRgstNo=false;
        boolean flag=false;

        try {
            URL url = new URL("http://apis.data.go.kr/B552015/NpsBplcInfoInqireService/getBassInfoSearch?bzowr_rgst_no="+
                    cert_num+"&serviceKey=8MgcU9wt8d57LC2%2FvyJgSid2uwLVAX2m8E4A%2BWDNlPGzxxHXXftoQ%2FIr9aD1JYnTIQkwe5ZtsfflWifGbi9ENg%3D%3D");
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);
            int parserEvent = parser.getEventType();
            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("bzowrRgstNo")) {
                            inbzowrRgstNo = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if (inbzowrRgstNo) {
                            bzowrRgstNo = parser.getText();
                            inbzowrRgstNo = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            if(cert_num.equals(bzowrRgstNo.substring(0,6)))
                                flag=true;
                        }
                        break;

                }
                if (flag) break;
                else
                    parserEvent = parser.next();

            }
        }catch(Exception e){}

        if(flag){
            Toast.makeText(CreateEmployerActivity.this,"사업자 인증이 완료되었습니다.",Toast.LENGTH_SHORT).show();
            cert_employer.setText("완료");
            cert_employer.setEnabled(false);
            cert_flag=true;
        }
        else{
            Toast.makeText(CreateEmployerActivity.this,"유효하지 않은 사업자 번호입니다.",Toast.LENGTH_SHORT).show();

        }

    }
}
