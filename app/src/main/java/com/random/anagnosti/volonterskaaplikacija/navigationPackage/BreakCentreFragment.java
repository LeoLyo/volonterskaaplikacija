package com.random.anagnosti.volonterskaaplikacija.navigationPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.random.anagnosti.volonterskaaplikacija.R;

/**
 * Klasa koja jos nije implementovana. Sluzice prikazivanju liste svih prethodnih pauza koje je osoba uzela, kao i mogucnost trazenja nove pauze.
 */
public class BreakCentreFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_request_break,container,false);
    }
}
