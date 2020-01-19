package com.igor1c.taskmanager.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {

    public static DateHelper DATE_HELPER = new DateHelper();

    private static final String timeZoneString = "GMT";

    // "C" is for colon (":")
    // "_" is for space
    // "P" is for point / dot
    // "H" is for hyphen ("-")
    public static SimpleDateFormat sdf_yyyycMMcdd;
    public static SimpleDateFormat sdf_yyyyhMMhddThhcmmcss;
    public static SimpleDateFormat sdf_yyyyhMMhdd_hhcmmcss;
    public static SimpleDateFormat sdf_yyyycMMcdd_hhhmmhsscS;
    private static final String SDF_STRING_YYYYСMMСDD = "yyyy.MM.dd";
    private static final String SDF_STRING_YYYYHMMHDDTHHCMMCSS = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String SDF_STRING_YYYYHMMHDD_HHCMMCSS = "yyyy-MM-dd HH:mm:ss";
    private static final String SDF_STRING_YYYYСMMСDD_HHHMMHSSCS = "yyyy.MM.dd HH-mm-ss.S";



    static void init() {}

    public DateHelper() {

        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);

        sdf_yyyycMMcdd = new SimpleDateFormat(SDF_STRING_YYYYСMMСDD);
        sdf_yyyycMMcdd.setTimeZone(timeZone);

        sdf_yyyyhMMhddThhcmmcss = new SimpleDateFormat(SDF_STRING_YYYYHMMHDDTHHCMMCSS);
        sdf_yyyyhMMhddThhcmmcss.setTimeZone(timeZone);

        sdf_yyyyhMMhdd_hhcmmcss = new SimpleDateFormat(SDF_STRING_YYYYHMMHDD_HHCMMCSS);
        sdf_yyyyhMMhdd_hhcmmcss.setTimeZone(timeZone);

        sdf_yyyycMMcdd_hhhmmhsscS = new SimpleDateFormat(SDF_STRING_YYYYСMMСDD_HHHMMHSSCS);
        sdf_yyyycMMcdd_hhhmmhsscS.setTimeZone(timeZone);

    }



    public static Date dateStartOfDay(Date date) throws ParseException {

        String stringDate = sdf_yyyycMMcdd.format(date);

        Calendar instance = Calendar.getInstance();
        instance.setTime(sdf_yyyycMMcdd.parse(stringDate));

        return instance.getTime();

    }

    public static String dateToString(SimpleDateFormat curSdf, Date curDate) {
        return curSdf.format(curDate);
    }

    public static Date dateAdd(Date date, int addType, int quota) {

        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(addType, quota);
        return instance.getTime();

    }

    public static Date dateAddDays(Date date, int days) {

        return dateAdd(date, Calendar.DAY_OF_MONTH, days);

    }

    public static String getCurrentDateString() {
        return DateHelper.dateToString(DateHelper.sdf_yyyycMMcdd_hhhmmhsscS, new Date());
    }

    public static Date stringToDate(SimpleDateFormat sdf, String dateString) {

        try {
            return new SimpleDateFormat(sdf.toPattern()).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

}
