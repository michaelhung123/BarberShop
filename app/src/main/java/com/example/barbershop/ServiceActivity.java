package com.example.barbershop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Adaptor.CategoryAdapter;
import com.example.barbershop.Adaptor.ServiceAdapter;
import com.example.barbershop.Domain.Category;
import com.example.barbershop.Domain.Service;
import com.example.barbershop.Domain.Staff;
import com.example.barbershop.Module.CategoryDataSource;
import com.example.barbershop.Module.ServiceDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_in_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        RecyclerView rcvService = findViewById(R.id.rcvService);
        ServiceAdapter serviceAdapter = new ServiceAdapter(this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvService.setLayoutManager(linearLayoutManager1);
        rcvService.setLayoutManager(new GridLayoutManager(this, 2));
        serviceAdapter.setData(getListService());
        rcvService.setAdapter(serviceAdapter);
    }

    private List<Service> getListService() {
            SharedPreferences sharedPreferences = getSharedPreferences("categoryId", Context.MODE_PRIVATE);
            int categoryId = sharedPreferences.getInt("categoryId", -1);

            ServiceDataSource serviceDataSource = new ServiceDataSource(this);
            List<Service> serviceList = serviceDataSource.getService_CategoryId(this,categoryId);
            return serviceList;
    }

    //    Bắt sự kiện button Back trên Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Tạo Intent để chuyển đến MainActivity
            Intent intent = new Intent(ServiceActivity.this, MainActivity.class);

            // Chạy Intent để chuyển đến SecondActivity
            startActivity(intent);

            // Kết thúc Activity hiện tại (MainActivity) nếu bạn không muốn quay lại nó
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
