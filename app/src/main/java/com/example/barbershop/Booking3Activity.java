package com.example.barbershop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Adaptor.SelectedServicesViewModel;
import com.example.barbershop.Adaptor.ServiceAdapter;
import com.example.barbershop.Common.Common;
import com.example.barbershop.Domain.Account;
import com.example.barbershop.Domain.Booking;
import com.example.barbershop.Domain.Service;
import com.example.barbershop.Module.AccountDataSource;
import com.example.barbershop.Module.BookingDataSource;

import java.util.List;

public class Booking3Activity extends AppCompatActivity {
    private SelectedServicesViewModel viewModel;
    private TextView txtUsername;
    private TextView txtStaff;
    private TextView txtBookingTime;
    private TextView txtSlot;
    private RecyclerView rcvService;
    private Button btnNext;
    ProgressBar progressBar;

    Booking booking = new Booking();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_layout_3);

        txtUsername = findViewById(R.id.txtUsername);
        txtStaff = findViewById(R.id.txtStaff);
        txtBookingTime = findViewById(R.id.txtBookingTime);
        txtSlot = findViewById(R.id.txtSlot);
        rcvService = findViewById(R.id.rcvService);
        progressBar = findViewById(R.id.progressBar);
        btnNext = findViewById(R.id.btnNext);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId",-1);

        AccountDataSource accountDataSource = new AccountDataSource(this);

        BookingDataSource bookingDataSource = new BookingDataSource(this);
        booking = bookingDataSource.getBookingsByUser(this,userId);

        String userName = accountDataSource.getUserByUserId(booking.getUserId());
        txtUsername.setText(userName);

        String staffName = accountDataSource.getStaffByStaffId(booking.getStaffId());
        txtStaff.setText(staffName);

        txtBookingTime.setText(booking.getTime());

        String slotString = Common.convertTimeSlotToString(Math.toIntExact(booking.getSlot()));
        txtSlot.setText(slotString);

        MyApplication myApplication = (MyApplication) getApplication();
        viewModel = myApplication.getViewModel();

        ServiceAdapter serviceAdapter = new ServiceAdapter(this, null,true,viewModel);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvService.setLayoutManager(linearLayoutManager1);
        rcvService.setLayoutManager(new GridLayoutManager(this, 2));
        serviceAdapter.setData(getListService());
        rcvService.setAdapter(serviceAdapter);
        
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.INVISIBLE);

                CustomToast.makeText(Booking3Activity.this, "Đặt lịch thành công", CustomToast.LENGTH_SHORT,CustomToast.SUCCESS,true).show();
                
                Intent intent = new Intent(Booking3Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
                
            }
        });
    }

    private List<Service> getListService() {
        // Khởi tạo ViewModel
        return viewModel.getSelectedServices();
    }
}
