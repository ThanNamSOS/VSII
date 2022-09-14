package com.vsii.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    /**
     * Get {@link DateFormat} based on date <code>pattern</code>
     *
     * @param pattern format date pattern
     * @return new date format {@link DateFormat}
     */
    public static DateFormat df(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * Format date as string based on date <code>pattern</code>
     *
     * @param date    date value need to format
     * @param pattern format date pattern
     * @return string date
     */
    public static String format(Date date, String pattern) {
        if (date == null || StringUtils.isEmpty(pattern)) {
            LOGGER.error(String.format("date or pattern [%s, %s] not allow null", date, pattern));

            return "";
        }

        return df(pattern).format(date);
    }

    public static Date parse(String date, String pattern) {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(pattern)) {
            LOGGER.error(String.format("date or pattern [%s, %s] not allow null", date, pattern));
            return null;
        }
        try {
            return df(pattern).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Pair<Date, Date> getDateRange() {
        Date begining, end;

        {
            Calendar calendar = getCalendarForNow();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            setTimeToBeginningOfDay(calendar);
            begining = calendar.getTime();
        }

        {
            Calendar calendar = getCalendarForNow();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            setTimeToEndofDay(calendar);
            end = calendar.getTime();
        }

        return Pair.of(begining, end);
    }

    public static String formatDatefromDate(String dateStr, String sourcePt, String targetPt) {
        return format(parse(dateStr, sourcePt), targetPt);
    }

    public static Date parseSubmisstionDate(String dateStr) {
        Date date = parse(dateStr, "yyyy-MM-dd HH:mm:ss");
        if (date == null) {
            date = parse(dateStr, "yyyyMMddHHmmss");
        }
        if (date == null) {
            date = parse(dateStr, "yyyy-MM-dd");
        }
        if (date == null) {
            date = parse(dateStr, "yyyyMMdd");
        }
        return date;
    }

    public static Pair<Date, Date> getDateRange(Calendar calendar) {
        Date begining, end;

        {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            setTimeToBeginningOfDay(calendar);
            begining = calendar.getTime();
        }

        {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            setTimeToEndofDay(calendar);
            end = calendar.getTime();
        }

        return Pair.of(begining, end);
    }

    private static Calendar getCalendarForNow() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    private static void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setTimeToEndofDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }
    public static Timestamp convertDate(String dateInput){
        if (!dateInput.equals("") && dateInput != null) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(dateInput);
                return date == null ? null : new java.sql.Timestamp(date.getTime());
            } catch (Exception e) {
                LOGGER.info("Erro Date Format: " + dateInput);
                return null;
            }
        }
        return null;
    }

    public static Timestamp convertTimestamp(String dateInput){
        try {
            if(dateInput != null && dateInput != ""){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                Date parsedDate = dateFormat.parse(dateInput);
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                return timestamp;
            }
        }catch (Exception e){
            LOGGER.info("Erro Timestamp Format: "+dateInput);
            return null;
        }
        return null;
    }


//    public Date convertDate(String startDateString){
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Date startDate;
//        try {
//            startDate = df.parse(startDateString);
//            String newDateString = df.format(startDate);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
}
