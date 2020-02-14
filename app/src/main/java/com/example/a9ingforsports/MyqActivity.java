package com.example.a9ingforsports;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyqActivity extends Activity {

    public Button imgbtn;
    public ImageView imageView;
    public ImageView imageView2;
    public ImageView imageView3;
    final String TAG=getClass().getSimpleName();
    final static int TAKE_PICTURE=1;

    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO=1;
    static final int REQUEST_GALLERY=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.myq_mypage);

        imgbtn=findViewById(R.id.makemyq_photo);
        imageView=findViewById(R.id.imageView4);
        imageView2=findViewById(R.id.imageView5);
        imageView3=findViewById(R.id.imageView7);
        imgbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makeDialog();
            }
        });
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                Log.d(TAG,"권한 설정 완료");
            }
            else{
                Log.d(TAG,"권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
    }
    public void makeDialog(){
        AlertDialog.Builder ad=new AlertDialog.Builder(MyqActivity.this);
        ad.setTitle("자격증 등록");

        ad.setPositiveButton("갤러리", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_GALLERY);

            }
        });
        ad.setNegativeButton("카메라", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dispatchTakePictureIntent();
                dialog.dismiss();
            }
        });
        ad.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = ad.create();
        alertDialog.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        Log.d(TAG, "onRequestPermissionResult");
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
            Log.d(TAG,"Permission: "+permissions[0]+"was"+grantResults[0]);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        }

                    }
                    break;
                case REQUEST_GALLERY:
                    if(resultCode==RESULT_OK){
                        try{
                            InputStream in=getContentResolver().openInputStream(intent.getData());
                            Bitmap img= BitmapFactory.decodeStream(in);
                            in.close();
                            imageView2.setImageBitmap(img);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private File createImageFile() throws IOException{
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName="JPEG_"+timeStamp+"_";
        File storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image=File.createTempFile(
                imageFileName,".jpg",storageDir
        );

        mCurrentPhotoPath=image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            File photoFile=null;
            try{
                photoFile=createImageFile();

            }catch (IOException e){

            }
            if(photoFile!=null){
                Uri photoURI= FileProvider.getUriForFile(this,"com.example.a9ingforsports.fileprovider",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);
            }
        }
    }
}