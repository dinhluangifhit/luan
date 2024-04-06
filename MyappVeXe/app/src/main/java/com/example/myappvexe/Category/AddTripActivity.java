package com.example.myappvexe.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.myappvexe.R;
import com.example.myappvexe.SQLiteHelper;

import java.util.Calendar;

public class AddTripActivity extends AppCompatActivity {
    private AutoCompleteTextView AutoCompleteStationTrip,  AutoCompleteLocationTrip;
    private SQLiteDatabase db;
    private EditText EditTimeBusStar, EditDateBusStar, EditTimeBusEnd, TimeEnd, PriceTrip, SeatTrip;
    private Button BntAddTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        EditTimeBusStar = findViewById(R.id.editTimeBusStar);
        EditDateBusStar = findViewById(R.id.editDateBusStar);
        EditTimeBusEnd = findViewById(R.id.editTimeBusEnd);
        TimeEnd = findViewById(R.id.timeEnd);
        PriceTrip = findViewById(R.id.priceTrip);
        SeatTrip = findViewById(R.id.seatTrip);
        AutoCompleteStationTrip = findViewById(R.id.autoCompleteStationTrip);
        AutoCompleteLocationTrip = findViewById(R.id.autoCompleteLocationTrip);
        BntAddTrip = findViewById(R.id.bntAddTrip);



        SQLiteHelper dbHelper = new SQLiteHelper(this);
        db = dbHelper.getReadableDatabase();
        LocationAutoCompleteAdapter adapter = new LocationAutoCompleteAdapter(this, null, db);

        AutoCompleteLocationTrip.setAdapter(adapter);
        AutoCompleteStationTrip.setAdapter(adapter);

        AutoCompleteLocationTrip.setOnItemClickListener((adapterView, view, position, id) -> {
            // Lấy CursorAdapter và Cursor tương ứng
            CursorAdapter cursorAdapter = (CursorAdapter) AutoCompleteLocationTrip.getAdapter();
            Cursor cursor = cursorAdapter.getCursor();
            // Di chuyển Cursor đến vị trí của mục đã chọn
            cursor.moveToPosition(position);

            // Lấy giá trị từ cột "Name" của Cursor và đặt vào AutoCompleteTextView
            String selectedName = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
            AutoCompleteLocationTrip.setText(selectedName);

            // Đóng danh sách và loại bỏ focus khỏi AutoCompleteTextView
            AutoCompleteLocationTrip.dismissDropDown();
            AutoCompleteLocationTrip.clearFocus();
        });

        AutoCompleteStationTrip.setOnItemClickListener((adapterView, view, position, id) -> {
            // Lấy CursorAdapter và Cursor tương ứng
            CursorAdapter cursorAdapter = (CursorAdapter) AutoCompleteStationTrip.getAdapter();
            Cursor cursor = cursorAdapter.getCursor();
            // Di chuyển Cursor đến vị trí của mục đã chọn
            cursor.moveToPosition(position);

            // Lấy giá trị từ cột "Name" của Cursor và đặt vào AutoCompleteTextView
            String selectedName = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
            AutoCompleteStationTrip.setText(selectedName);

            // Đóng danh sách và loại bỏ focus khỏi AutoCompleteTextView
            AutoCompleteStationTrip.dismissDropDown();
            AutoCompleteStationTrip.clearFocus();
        });

        //Sử lý thêm vào Trip
        BntAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        db.close();
    }

    //Hiển thị chọn thời gian
    public  void showDialogTime(View v){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int munite = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                EditTimeBusStar.setText(hourOfDay + ":" + minute);
            }
        }, hour, munite, true);
        timePickerDialog.show();
    }

    public  void showDialogTimeEnd(View v){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int munite = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                EditTimeBusEnd.setText(hourOfDay + ":" + minute);
            }
        }, hour, munite, true);
        timePickerDialog.show();
    }

    //Hiển thị chọn ngày
    public void showDialogDate(View v){
        final  Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                EditDateBusStar.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();

    }
}