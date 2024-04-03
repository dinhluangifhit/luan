package com.example.myappvexe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

public class CustomerArrayAdapter {
    //Kết nối SQLite
    private SQLiteHelper dbHelper;
    private SQLiteDatabase database;
    private String[] allColumsCustomer = {SQLiteHelper.ID_COL, SQLiteHelper.Name_COL,SQLiteHelper.userName_COL,SQLiteHelper.passWord_COL, SQLiteHelper.email_COL, SQLiteHelper.gender_COL,
                                            SQLiteHelper.dateOfBirth_COL,SQLiteHelper.phone_COL};

    CustomerArrayAdapter(Context context){
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLiteException{
        database = dbHelper.getWritableDatabase();
    }

    public void close() {dbHelper.close();}

    public Customer CreateCustomer (String name){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.Name_COL, name);
        long inSertID = database.insert(SQLiteHelper.TABLE_NAME, null, values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_NAME, allColumsCustomer, SQLiteHelper.ID_COL +
                " = " + inSertID, null, null, null, null, null);
        cursor.moveToFirst();
        Customer newCustomer = cursorToCustomer(cursor);
        cursor.close();
        return newCustomer;
    }

    public void deletedCustomer(Customer name){
        long id  = name.getId();
        System.out.println("Coment deleted withd id: " + id);
        database.delete(SQLiteHelper.TABLE_NAME, SQLiteHelper.ID_COL + " = " + id, null);
    }

    public List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<Customer>();
        Cursor cursor = database.query(SQLiteHelper.TABLE_NAME,allColumsCustomer, null,null,null, null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Customer name = cursorToCustomer(cursor);
            customers.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return customers;
    }


    private Customer cursorToCustomer(Cursor cursor){
        Customer customer = new Customer();
        customer.setId(cursor.getLong(0));
        customer.setName(cursor.getString(1));
        customer.setUsername(cursor.getString(2));
        customer.setPassword(cursor.getString(3));
        customer.setEmail(cursor.getString(4));
        customer.setGender(cursor.getString(5));
        customer.setDayofbidth(cursor.getString(6));
        customer.setPhone(cursor.getString(7));
        return customer;
    }

}
