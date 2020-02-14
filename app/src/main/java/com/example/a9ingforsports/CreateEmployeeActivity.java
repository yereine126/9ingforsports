package com.example.a9ingforsports;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Pattern;

public class CreateEmployeeActivity extends Activity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextID;
    private EditText editTextName;
    private EditText editTextContact;
    private EditText editTextAddress;
    private EditText pw2;
    private EditText editTextqname;
    private EditText editTextqname2;
    private EditText editTextqnum;
    private RadioGroup radioGroup;

    private Button create_employee;
    private Button cert_employee;
    private Button check_id;


    private boolean id_flag=true;
    private boolean cert_flag=false;
    private boolean regis=true;
    private String favorite="";

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newaccount_employee);

        editTextName=(EditText)findViewById(R.id.name_employee);
        editTextID=(EditText)findViewById(R.id.id_employee);
        editTextEmail=(EditText)findViewById(R.id.email_employee);
        editTextPassword=(EditText)findViewById(R.id.pw_employee);
        editTextContact=(EditText)findViewById(R.id.contact_employee);

        editTextqname=(EditText)findViewById(R.id.qname_employee);
        editTextqname2=(EditText)findViewById(R.id.qname2_employee);
        editTextqnum=(EditText)findViewById(R.id.qnum_employee);

        editTextAddress=(EditText)findViewById(R.id.address_employee);
        pw2=(EditText)findViewById(R.id.pw_employee2);

        create_employee = (Button) findViewById(R.id.create_employee);
        cert_employee=(Button)findViewById(R.id.check_q_button);
        check_id=(Button)findViewById(R.id.checkid_button);

        radioGroup=(RadioGroup)findViewById(R.id.f_radio2);
        cert_employee.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                new cert().execute();
            }
        });
        create_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                if(regis&&id_flag&&cert_flag) {
                    Intent intent = new Intent(CreateEmployeeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        check_id.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(editTextID.getText().toString().trim().length()>=8&&editTextID.getText().toString().trim().length()<=15){
                    databaseReference.child("userinfo").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> child=dataSnapshot.getChildren().iterator();
                            while(child.hasNext()){
                                if(editTextID.getText().toString().trim().equals(child.next().getKey())){
                                    id_flag=false;
                                    Toast.makeText(CreateEmployeeActivity.this,"이미 존재하는 아이디입니다",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(CreateEmployeeActivity.this, "올바른 아이디 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)findViewById(checkedId);
                switch (checkedId){
                    case R.id.yoga_radioemployee :
                        favorite="요가,필라테스";
                        break;
                    case R.id.aerobic_radioemployee:
                        favorite="에어로빅";
                        break;
                    case R.id.swim_radioemployee:
                        favorite="수영";
                        break;
                    case R.id.health_radioemployee:
                        favorite="헬스";
                        break;
                    case R.id.etc_radioemployee:
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
        String address=editTextAddress.getText().toString().trim();

        String qname1=editTextqname.getText().toString().trim();
        String qname2=editTextqname2.getText().toString().trim();
        String qnum=editTextqnum.getText().toString().trim();

        if(email.getBytes().length<=0||password.getBytes().length<=0||
                id.getBytes().length<=0||name.getBytes().length<=0||contact.getBytes().length<=0||
                qname1.getBytes().length<=0||qname2.getBytes().length<=0||qnum.getBytes().length<=0||address.getBytes().length<=0) {
            Toast.makeText(CreateEmployeeActivity.this, "모두 입력해주세요", Toast.LENGTH_SHORT).show();
            regis=false;
        }
        else if(!Pattern.matches("^[a-zA-Z0-9]*$",password)){
            Toast.makeText(CreateEmployeeActivity.this, "올바른 비밀번호 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
            regis=false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            regis=false;
            Toast.makeText(CreateEmployeeActivity.this, "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
        }

        else if(!(password.equals(password2))){
            regis=false;
            Toast.makeText(CreateEmployeeActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        }
        else if(!Pattern.matches("^[0-9]*$",contact)){
            Toast.makeText(CreateEmployeeActivity.this, "대표번호 숫자만 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else if(!Pattern.matches("^[0-9]*$",qnum)){
            Toast.makeText(CreateEmployeeActivity.this, "사업자번호 숫자만 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else{
            regis=true;
            if(regis&&id_flag) {
                Employee_User user = new Employee_User(id,password,name,contact,address,qname1, qname2, qnum, email,favorite);
                databaseReference.child("userinfo").child(id).setValue(user);
                databaseReference.child("apply").child(id).child("apply_num").setValue(0);
                databaseReference.child("apply").child(id).child("cert_num").setValue(0);
                databaseReference.child("apply").child(id).child("resume_num").setValue(0);
            }
        }


    }
    public void makeDialog(){
        id_flag=true;
        Toast.makeText(CreateEmployeeActivity.this, "사용 가능한 아이디입니다", Toast.LENGTH_SHORT).show();
        check_id=(Button)findViewById(R.id.checkid_button);
        check_id.setText("사용가능");
        check_id.setEnabled(false);
    }
    private class cert extends  AsyncTask<Void, Void, Void>{
        String qname1=editTextqname.getText().toString().trim();
        String qname2=editTextqname2.getText().toString().trim();
        String qnum=editTextqnum.getText().toString().trim();
        String get="searchQulNm="+qname1+"&searchMemNm="+qname2+"&searchQulRegNum="+qnum;
        String url="https://www.pqi.or.kr/inf/qul/infQulList.do?";
        String text;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void...params){
            try {

                Connection.Response response=Jsoup.connect(url+get).method(Connection.Method.GET).execute();
                Document document = response.parse();
                Elements elm=document.select("article.content_result");
                text=elm.text();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            if(text.contains(qname1)&&text.contains(qname2)){
                Toast.makeText(CreateEmployeeActivity.this,"자격증 인증이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                cert_employee.setText("인증 완료");
                cert_employee.setEnabled(false);
                cert_flag=true;
            }
            else{
                Toast.makeText(CreateEmployeeActivity.this, "유효하지 않은 자격증입니다.",Toast.LENGTH_SHORT).show();
            }
        }
    }



}
