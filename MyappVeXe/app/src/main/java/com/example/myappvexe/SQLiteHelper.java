package com.example.myappvexe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.mindrot.jbcrypt.BCrypt;


public class SQLiteHelper extends SQLiteOpenHelper{
    //Tạo cơ sở dữ liệu
    private static final String DB_NAME = "mySQLiteBanVeXe";
    private static final int VERSION = 1;
    //Dữ liệu bảng khách hàng
    public static final String TABLE_NAME = "Register";

    public static final String ID_COL = "id";

    public static final String Name_COL = "Name";

    public static final String userName_COL = "userName";

    public static final String passWord_COL = "passWord";

    public static final String email_COL = "email";

    public static final String gender_COL = "gender";

    public static final String dateOfBirth_COL = "dateOfBirth";

    public static final String phone_COL = "phone";


    //Dữ liệu bảng Admin
    private static final String TABLE_ADMIN = "Admin";

    private static final String ID_ADMIN_COL = "id";

    private static final String NameAdmin_COL = "Name";

    private static final String userAdmin_COL = "userName";

    private static final String passWordAdmin_COL = "passWord";

    private static final String emailAdmin_COL = "email";

    private static final String phoneAdmin_COL = "phone";

    //Dữ liệu bảng Admin
    public static final String TABLE_STAFF= "Staff";

    public static final String ID_STAFF_COL = "id";

    public static final String NameSTAFF_COL = "Name";

    public static final String userSTAFF_COL = "userName";

    public static final String passWordSTAFF_COL = "passWord";

    public static final String emailSTAFF_COL = "email";

    public static final String phoneSTAFF_COL = "phone";



    private final String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + Name_COL + " TEXT,"

                + userName_COL + " TEXT,"

                + passWord_COL + " TEXT,"

                + email_COL + " TEXT,"

                + gender_COL + " TEXT,"

                + dateOfBirth_COL + " TEXT,"

                + phone_COL + " TEXT)";



     private final String adamin = "CREATE TABLE " + TABLE_ADMIN + " ("
                + ID_ADMIN_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + NameAdmin_COL + " TEXT,"

                + userAdmin_COL + " TEXT,"

                + passWordAdmin_COL + " TEXT,"

                + emailAdmin_COL + " TEXT,"

                + phoneAdmin_COL + " TEXT)";



    private final String adstaff = "CREATE TABLE " + TABLE_STAFF+ " ("
            + ID_STAFF_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "

            + NameSTAFF_COL + " TEXT,"

            + userSTAFF_COL + " TEXT,"

            + passWordSTAFF_COL + " TEXT,"

            + emailSTAFF_COL + " TEXT,"

            + phoneSTAFF_COL + " TEXT)";


    public SQLiteHelper( Context context) {

        super(context, DB_NAME, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(query);
        db.execSQL(adamin);
        db.execSQL(adstaff);

        ContentValues values = new ContentValues();
        values.put(NameAdmin_COL, "Nguyen Van A");
        values.put(userAdmin_COL, "adminA");
        String hashedAdminPassWord = BCrypt.hashpw("adminA", BCrypt.gensalt());
        values.put(passWordAdmin_COL, hashedAdminPassWord);
        values.put(emailAdmin_COL, "admin@example.com");
        values.put(phoneAdmin_COL, "1234567890");
        db.insert(TABLE_ADMIN, null, values);
    }


    // thêm user
    public void addNewAccount (String RegisterName, String RegisterUserName, String PassWord, String RegisterEmail,
                               String RegisterGender, String RegisterBirth,String RegisterPhone){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();

        values.put(Name_COL, RegisterName);
        values.put(userName_COL, RegisterUserName);
        values.put(passWord_COL, PassWord);
        values.put(email_COL, RegisterEmail);
        values.put(gender_COL, RegisterGender);
        values.put(dateOfBirth_COL, RegisterBirth);
        values.put(phone_COL, RegisterPhone);



        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //Thêm Admin
    public boolean adAdmin(String AdminName, String AdminUser, String AdminPassWord, String AdminEmail, String AdminPhone){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NameAdmin_COL, AdminName);
        values.put(userAdmin_COL, AdminUser);
        values.put(passWordAdmin_COL, AdminPassWord);
        values.put(emailAdmin_COL, AdminEmail);
        values.put(phoneAdmin_COL, AdminPhone);


        db.insert(TABLE_ADMIN, null, values);
        db.close();

        return false;
    }




    //Thêm Staff
    public void  adStaff(String StaffName, String StaffUser, String StaffPassWord, String StaffEmail, String StaffPhone){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NameSTAFF_COL, StaffName);
        values.put(userSTAFF_COL, StaffUser);
        values.put(passWordSTAFF_COL, StaffPassWord);
        values.put(emailSTAFF_COL, StaffEmail);
        values.put(phoneSTAFF_COL, StaffPhone);


        db.insert(TABLE_STAFF, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + TABLE_ADMIN + TABLE_STAFF);
        onCreate(db);
    }

