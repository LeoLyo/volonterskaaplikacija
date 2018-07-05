package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.random.anagnosti.volonterskaaplikacija.R;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Treca strana to jest fragment Create Event dela aplikacije. U odnosu na unesene datume u prethodnom fragmentu ili te strani, ovde se kreira isti broj fragmenata kao broj dana i ubaci u listu.
 * Pored fragmenata u listi se nalazi  confirm Button koje potvrdjuje unose svih fragmenata.
 */
public class CreateEventFragmentPage3 extends Fragment implements Observer{
    private static final String TAG = "CreateEventFragmentPage3";


    private ListView listview;
    private ArrayList<EventDay> eventDayChildren=new ArrayList<>();



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private long numberOfChildDays;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateEventFragmentPage3() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateEventFragmentPage3 newInstance(String param1, String param2) {
        CreateEventFragmentPage3 fragment = new CreateEventFragmentPage3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numberOfChildDays=-68;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Desava se provera da li se neki dan Event-a nije popunilo. U slucaju da nije, ne obelezava se polje u Singleton-u da je page 3 spreman za registrovanje u bazu.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_createevent_page3,container,false);
        Button confirmButton = (Button)rootView.findViewById(R.id.createeventpage3buttonConfirm);

        final Singleton singleton = Singleton.Instance();
        eventDayChildren=singleton.mEventDays;
        listview = (ListView) rootView.findViewById(R.id.createeventpage3listview);
        listview.setAdapter(new CreateEventPage3ListAdapter(getContext(),eventDayChildren));


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check=true;
                for(int i=0;i<eventDayChildren.size();i++){
                    if(!eventDayChildren.get(i).isFilled()){
                        check=false;
                        break;
                    }
                }
                if(check){
                    Toast.makeText(getContext(), "All days entered successfully!", Toast.LENGTH_SHORT).show();
                    singleton.somethingDoneInEveryPart[2]=true;
                }else{
                    Toast.makeText(getContext(), "Some days haven't been filled. Please fill all days.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            long tempNumber=prefs.getLong("Page3MiniPageCount",-7);
            if(tempNumber!=numberOfMiniPages){
                Fragment frg = null;
                frg = getActivity().getSupportFragmentManager().findFragmentByTag("CreateEventFragmentPage3");
                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();
            }
        }
    }
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    /**
     * Na resume-ovanje fragmenta, refresh-uje se lista u slucaju da je Refresh-ovanje potrebno.
     */
    @Override
    public void onResume() {

        Singleton singleton = Singleton.Instance();
        long tempNumber = singleton.currentNumberOfDays;
        if(singleton.currentEventDaysChanged==true) {
            for (int i = 0; i < tempNumber; i++) {
                EventDay ev = new EventDay();
                singleton.mEventDays.add(ev);
            }
            listview.setAdapter(new CreateEventPage3ListAdapter(getContext(), eventDayChildren));
            singleton.currentEventDaysChanged=false;
            singleton.dateStartChanged=false;
            singleton.dateEndChanged=false;
            numberOfChildDays=tempNumber;
            Toast.makeText(getActivity(), "Refresh successful", Toast.LENGTH_SHORT).show();
        }

        super.onResume();
    }
}
