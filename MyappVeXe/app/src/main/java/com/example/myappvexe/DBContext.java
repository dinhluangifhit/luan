package com.example.myappvexe;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBContext {
    SQLiteHelper helper;
    Context context;
    SQLiteDatabase db;

    public DBContext(Context context) {
        this.context = context;
    }

    public DBContext opent() throws SQLiteException {
        helper = new SQLiteHelper(this.context);
        db = helper.getWritableDatabase();
        return this;
    }

    public  void close(){
        helper.close();
    }

    //Cursor là một con trỏ, trỏ đến kết quả truy vấn của câu dữ liệu
    public Cursor selectAll(){
        String[] col = new String[]{"CustumerID","Name", "userName", "passWord", "email", "gender", "dateOfBirth", "phone"};
        Cursor cursor = db.query("custumers",col, null,null,null,null,null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //Thêm dữ liệu vào table
    public  void insert(String name, String Username, String Password, String Email, String Gender, String DateOfBirth, String Phone){
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("userName", Username);
        values.put("passWord", Password);
        values.put("email", Email);
        values.put("gender", Gender);
        values.put("dateOfBirth", DateOfBirth);
        values.put("phone", Phone);
        db.insert("custumers",null,values);

    }

    //Cập nhật dữ liệu vào bảng
    public void update(long id,String name, String Username, String Password, String Email, String Gender, String DateOfBirth, String Phone){
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("userName", Username);
        values.put("passWord", Password);
        values.put("email", Email);
        values.put("gender", Gender);
        values.put("dateOfBirth", DateOfBirth);
        values.put("phone", Phone);
        db.update("custumers", values,"CustumerID =" + id,null);

    }
    public void delete(long id){
        db.delete("custumers","CustumerID= =" +id,null);
    }



}
