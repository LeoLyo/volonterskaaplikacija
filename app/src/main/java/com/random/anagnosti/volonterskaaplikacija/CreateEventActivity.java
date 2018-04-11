package com.random.anagnosti.volonterskaaplikacija;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import static android.content.ContentValues.TAG;

public class CreateEventActivity extends Activity {

    private int PLACE_PICKER_REQUEST = 1;

    private TextView eventLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);

        eventLocation = findViewById(R.id.textViewLocation);

    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void creatingAnEvent(View view){

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Intent intent;

        try {
            intent = builder.build(this);
            startActivityForResult(intent,PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            Log.e(TAG,"onClick: GooglePlayServicesNotAvailableException: "+e.getMessage());
        }
        /*
        try {
            startActivityForResult(builder.build(CreateEventActivity.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG,"onClick: GooglePlayServicesRepairableException: "+e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG,"onClick: GooglePlayServicesNotAvailableException: "+e.getMessage());

        }*/
    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==PLACE_PICKER_REQUEST){
            if(resultCode==RESULT_OK){
                Place place = PlacePicker.getPlace(this,data);
                String locationAddress = String.format("Location address: %s",place.getAddress());
                String locationName = String.format("Location name: %s",place.getName());
                eventLocation.setText(locationName+" | "+locationAddress);
            }
        }
    }



}
