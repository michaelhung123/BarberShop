package com.example.barbershop;

import android.app.Activity;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Adaptor.SelectedServicesViewModel;
import com.example.barbershop.Adaptor.ServiceAdapter;
import com.example.barbershop.Adaptor.StaffAdapter;
import com.example.barbershop.Common.Common;
import com.example.barbershop.Domain.Account;
import com.example.barbershop.Domain.Booking;
import com.example.barbershop.Domain.BookingDetail;
import com.example.barbershop.Domain.Service;
import com.example.barbershop.Domain.Staff;
import com.example.barbershop.Module.AccountDataSource;
import com.example.barbershop.Module.BookingDataSource;
import com.example.barbershop.Module.BookingDetailDataSource;
import com.example.barbershop.Module.CategoryDataSource;
import com.example.barbershop.Module.ServiceDataSource;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity {
    BookingDataSource bookingDataSource = new BookingDataSource(this);
    Booking booking = new Booking();
    BookingDetail bookingDetail = new BookingDetail();
    BookingDetailDataSource bookingDetailDataSource = new BookingDetailDataSource(this);
    private SelectedServicesViewModel viewModel;
    ArrayList<Account> staffList = new ArrayList<>();
    Spinner spinnerStaff;
    RecyclerView rcvService;
    TextView spinnerStaffSelected;
    TextView totalServices;
    Button btnPayNow;
//    BookingDetailDataSource bookingDetailDataSource = new BookingDetailDataSource(this);
//    ServiceDataSource  serviceDataSource = new ServiceDataSource(this);

    String amount = "";
    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(Common.PAYPAL_CLIENT_ID);

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_layout);
        AccountDataSource accountDataSource = new AccountDataSource(this);

        MyApplication myApplication = (MyApplication) getApplication();
        viewModel = myApplication.getViewModel();

//        spinnerStaff = findViewById(R.id.spinnerStaff);
//        spinnerStaffSelected = findViewById(R.id.spinnerStaffSelected);
        rcvService = findViewById(R.id.rcvService);
        btnPayNow = findViewById(R.id.btnPayNow);
        totalServices = findViewById(R.id.totalServices);

        staffList = AccountDataSource.selectAccountsRoleStaff(getApplicationContext());
        ArrayAdapter<Account> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, staffList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
//        spinnerStaff.setAdapter(adapter);
//        spinnerStaff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                spinnerStaffSelected.setText(spinnerStaff.getSelectedItem().toString());
//                int staffId = accountDataSource.getStaffIdByUsername(spinnerStaffSelected.getText().toString());
//                booking.setStaffId(staffId);
//                SharedPreferences sharedPreferences = getSharedPreferences("BookingData", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("staffId", staffId);
//                editor.apply();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        ServiceAdapter serviceAdapter = new ServiceAdapter(this, null,true,viewModel);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvService.setLayoutManager(linearLayoutManager1);
        rcvService.setLayoutManager(new GridLayoutManager(this, 2));
        serviceAdapter.setData(getListService());
        rcvService.setAdapter(serviceAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);


        SharedPreferences sharedPreferences3 = getSharedPreferences("BookingData",MODE_PRIVATE);
        int staffId = sharedPreferences3.getInt("staffId",-1);

        SharedPreferences sharedPreferences2 = getSharedPreferences("BookingData", MODE_PRIVATE);
        Long slot = sharedPreferences2.getLong("slot", -1);
        String getTime = sharedPreferences2.getString("getTime", "");
        String createTime = sharedPreferences2.getString("createTime", "");

        booking.setUserId(userId);
        booking.setTime(getTime);
        booking.setSlot(slot);
        booking.setCreateTime(createTime);
        booking.setStaffId(staffId);

        Double total = viewModel.getTotalServices();
        String totalStringFormat = String.format("Tổng tiền: $%1$s", total);
        totalServices.setText(totalStringFormat);
        booking.setTotal(total);

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
                bookingDataSource.addBooking(booking);

                int bookingId = bookingDataSource.getIdBooking(BookingActivity.this, booking.getUserId(), booking.getTime());

                for (Service service : viewModel.getSelectedServices()){

                    bookingDetail.setBookingId(bookingId);
                    bookingDetail.setServiceId(service.getId());

                    bookingDetailDataSource.addBookingService(bookingDetail);
                }
            }
        });

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

    }

    private void processPayment(){
        amount = String.valueOf(viewModel.getTotalServices());
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD",
                "Chuyển khoản cho Quoc Hung", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, Booking3Activity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", amount));
                        CustomToast.makeText(BookingActivity.this, "Thanh toán thành công", CustomToast.LENGTH_SHORT,CustomToast.SUCCESS,true).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                CustomToast.makeText(BookingActivity.this, "Hủy", CustomToast.LENGTH_SHORT,CustomToast.ERROR,true).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
    }


    private List<Service> getListService() {
        // Khởi tạo ViewModel
        return viewModel.getSelectedServices();
    }
}

