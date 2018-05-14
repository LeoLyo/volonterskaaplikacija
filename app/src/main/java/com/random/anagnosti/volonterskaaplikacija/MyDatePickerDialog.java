package com.random.anagnosti.volonterskaaplikacija;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MyDatePickerDialog implements View.OnClickListener{

    Context localContext;
    TextView localTextView;
    String dayName;

    public MyDatePickerDialog(Context context, TextView textView, String dayName){
        this.localContext = context;
        this.localTextView = textView;
        this.dayName=dayName;
    }

    @Override
    public void onClick(View view) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(localContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                String date =dayName + dayOfMonth+"/"+monthOfYear+"/"+year;
                localTextView.setText(date);
            }
        },year,month,day);
        datePickerDialog.show();
    }



}

