package com.example.myappvexe;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.util.Log;
import java.text.BreakIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class DangKyActivity extends AppCompatActivity {
    private SQLiteHelper sQLiteHelper;
    EditText EdRegisterBirth;
    EditText EdRegisterName,EdRegisterUserName,EdPassWord,EdEndPassWord,EdRegisterEmail,EdRegisterPhone;
    RadioButton RadioNam, RadioNu;
//    CheckBox chBox;
    Button BntDangKy;
//    DBContext db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        EdRegisterName = (EditText) findViewById(R.id.edRegisterName);
        EdPassWord = (EditText) findViewById(R.id.edPassWord);
        EdRegisterUserName = (EditText) findViewById(R.id.edRegisterUserName);
        EdRegisterEmail = (EditText) findViewById(R.id.edRegisterEmail);
        EdRegisterPhone = (EditText) findViewById(R.id.edRegisterPhone);
        EdEndPassWord = (EditText) findViewById(R.id.edEndPassWord);
        BntDangKy =  (Button)  findViewById(R.id.bntDangKy);
        EdRegisterBirth = (EditText) findViewById(R.id.edRegisterBirth);
//        RadioNam =  findViewById(R.id.radioNam);
//        RadioNu = findViewById(R.id.radioNu);


        sQLiteHelper = new SQLiteHelper(DangKyActivity.this);
////
        //Mở database để ghi đọc dữ liệu
//        try {
//            db = new DBContext(this);
//            db.opent();
//        }catch (Exception e){
//            Log.d(e.getMessage(), "onCreate: ");
//        }




       BntDangKy.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {

               String RegisterName = EdRegisterName.getText().toString().trim();
               String RegisterUserName =EdRegisterUserName.getText().toString().trim();
               String PassWord =EdPassWord.getText().toString().trim();
               String RegisterEmail =EdRegisterEmail.getText().toString().trim();
               String RegisterBirth =EdRegisterBirth.getText().toString().trim();
               String RegisterPhone =EdRegisterPhone.getText().toString().trim();

               sQLiteHelper.addNewAccount(RegisterName, RegisterUserName, PassWord, RegisterEmail,RegisterBirth ,RegisterPhone);

               // after adding the data we are displaying a toast message.
               Toast.makeText(DangKyActivity.this, "Course has been added.", Toast.LENGTH_SHORT).show();


//               try{
//                   if (validaterFields()){
//                       String Gender = null;
//                       if(RadioNam.isChecked()){
//                           Gender = "Nam";
//
//                       }else if(RadioNu.isChecked()){
//                           Gender = "Nữ";
//                       }
//
//                       db.insert(
////                               EdRegisterName.getText().toString().trim(),EdRegisterUserName.getText().toString().trim(),
////                               EdPassWord.getText().toString().trim(),EdRegisterEmail.getText().toString().trim(),Gender,
////                               EdRegisterBirth.getText().toString().trim(),
////                               EdRegisterPhone.getText().toString().trim());
//                       Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//                   }else{
//                       Toast.makeText(DangKyActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                   }
//               }catch (Exception e){
//                   e.printStackTrace();
//                   Log.e("DangKyActivity", "Lỗi xảy ra khi thêm dữ liệu vào cơ sở dữ liệu", e);
//
//                   Toast.makeText(DangKyActivity.this, "Lỗi ",Toast.LENGTH_SHORT).show();
//               }
           }
       });
        EdRegisterName.addTextChangedListener(new TextWatcher() {//Kiểm tra tên có ký tực đặc biệt
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            //Kiểm tra ngay khi người dùng thay đổi văn bản
                if(!isValidName(s.toString())){
                    EdRegisterName.setError("Tên không được chứa các ký tự đặc biệt ");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //Check mật khẩu
        EdEndPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passWord = EdPassWord.getText().toString();
                String endConfimPassWord = s.toString();
                if(!passWord.equals(endConfimPassWord)){
                    EdEndPassWord.setError("Mật khẩu nhập lại không khớp");
                }else{
                    EdEndPassWord.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        EdRegisterBirth = findViewById(R.id.edRegisterBirth);
    }


    //Kiểm tra đã nhập đủ các trường chưa
    private boolean validaterFields(){
        String name = EdRegisterName.getText().toString().trim();
        String username = EdRegisterUserName.getText().toString().trim();
        String password = EdPassWord.getText().toString().trim();
        String confirmPassWord = EdEndPassWord.getText().toString().trim();
        String birthday = EdRegisterBirth.getText().toString().trim();
        String email = EdRegisterEmail.getText().toString().trim();
        String phone = EdRegisterPhone.getText().toString().trim();




        //Kiểm tra đã nhập đầy đủ các trường chưa
        if(name.isEmpty()||username.isEmpty()||password.isEmpty()||confirmPassWord.isEmpty()||birthday.isEmpty()||
        email.isEmpty()||phone.isEmpty()){
            return false;
        }


//        if (!RadioNam.isChecked() && !RadioNu.isChecked()){
//            Toast.makeText(this,"Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (!chBox.isChecked()){
//            Toast.makeText(this, "Vui lòng xác nhận không phải robot", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }

//    private void saveDataDatabase(){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put("Name", EdRegisterName.getText().toString().trim());
//        values.put("userName", EdRegisterUserName.getText().toString().trim());
//        values.put("passWord", EdPassWord.getText().toString().trim());
//        values.put("email", EdRegisterEmail.getText().toString().trim());
//        values.put("gender", RadioNam.isChecked() ? "Nữ" : "Nam");
//        values.put("dateOfBirth", EdRegisterBirth.getText().toString().trim());
//        values.put("phone", EdRegisterPhone.getText().toString().trim());
//
//        long newRowid = db.insert("custumers", null, values);
//
//        if(newRowid != -1){
//            Toast.makeText(this,"Đã đăng ký thành công", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(this,"Lỗi", Toast.LENGTH_SHORT).show();
//        }
//        db.close();
//    }


    private boolean isValidName(String name){
        String regex = "^[\\p{L} .'-]+$";
        return name.matches(regex);
    }
        public void showDatePickDialog(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(DangKyActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
               String dateOfBirth = dayOfMonth + "/" + (month + 1) + "/" + year;
                EdRegisterBirth.setText(dateOfBirth);
            }
        }, // Thiết lập ngày mặc định khi mở DatePickerDialog
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
//Chuyển về trang đăng nhập
    public void onDangNhapClick(View view){
        Intent intent = new Intent(this,DangNhapActivity.class);
        startActivity(intent);
    }

}