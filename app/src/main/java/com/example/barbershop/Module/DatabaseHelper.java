package com.example.barbershop.Module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.barbershop.Domain.Account;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // TABLE ACCOUNT
    public static final String ACCOUNT_TABLE = "ACCOUNT";
    public static final String COLUMN_ACCOUNT_ID = "ID";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_PHONENUMBER = "PHONE";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_GENDER = "GENDER";
    public static final String COLUMN_DATEOFBIRTH = "DATE_OF_BIRTH";
    public static final String COLUMN_FOREIGN_ROLEID = "roleID";

    //TABLE ROLE
    public static final String ROLE_TABLE = "ROLE";
    public static final String COLUMN_ROLE_ID = "ID";
    public static final String COLUMN_ROLE_NAME = "ROLENAME";

    //TABLE CATEGORIES
    public static final String CATEGORIES_TABLE = "CATEGORIES";
    public static final String COLUMN_CATEGORY_ID = "ID";
    public static final String COLUMN_CATEGORY_NAME = "NAME";
    public static final String COLUMN_CATEGORY_DESCRIPTION = "DESCRIPTION";

    public static final String COLUMN_CATEGORY_FILE_PICTURE = "FILE_PICTURE";

    //TABLE SERVICES
    public static final String SERVICES_TABLE = "SERVICES";
    public static final String COLUMN_SERVICE_ID = "ID";
    public static final String COLUMN_SERVICE_NAME = "NAME";
    public static final String COLUMN_SERVICE_CATEGORY_ID = "CATEGORY_ID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "barber.db", null, 12);
    }

    //this is called the first time a database is accessed. There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        createAccountTable(db);
        createRoleTable(db);
        createCategoriesTable(db);
        createServicesTable(db);
    }

    //this is called if the database version number changes, It prevents previous users app from breaking when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ
//        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
//        db.execSQL("DROP TABLE IF EXISTS " + ROLE_TABLE);
//        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE);
        // Tạo lại bảng mới với cấu trúc đã cập nhật
        onCreate(db);
    }

    public void createAccountTable(SQLiteDatabase db) {
        String createTableAccount = "CREATE TABLE IF NOT EXISTS " + ACCOUNT_TABLE + " (" +
                COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_PHONENUMBER + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_DATEOFBIRTH + " TEXT, " +
                COLUMN_FOREIGN_ROLEID + " INTEGER REFERENCES " + ROLE_TABLE + "("+ COLUMN_ROLE_ID + ")" +
                ")";

        db.execSQL(createTableAccount);
    }
    public void createRoleTable(SQLiteDatabase db) {
        String createTableRole = "CREATE TABLE IF NOT EXISTS " + ROLE_TABLE + " (" + COLUMN_ROLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ROLE_NAME + " TEXT ) ";

        db.execSQL(createTableRole);
    }

    public void createCategoriesTable(SQLiteDatabase db) {
        String createTableCategories = "CREATE TABLE IF NOT EXISTS " + CATEGORIES_TABLE + " (" + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY_NAME + " TEXT, " +
                COLUMN_CATEGORY_DESCRIPTION + " TEXT, " +
                COLUMN_CATEGORY_FILE_PICTURE + " TEXT )";

        db.execSQL(createTableCategories);
    }

    public void createServicesTable(SQLiteDatabase db) {
        String createTableServices = "CREATE TABLE IF NOT EXISTS " + SERVICES_TABLE + " (" + COLUMN_SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SERVICE_NAME + " TEXT, " +
                COLUMN_SERVICE_CATEGORY_ID + " INTEGER REFERENCES " + CATEGORIES_TABLE + "("+ COLUMN_CATEGORY_ID + "))";

        db.execSQL(createTableServices);
    }

    public boolean addOne(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, account.getUsername());
        cv.put(COLUMN_PASSWORD, account.getPassword());
        db.insert(ACCOUNT_TABLE, null, cv);
        return true;
    }

    public void setDefaultRoles() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ROLE_NAME, "admin");
        db.insert(ROLE_TABLE, null, cv);

        cv.put(COLUMN_ROLE_NAME, "barber");
        db.insert(ROLE_TABLE, null, cv);

        cv.put(COLUMN_ROLE_NAME, "user");
        db.insert(ROLE_TABLE, null, cv);
    }


    public List<Account> getAccounts() {
        List<Account> accountList = new ArrayList<>();

        String queryString = "SELECT * FROM " + ACCOUNT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int accountId = cursor.getInt(0);
                String userAccount = cursor.getString(1);
                String passwordAccount = cursor.getString(2);

                Account newAccount = new Account(accountId, userAccount, passwordAccount);
                accountList.add(newAccount);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return accountList;
    }

    public boolean checkAccount(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_ACCOUNT_ID};
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(ACCOUNT_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }


}
