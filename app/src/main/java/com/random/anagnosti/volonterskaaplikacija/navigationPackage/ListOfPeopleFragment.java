package com.random.anagnosti.volonterskaaplikacija.navigationPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.random.anagnosti.volonterskaaplikacija.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Klasa koja sluzi za prikaz listu ljudi eventa izvucenu iz baze.
 */
public class ListOfPeopleFragment extends Fragment {

    private TextView listOfPeopleTV;
    private RecyclerView recyclerView;
    private FirebaseFirestore mFirestore;
    private List<People> peopleList;
    private PeopleRecyclerAdapter peopleRecyclerAdapter;

    /**
     * Odlaskom u bazu, izvuce se za specifican event lista svih ljudi.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigation_list_of_people,container,false);

        peopleList = new ArrayList<>();
        peopleRecyclerAdapter = new PeopleRecyclerAdapter(rootView.getContext(),peopleList);

        listOfPeopleTV = rootView.findViewById(R.id.fragment_navigation_list_of_people_textview);

        recyclerView = rootView.findViewById(R.id.fragment_navigation_list_of_people_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setAdapter(peopleRecyclerAdapter);

        mFirestore=FirebaseFirestore.getInstance();

        mFirestore.collection("events").document("1FlSWkk9aD8j29vfnHgz").collection("people").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d(TAG,"Error: "+e.getMessage());
                }

                for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                    if(doc.getType()==DocumentChange.Type.ADDED){
                        People person = doc.getDocument().toObject(People.class).withId(doc.getDocument().getId());
                        peopleList.add(person);

                        peopleRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        return rootView;
    }
}
