package com.example.barbershop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class StaffProfileActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_profile);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        TextView titleTextView = new TextView(this);
        titleTextView.setText("Profile"); // Thay "Your Title" bằng tiêu đề của bạn
        titleTextView.setTextSize(20); // Cỡ chữ tiêu đề
        titleTextView.setTextColor(Color.WHITE); // Màu chữ tiêu đề
        titleTextView.setGravity(Gravity.CENTER); // Canh giữa tiêu đề
        // Đặt TextView tùy chỉnh làm tiêu đề cho Toolbar
        toolbar.addView(titleTextView);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView about_title = findViewById(R.id.about);
        TextView photo_tittle = findViewById(R.id.photo);
        // Tạo một SpannableString từ nội dung của TextView
        String text = about_title.getText().toString();
        String text2 = photo_tittle.getText().toString();

        SpannableString spannableString = new SpannableString(text);
        SpannableString spannableString2 = new SpannableString(text2);

        // Tạo một UnderlineSpan để tạo dấu gạch dưới
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, 0, text.length(), 5);
        spannableString2.setSpan(underlineSpan, 0, text2.length(), 5);

        int greenColor = getResources().getColor(R.color.green_custom);
        // Tạo một ForegroundColorSpan để đặt màu văn bản (màu vàng)
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(greenColor);
        spannableString.setSpan(foregroundColorSpan, 0, text.length(), 0);
        spannableString2.setSpan(foregroundColorSpan, 0, text2.length(), 0);

        // Đặt lại TextView với SpannableString
        about_title.setText(spannableString);
        photo_tittle.setText(spannableString2);
    }

    //    Bắt sự kiện button Back trên Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Tạo Intent để chuyển đến MainActivity
            Intent intent = new Intent(StaffProfileActivity.this, MainActivity.class);

            // Chạy Intent để chuyển đến SecondActivity
            startActivity(intent);

            // Kết thúc Activity hiện tại (MainActivity) nếu bạn không muốn quay lại nó
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
