package com.example.group_5_android_project;

import java.io.Serializable;
import java.util.Date;


/**
 * EntryType enumerator.
 * @author Oskari Pahkala
 */
enum EntryType {
    LIIKUNTA,
    RUOKA,
    UNDEF
}

/**
 * Calendar entry object class.
 * @author Oskari Pahkala
 */
public class CalendarEntry implements Comparable<CalendarEntry>, Serializable {

    private final EntryType type;
    private final Date time;
    private final String description;

    /**
     * Class constructor.
     * @param type type of entry
     * @param time date and time of entry
     * @param description description of entry
     */
    public CalendarEntry(EntryType type, Date time, String description){
        this.type = type;
        this.time = time;
        this.description = description;
    }

    /**
     * Returns type of entry.
     * @return type of entry
     */
    public EntryType getType(){
        return type;
    }

    /**
     * Returns date and time of entry.
     * @return date and time of entry
     */
    public Date getTime(){
        return time;
    }

    /**
     * Returns description of entry.
     * @return description of entry
     */
    @Override
    public String toString(){
        return description;
    }

    /**
     * Compares two {@link CalendarEntry} based on their time.
     * @param o comparable {@link CalendarEntry}
     * @return 0 if the dates of this and param are equal, less than 0 if date of this is before param date, greater than 0 if date of this is after param date
     */
    @Override
    public int compareTo(CalendarEntry o) {
        return this.time.compareTo(o.time);
    }
}
