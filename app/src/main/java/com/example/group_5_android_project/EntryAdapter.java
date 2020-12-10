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

public class EntryAdapter extends ArrayAdapter<CalendarEntry> {

    private final ArrayList<CalendarEntry> data;//original passed data
    private ArrayList<CalendarEntry> filtered = new ArrayList<>();//filtered entries

    public EntryAdapter(Context context, ArrayList<CalendarEntry> objects) {
        super(context, 0);
        data = objects;
    }

    public int getCount() {
        return filtered.size();
    }

    public CalendarEntry getItem(int position) {
        return filtered.get(position);
    }

    public long getItemId(int position) {
        return data.indexOf(filtered.get(position));
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        CalendarEntry entry = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.entryview, parent, false);
        }

        //get views
        ImageView icon = convertView.findViewById(R.id.entryIcon);
        TextView description = convertView.findViewById(R.id.entryDescription);
        TextView timeStamp = convertView.findViewById(R.id.entryTImeStamp);

        //set icon image
        switch (entry.getType()) {
            case LIIKUNTA:
                icon.setImageResource(R.drawable.ic_entry_exercise);
                break;
            case RUOKA:
                icon.setImageResource(R.drawable.ic_entry_food);
                break;
        }

        //set description text
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

    public void filter(long millis){
        ArrayList<CalendarEntry> match = new ArrayList<>();

        Calendar filter = Calendar.getInstance();
        filter.setTimeInMillis(millis);

        for(CalendarEntry entry : data){

            Calendar entrytime = Calendar.getInstance();
            entrytime.setTime(entry.getTime());

            if (entrytime.get(Calendar.YEAR) == filter.get(Calendar.YEAR) &&
                entrytime.get(Calendar.MONTH) == filter.get(Calendar.MONTH) &&
                entrytime.get(Calendar.DAY_OF_MONTH) == filter.get(Calendar.DAY_OF_MONTH))
            {
                match.add(entry);
            }
        }

        //save matches to sorted
        filtered = match;

        //sort from earliest to latest
        Collections.sort(filtered);

        notifyDataSetChanged();
    }
}