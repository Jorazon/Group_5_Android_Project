package com.example.group_5_android_project;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EntryList entries;

    CalendarView calendar;
    ListView entryListView;

    long calendarSelectedDate;

    EntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entries = EntryList.getInstance();
        calendar = findViewById(R.id.calendarView);
        entryListView = findViewById(R.id.list_entries);

        calendarSelectedDate = calendar.getDate();

        for(int i = 0; i < 10; ++i){
            entries.addEntry(new CalendarEntry(new EntryType[]{EntryType.RUOKA,EntryType.LIIKUNTA}[new Random().nextInt(2)] ,new Date(calendarSelectedDate),"test entry " + i));
        }

        //set adapter for listview
        adapter = new EntryAdapter(
                this,
                entries.getEntries()
        );

        entryListView.setAdapter(adapter);

        calendar.setOnDateChangeListener(
            (view, year, month, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,dayOfMonth-1);//jostain syystä pitää laittaa -1 että päivä osuu oikein
                setFilter(calendar.getTimeInMillis());
            }
        );
    }

    private void setFilter(long TimeInMillis){
        calendarSelectedDate = TimeInMillis;
        adapter.getFilter().filter(Long.toString(TimeInMillis));
        findViewById(R.id.noentriesflag).setVisibility((entryListView.getCount() == 0) ? View.INVISIBLE : View.VISIBLE);
    }
}