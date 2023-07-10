package com.example.barbershop;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Domain.Account;
import com.example.barbershop.Module.DatabaseHelper;
//import com.example.barbershop.Module.RoleDatabase;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextInputEditText txtUsername;
    TextInputEditText txtPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        DatabaseHelper databaseHelper = new DatabaseHelper(LoginActivity.this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SQLiteDatabase db = databaseHelper.getWritableDatabase();
//                databaseHelper.onCreate(db);
                boolean isCheckAccount = databaseHelper.checkAccount(txtUsername.getText().toString(),txtPassword.getText().toString());
                if(!isCheckAccount){
                    Log.d("Check tk: ", "false");
                Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác !", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("Check tk", "Dang nhap thanh cong !");
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                    loginSuccess();
                }
            }
        });
    }

    public void loginSuccess() {
        // Tạo Intent để chuyển đến MainActivity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        // Chạy Intent để chuyển đến SecondActivity
        startActivity(intent);

        // Kết thúc Activity hiện tại (MainActivity) nếu bạn không muốn quay lại nó
        finish();
    }
}
