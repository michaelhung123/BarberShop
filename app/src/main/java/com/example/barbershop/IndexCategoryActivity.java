package com.example.barbershop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import com.example.barbershop.Module.CategoryDataSource;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IndexCategoryActivity extends AppCompatActivity {
    Button btnCreateCategory;
    ListView lvCategoires;
    ArrayList<Category> categories = new ArrayList<>();
    ArrayAdapter adapterListView;
    private static int IMG_REQ = 1;
    private Uri imagePath;
    ImageView imgView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_categories);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        btnCreateCategory = findViewById(R.id.btnCreateCategory);
        lvCategoires = findViewById(R.id.lvCategories);
        categories = CategoryDataSource.selectCategories(IndexCategoryActivity.this);
        adapterListView = new ArrayAdapter(IndexCategoryActivity.this, android.R.layout.simple_list_item_1, categories) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };
        lvCategoires.setAdapter(adapterListView);
//        initConfig();


        btnCreateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCreateCategory();
            }
        });

        lvCategoires.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogUpdateCategory(position);
            }
        });


    }

//    private void initConfig() {
//        Map config = new HashMap();
//        config.put("cloud_name", "dgm68hajt");
//        config.put("api_key", "445342655699255");
//        config.put("api_secret", "-RkgzrKOgwbd32E9oK71iOW_WDQ");
//        config.put("secure", true);
//        MediaManager.init(this, config);
//    }

    private void requestPermissions() {
        if(ContextCompat.checkSelfPermission(IndexCategoryActivity.this, android.Manifest.permission.READ_MEDIA_IMAGES)
                == PackageManager.PERMISSION_GRANTED){
            selectImage();
        }else
        {
            ActivityCompat.requestPermissions(IndexCategoryActivity.this, new String[]{
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

    private void showDialogCreateCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(IndexCategoryActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_category, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText createName = view.findViewById(R.id.createName);
        EditText createDescription = view.findViewById(R.id.createDescription);
        imgView = view.findViewById(R.id.imgView);
        Button btnAdd = view.findViewById(R.id.btnAdd);


        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermissions();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category category = new Category();
                CategoryDataSource categoryDataSource = new CategoryDataSource(IndexCategoryActivity.this);
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
                        category.setName(createName.getText().toString());
                        category.setDescription(createDescription.getText().toString());
                        category.setImagePic(imageUrl);

                        CategoryDataSource categoryDataSource = new CategoryDataSource(IndexCategoryActivity.this);
                        if (categoryDataSource.addCategory(category) instanceof Category) {
                            Toast.makeText(IndexCategoryActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            categories.clear();
                            categories.addAll(CategoryDataSource.selectCategories(IndexCategoryActivity.this));
                            adapterListView.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(IndexCategoryActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
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
    }

    private void showDialogUpdateCategory(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(IndexCategoryActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_category, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText editName = view.findViewById(R.id.editName);
        EditText editDescription = view.findViewById(R.id.editDescription);
        imgView = view.findViewById(R.id.imgView);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        Category category = categories.get(pos);

        editName.setText(category.getName());
        editDescription.setText(category.getDescription());
        Picasso.get().load(category.getImagePic()).resize(300,300).into(imgView);

//        initConfig();

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermissions();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category.setName(editName.getText().toString());
                category.setDescription(editDescription.getText().toString());
//                category.setImagePic(editFile.getText().toString());

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
                        category.setName(editName.getText().toString());
                        category.setDescription(editDescription.getText().toString());
                        category.setImagePic(imageUrl);

                        CategoryDataSource categoryDataSource = new CategoryDataSource(IndexCategoryActivity.this);
                        if (categoryDataSource.updateCategory(IndexCategoryActivity.this, category)) {
                            Toast.makeText(IndexCategoryActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            categories.clear();
                            categories.addAll(CategoryDataSource.selectCategories(IndexCategoryActivity.this));
                            adapterListView.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(IndexCategoryActivity.this, "Cập nhật thất bại!!", Toast.LENGTH_SHORT).show();
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CategoryDataSource.deleteCategory(IndexCategoryActivity.this, category.getId())) {
                    Toast.makeText(IndexCategoryActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    categories.clear();
                    categories.addAll(CategoryDataSource.selectCategories(IndexCategoryActivity.this));
                    adapterListView.notifyDataSetChanged();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(IndexCategoryActivity.this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //    Bắt sự kiện button Back trên Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Tạo Intent để chuyển đến MainActivity
            Intent intent = new Intent(this, MainActivity.class);

            // Chạy Intent để chuyển đến SecondActivity
            startActivity(intent);

            // Kết thúc Activity hiện tại (MainActivity) nếu bạn không muốn quay lại nó
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
