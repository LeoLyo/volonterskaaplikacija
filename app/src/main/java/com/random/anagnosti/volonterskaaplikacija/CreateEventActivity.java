package com.random.anagnosti.volonterskaaplikacija;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class CreateEventActivity extends AppCompatActivity implements CreateEventFragmentPage1.OnFragmentInteractionListener,CreateEventFragmentPage2.OnFragmentInteractionListener,CreateEventFragmentPage3.OnFragmentInteractionListener{

    private static final String TAG = "CreateEventActivity";

    //private SectionsPagerAdapter mSectionsPagerAdapter;
    //private ViewPager mViewp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);

        TabLayout createEventTabLayout = (TabLayout)findViewById(R.id.createeventtablayout);
        //createEventTabLayout.setupWithViewPager(mViewp);
        //int counter = createEventTabLayout.getTabCount();
        for (int i=0;i<3;i++){
           createEventTabLayout.addTab(createEventTabLayout.newTab().setText("Part "+(i+1)));
        }
        createEventTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager mViewp=(ViewPager) findViewById(R.id.createeventcontainer);
        final SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),createEventTabLayout.getTabCount());
        mViewp.setAdapter(mSectionsPagerAdapter);
        mViewp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(createEventTabLayout));

        createEventTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.createeventcontainer);
        fragment.onActivityResult(requestCode, resultCode, data);
    }*/


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
