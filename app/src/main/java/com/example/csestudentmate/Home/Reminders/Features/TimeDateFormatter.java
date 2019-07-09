package com.example.csestudentmate.Home.Reminders.Features;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeDateFormatter {
    public TimeDateFormatter(){}
    public String setTimeFormat(int hour, int minute){

        // Setting am pm
        String amPm;
        if(hour == 0){
            amPm = " AM";
            hour = 12;
        }else if(hour == 12){
            amPm = " PM";
        }
        else if(hour > 12){
            amPm = " PM";
            hour -= 12;
        }else{
            amPm = " AM";
        }
        // Time formating
        return (String.format("%02d:%02d", hour, minute) + amPm);
    }

    public String setDateFormat(int day, int month, int year){

        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");

        // To get month name
        calendar.set(calendar.MONTH, month);
        String tempDate;
        tempDate = simpleDateFormat.format(calendar.getTime());

        //Date formating
        tempDate += String.format(" %d, %d", day, year);

        return tempDate;
    }
}
