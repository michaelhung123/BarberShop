package com.example.barbershop.Module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.barbershop.Domain.Account;

public class AccountDataSource {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
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

}
