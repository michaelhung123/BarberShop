package com.example.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private HomeStaffFragment homeStaffFragment;
    private CutFragment cutFragment;
    private SettingFragment settingFragment;
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCategoryList;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);

        // Khởi tạo các Fragment
        homeFragment = new HomeFragment();
        cutFragment = new CutFragment();
        settingFragment = new SettingFragment();
        homeStaffFragment = new HomeStaffFragment();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Khởi tạo Fragment Manager
        fragmentManager = getSupportFragmentManager();

        // Hiển thị Fragment mặc định
        if (roleId == 2) {
            // Hiển thị HomeStaffFragment khi roleId = 2
            fragmentManager.beginTransaction().replace(R.id.mainConstraintLayout, homeStaffFragment).commit();
        } else {
            // Hiển thị HomeFragment mặc định
            fragmentManager.beginTransaction().replace(R.id.mainConstraintLayout, homeFragment).commit();
        }
//        fragmentManager.beginTransaction().replace(R.id.mainConstraintLayout, homeStaffFragment).commit();

        // Thiết lập sự kiện chuyển đổi Fragment khi người dùng nhấp vào mục menu
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.action_home){
                    if (roleId == 2) {
                        // Hiển thị HomeStaffFragment kh    i roleId = 2
                        fragmentManager.beginTransaction().replace(R.id.mainConstraintLayout, homeStaffFragment).commit();
                    } else {
                        // Hiển thị HomeFragment mặc định
                        fragmentManager.beginTransaction().replace(R.id.mainConstraintLayout, homeFragment).commit();
                    }
                }
                else if(item.getItemId() == R.id.action_cut){
                    fragmentManager.beginTransaction().replace(R.id.mainConstraintLayout, cutFragment).commit();
                }
                else if(item.getItemId() == R.id.action_setting){
                    fragmentManager.beginTransaction().replace(R.id.mainConstraintLayout, settingFragment).commit();
                }
                return true;
            }
        });
    }
}