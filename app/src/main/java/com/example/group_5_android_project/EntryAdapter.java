package com.example.group_5_android_project;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView

public class EntryAdapter extends ArrayAdapter<CalendarEntry> {

    private Activity activity;

    public EntryAdapter(Context context, List<CalendarEntry> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        CalendarEntry entry = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.entryview, parent, false);
        }

        ImageView icon = convertView.findViewById(R.id.entryIcon);
        TextView description = convertView.findViewById(R.id.entryDescription);

        //set icon image
        switch (entry.getType()){
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
}
