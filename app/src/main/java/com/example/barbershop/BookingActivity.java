package com.example.barbershop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Adaptor.ServiceAdapter;
import com.example.barbershop.Adaptor.StaffAdapter;
import com.example.barbershop.Domain.Account;
import com.example.barbershop.Domain.Booking;
import com.example.barbershop.Domain.Service;
import com.example.barbershop.Domain.Staff;
import com.example.barbershop.Module.AccountDataSource;
import com.example.barbershop.Module.CategoryDataSource;
import com.example.barbershop.Module.ServiceDataSource;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity {
    ArrayList<Account> staffList = new ArrayList<>();
    Spinner spinnerStaff;
    RecyclerView rcvService;
    TextView spinnerStaffSelected;
    Button btnAddListService;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_layout);

        spinnerStaff = findViewById(R.id.spinnerStaff);
        spinnerStaffSelected = findViewById(R.id.spinnerStaffSelected);
        rcvService = findViewById(R.id.rcvService);

        staffList = AccountDataSource.selectAccountsRoleStaff(getApplicationContext());
        ArrayAdapter<Account> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, staffList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerStaff.setAdapter(adapter);
        spinnerStaff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerStaffSelected.setText(spinnerStaff.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ServiceActivity serviceActivity = new ServiceActivity();
        serviceActivity.getBtnAddListService();

        ServiceAdapter serviceAdapter = new ServiceAdapter(this, btnAddListService,true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvService.setLayoutManager(linearLayoutManager1);
        rcvService.setLayoutManager(new GridLayoutManager(this, 2));
        serviceAdapter.setData(getListService());
        rcvService.setAdapter(serviceAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1); // -1 là giá trị mặc định nếu không tìm thấy ID

        Booking booking = new Booking();
        booking.setUserId(userId);
        Log.d("user1", String.valueOf(booking.getUserId()));
    }

    private List<Service> getListService() {

        SharedPreferences sharedPreferences = getSharedPreferences("categoryId", Context.MODE_PRIVATE);
        int categoryId = sharedPreferences.getInt("categoryId", -1);

        ServiceDataSource serviceDataSource = new ServiceDataSource(this);
        List<Service> serviceList = serviceDataSource.getService_CategoryId(this,categoryId);
        return serviceList;
    }
}

