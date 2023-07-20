package com.example.barbershop.Module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.barbershop.Domain.Account;
import com.example.barbershop.Domain.Category;

import java.util.ArrayList;

public class AccountDataSource {
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;
    private String[] allColumns = {
            dbHelper.COLUMN_ACCOUNT_ID,
            dbHelper.COLUMN_USERNAME,
            dbHelper.COLUMN_PASSWORD,
            dbHelper.COLUMN_PHONENUMBER,
            dbHelper.COLUMN_EMAIL,
            dbHelper.COLUMN_GENDER,
            dbHelper.COLUMN_DATEOFBIRTH,
            dbHelper.COLUMN_FOREIGN_ROLEID,
    };

    public AccountDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public static ArrayList<Account> selectAccountsRoleUser(Context context) {
        ArrayList<Account> accounts = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getReadableDatabase();
        // Define the SELECT query
        String query = "SELECT * FROM " + dbHelper.ACCOUNT_TABLE + " WHERE " + dbHelper.COLUMN_FOREIGN_ROLEID + " = 3";
        //SELECT * FROM ACCOUNT WHERE roleID = 3

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Iterate through the cursor and retrieve category data
        if (cursor.moveToFirst()) {
            do {
                // Retrieve column values from the cursor
                int accountID = cursor.getInt(0);
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                String phoneNumber = cursor.getString(3);
                String email = cursor.getString(4);
                String gender = cursor.getString(5);
                String dateOfBirth = cursor.getString(6);
                int role_ID = cursor.getInt(7);

                // Create a Category object
                Account account = new Account(accountID, username, password, phoneNumber, email, gender, dateOfBirth, role_ID);

                // Add the category to the list
                accounts.add(account);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        return accounts;
    }

    public Account addAccount(Account account){
        open();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_USERNAME, account.getUsername());
        cv.put(dbHelper.COLUMN_PASSWORD, account.getPassword());
        cv.put(dbHelper.COLUMN_EMAIL, account.getEmail());
        cv.put(dbHelper.COLUMN_PHONENUMBER, account.getPhone());
        cv.put(dbHelper.COLUMN_DATEOFBIRTH, account.getDateOfBirth().toString());
        cv.put(dbHelper.COLUMN_FOREIGN_ROLEID, account.getRoleId());
        cv.put(dbHelper.COLUMN_GENDER, account.getGender());

        long insertId = db.insert(dbHelper.ACCOUNT_TABLE, null, cv);
        Cursor cursor = db.query(dbHelper.ACCOUNT_TABLE, allColumns, null,null,null,null,null );
        cursor.moveToFirst();
        Account newAccount = cursorToAccount(cursor);
        cursor.close();
        return newAccount;
    }

    private Account cursorToAccount(Cursor cursor) {
        Account account = new Account();
        account.setId(cursor.getInt(0));
        account.setUsername(cursor.getString(1));
        account.setPassword(cursor.getString(2));
        account.setEmail(cursor.getString(3));
        account.setPhone(cursor.getString(4));
        account.setGender(cursor.getString(5));
        account.setDateOfBirth(cursor.getString(6));
        account.setRoleId(cursor.getInt(7));
        return account;
    }

    public boolean checkAccount(String username, String password) {
        db = dbHelper.getReadableDatabase();
        String[] columns = {dbHelper.COLUMN_ACCOUNT_ID,dbHelper.COLUMN_USERNAME,dbHelper.COLUMN_PASSWORD};
        String selection = dbHelper.COLUMN_USERNAME + " = ? AND " + dbHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(dbHelper.ACCOUNT_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }



}
