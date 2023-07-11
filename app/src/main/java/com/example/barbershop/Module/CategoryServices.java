package com.example.barbershop.Module;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.ContentView;
import androidx.annotation.Nullable;

import com.example.barbershop.Domain.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryServices extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "barber.db";
    private static final int DATABASE_VERSION = 2;

    // Categories table
    private static final String TABLE_CATEGORIES = "categories";
    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "name";
    private static final String COLUMN_CATEGORY_DESCRIPTION = "description";

    public CategoryServices(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCategoriesTableQuery = "CREATE TABLE " + TABLE_CATEGORIES + "(" +
                COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY," +
                COLUMN_CATEGORY_NAME + " TEXT, " +
                COLUMN_CATEGORY_DESCRIPTION + " TEXT)" ;
        db.execSQL(createCategoriesTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        // Select all rows from the Categories table
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Iterate through the cursor and retrieve category data
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int categoryId = cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
                @SuppressLint("Range") String categoryName = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME));
                @SuppressLint("Range") String categoryDescription = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_DESCRIPTION));

                Category category = new Category(categoryName, categoryDescription);
                categories.add(category);
            } while (cursor.moveToNext());
        }

        // Close the cursor and the database connection
        cursor.close();
        db.close();

        return categories;
    }

    public boolean addCategory(Category c) {
//        List<Category> categories = new ArrayList<>();
//        String insertQuery = "INSERT INTO " + TABLE_CATEGORIES + "(" + COLUMN_CATEGORY_NAME +", " + COLUMN_CATEGORY_DESCRIPTION + ") values ('"+ name + "', '" + description +"')";
//        Log.d("insert: ", insertQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CATEGORY_NAME, c.getName());
        cv.put(COLUMN_CATEGORY_DESCRIPTION, c.getDescription());
        long check = db.insert(TABLE_CATEGORIES, null, cv);
        if(check == -1) {
            return false;
        }
        return true;
    }

//    public List<Category> viewCategories() {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//        String selectQuery = "SELECT * FROM Categories";
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        List<String> categories = new ArrayList<>();
//
//        if (cursor.moveToFirst()) {
//            do {
//                String categoryName = cursor.getString(cursor.getColumnIndex("category_name"));
//                categories.add(categoryName);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//
//        categoryAdapter.clear();
//        categoryAdapter.addAll(categories);
//        categoryListView.setAdapter(categoryAdapter);
//    }
}
