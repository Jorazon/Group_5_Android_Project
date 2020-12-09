package com.example.group_5_android_project;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        //set date picker to date selected in mainactivity calendar
        DatePicker datePicker = findViewById(R.id.newEntryDate);
        Intent intent = getIntent();
        long time = intent.getLongExtra("date",0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        datePicker.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void addEntry(View view){

        //get values from views and create and add new calendar entry
        String description = ((TextView) findViewById(R.id.newEntryDescription)).getText().toString();

        if(description.isEmpty()){
            //notify user if description is blank and cancel entry creation
            Toast.makeText(this,R.string.no_empty_description,Toast.LENGTH_SHORT).show();
            return;
        }

        EntryType newEntryType = EntryType.UNDEF;

        RadioGroup types = findViewById(R.id.newEntryType);

        int checked = types.getCheckedRadioButtonId();

        if (checked == R.id.radioExercise){
            newEntryType = EntryType.LIIKUNTA;
        }
        if (checked == R.id.radioFood){
            newEntryType = EntryType.RUOKA;
        }

        TimePicker timePicker = findViewById(R.id.newEntryTime);
        DatePicker datePicker = findViewById(R.id.newEntryDate);
        Calendar calendar = Calendar.getInstance();

        if(Build.VERSION.SDK_INT < 23){
            calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),timePicker.getCurrentHour(),timePicker.getCurrentMinute());
        } else {
            calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),timePicker.getHour(),timePicker.getMinute());
        }

        CalendarEntry newEntry = new CalendarEntry(newEntryType,calendar.getTime(),description);

        EntryList.getInstance().addEntry(newEntry);

        //return to mainactivity
        startActivity(new Intent(this,MainActivity.class));
    }
}