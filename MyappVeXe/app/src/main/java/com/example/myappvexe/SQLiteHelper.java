package com.example.myappvexe;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper{
    //Tạo cơ sở dữ liệu
    private static final String DB_NAME = "mySQLiteBanVeXe";
    private static final int VERSION = 1;

    private static final String TABLE_NAME = "Register";
    private static final String ID_COL = "id";

    private static final String Name_COL = "Name";

    private static final String userName_COL = "userName";

    private static final String passWord_COL = "passWord";

    private static final String email_COL = "email";

    private static final String gender_COL = "gender";

    private static final String dateOfBirth_COL = "dateOfBirth";

    private static final String phone_COL = "phone";

    public SQLiteHelper( Context context) {

        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String sqlText = "CREATE TABLE custumers(CustumerID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "Name TEXT  NOT NULL , " +
//                "userName TEXT  NOT NULL, " +
//                "passWord TEXT  NOT NULL , " +
//                "email TEXT  NOT NULL, " +
//                "gender INTEGER  NOT NULL , " +
//                "dateOfBirth TEXT  NOT NULL, " +
//                "phone INTEGER  NOT NULL)";

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Name_COL + " TEXT,"
                + userName_COL + " TEXT,"
                + passWord_COL + " TEXT,"
                + email_COL + " TEXT,"

                + gender_COL + " TEXT,"

                + dateOfBirth_COL + " TEXT,"

                + phone_COL + " TEXT)";

        db.execSQL(query);

    }

    // thêm user
    public void addNewAccount (String RegisterName, String RegisterUserName, String RegisterEmail,
                               String RegisterPhone, String RegisterBirth, String PassWord){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();

        values.put(Name_COL, RegisterName);
        values.put(userName_COL, RegisterUserName);
        values.put(passWord_COL, PassWord);
        values.put(email_COL, RegisterEmail);
        values.put(phone_COL, RegisterPhone);
        values.put(dateOfBirth_COL, RegisterBirth);


        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
