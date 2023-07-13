package com.example.barbershop.Module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.barbershop.Domain.Category;
import com.example.barbershop.Domain.Service;

public class ServiceDataSource {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    private String[] allColumns = {
            dbHelper.COLUMN_SERVICE_ID,
            dbHelper.COLUMN_SERVICE_NAME,
            dbHelper.COLUMN_SERVICE_CATEGORY_ID,
    };

    public ServiceDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Service addService(Service serv){
        open();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_SERVICE_NAME, serv.getName());
        cv.put(dbHelper.COLUMN_SERVICE_CATEGORY_ID, serv.getCategory_id());

        long insertId = db.insert(dbHelper.SERVICES_TABLE, null, cv);
        Cursor cursor = db.query(dbHelper.SERVICES_TABLE, allColumns, null,null,null,null,null );
        cursor.moveToFirst();
        Service newServ = cursorToService(cursor);
        cursor.close();
        return newServ;
    }

    private Service cursorToService(Cursor cursor) {
        Service serv = new Service();
        serv.setName(cursor.getString(0));
        serv.setCategory_id(cursor.getInt(1));
        return serv;
    }
}
