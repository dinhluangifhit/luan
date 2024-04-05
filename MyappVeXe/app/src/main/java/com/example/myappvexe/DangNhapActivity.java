package com.example.myappvexe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private AuthenticationManager authManager;
    private SQLiteDatabase mydatabase;
    private EditText EdUser, EdPassWord;
    private boolean passwordVisible;
    private Button BntDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mydatabase = openOrCreateDatabase("mydatabaseBanVeXe.sqlite", MODE_PRIVATE, null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        EdUser = (EditText) findViewById(R.id.edUser);
        EdPassWord = (EditText)  findViewById(R.id.edPassWord);
        //Hiển thị mật khẩu của người dùng
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

        setupLogin();
        checkLogin();


    }

    //Chuyển trang đăng ký người dùng
    public void onRegisterClick(View view){
        Intent intent = new Intent(this, DangKyActivity.class);
        startActivity(intent);
    }

    //Sử lý sự kiện khi người dùng đăng nhập
    private void handleLogin(String userName, String passWord){
        if(!userName.isEmpty() && !passWord.isEmpty()){
            if(authManager.auhenticate(userName, passWord)){
                //Đăng nhập thành công người dùng là user
                saveLogin(userName, passWord, "user");
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if(authManager.AdminAuthenticate(userName, passWord)){
                //Đăng nhập với vai trò Admin
                saveLogin(userName, passWord, "admin");
                Toast.makeText(this, "Đăng nhập thành công với vai trò Admin", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DangNhapActivity.this, AdminActivity.class);
                intent.putExtra("userName", userName);
                intent.putExtra("passWord", passWord);

                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }
    }


    private void setupLogin(){
        BntDangNhap = (Button) findViewById(R.id.bntDangNhap);
        BntDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = EdUser.getText().toString().trim();
                String passWord = EdPassWord.getText().toString().trim();
                handleLogin(userName, passWord);
            }
        });
    }


    private void saveLogin( String userName, String passWord, String userRole){
        //Lưu thông tin đăng nhập
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", userName);
        editor.putString("passWord", passWord);
        editor.putString("userRole", userRole);
        editor.putBoolean("isLogin", true);//Đánh dấu người dùng đã đăng nhập thành công
        editor.apply();
    }

    private void checkLogin(){
        //Kiểm tra người dùng đã đăng nhập trước đó
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin",false);
        String userRole = sharedPreferences.getString("userRole", "");
        if(isLogin){
            //Người dùng đã đăng nhập
            if("admin".equalsIgnoreCase(userRole)){
                //Người dùng đăng nhập là Admin
                Intent adminIntent = new Intent(DangNhapActivity.this, AdminActivity.class);
                startActivity(adminIntent);
                finish();
            } else if("user".equalsIgnoreCase(userRole)){
                //Người dùng đăng nhập là user
                Intent userIntent = new Intent(DangNhapActivity.this, MainActivity.class);
                startActivity(userIntent);
                finish();
            } else {
                //Trường hợp không xác định đăng nhập lại
                //Xóa dữ liệu đăng nhập không hợp lệ
                sharedPreferences.edit().clear().apply();
            }

        }
    }


}