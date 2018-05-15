package com.random.anagnosti.volonterskaaplikacija;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class CreateEventActivity extends AppCompatActivity {

    private static final String TAG = "CreateEventActivity";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewp=(ViewPager) findViewById(R.id.createeventcontainer);
        mViewp.setAdapter(mSectionsPagerAdapter);

        TabLayout createEventTabLayout = (TabLayout)findViewById(R.id.createeventtablayout);
        createEventTabLayout.setupWithViewPager(mViewp);
        int counter = createEventTabLayout.getTabCount();
        for (int i=0;i<counter;i++){
            createEventTabLayout.getTabAt(i).setText("Part "+(i+1));
        }

    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.createeventcontainer);
        fragment.onActivityResult(requestCode, resultCode, data);
    }


}
