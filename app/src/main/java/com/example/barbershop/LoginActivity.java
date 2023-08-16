package com.example.barbershop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Domain.Account;
import com.example.barbershop.Module.AccountDataSource;
import com.example.barbershop.Module.DatabaseHelper;
//import com.example.barbershop.Module.RoleDatabase;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextInputEditText txtUsername;
    TextInputEditText txtPassword;
    private ProgressBar progressBar;
    private TextView text1;
    private TextView textSignUp;
    boolean isProgressVisible = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        text1 = findViewById(R.id.text1);
        textSignUp = findViewById(R.id.textSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        AccountDataSource accountDataSource = new AccountDataSource(LoginActivity.this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = findViewById(R.id.progressBar);

                boolean isCheckAccount = accountDataSource.checkAccount(txtUsername.getText().toString(),txtPassword.getText().toString());

                if (isProgressVisible || !isCheckAccount) {
                    progressBar.setVisibility(View.GONE);
                    isProgressVisible = false;
                } else {
                    isProgressVisible = true;
                    progressBar.setVisibility(View.VISIBLE);
                    text1.setVisibility(View.INVISIBLE);
                    textSignUp.setVisibility(View.INVISIBLE);
                }

                if(!isCheckAccount){
                    CustomToast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác", CustomToast.LENGTH_SHORT,CustomToast.ERROR,true).show();
                }
                else {
                    //Lưu thông tin của người dùng vừa nhập vào SharedPreferences để hiển thị lên giao diện
                    int userId = accountDataSource.getUserIdByUsername(txtUsername.getText().toString());
                    int roleId = accountDataSource.getRoleIdByAccountId(userId);
                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", txtUsername.getText().toString());
                    editor.putInt("userId", userId);
                    editor.putInt("roleId", roleId);
                    editor.apply();

                    if(roleId == 1) {
                        CustomToast.makeText(LoginActivity.this, "Bạn đang đăng nhập với quyền Admin", CustomToast.LENGTH_SHORT,CustomToast.SUCCESS,true).show();
                        loginSuccess();
                    } else if(roleId == 2) {
                        CustomToast.makeText(LoginActivity.this, "Bạn đang đăng nhập với quyền Staff", CustomToast.LENGTH_SHORT,CustomToast.SUCCESS,true).show();
                        loginSuccess();
                    }
                    loginSuccess();
                }
            }
        });

        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
