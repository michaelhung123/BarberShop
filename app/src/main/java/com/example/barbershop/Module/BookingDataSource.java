package com.example.barbershop.Module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.barbershop.Domain.Booking;
import com.example.barbershop.Domain.Service;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class BookingDataSource {
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;
    private String[] allColumns = {
            dbHelper.COLUMN_BOOKING_ID,
            dbHelper.COLUMN_BOOKING_USER_ID,
            dbHelper.COLUMN_BOOKING_STAFF_ID,
            dbHelper.COLUMN_BOOKING_TIME,
            dbHelper.COLUMN_BOOKING_STATUS,
            dbHelper.COLUMN_BOOKING_CREATE_TIME,
    };

    public BookingDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

//    public Booking addBooking(Booking b){
//        open();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.COLUMN_BOOKING_USER_ID, b.getUserId());
//        cv.put(dbHelper.COLUMN_BOOKING_STAFF_ID, b.getStaffId());
//        cv.put(dbHelper.COLUMN_BOOKING_TIME, b.getTime());
//        cv.put(dbHelper.COLUMN_BOOKING_CREATE_TIME, b.getCreateTime());
//
//        long insertId = db.insert(dbHelper.BOOKING_TABLE, null, cv);
//
//        Cursor cursor = db.query(dbHelper.BOOKING_TABLE, allColumns, null,null,null,null,null );
//        cursor.moveToFirst();
//        Booking newBooking = cursorToBooking(cursor);
//        cursor.close();
//        return newBooking;
//    }

    private Booking cursorToBooking(Cursor cursor) {
        Booking b = new Booking();
        b.setUserId(cursor.getInt(1));
        b.setStaffId(cursor.getInt(2));
        b.setTime(cursor.getString(3));
        boolean status = cursor.getInt(4) == 1;
        b.setStatus(status);
        return b;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.BOOKING_TABLE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Booking booking = cursorToBooking(cursor);
            bookings.add(booking);
            cursor.moveToNext();
        }
        cursor.close();
        return bookings;
    }
}
