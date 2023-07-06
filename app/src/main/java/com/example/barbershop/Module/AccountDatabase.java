package com.example.barbershop.Module;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.barbershop.Domain.UserDomain;

public class AccountDatabase extends SQLiteOpenHelper {
    public static final String ACCOUNT_TABLE = "ACCOUNT";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_RoleID = "ROLE_ID";


    public AccountDatabase(@Nullable Context context) {
        super(context, "barbershop.db", null, 1);
    }

    //this is called the first time a database is accessed. There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + ACCOUNT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " + " TEXT)";
        db.execSQL(createTableStatement);
    }

    //this is called if the database version number changes, It prevents previous users app from breaking when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
        // Tạo lại bảng mới với cấu trúc đã cập nhật
        onCreate(db);
    }

    public boolean addOne(UserDomain userDomain) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, userDomain.getUsername());
        cv.put(COLUMN_PASSWORD, userDomain.getPassword());
        long insert = db.insert(ACCOUNT_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }
}
