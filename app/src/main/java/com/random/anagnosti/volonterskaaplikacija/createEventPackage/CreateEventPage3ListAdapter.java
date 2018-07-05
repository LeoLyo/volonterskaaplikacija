package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.random.anagnosti.volonterskaaplikacija.R;

import java.util.ArrayList;

/**
 * Adapter za listu koriscenju u fragmentu trecem, to jest klasi CreateEventFragmentPage3.
 */
public class CreateEventPage3ListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<EventDay> eventDays;
    private static LayoutInflater inflater = null;

    /**
     * Konstruktor koji preuzima kontekst, listu dana i LayoutInflater.
     */
    public CreateEventPage3ListAdapter(Context context, ArrayList<EventDay> eventDays){
        this.context=context;
        this.eventDays=eventDays;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Vraca se duzina liste dana.
     */
    @Override
    public int getCount() {
        return eventDays.size();
    }

    /**
     * Vracaju se svi dani u listi.
     */
    public ArrayList<EventDay> getAllDays(){
        return eventDays;
    }

    /**
     * Vraca se specifican dan iz liste.
     */
    @Override
    public Object getItem(int i) {
        return eventDays.get(i);
    }

    /**
     * Vraca se id odredjenog dana iz liste.
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Posle referenciranja vizuelnih elemenata, na klik slike koja predstavlja dugme za prihvacenje inputa u ovom specificnom fragmentu, uspesno se upisuju svi podaci iz odgovarajucih polja
     * u Singleton.
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = view;
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_child_craeteevent_page3,null);
        }
        final Singleton singleton = Singleton.Instance();
        TextView childIdOfDay = (TextView) rootView.findViewById(R.id.idofday);
        final EditText childTimeOfStart = (EditText) rootView.findViewById(R.id.timeofstart);
        final EditText childTimeOfEnd = (EditText) rootView.findViewById(R.id.timeofend);
        EditText childAttachADailyPlan = (EditText) rootView.findViewById(R.id.attachadailyplan);
        ImageView smallwhitepluss = rootView.findViewById(R.id.smallwhitepluss);
        final int position = i;
        final String temp = "Day "+(i+1)+" | "+singleton.dates.get(i);
        childIdOfDay.setText(temp);
        smallwhitepluss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleton.mEventDays.get(position).setTimeStart(childTimeOfStart.getText().toString());
                singleton.mEventDays.get(position).setTimeEnd(childTimeOfEnd.getText().toString());
                singleton.mEventDays.get(position).setFilled(true);
                singleton.mEventDays.get(position).setDate(singleton.dates.get(position));
                Toast.makeText(context, "Inputted "+temp+" successfully!", Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }
}
