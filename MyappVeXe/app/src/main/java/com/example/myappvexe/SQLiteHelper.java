package com.example.myappvexe;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper{
    //Tạo cơ sở dữ liệu
    static final String DB_NAME = "mySQLiteBanVeXe";
    static final int VERSION = 1;
    public SQLiteHelper( Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlText = "CREATE TABLE custumers(CustumerID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT  NOT NULL , " +
                "userName TEXT  NOT NULL, " +
                "passWord TEXT  NOT NULL , " +
                "email TEXT  NOT NULL, " +
                "gender INTEGER  NOT NULL , " +
                "dateOfBirth TEXT  NOT NULL, " +
                "phone INTEGER  NOT NULL)";
        db.execSQL(sqlText);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS custumers");
    }
}
