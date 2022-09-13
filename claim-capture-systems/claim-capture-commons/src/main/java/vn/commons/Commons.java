package vn.commons;

import lombok.Data;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Commons {
    final static String DATE_TIME = "yyyy-MM-dd";

    public static boolean isNullOrEmpty(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        s = s.trim();
        if ("".equals(s)) {
            return true;
        }
        return false;
    }

    public static boolean isTimeStamp(String date) {
        try {
            if (date == null || date.isEmpty()) {
                return true;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME);
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // start date ,end date
    public static boolean isDateCorrect(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return true;
        }
        try {
            DateFormat df = new SimpleDateFormat(DATE_TIME);
            df.setLenient(false);
            Date date = df.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return matchesDatePattern(dateString);
        } catch (ParseException e) {
            return false;
        }
    }

    private static boolean matchesDatePattern(String dateString) {
        return dateString.matches("^\\d+\\-\\d+\\-\\d+");
    }


    //    utf 8
    public static boolean isUTF8MisInterpreted(String input) {
        return isUTF8MisInterpreted(input, "Windows-1252");
    }

    private static boolean isUTF8MisInterpreted(String input, String encoding) {

        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
        CharsetEncoder encoder = Charset.forName(encoding).newEncoder();
        ByteBuffer tmp;
        try {
            tmp = encoder.encode(CharBuffer.wrap(input));
        } catch (CharacterCodingException e) {
            return false;
        }

        try {
            decoder.decode(tmp);
            return true;
        } catch (CharacterCodingException e) {
            return false;
        }
    }

    public static String convertDateTime(String time) {
        String subtime = time.substring(0, 10);
        System.out.println(subtime);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            Long date1 = sdf.parse(subtime).getTime();
            return date1.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static Timestamp convertDateToTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }
}
