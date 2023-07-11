package com.example.barbershop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Domain.Category;
import com.example.barbershop.Module.CategoryDataSource;
import com.google.android.material.textfield.TextInputEditText;

public class CategoryActivity extends AppCompatActivity {
    Button btnCreate;
    TextInputEditText txtName;
    TextInputEditText txtDescription;
    TextInputEditText txtFile;
    Category category;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_category);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = new Category();
                category.setName(txtName.getText().toString());
                category.setDescription(txtDescription.getText().toString());
                category.setImagePic(txtFile.getText().toString());
                CategoryDataSource categoryDataSource = new CategoryDataSource(CategoryActivity.this);
                if (categoryDataSource.addCategory(category) instanceof Category) {
                    Toast.makeText(CategoryActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CategoryActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
