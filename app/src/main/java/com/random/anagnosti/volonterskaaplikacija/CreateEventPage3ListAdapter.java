package com.random.anagnosti.volonterskaaplikacija;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CreateEventPage3ListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<EventDay> eventDays=new ArrayList<>();
    private static LayoutInflater inflater = null;

    public CreateEventPage3ListAdapter(Context context, ArrayList<EventDay> eventDays){
        this.context=context;
        this.eventDays=eventDays;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return eventDays.size();
    }

    @Override
    public Object getItem(int i) {
        return eventDays.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = view;
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_child_craeteevent_page3,null);
        }

        TextView childIdOfDay = (TextView) rootView.findViewById(R.id.idofday);
        EditText childTimeOfStart = (EditText) rootView.findViewById(R.id.timeofstart);
        EditText childTimeOfEnd = (EditText) rootView.findViewById(R.id.timeofend);
        EditText childAttachADailyPlan = (EditText) rootView.findViewById(R.id.attachadailyplan);
        EventDay currentDay = eventDays.get(i);
        String temp = "Day "+(i+1);
        childIdOfDay.setText(temp);
        return rootView;
    }
}
