package com.igor1c.taskmanager.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {

    public static DateHelper DATE_HELPER = new DateHelper();

    private static final String timeZoneString = "GMT+7";

    // "A" is for apostrophe ("'")
    // "C" is for colon (":")
    // "_" is for space
    // "P" is for point / dot
    // "H" is for hyphen ("-")
    public static final SimpleDateFormat SDF_YYYYPMMPDD;
    public static final SimpleDateFormat SDF_YYYYHMMHDDATAHHCMMCSS;
    public static final SimpleDateFormat SDF_YYYYHMMHDDTHHCMMCSSPSZ;
    public static final SimpleDateFormat SDF_YYYYHMMHDD_HHCMMCSS;
    public static final SimpleDateFormat SDF_YYYYHMMHDD_HHCMMCSSPS;
    public static final SimpleDateFormat SDF_YYYYPMMPDD_HHHMMHSSPS;
    private static final String SDF_STRING_YYYYPMMPDD = "yyyy.MM.dd";
    private static final String SDF_STRING_YYYYHMMHDDATAHHCMMCSS = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String SDF_STRING_YYYYHMMHDDTHHCMMCSSPSZ = "yyyy-MM-dd'T'HH:mm:ss.S'Z'";
    private static final String SDF_STRING_YYYYHMMHDD_HHCMMCSS = "yyyy-MM-dd HH:mm:ss";
    private static final String SDF_STRING_YYYYHMMHDD_HHCMMCSSPS = "yyyy-MM-dd HH:mm:ss.S";
    private static final String SDF_STRING_YYYYPMMPDD_HHHMMHSSPS = "yyyy.MM.dd HH-mm-ss.S";

    static {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);

        SDF_YYYYPMMPDD = new SimpleDateFormat(SDF_STRING_YYYYPMMPDD);
        SDF_YYYYPMMPDD.setTimeZone(timeZone);

        SDF_YYYYHMMHDDATAHHCMMCSS = new SimpleDateFormat(SDF_STRING_YYYYHMMHDDATAHHCMMCSS);
        SDF_YYYYHMMHDDATAHHCMMCSS.setTimeZone(timeZone);

        SDF_YYYYHMMHDDTHHCMMCSSPSZ = new SimpleDateFormat(SDF_STRING_YYYYHMMHDDTHHCMMCSSPSZ);
        SDF_YYYYHMMHDDTHHCMMCSSPSZ.setTimeZone(timeZone);

        SDF_YYYYHMMHDD_HHCMMCSS = new SimpleDateFormat(SDF_STRING_YYYYHMMHDD_HHCMMCSS);
        SDF_YYYYHMMHDD_HHCMMCSS.setTimeZone(timeZone);

        SDF_YYYYHMMHDD_HHCMMCSSPS = new SimpleDateFormat(SDF_STRING_YYYYHMMHDD_HHCMMCSSPS);
        SDF_YYYYHMMHDD_HHCMMCSSPS.setTimeZone(timeZone);

        SDF_YYYYPMMPDD_HHHMMHSSPS = new SimpleDateFormat(SDF_STRING_YYYYPMMPDD_HHHMMHSSPS);
        SDF_YYYYPMMPDD_HHHMMHSSPS.setTimeZone(timeZone);
    }



    public DateHelper() {

    }



    public static Date dateStartOfDay(Date date) throws ParseException {

        String stringDate = SDF_YYYYPMMPDD.format(date);

        Calendar instance = Calendar.getInstance();
        instance.setTime(SDF_YYYYPMMPDD.parse(stringDate));

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
        return DateHelper.dateToString(DateHelper.SDF_YYYYPMMPDD_HHHMMHSSPS, new Date());
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
