package com.example.barbershop;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Domain.Category;
import com.example.barbershop.Domain.Service;
import com.example.barbershop.Module.CategoryDataSource;
import com.example.barbershop.Module.ServiceDataSource;

import java.util.ArrayList;

public class IndexServiceActivity extends AppCompatActivity {
    Button btnCreate;
    ArrayList<Category> categoryNames = new ArrayList<>();

    Service service;
    ArrayList<Service> services = new ArrayList<>();
    ServiceDataSource serviceDataSource = new ServiceDataSource(IndexServiceActivity.this);
    ArrayAdapter adapterListView;
    ListView lvServices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_services);
        btnCreate = findViewById(R.id.btnCreateService);
        services = ServiceDataSource.selectServices(IndexServiceActivity.this);
        lvServices = findViewById(R.id.lvServices);
        categoryNames = CategoryDataSource.selectCategories(IndexServiceActivity.this);
        adapterListView = new ArrayAdapter<>(IndexServiceActivity.this, android.R.layout.simple_list_item_1, services);
        lvServices.setAdapter(adapterListView);
        service = new Service();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCreateService();
            }
        });

        lvServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogUpdateService(position);
            }
        });
    }

    private void showDialogCreateService() {
        AlertDialog.Builder builder = new AlertDialog.Builder(IndexServiceActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_service, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText createName = view.findViewById(R.id.createName);
        EditText createDescription = view.findViewById(R.id.createDescription);
        ImageView imgView = view.findViewById(R.id.imgView);
        EditText createPrice = view.findViewById(R.id.createPrice);
        Spinner spinnerCategory = view.findViewById(R.id.spnCategory);
        ArrayAdapter adapter = new ArrayAdapter(IndexServiceActivity.this, android.R.layout.simple_spinner_item, categoryNames);
        Button btnAdd = view.findViewById(R.id.btnAdd);

        spinnerCategory.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.setName(createName.getText().toString());
                service.setDescription(createDescription.getText().toString());
//                service.setFilePath(createFile.getText().toString());
                service.setPrice(Integer.parseInt(createPrice.getText().toString()));
                if (serviceDataSource.addService(service) instanceof Service) {
                    Toast.makeText(IndexServiceActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    services.clear();
                    services.addAll(serviceDataSource.selectServices(IndexServiceActivity.this));
                    adapterListView.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(IndexServiceActivity.this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                System.out.println(selectedCategory.getId());
                service.setCategory_id(selectedCategory.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có lựa chọn nào được chọn
            }
        });
    }

    private void showDialogUpdateService(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(IndexServiceActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_service, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText editName = view.findViewById(R.id.editName);
        EditText editDescription = view.findViewById(R.id.editDescription);
        EditText editFile = view.findViewById(R.id.editFile);
        EditText editPrice = view.findViewById(R.id.editPrice);
        Spinner spinnerCategory = view.findViewById(R.id.spnCategory);
        ArrayAdapter adapter = new ArrayAdapter(IndexServiceActivity.this, android.R.layout.simple_spinner_item, categoryNames);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        Service service = services.get(pos);

        editName.setText(service.getName());
        editDescription.setText(service.getDescription());
        editPrice.setText(Double.toString(service.getPrice()));
        editFile.setText(service.getFilePath());
        spinnerCategory.setAdapter(adapter);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.setName(editName.getText().toString());
                service.setDescription(editDescription.getText().toString());
                service.setPrice(Double.parseDouble(editPrice.getText().toString()));
                service.setFilePath(editFile.getText().toString());

                System.out.println(editName.getText().toString());

                if(ServiceDataSource.updateService(IndexServiceActivity.this, service)) {
                    Toast.makeText(IndexServiceActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    services.clear();
                    services.addAll(ServiceDataSource.selectServices(IndexServiceActivity.this));
                    adapterListView.notifyDataSetChanged();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(IndexServiceActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ServiceDataSource.deleteService(IndexServiceActivity.this, service.getId())) {
                    Toast.makeText(IndexServiceActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    services.clear();
                    services.addAll(ServiceDataSource.selectServices(IndexServiceActivity.this));
                    adapterListView.notifyDataSetChanged();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(IndexServiceActivity.this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                System.out.println(selectedCategory.getId());
                service.setCategory_id(selectedCategory.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có lựa chọn nào được chọn
            }
        });
    }
}
