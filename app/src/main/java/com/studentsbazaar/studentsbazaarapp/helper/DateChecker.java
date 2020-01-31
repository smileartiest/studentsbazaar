package com.studentsbazaar.studentsbazaarapp.helper;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateChecker {

    boolean result;

    public static final String DATE_INPUT_FORMAT = "yyyy-MM-dd";
    /**
     * DateTime Output Format
     */
    public static final String DATE_OUTPUT_FORMAT = "dd-MMM-yyyy";

    public String dateAlphabet(String event_start_date){

        String mDateTime = null;
        try {

            mDateTime = DateUtils.formatDateFromDateString(DATE_INPUT_FORMAT, DATE_OUTPUT_FORMAT, event_start_date);
        } catch (ParseException e) {
            e.printStackTrace();

        }
            return mDateTime;
    }





        public Boolean lastdate(String dateInString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date lastdate = sdf.parse(dateInString);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 0);
            cal.clear(Calendar.HOUR_OF_DAY);
            cal.clear(Calendar.HOUR);
            cal.clear(Calendar.AM_PM);
            cal.clear(Calendar.MINUTE);
            cal.clear(Calendar.SECOND);
            cal.clear(Calendar.MILLISECOND);
            Date curr = cal.getTime();


            Calendar cal2 = Calendar.getInstance();
            cal2.add(Calendar.DATE, 5);
            Date future = cal2.getTime();


            if (lastdate.compareTo(curr) > 0) {

                if (lastdate.compareTo(future) < 0) {

                    result = true;
                } else {

                    result = false;
                }

            } else if (lastdate.compareTo(curr) < 0) {

                result = false;
            } else if (lastdate.compareTo(curr) == 0) {

                result = true;
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Boolean previousdate(String dateInString) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date lastdate = sdf.parse(dateInString);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 0);
            cal.clear(Calendar.HOUR_OF_DAY);
            cal.clear(Calendar.HOUR);
            cal.clear(Calendar.AM_PM);
            cal.clear(Calendar.MINUTE);
            cal.clear(Calendar.SECOND);
            cal.clear(Calendar.MILLISECOND);
            Date curr = cal.getTime();

            if (curr.compareTo(lastdate) <= 0) {

                Log.d("DATECHECK",lastdate+"");
                result = true;
            } else {

                result = false;
            }


        }catch (Exception e){

        }

        return result;
    }


}
