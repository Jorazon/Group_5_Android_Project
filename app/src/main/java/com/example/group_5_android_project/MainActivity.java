package com.example.group_5_android_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Calendar calendar = Calendar.getInstance();
    EntryList entries = EntryList.getInstance();
    EntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle(R.string.app_name);

        CalendarView calendarView = findViewById(R.id.calendarView);
        ListView entryListView = findViewById(R.id.list_entries);

        /*
        for(int i = 0; i < 3; ++i){
            //generate test entries
            entries.addEntry(new CalendarEntry(new EntryType[]{EntryType.RUOKA,EntryType.LIIKUNTA}[new Random().nextInt(2)] ,new Date(calendarView.getDate()),"testing 123...\n\n\n\n\ntest entry " + (i+1)));
        }
        */

        //load entries from data storage when app is opened
        if (entries.getEntries().size() == 0) {
            @SuppressWarnings("unchecked")
            ArrayList<CalendarEntry> loaded = (ArrayList<CalendarEntry>) FileIO.readFromFile(this, "CalendarEntries");
            if (loaded != null) {
                Log.d("loadEntries", "" + loaded.size());
                entries.addAll(loaded);
            }
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
                    setNoEntriesFlag();
                }
        );

        adapter.filter(calendarView.getDate());//fix all entries from showing when creating the activity

        setNoEntriesFlag();
    }

    @Override
    protected void onPause(){
        super.onPause();
        //save entries to data storage when app is going to stop
        Log.d("loadEntries","attempting save");
        if(FileIO.saveToFile(this,entries.getEntries(),"CalendarEntries")){
            Log.d("loadEntries","saved");
        }
    }

    public void addEntry(View view){
        Intent intent = new Intent(this, AddEntry.class);
        intent.putExtra("date", calendar.getTimeInMillis());
        startActivity(intent);
    }

    private void setNoEntriesFlag(){
        findViewById(R.id.noentriesflag).setVisibility((adapter.getCount() == 0) ? View.VISIBLE : View.INVISIBLE);
    }
}