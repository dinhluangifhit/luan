package com.example.myappvexe.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myappvexe.Customer.Customer;
import com.example.myappvexe.Customer.CustomerArrayAdapter;
import com.example.myappvexe.Customer.ListAdapterCustpmer;
import com.example.myappvexe.R;
import com.example.myappvexe.SQLiteHelper;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private EditText SearchTripList;
    private TextView TxtAddTrip;
    private ListAdapterTrip adapterTrip;
    private SwipeRefreshLayout SwipeRefreshLayout;
    private SQLiteHelper sqLiteHelper;
    private TripArrayAdapter adapter;
    private ListView ListShowTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        TxtAddTrip = findViewById(R.id.txtAddTrip);
        ListShowTrip = findViewById(R.id.listShowTrip);

        adapter = new TripArrayAdapter(this);
        adapter.opent();


        List<Trip> tripList =  adapter.getAllTrips();
        //Hiển thị danh sách khách hàng
        ListAdapterTrip adapter = new ListAdapterTrip(this, tripList);
        ListShowTrip.setAdapter(adapter);

        SwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        loadTripList();
        //Load lại danh sach khi admin thay dổi nhân viên
        SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTripList();
                SwipeRefreshLayout.setRefreshing(false);
            }
        });

        TxtAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, AddTripActivity.class);
                startActivity(intent);
            }
        });
        
    }

    private  void loadTripList(){
        adapter = new TripArrayAdapter(this);
        adapter.opent();

        List<Trip> loadList = adapter.getAllTrips();
        ListAdapterTrip adapter = new ListAdapterTrip(this, loadList);
        ListShowTrip.setAdapter(adapter);
    }
}