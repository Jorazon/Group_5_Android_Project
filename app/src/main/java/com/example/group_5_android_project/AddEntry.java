package com.example.group_5_android_project;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

/**
 * Activity for handling adding new entries.
 * @author Oskari Pahkala
 */

public class AddEntry extends AppCompatActivity {

    /**
     * Sets DatePicker initial date to the one selected in {@link MainActivity} CalendarView that was passed in the Intent.
     * @param savedInstanceState the activity's previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        //set DatePicker to date selected in MainActivity CalendarView
        DatePicker datePicker = findViewById(R.id.newEntryDate);
        Intent intent = getIntent();
        long time = intent.getLongExtra("date",0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        datePicker.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Creates a new {@link CalendarEntry} based on values entered to views in this activity and adds it to {@link EntryList} before returning to {@link MainActivity}.
     * @param view view reference from the view the method is attached to
     */
    public void addEntry(View view){

        //get values from views and create and add new calendar entry
        String description = ((TextView) findViewById(R.id.newEntryDescription)).getText().toString();

        if(description.isEmpty()){
            //notify user if description is blank and cancel entry creation
            Toast.makeText(this,R.string.no_empty_description,Toast.LENGTH_SHORT).show();
            return;
        }

        //initialize entry type to undefined
        EntryType newEntryType = EntryType.UNDEF;
        //get reference to RadioGroup
        RadioGroup types = findViewById(R.id.newEntryType);
        //get id of checked radiobutton
        int checked = types.getCheckedRadioButtonId();
        //set entry type based on selected RadioButton
        if (checked == R.id.radioExercise){
            newEntryType = EntryType.LIIKUNTA;
        }
        if (checked == R.id.radioFood){
            newEntryType = EntryType.RUOKA;
        }
        //get references to time and date pickers
        TimePicker timePicker = findViewById(R.id.newEntryTime);
        DatePicker datePicker = findViewById(R.id.newEntryDate);
        //get Calendar instance
        Calendar calendar = Calendar.getInstance();

        //set calendar in SDK version preferred way
        if(Build.VERSION.SDK_INT < 23){
            calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),timePicker.getCurrentHour(),timePicker.getCurrentMinute());
        } else {
            calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),timePicker.getHour(),timePicker.getMinute());
        }

        //save selected date to SharedPreferences
        FileIO.saveMillis(this, calendar.getTimeInMillis());

        //create new CalendarEntry object
        CalendarEntry newEntry = new CalendarEntry(newEntryType,calendar.getTime(),description);

        //add the new CalendarEntry to EntryList
        EntryList.getInstance().addEntry(newEntry);

        //return to MainActivity
        startActivity(new Intent(this,MainActivity.class));
    }
}