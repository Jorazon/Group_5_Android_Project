package com.example.group_5_android_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EntryList entries;

    CalendarView calendarView;
    ListView entryListView;

    Calendar calendar = Calendar.getInstance();

    EntryAdapter adapter;

    View noEntriesFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entries = EntryList.getInstance();
        calendarView = findViewById(R.id.calendarView);
        entryListView = findViewById(R.id.list_entries);
        noEntriesFlag = findViewById(R.id.noentriesflag);

        for(int i = 0; i < 10; ++i){
            entries.addEntry(new CalendarEntry(new EntryType[]{EntryType.RUOKA,EntryType.LIIKUNTA}[new Random().nextInt(2)] ,new Date(calendarView.getDate()),"testing 123...\n\n\n\n\ntest entry " + (i+1)));
        }

        //set adapter for list view
        adapter = new EntryAdapter(
                this,
                entries.getEntries()
        );

        entryListView.setAdapter(adapter);

        calendarView.setOnDateChangeListener(
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year,month,dayOfMonth,0,0,0);
                    adapter.filter(calendar.getTime().getTime());
                    Log.d("count",Integer.toString(adapter.getCount()));
                    noEntriesFlag.setVisibility((adapter.getCount() == 0) ? View.VISIBLE : View.INVISIBLE);
                }
        );
    }

    public void addEntry(View view){
        startActivity(new Intent(this, AddEntry.class));
    }
}