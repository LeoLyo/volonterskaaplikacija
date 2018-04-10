package com.random.anagnosti.volonterskaaplikacija;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    ListView listView;
    String[] peopleList = {"Osoba 1", "Osoba2", "Osoba3", "Osoba4"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        listView = findViewById(R.id.listoftasks);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.activity_listview,R.id.textView,peopleList);
        listView.setAdapter(arrayAdapter);
    }



    public void showAction(View view){
        if(listView.getVisibility()==View.GONE) {
            listView.setVisibility(View.VISIBLE);
        }else if(listView.getVisibility()==View.VISIBLE){
            listView.setVisibility(View.GONE);
        }

    }

}
