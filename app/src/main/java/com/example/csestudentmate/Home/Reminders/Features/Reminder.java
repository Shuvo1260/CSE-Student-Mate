package com.example.csestudentmate.Home.Reminders.Features;

public class Reminder {
    private long id;
    private String title;
    private String details;
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;
    private int activated;

    public Reminder(){}
    public Reminder(long id, String title, String details, int hour, int minute, int day, int month, int year, int activated) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
        this.activated = activated;
    }
    public Reminder(String title, String details, int hour, int minute, int day, int month, int year, int activated) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
        this.activated = activated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getActivated() {
        return activated;
    }

    public void setActivated(int activated) {
        this.activated = activated;
    }
}
