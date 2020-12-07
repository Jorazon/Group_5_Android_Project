package com.example.group_5_android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EntryList entries;

    CalendarView calendar;
    ListView entryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entries = EntryList.getInstance();
        calendar = findViewById(R.id.calendarView);
        entryListView = findViewById(R.id.list_entries);


        for(int i = 0; i < 10; ++i){
            entries.addEntry(new CalendarEntry(new EntryType[]{EntryType.RUOKA,EntryType.LIIKUNTA}[new Random().nextInt(2)] ,new Date(calendar.getDate()),"test entry " + i));
        }

        //set adapter for listview
        entryListView.setAdapter(new EntryAdapter(
                this,
                entries.getEntries()
        ));
    }
}