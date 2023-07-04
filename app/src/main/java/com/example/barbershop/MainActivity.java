package com.example.barbershop;

import com.example.barbershop.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.barbershop.Adaptor.CategoryAdaptor;
import com.example.barbershop.Domain.CategoryDomain;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


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
//        recyclerViewCategory();

    }

//    private void recyclerViewCategory() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerViewCategoryList = findViewById(R.id.recycleView1);
//        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
//
//        ArrayList<CategoryDomain> category = new ArrayList<>();
//        category.add(new CategoryDomain("Hair cut", "pic1"));
//        category.add(new CategoryDomain("Hair cut", "pic2"));
//        category.add(new CategoryDomain("Hair cut", "pic3"));
//        category.add(new CategoryDomain("Hair cut", "pic4"));
//        category.add(new CategoryDomain("Hair cut", "pic5"));
//        category.add(new CategoryDomain("Hair cut", "pic6"));
//
//        adapter = new CategoryAdaptor(category);
//        recyclerViewCategoryList.setAdapter(adapter);
//    }
}