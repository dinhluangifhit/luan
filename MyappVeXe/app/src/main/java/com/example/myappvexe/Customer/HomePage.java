package com.example.myappvexe.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappvexe.DangNhapActivity;
import com.example.myappvexe.R;
import com.example.myappvexe.SQLiteHelper;

public class HomePage extends AppCompatActivity {
    TextView TxtCusName;
    ImageView Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        TxtCusName = findViewById(R.id.txtCusName);
        Logout = findViewById(R.id.logOut);


        // Kiểm tra nếu có thông tin tài khoản user từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String saverCusName = sharedPreferences.getString("userName", "");

        if(!saverCusName.isEmpty()){
            SQLiteHelper dbHelper = new SQLiteHelper(this);
            Customer customer = dbHelper.getCustomerInfor(saverCusName);
            if(customer != null) {
                TxtCusName.setText(customer.getName());
            }
        }

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removreLogin();
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

        Intent intent = new Intent(HomePage.this, DangNhapActivity.class);
        startActivity(intent);
        finish();
    }
}