package com.example.myappvexe;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class DangNhapActivity extends AppCompatActivity {
    SQLiteDatabase mydatabase;
    EditText edUser, edPassWord;
    boolean passwordVisible;
    Button bntDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mydatabase = openOrCreateDatabase("mydatabaseBanVeXe.sqlite", MODE_PRIVATE, null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        edUser = (EditText) findViewById(R.id.edUser);
        edPassWord = (EditText)  findViewById(R.id.edPassWord);

        edPassWord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=edPassWord.getRight()-edPassWord.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=edPassWord.getSelectionEnd();
                        if(passwordVisible){
                            edPassWord.setCompoundDrawablesRelativeWithIntrinsicBounds( 0, 0,
                                    R.drawable.ic_baseline_visibility_off_24, 0);
                            edPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        } else{
                            edPassWord.setCompoundDrawablesRelativeWithIntrinsicBounds( 0, 0,
                                    R.drawable.ic_baseline_remove_red_eye_24, 0);
                            //Hiển thị passWord
                            edPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=false;
                        }
                        edPassWord.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        bntDangNhap = (Button) findViewById(R.id.bntDangNhap);
        bntDangNhap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_UP){
                    EditText edUser = (EditText) findViewById(R.id.edUser);
                    EditText edPassWord = (EditText) findViewById(R.id.edPassWord);
                    String userName = edUser.getText().toString().trim();
                    String passWord = edPassWord.getText().toString().trim();

                    if(userName.isEmpty() || passWord.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });



    }
    public void onRegisterClick(View view){
        Intent intent = new Intent(this, DangKyActivity.class);
        startActivity(intent);
    }


}