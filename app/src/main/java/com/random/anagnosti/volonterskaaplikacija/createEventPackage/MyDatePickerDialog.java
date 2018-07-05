package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Klasa za odabir datume u DatePicker-u i njihovo modifikovanje.
 */
public class MyDatePickerDialog implements View.OnClickListener{

    Context localContext;
    TextView localTextView;
    int dayE;

    /**
     * Konstruktor za preuzimanje konteksta, TextView vizuelne komponente u koju ce se smestati tekst i jedinstven broj dana po kome se razlikuje prvi dan od poslednjeg.
     */
    public MyDatePickerDialog(Context context, TextView textView,int dayE){

        this.localContext = context;
        this.localTextView = textView;
        this.dayE = dayE;
    }

    /**
     * Nakon odabira datuma, proveri se o kom je danu rec. U zavisnosti od izabranog dana se rezultujuci string formatira na odredjen nacin.
     */
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

                Singleton singleton= Singleton.Instance();
                if(dayE==67){
                    String date ="First day:"+dayFormated;
                    localTextView.setText(date);
                    singleton.dateStartChanged=true;

                }else if(dayE==76){
                    String date ="Last day:"+dayFormated;
                    localTextView.setText(date);
                    singleton.dateEndChanged=true;
                }
            }
        },year,month,day);
        datePickerDialog.show();
    }




}

