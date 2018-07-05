package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.random.anagnosti.volonterskaaplikacija.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * Cetvrta strana to jest fragment Create Event dela aplikacije. Odvija se kreiranje pozicija za ljude eventa koje ce se koristiti u petom fragmentu.
 * Svaka pozicija mora imati jedinstven naziv, moguce je dodati opis ali nije neophodno i svaka pozicija moze imati sledbenika, parent-child odnos, ukljucujuci i sa samim sobom.
 */
public class CreateEventFragmentPage4 extends Fragment implements Observer {
    private static final String TAG = "CreateEventFragmentPage4";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManager;
    private ArrayList<EventRole> eRoleList;
    private CreateEventPage4RecyclerAdapter eRoleAdapter;
    private Dialog popUpDialog;

    private Button confirmEditButton;
    private CardView createButton;
    private EditText titleEdit, descriptionEdit;
    private TextView subordinatesEdit;
    private ListView listOfAvailableRoles;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateEventFragmentPage4() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CreateEventFragmentPage4 newInstance(String param1, String param2) {
        CreateEventFragmentPage4 fragment = new CreateEventFragmentPage4();
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
     * Nakon referenciranja vizuelnih aspekata ovog fragmenta i inicijalizacije RecyclerView liste, na klik createButton-a se otvara popUpDialog sa prozorom za kreiranje role-a, to jest uloge
     * Event-a. Uloga se sastoji o naslova, opisa i sledbenika. Nakon popunjavanja i na pritisak dugmeta za zavrsavanje kreiranja, uspesno se kreira uloga ukoliko se unese bilo kakav naslov
     * uloge koji vec nije u listi, pa ubaci se u Singleton i prikaze se u RecyclerView listu u obliku CardView objekta.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_createevent_page4, container, false);
        createButton = rootView.findViewById(R.id.createEventPage4CreateButton);
        popUpDialog = new Dialog(this.getContext());

        final Singleton singleton = Singleton.Instance();
        eRoleList = singleton.mEventRoles;
        recyclerView = rootView.findViewById(R.id.createEventPage4RecyclerView);
        rLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(rLayoutManager);
        eRoleAdapter = new CreateEventPage4RecyclerAdapter(this.getActivity(), inflater.getContext(), eRoleList);
        recyclerView.setAdapter(eRoleAdapter);

        /*RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvClasses.setLayoutManager(layoutManager);
        rvClasses.setItemAnimator(new DefaultItemAnimator());
        classAdapter = new ClassAdapter(inflater.getContext(), selectedClasses);
        rvClasses.setAdapter(classAdapter);*/


        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                popUpDialog.setContentView(R.layout.fragment_child_createevent_page4_edit);

                confirmEditButton = popUpDialog.findViewById(R.id.createeventchildpage4confirmbutton);
                titleEdit = popUpDialog.findViewById(R.id.createeventchildpage4titleedit);
                descriptionEdit = popUpDialog.findViewById(R.id.createeventchildpage4descriptionedit);
                listOfAvailableRoles = popUpDialog.findViewById(R.id.createeventchildpage4listviewedit);
                final CreateEventPage4InnerCustomAdapter cAdapter = new CreateEventPage4InnerCustomAdapter(getActivity(), eRoleList);
                listOfAvailableRoles.setAdapter(cAdapter);
                if (listOfAvailableRoles.getScaleY() > 200) {
                    listOfAvailableRoles.setScaleY(200);
                }
                listOfAvailableRoles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        EventRole tempRole = eRoleList.get(i);

                        if (tempRole.isChecked()) {
                            tempRole.setChecked(false);
                        } else {
                            tempRole.setChecked(true);
                        }
                        eRoleList.set(i, tempRole);

                        cAdapter.updateRecords(eRoleList);
                    }
                });

                confirmEditButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Singleton singleton1 = Singleton.Instance();
                        String titleCheck = titleEdit.getText().toString();
                        if (titleCheck.matches("")) {
                            Toast.makeText(getContext(), "Title is empty. Please enter title.", Toast.LENGTH_SHORT).show();
                        } else {
                            boolean ifExists = false;
                            for (int i = 0; i < eRoleList.size(); i++) {
                                if (titleCheck.matches(eRoleList.get(i).getName())) {
                                    ifExists = true;
                                    break;
                                }
                            }
                            if (ifExists) {
                                Toast.makeText(getContext(), "A position with this title already exists. Please select another title.", Toast.LENGTH_SHORT).show();
                            } else {

                                EventRole newRole = new EventRole(titleCheck, singleton1.mEventRoles.size());
                                ArrayList<EventRole> selectedSubs = new ArrayList<EventRole>();
                                for (int j = 0; j < eRoleList.size(); j++) {
                                    if (eRoleList.get(j).isChecked()) {
                                        selectedSubs.add(eRoleList.get(j));
                                    }
                                }
                                newRole.setDescription(descriptionEdit.getText().toString());
                                newRole.setSubordinates(selectedSubs);
                                singleton1.mEventRoles.add(newRole);
                                eRoleList = singleton1.mEventRoles;
                                eRoleAdapter = new CreateEventPage4RecyclerAdapter(getActivity(), getContext(), eRoleList);
                                recyclerView.setAdapter(eRoleAdapter);
                                refreshRoleList(eRoleList);
                                popUpDialog.dismiss();
                            }
                        }
                    }
                });
                Objects.requireNonNull(popUpDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popUpDialog.show();
                singleton.somethingDoneInEveryPart[3]=true;
            }
        });

        return rootView;
    }

    /**
     * Zbog prirode koda za biranje sledbenika, one ostaju selektovane u slucaju ulaza u popUpDialog za kreiranje nove uloge. Ovom metodom resetujemo stanje svakog moguceg sledbenika i stavljamo
     * ih na "not selected".
     */
    private void refreshRoleList(ArrayList<EventRole> roleListForRefreshing) {
        for (int i = 0; i < roleListForRefreshing.size(); i++) {
            if (roleListForRefreshing.get(i).isChecked() == true) {
                roleListForRefreshing.get(i).setChecked(false);
            }
        }
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
