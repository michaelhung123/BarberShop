package com.example.barbershop.Module;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.barbershop.Domain.Account;
import com.example.barbershop.Domain.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDataSource {
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;

    private String[] allColumns = {
            dbHelper.COLUMN_CATEGORY_ID,
            dbHelper.COLUMN_CATEGORY_NAME,
            dbHelper.COLUMN_CATEGORY_DESCRIPTION,
            dbHelper.COLUMN_CATEGORY_FILE_PICTURE,
    };

    public CategoryDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public static ArrayList<Category> selectCategories(Context context) {
        ArrayList<Category> categories = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getReadableDatabase();
        // Define the SELECT query
        String query = "SELECT * FROM " + dbHelper.CATEGORIES_TABLE;

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Iterate through the cursor and retrieve category data
        if (cursor.moveToFirst()) {
            do {
                // Retrieve column values from the cursor
                int categoryId = cursor.getInt(0);
                String categoryName = cursor.getString(1);
                String categoryDescription = cursor.getString(2);
                String categoryFilePicture = cursor.getString(3);

                // Create a Category object
                Category category = new Category(categoryId, categoryName, categoryDescription, categoryFilePicture);

                // Add the category to the list
                categories.add(category);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        return categories;
    }


    public Category addCategory(Category cate){
        open();
        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.COLUMN_CATEGORY_ID, cate.getId());
        cv.put(dbHelper.COLUMN_CATEGORY_NAME, cate.getName());
        cv.put(dbHelper.COLUMN_CATEGORY_DESCRIPTION, cate.getDescription());
        cv.put(dbHelper.COLUMN_CATEGORY_FILE_PICTURE, cate.getImagePic());

        long insertCate = db.insert(dbHelper.CATEGORIES_TABLE, null, cv);
        Cursor cursor = db.query(dbHelper.CATEGORIES_TABLE, allColumns, null,null,null,null,null );
        cursor.moveToFirst();
        Category newCate = cursorToCate(cursor);
        cursor.close();
        return newCate;
    }

public static boolean updateCategory(Context context, Category cate) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_CATEGORY_NAME, cate.getName());
        cv.put(dbHelper.COLUMN_CATEGORY_DESCRIPTION, cate.getDescription());
        cv.put(dbHelper.COLUMN_CATEGORY_FILE_PICTURE, cate.getImagePic());
        int updateCate = db.update(dbHelper.CATEGORIES_TABLE, cv, dbHelper.COLUMN_CATEGORY_ID + " = ?", new String[]{Integer.toString(cate.getId())});
        return updateCate > 0;
}

public static boolean deleteCategory(Context context, int categoryId) {
    dbHelper = new DatabaseHelper(context);
    db = dbHelper.getWritableDatabase();
    int deleteCate = db.delete(dbHelper.CATEGORIES_TABLE, dbHelper.COLUMN_CATEGORY_ID + " = ?", new String[]{Integer.toString(categoryId)});
    return deleteCate > 0;
}

    private Category cursorToCate(Cursor cursor) {
        Category cate = new Category();
        cate.setId(cursor.getInt(0));
        cate.setName(cursor.getString(1));
        cate.setDescription(cursor.getString(2));
        cate.setImagePic(cursor.getString(3));
        return cate;
    }

    public String getFilePictureForCategory(int categoryId) {
        db = dbHelper.getReadableDatabase();
        String[] projection = {dbHelper.COLUMN_CATEGORY_FILE_PICTURE};
        String selection = dbHelper.COLUMN_CATEGORY_ID + "=?";
        String[] selectionArgs = {String.valueOf(categoryId)};
        Cursor cursor = db.query(dbHelper.CATEGORIES_TABLE, projection, selection, selectionArgs, null, null, null);
        String filePicture = null;
        if (cursor.moveToFirst()) {
            filePicture = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_CATEGORY_FILE_PICTURE));
        }
        cursor.close();
        return filePicture;
    }
}
