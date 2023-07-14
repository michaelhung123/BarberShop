package com.example.barbershop;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Domain.Category;
import com.example.barbershop.Module.CategoryDataSource;

import java.util.ArrayList;

public class IndexCategoryActivity extends AppCompatActivity {
    Button btnCreateCategory;
    ListView lvCategoires;
    ArrayList<Category> categories = new ArrayList<>();
    ArrayAdapter adapterListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_categories);

        btnCreateCategory = findViewById(R.id.btnCreateCategory);
        lvCategoires = findViewById(R.id.lvCategories);
        categories = CategoryDataSource.selectCategories(IndexCategoryActivity.this);
        adapterListView = new ArrayAdapter<>(IndexCategoryActivity.this, android.R.layout.simple_list_item_1, categories);
        lvCategoires.setAdapter(adapterListView);

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

    private void showDialogCreateCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(IndexCategoryActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_category, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText createName = view.findViewById(R.id.createName);
        EditText createDescription = view.findViewById(R.id.createDescription);
        EditText createFile = view.findViewById(R.id.createFile);
        Button btnAdd = view.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category category = new Category();
                category.setName(createName.getText().toString());
                category.setDescription(createDescription.getText().toString());
                category.setImagePic(createFile.getText().toString());
                CategoryDataSource categoryDataSource = new CategoryDataSource(IndexCategoryActivity.this);
//                Toast.makeText(CategoryActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                if (categoryDataSource.addCategory(category) instanceof Category) {
                    Toast.makeText(IndexCategoryActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    categories.clear();
                    categories.addAll(CategoryDataSource.selectCategories(IndexCategoryActivity.this));
                    adapterListView.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(IndexCategoryActivity.this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                }
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
        EditText editFile = view.findViewById(R.id.editFile);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        Category category = categories.get(pos);

        editName.setText(category.getName());
        editDescription.setText(category.getDescription());
        editFile.setText(category.getImagePic());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category.setName(editName.getText().toString());
                category.setDescription(editDescription.getText().toString());
                category.setImagePic(editFile.getText().toString());

                System.out.println(editName.getText().toString());

                if(CategoryDataSource.updateCategory(IndexCategoryActivity.this, category)) {
                    Toast.makeText(IndexCategoryActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    categories.clear();
                    categories.addAll(CategoryDataSource.selectCategories(IndexCategoryActivity.this));
                    adapterListView.notifyDataSetChanged();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(IndexCategoryActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
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
}
