package com.example.barbershop.Module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.example.barbershop.Domain.Account;
import com.example.barbershop.Domain.Booking;
import com.example.barbershop.Domain.TimeSlot;

import java.util.ArrayList;

public class BookingDataSource {
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;
    private String[] allColumns = {
            dbHelper.COLUMN_BOOKING_ID,
            dbHelper.COLUMN_BOOKING_USER_ID,
            dbHelper.COLUMN_BOOKING_STAFF_ID,
            dbHelper.COLUMN_BOOKING_TIME,
            dbHelper.COLUMN_BOOKING_SLOT,
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

    public Booking addBooking(Booking b){
//        open();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_BOOKING_USER_ID, b.getUserId());
        cv.put(dbHelper.COLUMN_BOOKING_STAFF_ID, b.getStaffId());
        cv.put(dbHelper.COLUMN_BOOKING_TIME, b.getTime());
        cv.put(dbHelper.COLUMN_BOOKING_SLOT, b.getSlot());
        cv.put(dbHelper.COLUMN_BOOKING_CREATE_TIME, b.getCreateTime());
        cv.put(dbHelper.COLUMN_BOOKING_TOTAL, b.getTotal());
        cv.put(dbHelper.COLUMN_BOOKING_STATUS, b.isStatus());
        long insertId = db.insert(dbHelper.BOOKING_TABLE, null, cv);

        Cursor cursor = db.query(dbHelper.BOOKING_TABLE, allColumns, null,null,null,null,null );
        cursor.moveToFirst();
        Booking newBooking = cursorToBooking(cursor);
        cursor.close();
        return newBooking;
    }

    private Booking cursorToBooking(Cursor cursor) {
        Booking b = new Booking();
        b.setUserId(cursor.getInt(1));
        b.setStaffId(cursor.getInt(2));
        b.setTime(cursor.getString(3));
        b.setSlot(cursor.getLong(4));
//        b.getCreateTime(cursor.getString(3));
        boolean status = cursor.getInt(4) == 1;
        b.setStatus(status);
        return b;
    }

    public static ArrayList<Booking> selectBooking(Context context) {
        ArrayList<Booking> bookings = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getReadableDatabase();
        // Define the SELECT query
        String query = "SELECT * FROM " + dbHelper.BOOKING_TABLE;

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Iterate through the cursor and retrieve category data
        if (cursor.moveToFirst()) {
            do {
                // Retrieve column values from the cursor
                int bookingId = cursor.getInt(0);
                int userId = cursor.getInt(1);
                int staffId = cursor.getInt(2);
                String bookingTime = cursor.getString(3);
                Long slot = cursor.getLong(4);
                Double total = cursor.getDouble(6);
                String create_time = cursor.getString(7);

                // Create a Category object
                Booking booking = new Booking(bookingId, userId, staffId, bookingTime, create_time, slot, total, true);

                // Add the category to the list
                bookings.add(booking);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        return bookings;
    }

    public static ArrayList<Booking> selectBookingByUserId(Context context, int idUser) {
        ArrayList<Booking> bookings = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getReadableDatabase();
        // Define the SELECT query
        String query = "SELECT * FROM " + dbHelper.BOOKING_TABLE + " WHERE " + dbHelper.COLUMN_BOOKING_USER_ID + " = ? ";

        // Execute the query
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUser)});

        // Iterate through the cursor and retrieve category data
        if (cursor.moveToFirst()) {
            do {
                // Retrieve column values from the cursor
                int bookingId = cursor.getInt(0);
                int userId = cursor.getInt(1);
                int staffId = cursor.getInt(2);
                String bookingTime = cursor.getString(3);
                Long slot = cursor.getLong(4);
                Double total = cursor.getDouble(6);
                String create_time = cursor.getString(7);

                // Create a Category object
                Booking booking = new Booking(bookingId, userId, staffId, bookingTime,create_time, slot, total, true);

                // Add the category to the list
                bookings.add(booking);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        return bookings;
    }
    public int getIdBooking(Context context, int userId, String bookingTime) {
//        List<Integer> bookingIds = new ArrayList<>();
        int bookingId = -1;
        dbHelper = new DatabaseHelper( context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the SELECT query to retrieve bookingIds based on userId and bookingTime
        String query = "SELECT " + dbHelper.COLUMN_BOOKING_ID + " FROM " + dbHelper.BOOKING_TABLE +
                " WHERE " + dbHelper.COLUMN_BOOKING_USER_ID + " = ? AND " +
                dbHelper.COLUMN_BOOKING_TIME + " = ?";

        // Execute the query with selection arguments
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), bookingTime});

        // Iterate through the cursor and retrieve bookingIds
        if (cursor.moveToFirst()) {
            do {
                // Retrieve bookingId value from the cursor (column index 0)
                 bookingId = cursor.getInt(0);

                // Add the bookingId to the list
//                bookingIds.add(bookingId);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return bookingId;
    }

    public Booking getBookingsByUser(Context context, int idUser) {
        Booking booking = null;
        dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the SELECT query to retrieve booking information based on userId and bookingTime
        String query = "SELECT * FROM " + dbHelper.BOOKING_TABLE +
                " WHERE " + dbHelper.COLUMN_BOOKING_USER_ID + " = ? ";

        // Execute the query with selection arguments
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUser)});

