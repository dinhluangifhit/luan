package com.example.myappvexe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailCustomerActivity extends AppCompatActivity {
    private SQLiteHelper sqLiteHelper;
    private TextView DetailNameCus, DetailEmailCus, DetailGenderCus, DetailBirthCus, detailSdtCus;
    private Button BntRevisionStaff, bntDeleteStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);

        //Nhận thông tin từ intent
        Intent intent = getIntent();
        Customer selectedCus = (Customer) intent.getSerializableExtra("selectedCus");

        //Hiển thị thông tin nhân viên trên màn hình giao diện
        DetailNameCus = findViewById(R.id.detailNameCus);
        DetailEmailCus = findViewById(R.id.detailEmailCus);
        DetailGenderCus = findViewById(R.id.detailGenderCus);
        DetailBirthCus = findViewById(R.id.detailBirthCus);
        detailSdtCus = findViewById(R.id.detailSdtCus);

        DetailNameCus.setText("Họ Tên:  " + selectedCus.getName());
        DetailEmailCus.setText("Email: " + selectedCus.getEmail());
        DetailGenderCus.setText("Giới tính: " + selectedCus.getGender());
        DetailBirthCus.setText("Ngày sinh: " + selectedCus.getDayofbidth());
        detailSdtCus.setText("SĐT: " + selectedCus.getPhone());

    }
}