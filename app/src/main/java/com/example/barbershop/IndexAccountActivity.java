package com.example.barbershop;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Domain.Account;
import com.example.barbershop.Module.AccountDataSource;
import com.example.barbershop.Module.CategoryDataSource;

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

        adapterListView = new ArrayAdapter(IndexAccountActivity.this, android.R.layout.simple_list_item_1, accounts) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };
        lvAccounts.setAdapter(adapterListView);

        lvAccounts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogUpdateAccount(position);
            }
        });
    }

    public void showDialogUpdateAccount(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(IndexAccountActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_account, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        Switch swBlock = view.findViewById(R.id.swBlock);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);

        Account acc = accounts.get(pos);

//        Log.d("Account is block: ", String.valueOf(acc.getIs_Block()));
        Log.d("Account username: ", acc.getUsername());

        swBlock.setChecked(acc.getIs_Block());

        Log.d("swBlock check:", String.valueOf(swBlock.isChecked()));

        swBlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Checked", String.valueOf(swBlock.isChecked()));
                acc.setIs_Block(isChecked);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountDataSource.updateAccount(IndexAccountActivity.this, acc);
                accounts.clear();
                accounts.addAll(AccountDataSource.selectAccountsRoleUser(IndexAccountActivity.this));
                adapterListView.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }
}