        // Check if the cursor contains any rows
        if (cursor.moveToFirst()) {
            do {
                // Retrieve booking information from the cursor
                int id = cursor.getInt(0);
                int userId = cursor.getInt(1);
                int staffId = cursor.getInt(2);
                String bookingTime = cursor.getString(3);
                Long slot = cursor.getLong(4);
                Double total = cursor.getDouble(6);
                String createTime = cursor.getString(7);

                // Tạo đối tượng Booking với thông tin đã lấy được
                booking = new Booking(id, userId, staffId, bookingTime, createTime, slot, total,true);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return booking;
    }

    public ArrayList<Booking> getBookingsByStaffId(Context context, int idStaff) {
        ArrayList<Booking> bookings = new ArrayList<>();
        dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the SELECT query to retrieve booking information based on userId and bookingTime
        String query = "SELECT * FROM " + dbHelper.BOOKING_TABLE +
                " WHERE " + dbHelper.COLUMN_BOOKING_STAFF_ID + " = ? ";

        // Execute the query with selection arguments
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idStaff)});

        // Check if the cursor contains any rows
        if (cursor.moveToFirst()) {
            do {
                // Retrieve booking information from the cursor
                int id = cursor.getInt(0);
                int userId = cursor.getInt(1);
                int staffId = cursor.getInt(2);
                String bookingTime = cursor.getString(3);
                Long slot = cursor.getLong(4);
                String statusString = cursor.getString(5); // Lấy chuỗi status từ cơ sở dữ liệu
                boolean status = BookingDataSource.convertStringToBoolean(statusString);
                Double total = cursor.getDouble(6);
                String createTime = cursor.getString(7);

                // Tạo đối tượng Booking với thông tin đã lấy được
                Booking booking = new Booking(id, userId, staffId, bookingTime, createTime, slot, total,status);
                bookings.add(booking);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return bookings;
    }
    public static boolean convertStringToBoolean(String statusString) {
        return "1".equals(statusString); // Chuyển "1" thành true, ngược lại thành false
    }

    public static String convertBooleanToString(boolean statusBoolean) {
        return statusBoolean ? "1" : "0"; // Chuyển true thành "1", false thành "0"
    }


    public static ArrayList<TimeSlot> getTimeSlot(Context context) {
        ArrayList<TimeSlot> timeSlots = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getReadableDatabase();
        // Define the SELECT query
        String query = "SELECT " + dbHelper.COLUMN_BOOKING_SLOT + " FROM " + dbHelper.BOOKING_TABLE;

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Iterate through the cursor and retrieve category data
        if (cursor.moveToFirst()) {
            do {
                // Retrieve time slot value from the cursor (column index 0)
                Long slot = cursor.getLong(0);

                // Create a TimeSlot object
                TimeSlot timeSlotObj = new TimeSlot(slot);

                // Add the time slot to the list
                timeSlots.add(timeSlotObj);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        return timeSlots;
    }

    public static ArrayList<TimeSlot> getTimeSlotListForStaff(Context context, int staffId) {
        ArrayList<TimeSlot> timeSlots = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getReadableDatabase();

        // Define the SELECT query to retrieve time slots for a specific staff
        String query = "SELECT " + dbHelper.COLUMN_BOOKING_SLOT +
                " FROM " + dbHelper.BOOKING_TABLE +
                " WHERE " + dbHelper.COLUMN_BOOKING_STAFF_ID + " = ?";

        // Execute the query with staffId as the selection argument
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(staffId)});

        // Iterate through the cursor and retrieve time slot data
        if (cursor.moveToFirst()) {
            do {
                // Retrieve time slot value from the cursor (column index 0)
                Long slot = cursor.getLong(0);

                // Create a TimeSlot object
                TimeSlot timeSlotObj = new TimeSlot(slot);

                // Add the time slot to the list
                timeSlots.add(timeSlotObj);
            } while (cursor.moveToNext());
        }

        // Close the cursor
        cursor.close();

        return timeSlots;
    }


    // Phương thức cập nhật trạng thái của đặt chỗ
    public boolean updateBookingStatus(Context context, Booking booking) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Chuyển giá trị boolean thành số nguyên (1 hoặc 0)
        int statusValue = booking.isStatus() ? 1 : 0;

        cv.put(dbHelper.COLUMN_BOOKING_STATUS, statusValue);

        // Sử dụng ID của đặt chỗ để xác định dòng cần cập nhật
        int bookingId = booking.getId();
        int updateCount = db.update(
                dbHelper.BOOKING_TABLE,
                cv,
                dbHelper.COLUMN_BOOKING_ID + " = ?",
                new String[]{String.valueOf(bookingId)}
        );

        return updateCount > 0;
    }
}