    //Xóa data trong bảng
    public void deleteDataStaff(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        //Xóa nhân viên từ bảng nhân viên
        db.delete(TABLE_STAFF, userSTAFF_COL + "=?", new  String[]{username});
        db.close();
    }


    public  String getHashedPassWordByUserName(String userName){
        SQLiteDatabase db = this.getReadableDatabase();
        String hashedpassword = null;
        Cursor cursor  = db.query(TABLE_NAME, new String[]{passWord_COL}, userName_COL + "=?", new String[]{userName}, null, null, null);
        if(cursor!=null && cursor.moveToFirst()){
            hashedpassword = cursor.getString(0);
            cursor.close();
        }
        db.close();
        return hashedpassword;
    }


    //Cập nhật data
    public boolean updateStaff(String name, String username, String email, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NameSTAFF_COL, name);
        values.put(emailSTAFF_COL, email);
        values.put(phoneSTAFF_COL, phone);
        int rowsAffected = db.update(TABLE_STAFF, values, userSTAFF_COL + "=?", new  String[]{username});
        db.close();
        return rowsAffected > 0;
    }

    //Lấy passWord trong database để so sánh đăng nhập Admin
    public  String getHashedPassWordByUserNameAdmin(String userName){
        SQLiteDatabase db = this.getReadableDatabase();
        String hashedpassword = null;
        Cursor cursor  = db.query(TABLE_ADMIN, new String[]{passWordAdmin_COL}, userAdmin_COL + "=?", new String[]{userName}, null, null, null);
        if(cursor!=null && cursor.moveToFirst()){
            hashedpassword = cursor.getString(0);
            cursor.close();
        }
        db.close();
        return hashedpassword;
    }

    //Kiểm tra tên người dùng đăng nhập trong các table
    public  boolean isUserNameExists(String username){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        //Kiểm tra trong bảng Khách hàng
        cursor = db.rawQuery("SELECT 1 FROM Register WHERE username = ?", new String[]{username});
        if(cursor.getCount() > 0){
            cursor.close();
            return true;
        }
        cursor.close();


        //Kiểm tra trong bảng admin
        cursor = db.rawQuery("SELECT 1 FROM Admin WHERE username = ?", new String[]{username});
        if(cursor.getCount() > 0){
            cursor.close();
            return true;
        }
        cursor.close();

        //Kiểm tra trong bảng Staff
        cursor = db.rawQuery("SELECT 1 FROM Staff WHERE username = ?", new String[]{username});
        if(cursor.getCount() > 0){
            cursor.close();
            return true;
        }
        cursor.close();

        return false;
    }


    //Kiểm tra bên trong admin có chứa satff chưa
    public boolean isUserStaffExists(String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        //Kiểm tra bảng Admin
        cursor = db.rawQuery("SELECT 1 FROM Admin WHERE username = ?", new String[]{username});
        if(cursor.getCount() > 0){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }



}
