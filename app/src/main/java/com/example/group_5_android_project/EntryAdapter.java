package com.example.group_5_android_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView

public class EntryAdapter extends ArrayAdapter<CalendarEntry> {

    private final ArrayList<CalendarEntry> data;
    private ArrayList<CalendarEntry> filtered;

    public EntryAdapter(Context context, ArrayList<CalendarEntry> objects) {
        super(context, 0);
        data = objects;
        filtered = objects;
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

        ImageView icon = convertView.findViewById(R.id.entryIcon);
        TextView description = convertView.findViewById(R.id.entryDescription);

        //set icon image
        switch (entry.getType()) {
            case LIIKUNTA:
                icon.setImageResource(R.drawable.ic_entry_exercise);
                break;
            case RUOKA:
                icon.setImageResource(R.drawable.ic_entry_food);
                break;
        }

        description.setText(entry.toString());

        return convertView;
    }

    public void filter(long millis){
        long dayMillis = 86400000;

        ArrayList<CalendarEntry> match = new ArrayList<>();

        for(CalendarEntry entry : data){
            long entryTime = entry.getTime().getTime();

            boolean after = entryTime > millis;
            boolean before = entryTime < millis + dayMillis;

            if (before && after){
                match.add(entry);
            }
        }

        filtered = match;

        notifyDataSetChanged();
    }
}