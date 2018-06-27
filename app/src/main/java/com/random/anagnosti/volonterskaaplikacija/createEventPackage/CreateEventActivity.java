package com.random.anagnosti.volonterskaaplikacija.createEventPackage;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.random.anagnosti.volonterskaaplikacija.R;


public class CreateEventActivity extends AppCompatActivity implements CreateEventFragmentPage1.OnFragmentInteractionListener,CreateEventFragmentPage2.OnFragmentInteractionListener,CreateEventFragmentPage3.OnFragmentInteractionListener,CreateEventFragmentPage4.OnFragmentInteractionListener,CreateEventFragmentPage5.OnFragmentInteractionListener{

    private static final String TAG = "CreateEventActivity";

    Singleton singleton = Singleton.Instance();
    SectionsPagerAdapter mSectionsPagerAdapter;
    ImageView doneCreatingEventIcon;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);

        TabLayout createEventTabLayout = (TabLayout)findViewById(R.id.createeventtablayout);
        //createEventTabLayout.setupWithViewPager(mViewp);
        //int counter = createEventTabLayout.getTabCount();
        for (int i=0;i<5;i++){
           createEventTabLayout.addTab(createEventTabLayout.newTab().setText("Part "+(i+1)));
        }
        createEventTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager mViewp=(ViewPager) findViewById(R.id.createeventcontainer);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),createEventTabLayout.getTabCount());
        mViewp.setAdapter(mSectionsPagerAdapter);
        mViewp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(createEventTabLayout));
        createEventTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewp.setCurrentItem(tab.getPosition());
                Fragment fragment = ((SectionsPagerAdapter)mViewp.getAdapter()).getFragment(tab.getPosition());
                if(tab.getPosition()==2 && fragment != null){
                    fragment.onResume();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        doneCreatingEventIcon=findViewById(R.id.doneCreatingEventIcon);
        doneCreatingEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean everythingOk=true;
                for(int i=0;i<singleton.somethingDoneInEveryPart.size();i++){
                    if(!singleton.somethingDoneInEveryPart.get(i)){
                        everythingOk=false;
                    }
                }
                if(everythingOk){
                    uploadAndCreateEvent();
                }
            }
        });

    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~




    @Override
    protected void onDestroy() {
        super.onDestroy();
        singleton.destroyS();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void updateFragments(){
        mSectionsPagerAdapter.updateFragments();
    }

    private void uploadAndCreateEvent(){
        //Upload Part1

        //Upload Part2

        //Upload Part3

        //Upload Part4

        //Upload Part5

        //Upload Part6

    }


}
