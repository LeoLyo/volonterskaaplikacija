package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.random.anagnosti.volonterskaaplikacija.R;

import java.util.Observable;
import java.util.Observer;

/**
 * Prva strana to jest fragment Create Event dela aplikacije. U ovom fragmentu se nalaze EditText-ovi u koje se upisuju trazene vrednosti, osnovni podaci o Event-u.
 */
public class CreateEventFragmentPage1 extends Fragment implements Observer{
    private static final String TAG = "CreateEventFragmentPage1";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText eventName, eventOrganiser, eventDescription;
    private Button confirmInputs;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateEventFragmentPage1() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CreateEventFragmentPage1 newInstance(String param1, String param2) {
        CreateEventFragmentPage1 fragment = new CreateEventFragmentPage1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Svaki EditText se inicijalizuje, kao i dugme i na pritisak dugmeta se pozove metoda za ubacivanje vrednosti iz EditText-ova u Singleton. Takodje se promeni status
     * svih EditText-ova na selected=false.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_createevent_page1, container, false);

        eventName = rootView.findViewById(R.id.createEventFragment1EditEventName);
        eventOrganiser = rootView.findViewById(R.id.createEventFragment1EditOrganiser);
        eventDescription = rootView.findViewById(R.id.createEventFragment1EditDescriptionOfEvent);
        confirmInputs = rootView.findViewById(R.id.createEventFragment1ConfirmInputsButton);
        confirmInputs.setText("Confirm Inputs");

        confirmInputs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertIntoSingleton();
                Toast.makeText(getActivity(), "Information successfully entered.", Toast.LENGTH_SHORT).show();
                eventName.setSelected(false);
                eventOrganiser.setSelected(false);
                eventDescription.setSelected(false);
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

    /**
     * Metoda u kojoj se vrednosti iz svih EditText polja ubacuju u Singleton Event-a.
     */
    public void insertIntoSingleton(){
        Singleton singleton = Singleton.Instance();
        singleton.eventName=eventName.getText().toString();
        singleton.organiserName=eventOrganiser.getText().toString();
        singleton.descriptionOfEvent=eventDescription.getText().toString();
        singleton.somethingDoneInEveryPart[0]=true;
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
