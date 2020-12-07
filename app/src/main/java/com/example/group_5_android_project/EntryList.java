package com.example.group_5_android_project;

import java.util.ArrayList;

class EntryList {
    private static final EntryList ourInstance = new EntryList();

    static EntryList getInstance() {
        return ourInstance;
    }

    private ArrayList<CalendarEntry> entries;

    private EntryList() {
        entries = new ArrayList<>();
    }

    public ArrayList<CalendarEntry> getEntries() {
        return entries;
    }

    public void addEntry(CalendarEntry entry){
        entries.add(entry);
    }
}
