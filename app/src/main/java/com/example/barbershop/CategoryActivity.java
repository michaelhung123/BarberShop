package com.example.barbershop;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Domain.Category;
import com.example.barbershop.Module.CategoryServices;

import org.w3c.dom.Text;

public class CategoryActivity extends AppCompatActivity {
    Button btnAddCategory;
    TextView txtName;
    String name;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_category);

        btnAddCategory = findViewById(R.id.btnCreate);
        txtName = findViewById(R.id.txtName);

        btnAddCategory.setOnClickListener((v)-> {
            CategoryServices categoryServices = new CategoryServices(CategoryActivity.this);
//            categoryServices.addCategory();
            Category c = new Category(txtName.getText().toString(), "abc");
            categoryServices.addCategory(c);
        });
    }
}
