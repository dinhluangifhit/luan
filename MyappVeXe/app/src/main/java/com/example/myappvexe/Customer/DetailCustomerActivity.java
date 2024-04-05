package com.example.myappvexe.Customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myappvexe.R;
import com.example.myappvexe.SQLiteHelper;

public class DetailCustomerActivity extends AppCompatActivity {
    private SQLiteHelper sqLiteHelper;
    private TextView DetailNameCus, DetailEmailCus, DetailGenderCus, DetailBirthCus, detailSdtCus;
    private Button BntEditCus, BntDeleteCus;

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


        BntEditCus = (Button) findViewById(R.id.bntEditCus);
        BntEditCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedCus != null){
                    EditCustomerDialogActivity editCustomerDialogActivity = new EditCustomerDialogActivity(DetailCustomerActivity.this,selectedCus);
                    editCustomerDialogActivity.show();
                } else {
                    Toast.makeText(DetailCustomerActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Xóa khách hàng
        BntDeleteCus = (Button) findViewById(R.id.bntDeleteCus);
        BntDeleteCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCus != null){

                    AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(DetailCustomerActivity.this);
                    confirmBuilder.setTitle("Xác nhận xóa");
                    confirmBuilder.setMessage("Bạn có chắc chắc xóa khách hàng này?");
                    confirmBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String userNameCus = selectedCus.getUsername();
                            sqLiteHelper = new SQLiteHelper(DetailCustomerActivity.this);
                            sqLiteHelper.deleteDataCus(userNameCus);
                            Toast.makeText(DetailCustomerActivity.this, "Xóa khách hàng thành công", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(DetailCustomerActivity.this, DanhSachKhachHangAcitivity.class);
                            startActivity(intent1);
                            finish();

                        }
                    });
                    confirmBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Giữ nguyên màn hình
                        }
                    });
                    confirmBuilder.create().show();
                }
            }
        });

    }
}