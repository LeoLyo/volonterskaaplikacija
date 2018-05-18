package com.random.anagnosti.volonterskaaplikacija;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MyDatePickerDialog implements View.OnClickListener{

    Context localContext;
    TextView localTextView;
    int dayE;

    public MyDatePickerDialog(Context context, TextView textView,int dayE){

        this.localContext = context;
        this.localTextView = textView;
        this.dayE = dayE;
    }

    @Override
    public void onClick(View view) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(localContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                String dayFormated=dayOfMonth+"|"+monthOfYear+"|"+year;


                if(dayE==67){
                    String date ="First day:"+dayFormated;
                    localTextView.setText(date);

                }else if(dayE==76){
                    String date ="Last day:"+dayFormated;
                    localTextView.setText(date);
                }
            }
        },year,month,day);
        datePickerDialog.show();
    }




}

