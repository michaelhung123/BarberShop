package com.example.barbershop;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Domain.Category;
import com.example.barbershop.Domain.Service;
import com.example.barbershop.Module.CategoryDataSource;
import com.example.barbershop.Module.ServiceDataSource;

import java.util.ArrayList;
import java.util.List;

public class ServiceActivity extends AppCompatActivity {
    Button btnCreate;
    EditText txtName;
    Spinner spinnerCategory;
    ArrayList<Category> categoryNames = new ArrayList<>();
    ArrayAdapter adapter;
    Service serv = new Service();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_service);
        btnCreate = findViewById(R.id.btnCreateService);
        txtName = findViewById(R.id.txtServiceName);
        spinnerCategory = findViewById(R.id.spnCategory);
        categoryNames = CategoryDataSource.selectCategories(ServiceActivity.this);

        ArrayList<Category> categories = new ArrayList<>();

        ServiceDataSource serviceDataSource = new ServiceDataSource(ServiceActivity.this);

        adapter = new ArrayAdapter(ServiceActivity.this, android.R.layout.simple_spinner_item, categoryNames);
        spinnerCategory.setAdapter(adapter);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serv.setName(txtName.getText().toString());
                serv.setCategory_id(spinnerCategory.getSelectedItemPosition());
                if (serviceDataSource.addService(serv) != null) {
                    Toast.makeText(ServiceActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ServiceActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                System.out.println(selectedCategory.getId());
                serv.setCategory_id(selectedCategory.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có lựa chọn nào được chọn
            }
        });
    }
}
