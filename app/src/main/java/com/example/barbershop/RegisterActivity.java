package com.example.barbershop;

import android.app.DatePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Domain.Account;
import com.example.barbershop.Module.AccountDataSource;
import com.example.barbershop.Module.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText txtUsername;
    TextInputEditText txtPassword;
    TextInputEditText txtEmail;
    TextInputEditText txtPhone;
    TextInputEditText txtDateOfBirth;
    ImageView calendarImage;
    Button btnSignUp;
    Account acc = new Account();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        btnSignUp = findViewById(R.id.btnSignUp);
        calendarImage = findViewById(R.id.calendarImageView);
        txtDateOfBirth = findViewById(R.id.txtDateOfBirth);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);

        calendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        handleGender();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acc.setUsername(txtUsername.getText().toString());
                acc.setPassword(txtPassword.getText().toString());
                acc.setEmail(txtEmail.getText().toString());
                acc.setPhone(txtPhone.getText().toString());
                acc.setDateOfBirth(txtDateOfBirth.getText().toString());
                acc.setUsername(txtUsername.getText().toString());
                acc.setUsername(txtUsername.getText().toString());
                AccountDataSource accountDataSource = new AccountDataSource(RegisterActivity.this);
                if(accountDataSource.addAccount(acc) instanceof Account){
                    Toast.makeText(RegisterActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void showDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                // Xử lý giá trị năm sinh được chọn ở đây
                LocalDate date_of_birth = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    date_of_birth = LocalDate.of(year, month +1, dayOfMonth);
                }
                txtDateOfBirth.setText(date_of_birth.toString());
            }
        }, 2000, 2, 2);
        datePickerDialog.show();
    }

    public void handleGender(){
        Spinner genderSpinner = findViewById(R.id.spinner);
        TextView selectedGenderTextView = findViewById(R.id.selectedGenderTextView);

        // Tạo một danh sách các lựa chọn giới tính
        ArrayList<String> genderOptions = new ArrayList<>();
        genderOptions.add("Nam");
        genderOptions.add("Nữ");

        // Tạo một ArrayAdapter để liên kết dữ liệu giữa danh sách và Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán ArrayAdapter vào Spinner
        genderSpinner.setAdapter(adapter);

        // Thiết lập sự kiện lắng nghe khi người dùng chọn một lựa chọn giới tính
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị giới tính được chọn
                String selectedGender = genderOptions.get(position);
                selectedGenderTextView.setText(selectedGender);

                // Hiển thị giá trị giới tính trong TextView
                acc.setGender(selectedGender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có lựa chọn nào được chọn
            }
        });

    }

}
