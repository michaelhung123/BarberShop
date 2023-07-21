package com.example.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
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

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        ActionBar actionBar = getSupportActionBar();
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        // Khởi tạo các Fragment
        homeFragment = new HomeFragment();
        cutFragment = new CutFragment();
        settingFragment = new SettingFragment();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Khởi tạo Fragment Manager
        fragmentManager = getSupportFragmentManager();

        // Hiển thị Fragment mặc định
        fragmentManager.beginTransaction().replace(R.id.mainConstraintLayout, homeFragment).commit();

        // Thiết lập sự kiện chuyển đổi Fragment khi người dùng nhấp vào mục menu
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.action_home){
                    fragmentManager.beginTransaction().replace(R.id.mainConstraintLayout, homeFragment).commit();
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


    //Bắt sự kiện button Back trên Toolbar
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            // Tạo Intent để chuyển đến MainActivity
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//
//            // Chạy Intent để chuyển đến SecondActivity
//            startActivity(intent);
//
//            // Kết thúc Activity hiện tại (MainActivity) nếu bạn không muốn quay lại nó
////            finish();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}