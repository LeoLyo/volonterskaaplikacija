package com.random.anagnosti.volonterskaaplikacija.navigationPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.random.anagnosti.volonterskaaplikacija.R;

/**
 * Klasa koja jos nije implementovana. Sluzice prikazivanju liste obligacija korisnika, aktivnih, zavrsenih i onih koje jos nisu bile preuzete..
 */
public class MyObligationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_my_obligations,container,false);
    }
}
