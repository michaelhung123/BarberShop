package com.example.barbershop;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.barbershop.Domain.Category;
import com.example.barbershop.Domain.Service;
import com.example.barbershop.Module.CategoryDataSource;
import com.example.barbershop.Module.ServiceDataSource;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IndexServiceActivity extends AppCompatActivity {
    Button btnCreateService;
    ArrayList<Category> categoryNames = new ArrayList<>();

    Service service;
    ArrayList<Service> services = new ArrayList<>();
    ServiceDataSource serviceDataSource = new ServiceDataSource(IndexServiceActivity.this);
    ArrayAdapter adapterListView;
    ListView lvServices;
    private static int IMG_REQ = 1;
    private Uri imagePath;
    ImageView imgView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_services);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        btnCreateService = findViewById(R.id.btnCreateService);
        services = ServiceDataSource.selectServices(IndexServiceActivity.this);
        lvServices = findViewById(R.id.lvServices);
        categoryNames = CategoryDataSource.selectCategories(IndexServiceActivity.this);
        adapterListView = new ArrayAdapter(IndexServiceActivity.this, android.R.layout.simple_list_item_1, services) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };
        lvServices.setAdapter(adapterListView);
//        initConfig();
        service = new Service();

        btnCreateService.setOnClickListener(new View.OnClickListener() {
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

//    public void initConfig() {
//        Map config = new HashMap();
//        config.put("cloud_name", "dgm68hajt");
//        config.put("api_key", "445342655699255");
//        config.put("api_secret", "-RkgzrKOgwbd32E9oK71iOW_WDQ");
//        config.put("secure", true);
//        MediaManager.init(this, config);
//    }

    private void requestPermissions() {
        if(ContextCompat.checkSelfPermission(IndexServiceActivity.this, android.Manifest.permission.READ_MEDIA_IMAGES)
                == PackageManager.PERMISSION_GRANTED){
            selectImage();
        }else
        {
            ActivityCompat.requestPermissions(IndexServiceActivity.this, new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES
            },1);
        }
    }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQ && resultCode == Activity.RESULT_OK && data != null && data.getData() != null ){
            imagePath = data.getData();
            Picasso.get().load(imagePath).into(imgView);
        }
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
        imgView = view.findViewById(R.id.imgView);
        EditText createPrice = view.findViewById(R.id.createPrice);
        Spinner spinnerCategory = view.findViewById(R.id.spnCategory);
        ArrayAdapter adapter = new ArrayAdapter(IndexServiceActivity.this, android.R.layout.simple_spinner_item, categoryNames);
        Button btnAdd = view.findViewById(R.id.btnAdd);

        spinnerCategory.setAdapter(adapter);

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermissions();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaManager.get().upload(imagePath).callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {

                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {

                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        String imageUrl = resultData.get("secure_url").toString();
                        service.setName(createName.getText().toString());
                        service.setDescription(createDescription.getText().toString());
                        service.setPrice(Double.parseDouble(createPrice.getText().toString()));
                        service.setFilePath(imageUrl);

                        if (serviceDataSource.addService(service) instanceof Service) {
                            Toast.makeText(IndexServiceActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            services.clear();
                            services.addAll(ServiceDataSource.selectServices(IndexServiceActivity.this));
                            adapterListView.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(IndexServiceActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {

                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {

                    }
                }).dispatch();
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

    //    Bắt sự kiện button Back trên Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Tạo Intent để chuyển đến MainActivity
            Intent intent = new Intent(this, ServiceActivity.class);

            // Chạy Intent để chuyển đến SecondActivity
            startActivity(intent);

            // Kết thúc Activity hiện tại (MainActivity) nếu bạn không muốn quay lại nó
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
