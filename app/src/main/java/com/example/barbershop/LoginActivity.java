package com.example.barbershop;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Domain.UserDomain;
import com.example.barbershop.Module.AccountDatabase;
import com.example.barbershop.Module.CategoryServices;
import com.example.barbershop.Module.RoleDatabase;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        CategoryServices categoryServices = new CategoryServices(LoginActivity.this);
        SQLiteDatabase db = categoryServices.getWritableDatabase();
    }
}
