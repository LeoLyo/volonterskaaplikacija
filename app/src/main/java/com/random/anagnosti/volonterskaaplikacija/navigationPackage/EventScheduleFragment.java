package com.random.anagnosti.volonterskaaplikacija.navigationPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.random.anagnosti.volonterskaaplikacija.R;


/**
 * Klasa koja jos nije implementovana. Sluzice prikazivanju plana i programa eventa.
 */
public class EventScheduleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_event_schedule,container,false);
    }
}
