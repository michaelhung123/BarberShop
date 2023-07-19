package com.example.barbershop.Module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.barbershop.Domain.Account;
import com.example.barbershop.Domain.Service;
import com.example.barbershop.Domain.Voucher;

import java.util.ArrayList;

public class VoucherDataSource {
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;
    private String[] allColumns = {
            dbHelper.COLUMN_VOUCHER_ID,
            dbHelper.COLUMN_VOUCHER_NAME,
            dbHelper.COLUMN_VOUCHER_VALUE,
            dbHelper.COLUMN_VOUCHER_QUANTITY,
            dbHelper.COLUMN_VOUCHER_CODE,
            dbHelper.COLUMN_VOUCHER_START,
            dbHelper.COLUMN_VOUCHER_END
    };

    public VoucherDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public static ArrayList<Voucher> selectVouchers(Context context) {
        ArrayList<Voucher> vouchers = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getReadableDatabase();
        // Define the SELECT query
        String query = "SELECT * FROM " + dbHelper.VOUCHERS_TABLE;

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Iterate through the cursor and retrieve category data
        if (cursor.moveToFirst()) {
            do {
                // Retrieve column values from the cursor
                int voucherID = cursor.getInt(0);
                String voucherName = cursor.getString(1);
                String voucherCode = cursor.getString(2);
                double voucherValue = cursor.getDouble(3);
                int voucherQuantity = cursor.getInt(4);
                String voucherStartTime = cursor.getString(5);
                String voucherEndTime = cursor.getString(6);

                // Create a Category object
                Voucher voucher = new Voucher(voucherID, voucherName, voucherCode, voucherValue, voucherQuantity, voucherStartTime, voucherEndTime);

                // Add the category to the list
                vouchers.add(voucher);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        return vouchers;
    }

    public Voucher addVoucher(Voucher voucher){
        open();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_VOUCHER_NAME, voucher.getName());
        cv.put(dbHelper.COLUMN_VOUCHER_VALUE, voucher.getValue());
        cv.put(dbHelper.COLUMN_VOUCHER_QUANTITY, voucher.getQuantity());
        cv.put(dbHelper.COLUMN_VOUCHER_CODE, voucher.getCode());
        cv.put(dbHelper.COLUMN_VOUCHER_START, voucher.getStartTime());
        cv.put(dbHelper.COLUMN_VOUCHER_END, voucher.getEndTime());

        long insertId = db.insert(dbHelper.VOUCHERS_TABLE, null, cv);
        Cursor cursor = db.query(dbHelper.VOUCHERS_TABLE, allColumns, null,null,null,null,null );
        cursor.moveToFirst();
        Voucher newVoucher = cursorToVoucher(cursor);
        cursor.close();
        return newVoucher;
    }

    public static boolean updateService(Context context, Voucher voucher) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_VOUCHER_NAME, voucher.getName());
        cv.put(dbHelper.COLUMN_VOUCHER_VALUE, voucher.getValue());
        cv.put(dbHelper.COLUMN_VOUCHER_QUANTITY, voucher.getQuantity());
        cv.put(dbHelper.COLUMN_VOUCHER_CODE, voucher.getCode());
        cv.put(dbHelper.COLUMN_VOUCHER_START, voucher.getStartTime());
        cv.put(dbHelper.COLUMN_VOUCHER_END, voucher.getEndTime());
        int updateVoucher = db.update(dbHelper.VOUCHERS_TABLE, cv, dbHelper.COLUMN_VOUCHER_ID + " = ?", new String[]{Integer.toString(voucher.getId())});
        return updateVoucher > 0;
    }

    public static boolean deleteVoucher(Context context, int voucherID) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        int deleteCate = db.delete(dbHelper.VOUCHERS_TABLE, dbHelper.COLUMN_VOUCHER_ID + " = ?", new String[]{Integer.toString(voucherID)});
        return deleteCate > 0;
    }

    private Voucher cursorToVoucher(Cursor cursor) {
        Voucher voucher = new Voucher();
        voucher.setId(cursor.getInt(0));
        voucher.setName(cursor.getString(1));
        voucher.setCode(cursor.getString(2));
        voucher.setValue(cursor.getDouble(3));
        voucher.setQuantity(cursor.getInt(4));
        voucher.setStartTime(cursor.getString(5));
        voucher.setEndTime(cursor.getString(6));
        return voucher;
    }
}
