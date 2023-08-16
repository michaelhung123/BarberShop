package com.example.barbershop.Common;

import com.example.barbershop.Domain.Account;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Common {
    public static final String PAYPAL_CLIENT_ID = "AT6bxkunPAdCFP5bmp7FD_NhYf89Ji12Ta-5DyditvH4WddP_hjOElbyPuoz5jv7xtTe8FbX0EtUB3cs";
    public  static Account currentUser;
    public static Calendar bookingDate = Calendar.getInstance();
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");
    public static final int TIME_SLOT_TOTAL = 20;  //10 tiếng mở cửa, 30p = 1 task cắt tóc => 20 task
    public static Object DISABLE_TAG  = "DISABLE";

    public static String convertTimeSlotToString(int slot) {
        switch (slot) {
            case 0:
                return "9:00 - 30";
            case 1:
                return "9:30 - 10:00";
            case 2:
                return "10:00 - 10:30";
            case 3:
                return "10:30 - 11:00";
            case 4:
                return "11:00 - 11:30";
            case 5:
                return "11:30 - 12:00";
            case 6:
                return "12:00 - 12:30";
            case 7:
                return "12:30 - 13:00";
            case 8:
                return "13:00 - 13:30";
            case 9:
                return "13:30 - 14:00";
            case 10:
                return "14:00 - 14:30";
            case 11:
                return "15:00 - 15:30";
            case 12:
                return "15:30 - 16:00";
            case 13:
                return "16:00 - 16:30";
            case 14:
                return "16:30 - 17:00";
            case 15:
                return "17:00 - 17:30";
            case 16:
                return "17:00 - 17:30";
            case 17:
                return "17:00 - 17:30";
            case 18:
                return "17:00 - 17:30";
            case 19:
                return "17:00 - 17:30";
            default:
                return "Closed";
        }
    }
}
