package com.example.group_5_android_project;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton list of {@link CalendarEntry} objects.
 * @author Timi Rautio
 */
class EntryList {
    private static final EntryList ourInstance = new EntryList();

    /**
     * Returns an instance of the list.
     * @return instance of list
     */
    static EntryList getInstance() {
        return ourInstance;
    }

    //list of entries
    private final ArrayList<CalendarEntry> entries = new ArrayList<>();

    /**
     * Class constructor.
     */
    private EntryList() {}

    /**
     * Gets the list of {@link CalendarEntry} objects.
     * @return list of objects
     */
    public ArrayList<CalendarEntry> getEntries() {
        return entries;
    }

    /**
     * Adds single entry to list.
     * @param entry new entry
     */
    public void addEntry(CalendarEntry entry){
        entries.add(entry);
    }

    /**
     * Adds a list of entries to list.
     * @param list list of new entries
     */
    public void addAll(List<CalendarEntry> list){
        entries.addAll(list);
    }
}
