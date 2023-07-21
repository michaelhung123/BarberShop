package com.example.barbershop.Module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.barbershop.Domain.Category;
import com.example.barbershop.Domain.Service;

import java.util.ArrayList;

public class ServiceDataSource {
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;

    private String[] allColumns = {
            dbHelper.COLUMN_SERVICE_ID,
            dbHelper.COLUMN_SERVICE_NAME,
            dbHelper.COLUMN_SERVICE_DESCRIPTION,
            dbHelper.COLUMN_SERVICE_PRICE,
            dbHelper.COLUMN_SERVICE_FILE,
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

    public static ArrayList<Service> selectServices(Context context) {
        ArrayList<Service> services = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getReadableDatabase();
        // Define the SELECT query
        String query = "SELECT * FROM " + dbHelper.SERVICES_TABLE;

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Iterate through the cursor and retrieve category data
        if (cursor.moveToFirst()) {
            do {
                // Retrieve column values from the cursor
                int serviceId = cursor.getInt(0);
                String serviceName = cursor.getString(1);
                double servicePrice = cursor.getDouble(2);
                String serviceDescription = cursor.getString(3);
                String serviceFilePicture = cursor.getString(4);
                int serviceCategoryId = cursor.getInt(5);

                // Create a Category object
                Service service = new Service(serviceId, serviceName, servicePrice, serviceDescription, serviceFilePicture, serviceCategoryId);

                // Add the category to the list
                services.add(service);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        return services;
    }
    public  ArrayList<Service> getService_CategoryId(Context context, int categoryId) {
        ArrayList<Service> services = new ArrayList<>();
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + dbHelper.SERVICES_TABLE +
                " INNER JOIN " + dbHelper.CATEGORIES_TABLE +
                " ON " + dbHelper.SERVICES_TABLE + "." + dbHelper.COLUMN_SERVICE_CATEGORY_ID + " = " + dbHelper.CATEGORIES_TABLE + "." + dbHelper.COLUMN_CATEGORY_ID +
                " WHERE " + dbHelper.SERVICES_TABLE + "." + dbHelper.COLUMN_SERVICE_CATEGORY_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});
        // Iterate through the cursor and retrieve category data
        if (cursor.moveToFirst()) {
            do {
                // Retrieve column values from the cursor
                int serviceId = cursor.getInt(0);
                String serviceName = cursor.getString(1);
                double servicePrice = cursor.getDouble(2);
                String serviceDescription = cursor.getString(3);
                String serviceFilePicture = cursor.getString(4);
                int serviceCategoryId = cursor.getInt(5);

                Service service = new Service(serviceId, serviceName, servicePrice, serviceDescription, serviceFilePicture, serviceCategoryId);

                services.add(service);

            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();
        return services;
    }

    public Service addService(Service serv){
        open();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_SERVICE_NAME, serv.getName());
        cv.put(dbHelper.COLUMN_SERVICE_PRICE, serv.getPrice());
        cv.put(dbHelper.COLUMN_SERVICE_FILE, serv.getFilePath());
        cv.put(dbHelper.COLUMN_SERVICE_DESCRIPTION, serv.getDescription());
        cv.put(dbHelper.COLUMN_SERVICE_CATEGORY_ID, serv.getCategory_id());

        long insertId = db.insert(dbHelper.SERVICES_TABLE, null, cv);
        Cursor cursor = db.query(dbHelper.SERVICES_TABLE, allColumns, null,null,null,null,null );
        cursor.moveToFirst();
        Service newServ = cursorToService(cursor);
        cursor.close();
        return newServ;
    }

    public static boolean updateService(Context context, Service service) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_SERVICE_NAME, service.getName());
        cv.put(dbHelper.COLUMN_SERVICE_DESCRIPTION, service.getDescription());
        cv.put(dbHelper.COLUMN_SERVICE_PRICE, service.getPrice());
        cv.put(dbHelper.COLUMN_SERVICE_CATEGORY_ID, service.getCategory_id());
        cv.put(dbHelper.COLUMN_SERVICE_FILE, service.getFilePath());
        int updateService = db.update(dbHelper.SERVICES_TABLE, cv, dbHelper.COLUMN_SERVICE_ID + " = ?", new String[]{Integer.toString(service.getId())});
        return updateService > 0;
    }

    public static boolean deleteService(Context context, int serviceID) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        int deleteCate = db.delete(dbHelper.SERVICES_TABLE, dbHelper.COLUMN_SERVICE_ID + " = ?", new String[]{Integer.toString(serviceID)});
        return deleteCate > 0;
    }

    private Service cursorToService(Cursor cursor) {
        Service serv = new Service();
        serv.setName(cursor.getString(0));
        serv.setCategory_id(cursor.getInt(1));
        return serv;
    }

    public String getFilePictureForCategory(int serviceId) {
        db = dbHelper.getReadableDatabase();
        String[] projection = {dbHelper.COLUMN_SERVICE_FILE};
        String selection = dbHelper.COLUMN_SERVICE_ID + "=?";
        String[] selectionArgs = {String.valueOf(serviceId)};
        Cursor cursor = db.query(dbHelper.SERVICES_TABLE, projection, selection, selectionArgs, null, null, null);
        String filePicture = null;
        if (cursor.moveToFirst()) {
            filePicture = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_SERVICE_FILE));
        }
        cursor.close();
        return filePicture;
    }
}
