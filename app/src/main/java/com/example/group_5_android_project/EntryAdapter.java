package com.example.group_5_android_project;

import android.content.Context;
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

//https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView

public class EntryAdapter extends ArrayAdapter<CalendarEntry> implements Filterable {

    private final List<CalendarEntry> data;
    private List<CalendarEntry> filtered;

    private EntryFilter filter;

    public EntryAdapter(Context context, List<CalendarEntry> objects) {
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
        return position;
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

    @Override
    public Filter getFilter() {
        if(filter == null)
        {
            filter=new EntryFilter();
        }

        return filter;
    }

    private class EntryFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();

            if(constraint != null && constraint.length()>0) {

                ArrayList<CalendarEntry> resultingEntries = new ArrayList<>();

                long msInDay = 86400000;

                long date = Long.parseLong((String) constraint);

                Date selected = new Date(date - 1);
                Date selectedPlus1 = new Date(date + msInDay);

                for (CalendarEntry entry : data) {
                    Date time = entry.getTime();
                    if(time.after(selected) && time.before(selectedPlus1)){
                        resultingEntries.add(entry);
                    }
                }

                result.count = resultingEntries.size();
                result.values = resultingEntries;
            }
            else{
                result.count = data.size();
                result.values = data;
            }

            return result;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtered = (List<CalendarEntry>) results.values;
            notifyDataSetChanged();
        }
    }
}