package com.random.anagnosti.volonterskaaplikacija.navigationPackage;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.random.anagnosti.volonterskaaplikacija.R;

public class MainNavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private TextView eventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main);

        Toolbar toolbar = findViewById(R.id.activity_navigation_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.activity_navigation_main);

        NavigationView navigationView = findViewById(R.id.activity_navigation_nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_navigation_fragment_container, new MyObligationsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_my_obligations);
        }


       String eventId = getIntent().getStringExtra("EXTRA_EVENT_ID");
       //String eventId = "1FlSWkk9aD8j29vfnHgz";
        eventTitle = findViewById(R.id.activity_navigation_textView);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("events").document(eventId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document != null) {
                        eventTitle.setText(document.getString("event_name"));
                    }
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_list_of_people:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_navigation_fragment_container,new ListOfPeopleFragment()).commit();
                break;

            case R.id.nav_about_event:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_navigation_fragment_container,new AboutEventFragment()).commit();
                break;

            case R.id.nav_event_schedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_navigation_fragment_container,new EventScheduleFragment()).commit();
                break;
            case R.id.nav_my_obligations:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_navigation_fragment_container,new MyObligationsFragment()).commit();
                break;
            case R.id.nav_assign_obligations:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_navigation_fragment_container,new AssignObligationsFragment()).commit();
                break;
            case R.id.nav_break_centre:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_navigation_fragment_container,new BreakCentreFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
