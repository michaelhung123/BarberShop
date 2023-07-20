package com.example.barbershop;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Domain.Account;
import com.example.barbershop.Module.AccountDataSource;

import java.util.ArrayList;

public class IndexAccountActivity extends AppCompatActivity {
    ListView lvAccounts;
    ArrayList<Account> accounts = new ArrayList<>();
    ArrayAdapter adapterListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_accounts);

        lvAccounts = findViewById(R.id.lvAccounts);
        accounts = AccountDataSource.selectAccountsRoleUser(IndexAccountActivity.this);

        adapterListView = new ArrayAdapter<>(IndexAccountActivity.this, android.R.layout.simple_list_item_1, accounts);
        lvAccounts.setAdapter(adapterListView);
    }
}
