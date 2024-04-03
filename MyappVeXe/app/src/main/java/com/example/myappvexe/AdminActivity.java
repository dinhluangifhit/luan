package com.example.myappvexe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
    TextView ListStaff, ListUser, ListCastegory, ListProduct, ListStatistical, inLogout, ViewName, ViewEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ListUser = (TextView) findViewById(R.id.listUser);
        ListCastegory = (TextView) findViewById(R.id.listCastegory);
        ListProduct = (TextView) findViewById(R.id.listProduct);
        ListStatistical = (TextView) findViewById(R.id.listStatistical);
        inLogout = (TextView) findViewById(R.id.LogOut);
        ListStaff = (TextView) findViewById(R.id.listStaff);
        ViewName = (TextView) findViewById(R.id.viewNameAdmin);
        ViewEmail = (TextView) findViewById(R.id.viewEmail);



        ListStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, DanhSachNhanVienActivity.class);
                startActivity(intent);
                ListStaff.setBackgroundColor(Color.BLUE);
            }
        });

        ListUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, DanhSachKhachHangAcitivity.class);
                startActivity(intent);
                ListUser.setBackgroundColor(Color.BLUE);
            }
        });
    }


}