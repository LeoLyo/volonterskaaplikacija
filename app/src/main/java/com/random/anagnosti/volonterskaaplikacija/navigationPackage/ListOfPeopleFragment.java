package com.random.anagnosti.volonterskaaplikacija.navigationPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.random.anagnosti.volonterskaaplikacija.R;

public class ListOfPeopleFragment extends Fragment {

    TextView listOfPeopleTV;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigation_list_of_people,container,false);

        listOfPeopleTV = rootView.findViewById(R.id.fragment_navigation_list_of_people_textview);


        return rootView;
    }
}
