package com.example.barbershop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Adaptor.SelectedServicesViewModel;
import com.example.barbershop.Adaptor.TimeSlotAdapter;
import com.example.barbershop.Common.Common;
import com.example.barbershop.Domain.Account;
import com.example.barbershop.Domain.Booking;
import com.example.barbershop.Domain.BookingDetail;
import com.example.barbershop.Domain.Service;
import com.example.barbershop.Domain.TimeSlot;
import com.example.barbershop.Module.AccountDataSource;
import com.example.barbershop.Module.BookingDataSource;
import com.example.barbershop.Module.BookingDetailDataSource;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Booking2Activity extends AppCompatActivity {
    private SelectedServicesViewModel viewModel;

    RecyclerView rcvTimeslot;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button btnNext;
    private Button btnPayNow;
    Booking booking = new Booking();
    ProgressBar progressBar;
    Spinner spinnerStaff;
    ArrayList<Account> staffList = new ArrayList<>();
    TextView spinnerStaffSelected;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_layout_2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        MyApplication myApplication = (MyApplication) getApplication();
        viewModel = myApplication.getViewModel();

        progressBar = findViewById(R.id.progressBar);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        rcvTimeslot = findViewById(R.id.rcvTimeslot);
        btnNext = findViewById(R.id.btnNext);

//        TimeSlotAdapter timeSlotAdapter = new TimeSlotAdapter(getApplicationContext(),getListTimeSlot());
//        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
//        rcvTimeslot.setLayoutManager(linearLayoutManager1);
//        rcvTimeslot.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
//        rcvTimeslot.setAdapter(timeSlotAdapter);

//        Log.d("timeslotsss", String.valueOf(getListTimeSlot()));

        booking.setCreateTime(getTodaysDate());

        SharedPreferences sharedPreferences1 = getSharedPreferences("BookingData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("createTime", booking.getCreateTime());
        editor.apply();

        SharedPreferences sharedPreferences2 = getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sharedPreferences2.getInt("userId", -1);
        booking.setUserId(userId);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("BookingData", MODE_PRIVATE);
                Long slot = sharedPreferences.getLong("slot", -1); // -1 là giá trị mặc định nếu không tìm thấy ID
                booking.setSlot(slot);

                progressBar.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(Booking2Activity.this, BookingActivity.class);
                startActivity(intent);
            }
        });
//        List<TimeSlot> listTimeSlot = BookingDataSource.getTimeSlot(this);

        //SpinnerStaff
        AccountDataSource accountDataSource = new AccountDataSource(this);
        spinnerStaff = findViewById(R.id.spinnerStaff);
        spinnerStaffSelected = findViewById(R.id.spinnerStaffSelected);
        staffList = AccountDataSource.selectAccountsRoleStaff(getApplicationContext());
        ArrayAdapter<Account> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, staffList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerStaff.setAdapter(adapter);
        spinnerStaff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerStaffSelected.setText(spinnerStaff.getSelectedItem().toString());
                int staffId = accountDataSource.getStaffIdByUsername(spinnerStaffSelected.getText().toString());
                booking.setStaffId(staffId);

                SharedPreferences sharedPreferences = getSharedPreferences("BookingData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("staffId", staffId);
                editor.apply();

                List<TimeSlot> timeSlotss = BookingDataSource.getTimeSlotListForStaff(Booking2Activity.this,staffId);
                TimeSlotAdapter timeSlotAdapter = new TimeSlotAdapter(getApplicationContext(),timeSlotss);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                rcvTimeslot.setLayoutManager(linearLayoutManager1);
                rcvTimeslot.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                rcvTimeslot.setAdapter(timeSlotAdapter);


                SharedPreferences sharedPreferences2 = getSharedPreferences("BookingData", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor2.putInt("staffId", staffId);
                editor2.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month +1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                booking.setTime(date);
                SharedPreferences sharedPreferences = getSharedPreferences("BookingData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("getTime", booking.getTime()); // Lưu tên người dùng
                editor.apply();

            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);
    }

    private String makeDateString(int day, int month, int year) {
        return day + "-" + getMonthFormat(month) + "-" + year;

    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "1";
        if(month == 2)
            return "2";
        if(month == 3)
            return "3";
        if(month == 4)
            return "4";
        if(month == 5)
            return "5";
        if(month == 6)
            return "6";
        if(month == 7)
            return "7";
        if(month == 8)
            return "8";
        if(month == 9)
            return "9";
        if(month == 10)
            return "10";
        if(month == 11)
            return "11";
        if(month == 12)
            return "12";
        return "1";
    }
    public void openDatePicker(View view){
        datePickerDialog.show();
    }

    private List<TimeSlot> getListTimeSlot() {
        return BookingDataSource.getTimeSlotListForStaff(this,3);
    }

//    private List<TimeSlot> getListTimeSlotByStaff() {
//        List<TimeSlot> timeSlotList = new ArrayList<>();
//        BookingDataSource.getTimeSlotListForStaff(this,timeSlotList)
//        timeSlot.setSlot(3L);
//        return timeSlotList;
//    }

    //    Bắt sự kiện button Back trên Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Tạo Intent để chuyển đến MainActivity
            Intent intent = new Intent(Booking2Activity.this, ServiceActivity.class);

            // Chạy Intent để chuyển đến SecondActivity
            startActivity(intent);

            // Kết thúc Activity hiện tại (MainActivity) nếu bạn không muốn quay lại nó
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
