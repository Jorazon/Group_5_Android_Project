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

/**
 * Main activity of the application.
 * @author Oskari Pahkala
 */

public class MainActivity extends AppCompatActivity {

    Calendar calendar = Calendar.getInstance();
    EntryList entries = EntryList.getInstance();
    EntryAdapter adapter;

    /**
     * Loads entries at first start and sets the adapter for ListView and DateChangeListener for CalendarView.
     * @param savedInstanceState the activity's previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set different title
        this.setTitle(R.string.app_name);

        //get some references
        CalendarView calendarView = findViewById(R.id.calendarView);
        ListView entryListView = findViewById(R.id.list_entries);

        //set CalendarView date to one loaded form SharedPreferences or the default new Date
        calendarView.setDate(FileIO.loadMillis(this));

        /*
        for(int i = 0; i < 3; ++i){
            //generate test entries
            entries.addEntry(new CalendarEntry(new EntryType[]{EntryType.RUOKA,EntryType.LIIKUNTA}[new Random().nextInt(2)] ,new Date(calendarView.getDate()),"testing 123...\n\n\n\n\ntest entry " + (i+1)));
        }
        */

        //load entries from data storage and add them to EntryList when app is opened (there are always 0 entries when app is first opened even if some have been added before)
        if (entries.getEntries().size() == 0) {
            @SuppressWarnings("unchecked")
            ArrayList<CalendarEntry> loaded = (ArrayList<CalendarEntry>) FileIO.readFromFile(this, "CalendarEntries");
            if (loaded != null) {
                Log.d("loadEntries", "" + loaded.size());
                entries.addAll(loaded);
            }
        }

        //create and set adapter for list view
        adapter = new EntryAdapter(this);
        entryListView.setAdapter(adapter);

        //make an OnItemClickListener that removes an entry if it is long clicked
        entryListView.setOnItemLongClickListener(
            (parent, view, position, id) -> {
                EntryList.getInstance().removeEntry(id);
                adapter.notifyDataSetChanged();
                adapter.filter(calendar.getTime().getTime());
                setNoEntriesFlag();
                FileIO.saveToFile(this,entries.getEntries(),"CalendarEntries");
                return false;
            }
        );

        //set a OnDateChangeListener to update filter when user changes selected date on CalendarView
        calendarView.setOnDateChangeListener(
                (view, year, month, dayOfMonth) -> {

                    //update filter
                    calendar.set(year,month,dayOfMonth,0,0,0);
                    adapter.filter(calendar.getTime().getTime());

                    //update visibility of noentriesflag
                    setNoEntriesFlag();

                    //save selected date to SharedPreferences
                    FileIO.saveMillis(this, calendar.getTimeInMillis());
                }
        );

        //filter entries to appear only for the selected date
        adapter.filter(calendarView.getDate());

        //update visibility of noentriesflag
        setNoEntriesFlag();
    }

    /**
     * Saves entries when the activity loses focus.
     */
    @Override
    protected void onPause(){
        super.onPause();
        //save entries to data storage when the activity loses focus
        FileIO.saveToFile(this,entries.getEntries(),"CalendarEntries");
    }

    /**
     * Starts {@link AddEntry} activity passing the selected date millisecond epoch from CalendarView in the Intent.
     * @param view view reference from the view the method is attached to
     */
    public void addEntry(View view){
        //put selected date in millis as extra and start AddEntry activity
        Intent intent = new Intent(this, AddEntry.class);
        intent.putExtra("date", calendar.getTimeInMillis());
        startActivity(intent);
    }

    /**
     * Sets the visibility of noentriesflag based on adapter item count. Visible if count is 0 and invisible otherwise.
     */
    private void setNoEntriesFlag(){
        findViewById(R.id.noentriesflag).setVisibility((adapter.getCount() == 0) ? View.VISIBLE : View.INVISIBLE);
    }
}