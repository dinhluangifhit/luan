package com.example.myappvexe;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DangNhapActivity extends AppCompatActivity {
    AuthenticationManager authManager;
    SQLiteDatabase mydatabase;
    EditText EdUser, EdPassWord;
    boolean passwordVisible;
    Button BntDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mydatabase = openOrCreateDatabase("mydatabaseBanVeXe.sqlite", MODE_PRIVATE, null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        EdUser = (EditText) findViewById(R.id.edUser);
        EdPassWord = (EditText)  findViewById(R.id.edPassWord);

        EdPassWord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=EdPassWord.getRight()-EdPassWord.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=EdPassWord.getSelectionEnd();
                        if(passwordVisible){
                            EdPassWord.setCompoundDrawablesRelativeWithIntrinsicBounds( 0, 0,
                                    R.drawable.ic_baseline_visibility_off_24, 0);
                            EdPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        } else{
                            EdPassWord.setCompoundDrawablesRelativeWithIntrinsicBounds( 0, 0,
                                    R.drawable.ic_baseline_remove_red_eye_24, 0);
                            //Hiển thị passWord
                            EdPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=false;
                        }
                        EdPassWord.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        authManager = new AuthenticationManager(this);
        BntDangNhap = (Button) findViewById(R.id.bntDangNhap);
        BntDangNhap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction()==MotionEvent.ACTION_UP){
                    EditText EdUser = (EditText) findViewById(R.id.edUser);
                    EditText EdPassWord = (EditText) findViewById(R.id.edPassWord);
                    String userName = EdUser.getText().toString().trim();
                    String passWord = EdPassWord.getText().toString().trim();

                    if(!userName.isEmpty() && !passWord.isEmpty()) {

                        if (authManager.auhenticate(userName, passWord))
                        {
//                            //Lưu trạng thái đăng nhập của người dùng
//                            SharedPreferences sharedPreferences = getSharedPreferences("loginRegister", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putBoolean("registerLoggin", true);//Trạng thái đã đăng nhập
//                            editor.apply();

                            //Chuyển đến màn hình của người dùng
                            Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                        if (authManager.AdminAuthenticate(userName, passWord))
                        {
                            Intent intent = new Intent(DangNhapActivity.this, AdminActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(DangNhapActivity.this, "Đăng nhập Admin thành công", Toast.LENGTH_SHORT).show();
                        }


                        else{
                            Toast.makeText(DangNhapActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        }


                    } else {

                        Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
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