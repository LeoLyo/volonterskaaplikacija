package com.random.anagnosti.volonterskaaplikacija.createEventPackage;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Adapter za stranice CreateEvent dela, to jest sve fragmente. Ovde se fragmenti smenjuju u zavisnosti od toga koji se prikazuje na ekranu.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    int numberOfTabs;
    private Observable mObservers = new FragmentObserver();
    private Map<Integer,String> mFragmentTabs;
    private FragmentManager mFragmentManager;

    /**
     * Konstruktor za ovaj stranicni adapter sa brojem tabova i fragment managerom.
     */
    public SectionsPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs=numberOfTabs;
        mFragmentTabs = new HashMap<Integer,String>();
        mFragmentManager=fm;
    }

    /**
     * Metoda u kojoj se u odnosu na poziciju bira odredjen fragment. Ako pozicija prevazilazi broj fragmenata, vraca se null.
     */
    @Override
    public Fragment getItem(int position) {
        //mObservers.deleteObservers();//Clear existing observers
        // getItem is called to instantiate the fragment for the given page.
        switch(position){
            case 0:
                CreateEventFragmentPage1 tab1 = new CreateEventFragmentPage1();
                if(tab1 instanceof Observer){
                    mObservers.addObserver((Observer) tab1);
                }
                return tab1;
            case 1:
                CreateEventFragmentPage2 tab2 = new CreateEventFragmentPage2();
                if(tab2 instanceof Observer){
                    mObservers.addObserver((Observer) tab2);
                }
                return tab2;
            case 2:
                CreateEventFragmentPage3 tab3 = new CreateEventFragmentPage3();
                if(tab3 instanceof Observer){
                    mObservers.addObserver((Observer) tab3);
                }
                return tab3;
            case 3:
                CreateEventFragmentPage4 tab4 = new CreateEventFragmentPage4();
                if(tab4 instanceof Observer){
                    mObservers.addObserver((Observer) tab4);
                }
                return tab4;
            case 4:
                CreateEventFragmentPage5 tab5 = new CreateEventFragmentPage5();
                if(tab5 instanceof Observer){
                    mObservers.addObserver((Observer) tab5);
                }
                return tab5;
            default:
                return null;
        }

    }


    /**
     * Vraca se ukupan broj tabova, to jest fragmenata ili te stranica.
     */
    @Override
    public int getCount() {
        return numberOfTabs;
    }

    /**
     * Obavestavaju se observeri.
     */
    public void updateFragments(){
        mObservers.notifyObservers();
    }

    /**
     * Instancira se specifican fragment.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container,position);
        if(obj instanceof Fragment){
            //record the fragment tag here.
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            mFragmentTabs.put(position,tag);
        }
        return obj;
    }

    /**
     * Preuzima se fragment.
     */
    public Fragment getFragment(int position){
        String tag = mFragmentTabs.get(position);
        if(tag==null){
            return null;
        }
        return mFragmentManager.findFragmentByTag(tag);
    }
}

