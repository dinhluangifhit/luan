package com.example.myappvexe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class AddStaffActivity extends AppCompatActivity {
    private SQLiteHelper sqLiteHelper;
    EditText EdStaffName, EdStaffUserName,EdStaffPassWord, EdStaffEmail, EdStaffPhone;
    Button BntAddStaff;
    TextView TxtBackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        EdStaffName = (EditText) findViewById(R.id.edStaffName);
        EdStaffUserName = (EditText) findViewById(R.id.edStaffUserName);
        EdStaffPassWord = (EditText) findViewById(R.id.edStaffPassWord);
        EdStaffEmail = (EditText) findViewById(R.id.edStaffEmail);
        EdStaffPhone = (EditText) findViewById(R.id.edStaffPhone);
        BntAddStaff = (Button) findViewById(R.id.bntAddStaff);
        TxtBackList = (TextView) findViewById(R.id.txtBackList);

        sqLiteHelper = new SQLiteHelper(AddStaffActivity.this);

        //Back to list Staff
        TxtBackList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddStaffActivity.this, DanhSachNhanVienActivity.class);
                startActivity(intent);
            }
        });

        BntAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaterFields()) {
                    String StaffName = EdStaffName.getText().toString().trim();
                    String StaffUser = EdStaffUserName.getText().toString().trim();
                    String StaffPassWord = EdStaffPassWord.getText().toString().trim();
                    String StaffEmail = EdStaffEmail.getText().toString().trim();
                    String StaffPhone = EdStaffPhone.getText().toString().trim();
                    String hashedPassWordStaff = PassWordHash.hashPassWord(StaffPassWord);

                    if(sqLiteHelper.isUserNameExists(StaffUser)){
                        Toast.makeText(AddStaffActivity.this, "Tên đăng nhập đã tồn tại.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        sqLiteHelper.adStaff(StaffName, StaffUser, hashedPassWordStaff, StaffEmail, StaffPhone);

                        // after adding the data we are displaying a toast message.
                        Toast.makeText(AddStaffActivity.this, "Thêm nhân viên thành công.", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(AddStaffActivity.this, DanhSachNhanVienActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                       
                    }
                }
                else {
                    Toast.makeText(AddStaffActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        EdStaffName.addTextChangedListener(new TextWatcher() {//Kiểm tra tên có ký tực đặc biệt
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Kiểm tra ngay khi người dùng thay đổi văn bản
                if(!isValidName(s.toString())){
                    EdStaffName.setError("Tên không được chứa các ký tự đặc biệt ");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private boolean isValidName(String name){
        String regex = "^[\\p{L} .'-]+$";
        return name.matches(regex);
    }


    //Kiểm tra đã nhập đủ các trường chưa
    private boolean validaterFields(){
        String name = EdStaffName.getText().toString().trim();
        String username = EdStaffUserName.getText().toString().trim();
        String password = EdStaffPassWord.getText().toString().trim();
        String email = EdStaffEmail.getText().toString().trim();
        String phone = EdStaffPhone.getText().toString().trim();


        //Kiểm tra đã nhập đầy đủ các trường chưa
        if(name.isEmpty()||username.isEmpty()||password.isEmpty()||email.isEmpty()||phone.isEmpty()){
            return false;
        }
        return true;
    }



}