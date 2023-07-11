package com.example.barbershop.Module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.barbershop.Domain.Account;
import com.example.barbershop.Domain.Category;

public class CategoryDataSource {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

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

    public Category addCategory(Category cate){
        open();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_CATEGORY_ID, cate.getId());
        cv.put(dbHelper.COLUMN_CATEGORY_NAME, cate.getName());
        cv.put(dbHelper.COLUMN_CATEGORY_DESCRIPTION, cate.getDescription());
        cv.put(dbHelper.COLUMN_CATEGORY_FILE_PICTURE, cate.getImagePic());

        long insertId = db.insert(dbHelper.CATEGORIES_TABLE, null, cv);
        Cursor cursor = db.query(dbHelper.CATEGORIES_TABLE, allColumns, null,null,null,null,null );
        cursor.moveToFirst();
        Category newCate = cursorToCate(cursor);
        cursor.close();
        return newCate;
    }



    private Category cursorToCate(Cursor cursor) {
        Category cate = new Category();
        cate.setId(cursor.getInt(0));
        cate.setName(cursor.getString(1));
        cate.setDescription(cursor.getString(2));
        cate.setImagePic(cursor.getString(3));
        return cate;
    }
}
