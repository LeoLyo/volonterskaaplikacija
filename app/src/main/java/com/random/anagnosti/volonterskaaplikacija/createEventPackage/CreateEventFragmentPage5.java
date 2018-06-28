package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.random.anagnosti.volonterskaaplikacija.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateEventFragmentPage5.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateEventFragmentPage5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventFragmentPage5 extends Fragment implements Observer{
    private static final String TAG = "CreateEventFragmentPage5";

    private FloatingActionButton CEP5FAB;
    private Dialog popUpDialog;
    private ArrayList<EventPerson> allThePeople;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManager;
    private CreateEventPage5RecyclerAdapter eRoleAdapter;

    private Button editConfirmButton;
    private EditText editEmailText;
    private Spinner editRoleSpinner, editParentSpinner;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateEventFragmentPage5() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventFragmentPage5.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventFragmentPage5 newInstance(String param1, String param2) {
        CreateEventFragmentPage5 fragment = new CreateEventFragmentPage5();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_createevent_page5, container, false);
        CEP5FAB = rootView.findViewById(R.id.createEventPage5FloatingActionButton);
        popUpDialog = new Dialog(this.getContext());

        final Singleton singleton = Singleton.Instance();
        allThePeople=singleton.mEventPeople;
        recyclerView = rootView.findViewById(R.id.createEventPage5RecyclerView);
        rLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(rLayoutManager);
        eRoleAdapter = new CreateEventPage5RecyclerAdapter(this.getActivity(),this.getContext(), allThePeople);
        recyclerView.setAdapter(eRoleAdapter);

        CEP5FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpDialog.setContentView(R.layout.fragment_child_createevent_page5_edit);
                editEmailText = popUpDialog.findViewById(R.id.createeventchildpage5editedittext);
                editRoleSpinner = popUpDialog.findViewById(R.id.createeventchildpage5editspinner);
                editConfirmButton = popUpDialog.findViewById(R.id.createeventchildpage5editbutton);
                editParentSpinner = popUpDialog.findViewById(R.id.createeventchildpage5editparentspinner);

                ArrayAdapter<EventRole> spinnerAdapter = new ArrayAdapter<EventRole>(getContext(),android.R.layout.simple_spinner_item,singleton.mEventRoles);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                editRoleSpinner.setAdapter(spinnerAdapter);

                editRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        EventRole spinnerRole = (EventRole) editRoleSpinner.getSelectedItem();
                        ArrayList<EventPerson> spinnerListOfPeople = new ArrayList<>();
                        spinnerListOfPeople.add(new EventPerson("nobody@nonono.com",new EventRole("Nothing",-8),0));
                        for(int k=0;k<singleton.mEventRoles.size();k++){
                            for(int z=0;z<singleton.mEventRoles.get(k).getSubordinates().size();z++){
                                if(singleton.mEventRoles.get(k).getSubordinates().get(z).equals(spinnerRole)){
                                    for(int t=0;t<allThePeople.size();t++){
                                        if(allThePeople.get(t).getRoleOfIndividual().equals(singleton.mEventRoles.get(k)))
                                            spinnerListOfPeople.add(allThePeople.get(t));

                                    }
                                }
                            }
                        }
                        if(spinnerListOfPeople.isEmpty()){

                            ArrayList<String> nothing = new ArrayList<>();
                            nothing.add("No parent.");
                            ArrayAdapter<String> newSpinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,nothing);
                            newSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            editParentSpinner.setAdapter(newSpinnerAdapter);
                        }else{

                            ArrayAdapter<EventPerson> newSpinnerAdapter = new ArrayAdapter<EventPerson>(getContext(),android.R.layout.simple_spinner_item,spinnerListOfPeople);
                            newSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            editParentSpinner.setAdapter(newSpinnerAdapter);
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                editConfirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String emailCheck = editEmailText.getText().toString();
                        if (emailCheck.matches("")) {
                            Toast.makeText(getContext(), "Email is empty. Please enter an email.", Toast.LENGTH_SHORT).show();
                        }else if(!isEmailValid(emailCheck)){
                            Toast.makeText(getContext(), "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                        }else{
                            boolean ifExists = false;
                            for (int i = 0; i < singleton.mUsedEmails.size(); i++) {
                                if (emailCheck.matches(singleton.mUsedEmails.get(i))) {
                                    ifExists = true;
                                    break;
                                }
                            }
                            if (ifExists) {
                                Toast.makeText(getContext(), "This email has already been entered in the list of people.", Toast.LENGTH_SHORT).show();
                            }else{
                                EventRole selectedRole = (EventRole) editRoleSpinner.getSelectedItem();
                                EventPerson selectedParent = (EventPerson) editParentSpinner.getSelectedItem();
                                EventPerson newPerson = new EventPerson(emailCheck,selectedRole,0,selectedParent);
                                singleton.mEventPeople.add(newPerson);
                                singleton.mUsedEmails.add(emailCheck);
                                allThePeople=singleton.mEventPeople;
                                Toast.makeText(getContext(), "Number of people: "+allThePeople.size(), Toast.LENGTH_SHORT).show();
                                eRoleAdapter = new CreateEventPage5RecyclerAdapter(getActivity(), getContext(), allThePeople);
                                recyclerView.setAdapter(eRoleAdapter);

                                popUpDialog.dismiss();
                            }
                        }

                    }
                });
                Objects.requireNonNull(popUpDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popUpDialog.show();
                singleton.somethingDoneInEveryPart[4]=true;
            }
        });


        return rootView;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
}
