package com.random.anagnosti.volonterskaaplikacija;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    int numberOfTabs;

    public SectionsPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs=numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch(position){
            case 0:
                CreateEventFragmentPage1 tab1 = new CreateEventFragmentPage1();
                return tab1;
            case 1:
                CreateEventFragmentPage2 tab2 = new CreateEventFragmentPage2();
                return tab2;
            case 2:
                CreateEventFragmentPage3 tab3 = new CreateEventFragmentPage3();
                return tab3;
            default:
                return null;
        }

    }


    @Override
    public int getCount() {
        // Show 3 total pages.
        return numberOfTabs;
    }
}

