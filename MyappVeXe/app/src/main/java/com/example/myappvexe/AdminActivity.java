package com.example.myappvexe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myappvexe.Category.CategoryActivity;
import com.example.myappvexe.Customer.DanhSachKhachHangAcitivity;
import com.example.myappvexe.Location.LocationActivity;

public class AdminActivity extends AppCompatActivity {
    private TextView ListStaff, ListUser, ListCastegory, ListProduct, ListStatistical, inLogout, ViewName, ViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ListUser = (TextView) findViewById(R.id.listUser);
        ListCastegory = (TextView) findViewById(R.id.listCastegory);
        ListProduct = (TextView) findViewById(R.id.listProduct);
        ListStatistical = (TextView) findViewById(R.id.listStatistical);
        ListStaff = (TextView) findViewById(R.id.listStaff);
        ViewName = (TextView) findViewById(R.id.viewNameAdmin);
        ViewEmail = (TextView) findViewById(R.id.viewEmail);
        inLogout = (TextView) findViewById(R.id.logOut);

        // Kiểm tra nếu có thông tin tài khoản admin từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String saverUserName = sharedPreferences.getString("userName", "");



        if(!saverUserName.isEmpty()){
            SQLiteHelper dbHelper = new SQLiteHelper(this);
            Admin admin = dbHelper.getAdminInfor(saverUserName);
            ViewName.setText(admin.getName());
            ViewEmail.setText(admin.getEmail());
        }


        ListStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, DanhSachNhanVienActivity.class);
                startActivity(intent);
                finish();
                ListStaff.setBackgroundColor(Color.BLUE);
            }
        });

        ListUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, DanhSachKhachHangAcitivity.class);
                startActivity(intent);
                finish();
                ListUser.setBackgroundColor(Color.BLUE);
            }
        });

        ListCastegory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ListProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, CategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        inLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removreLogin();
                inLogout.setBackgroundColor(Color.GREEN);
            }
        });
    }

    //Xóa dữ lieeujj trong SharePregerences
    private void removreLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userName");
        editor.remove("passWord");
        editor.putBoolean("isLogin", false);
        editor.apply();

        Intent intent = new Intent(this, DangNhapActivity.class);
        startActivity(intent);
        finish();
    }



}