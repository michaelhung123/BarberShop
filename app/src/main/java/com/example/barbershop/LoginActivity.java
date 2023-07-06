package com.example.barbershop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Domain.UserDomain;
import com.example.barbershop.Module.AccountDatabase;
import com.example.barbershop.Module.RoleDatabase;
import com.google.android.material.textfield.TextInputEditText;

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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDomain userDomain;
              try {
                  userDomain =  new UserDomain(-1, txtUsername.getText().toString(), txtPassword.getText().toString());
                  Toast.makeText(LoginActivity.this, userDomain.toString(), Toast.LENGTH_SHORT).show();
              }catch (Exception e){
                  Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                  userDomain = new UserDomain(-1,"error","error");
              }

                AccountDatabase databaseHelper = new AccountDatabase(LoginActivity.this);
                boolean success = databaseHelper.addOne(userDomain);
                Toast.makeText(LoginActivity.this, "Success: "+success, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
