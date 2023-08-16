package com.example.barbershop.Module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.barbershop.Domain.BookingDetail;
import com.example.barbershop.Domain.Service;

import java.util.ArrayList;
import java.util.List;

public class BookingDetailDataSource {
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;
    private String[] allColumns = {
            dbHelper.COLUMN_BOOKING_ID,
            dbHelper.COLUMN_BOOKING_DETAIL_BOOKING_ID,
            dbHelper.COLUMN_BOOKING_DETAIL_SERVICE_ID,
    };

    public BookingDetailDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public BookingDetail addBookingService(BookingDetail bookingDetail) {
        open();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_BOOKING_DETAIL_BOOKING_ID,bookingDetail.getBookingId());
        cv.put(dbHelper.COLUMN_BOOKING_DETAIL_SERVICE_ID,bookingDetail.getServiceId());
        long insertId = db.insert(dbHelper.BOOKING_DETAIL_TABLE, null, cv);

        Cursor cursor = db.query(dbHelper.BOOKING_DETAIL_TABLE, allColumns, null,null,null,null,null );
        cursor.moveToFirst();
        BookingDetail newBookingDetail = cursorToBookingDetail(cursor);
        cursor.close();
        return newBookingDetail;
    }
    private BookingDetail cursorToBookingDetail(Cursor cursor) {
        BookingDetail b = new BookingDetail();
        b.setBookingId(cursor.getInt(1));
        b.setServiceId(cursor.getInt(2));
        return b;
    }
    public static ArrayList<BookingDetail> selectBookingDetail(Context context) {
        ArrayList<BookingDetail> bookingList = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getReadableDatabase();
        // Define the SELECT query
        String query = "SELECT * FROM " + dbHelper.BOOKING_DETAIL_TABLE;

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Iterate through the cursor and retrieve category data
        if (cursor.moveToFirst()) {
            do {
                // Retrieve column values from the cursor
                int id = cursor.getInt(0);
                int booking_id = cursor.getInt(1);
                int service_id = cursor.getInt(2);

                // Create a Category object
                BookingDetail bookingDetail = new BookingDetail(id, booking_id, service_id);

                // Add the category to the list
                bookingList.add(bookingDetail);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        return bookingList;
    }

    public List<Service> getServicesByBookingId(Context context,int bookingId) {
        ArrayList<Service> services = new ArrayList<>();
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getReadableDatabase();
        // Query để lấy tất cả dữ liệu từ bảng booking_service liên kết với bảng service dựa trên service_id
        String query = "SELECT * FROM " + dbHelper.SERVICES_TABLE +
                " INNER JOIN " + dbHelper.BOOKING_DETAIL_TABLE +
                " ON " + dbHelper.BOOKING_DETAIL_TABLE + "." + dbHelper.COLUMN_BOOKING_DETAIL_SERVICE_ID + " = "
                + dbHelper.SERVICES_TABLE + "." + dbHelper.COLUMN_SERVICE_ID +
                " WHERE " + dbHelper.BOOKING_DETAIL_TABLE + "." + dbHelper.COLUMN_BOOKING_DETAIL_BOOKING_ID + " = " + bookingId;

        Cursor cursor = db.rawQuery(query, null);
        //select * from SERVICES_TABLE INNER JOIN service_table ON booking_detail_table.Service_ID = service_table.Service_Id
        //WHERE booking_detail_table.BOOKING_DETAIL_ID = bookingId
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

        cursor.close();
        db.close();
        return services;
    }

}
