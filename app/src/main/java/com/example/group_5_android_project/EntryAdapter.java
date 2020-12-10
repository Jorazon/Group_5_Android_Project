package com.example.group_5_android_project;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

//https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView

/**
 * Adapter for displaying {@link CalendarEntry} objects in a ListView.
 * @author Oskari Pahkala
 */
public class EntryAdapter extends ArrayAdapter<CalendarEntry> {

    private final ArrayList<CalendarEntry> data;//original passed data
    private ArrayList<CalendarEntry> filtered = new ArrayList<>();//filtered entries

    /**
     * Class constructor.
     * @param context passed context.
     * @param objects data set to display
     */
    public EntryAdapter(Context context, ArrayList<CalendarEntry> objects) {
        super(context, 0);
        data = objects;
    }

    /**
     * Returns count of objects displayed after filtering.
     * @return count of objects displayed after filtering
     */
    public int getCount() {
        return filtered.size();
    }

    /**
     * Returns {@link CalendarEntry} item at a specific index from filtered items.
     * @param position position of requested item
     * @return CalendarEntry item at position
     */
    public CalendarEntry getItem(int position) {
        return filtered.get(position);
    }

    /**
     * Returns index of filtered item in original data.
     * @param position position of requested item
     * @return index of item in original data
     */
    public long getItemId(int position) {
        return data.indexOf(filtered.get(position));
    }

    /**
     * Makes a customized view based on item details.
     * @param position position of requested item
     * @param convertView base view to use for displaying item
     * @param parent parent view
     * @return customized view
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the item requested
        CalendarEntry entry = getItem(position);

        //if a view isn't specified create one
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.entryview, parent, false);
        }

        //get views from base view
        ImageView icon = convertView.findViewById(R.id.entryIcon);
        TextView description = convertView.findViewById(R.id.entryDescription);
        TextView timeStamp = convertView.findViewById(R.id.entryTImeStamp);

        //set icon image based on entry type
        switch (entry.getType()) {
            case LIIKUNTA:
                icon.setImageResource(R.drawable.ic_entry_exercise);
                break;
            case RUOKA:
                icon.setImageResource(R.drawable.ic_entry_food);
                break;
        }

        //set description text from entry
        description.setText(entry.toString());

        //display timestamp in device locale
        String pattern;
        if(DateFormat.is24HourFormat(getContext())){
            pattern = "H:mm";
        } else {
            pattern = "h:mm a";
        }
        timeStamp.setText(new SimpleDateFormat(pattern, Locale.getDefault()).format(entry.getTime()));

        //return customized view
        return convertView;
    }

    /**
     * Filter original data set to only show entries that are on a specific day.
     * @param millis millisecond epoch of day to filter items by.
     */
    public void filter(long millis){
        //empty list to store matches
        ArrayList<CalendarEntry> match = new ArrayList<>();

        //get instance of Calendar and set it to filter date
        Calendar filter = Calendar.getInstance();
        filter.setTimeInMillis(millis);

        //get a second Calendar instance to be later set to entry date
        Calendar entrytime = Calendar.getInstance();

        //test filter on all entries to find matches
        for(CalendarEntry entry : data){

            //set the second Calendar instance to entry date
            entrytime.setTime(entry.getTime());

            //see if dates match on date but not time
            if (entrytime.get(Calendar.YEAR) == filter.get(Calendar.YEAR) &&
                entrytime.get(Calendar.MONTH) == filter.get(Calendar.MONTH) &&
                entrytime.get(Calendar.DAY_OF_MONTH) == filter.get(Calendar.DAY_OF_MONTH))
            {
                //if it matches add it to match list
                match.add(entry);
            }
        }

        //save matches to sorted
        filtered = match;

        //sort from earliest to latest
        Collections.sort(filtered);

        //notify adapter that data set has been modifies
        notifyDataSetChanged();
    }
}