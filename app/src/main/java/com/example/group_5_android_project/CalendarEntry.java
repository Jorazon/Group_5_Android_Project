package com.example.group_5_android_project;

import java.io.Serializable;
import java.util.Date;

enum EntryType {
    LIIKUNTA,
    RUOKA,
    UNDEF
}

public class CalendarEntry implements Comparable<CalendarEntry>, Serializable {

    private final EntryType type;
    private final Date time;
    private final String description;

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

    @Override
    public int compareTo(CalendarEntry o) {
        return this.time.compareTo(o.time);
    }
}
