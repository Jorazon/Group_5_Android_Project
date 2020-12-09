package com.example.group_5_android_project;

import java.util.ArrayList;
import java.util.List;

class EntryList {
    private static final EntryList ourInstance = new EntryList();

    static EntryList getInstance() {
        return ourInstance;
    }

    private final ArrayList<CalendarEntry> entries = new ArrayList<>();

    private EntryList() {}

    public ArrayList<CalendarEntry> getEntries() {
        return entries;
    }

    public void addEntry(CalendarEntry entry){
        entries.add(entry);
    }

    public void addAll(List<CalendarEntry> list){
        entries.addAll(list);
    }
}
