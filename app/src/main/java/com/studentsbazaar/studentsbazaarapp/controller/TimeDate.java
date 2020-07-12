package com.studentsbazaar.studentsbazaarapp.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeDate {

    private Calendar calendar;//HH:mm:ss
    private SimpleDateFormat simpleDateFormat;
    String date,time;

    public TimeDate() {
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    }

    public String gettime(){
        time = simpleDateFormat.format(calendar.getTime());
        return time;
    }

    public String getseconds(){
        time = String.valueOf(calendar.get(Calendar.SECOND));
        return time;
    }

    public String getdate(){
        date = calendar.get(Calendar.DATE)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR);
        return date;
    }

    public String getDateYMD(){
        String MON = "", DAY = "";
        if((calendar.get(Calendar.MONTH)+1) < 10){
            MON = "0"+(calendar.get(Calendar.MONTH)+1);
        }
        if(calendar.get(Calendar.DATE)<10){
            DAY = "0"+calendar.get(Calendar.DATE);
        }
        date = calendar.get(Calendar.YEAR)+"-"+MON+"-"+DAY;
        return date;
    }

}
