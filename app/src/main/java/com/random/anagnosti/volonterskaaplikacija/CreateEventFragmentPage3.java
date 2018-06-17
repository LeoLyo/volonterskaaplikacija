package com.random.anagnosti.volonterskaaplikacija;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateEventFragmentPage3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateEventFragmentPage3#newInstance} factory method to
 * create an instance of this fragment.
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventFragmentPage3.
     */
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_createevent_page3,container,false);
        Button refreshButton = (Button)rootView.findViewById(R.id.createeventpage3buttonRefresh);
        Button confirmButton = (Button)rootView.findViewById(R.id.createeventpage3buttonConfirm);

        Singleton singleton = Singleton.Instance();
        eventDayChildren=singleton.mEventDays;
        listview = (ListView) rootView.findViewById(R.id.createeventpage3listview);
        listview.setAdapter(new CreateEventPage3ListAdapter(getContext(),eventDayChildren ));




        refreshButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
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
                else{
                    if(tempNumber==numberOfChildDays){
                        Toast.makeText(getActivity(), "Refresh failed, number of days is the same.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Refresh failed, no date has been selected yet.", Toast.LENGTH_SHORT).show();
                    }
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


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


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
