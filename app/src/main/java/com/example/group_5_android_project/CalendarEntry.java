package com.example.group_5_android_project;

import java.util.Date;

enum EntryType {
    LIIKUNTA,
    RUOKA
}

public class CalendarEntry {

    private EntryType type;
    private Date time;
    private String description;

    public CalendarEntry(EntryType type, Date time, String description){
        this.type = type;
        this.time = time;
        this.description = description;
    }

    public EntryType getType(){
        return type;
    }

    public Date getTime(){
        return time;
    }

    @Override
    public String toString(){
        return description;
    }
}
