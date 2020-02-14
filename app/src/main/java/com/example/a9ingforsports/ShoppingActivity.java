package com.example.a9ingforsports;

import android.support.annotation.NonNull;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_main);
        ImageView iv_newbal = (ImageView) findViewById(R.id.newbal);
        ImageView iv_xexymix = (ImageView) findViewById(R.id.xexymix);
        ImageView iv_adidas = (ImageView) findViewById(R.id.adidas);
        ImageView iv_descente = (ImageView) findViewById(R.id.descente);
        ImageView iv_arena = (ImageView) findViewById(R.id.arena);
        ImageView iv_nike = (ImageView) findViewById(R.id.nike);
        ImageView iv_andar = (ImageView) findViewById(R.id.andar);
        ImageView iv_fila = (ImageView) findViewById(R.id.fila);
        ImageView iv_mizuno = (ImageView) findViewById(R.id.mizuno);
        ImageView iv_under = (ImageView) findViewById(R.id.under);


        iv_newbal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nbkorea.com/index.action"));
                startActivity(intent);
            }
        });
        iv_xexymix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.xexymix.com/"));
                startActivity(intent);
            }
        });
        iv_adidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://shop.adidas.co.kr/adiMain.action"));
                startActivity(intent);
            }
        });
        iv_descente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://shop.descentekorea.co.kr/index.do?netFunnelYn=Y"));
                startActivity(intent);
            }
        });
        iv_arena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.arena.co.kr/"));
                startActivity(intent);
            }
        });
        iv_nike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nike.com/kr/ko_kr/"));
                startActivity(intent);
            }
        });
        iv_andar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.andar.co.kr/"));
                startActivity(intent);
            }
        });
        iv_fila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.fila.co.kr/main/main.asp"));
                startActivity(intent);
            }
        });
        iv_mizuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mizuno.co.kr/"));
                startActivity(intent);
            }
        });
        iv_under.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.underarmour.co.kr/ko-kr/"));
                startActivity(intent);
            }
        });
    }
}
