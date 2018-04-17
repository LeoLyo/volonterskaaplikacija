package com.random.anagnosti.volonterskaaplikacija;

import android.app.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.IOException;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class CreateEventActivity extends Activity {

    private static final int PLACE_PICKER_REQUEST = 1;
    private static final int CHOOSE_IMAGE = 101;

    private TextView eventLocation, eventImage, eventFirstDay, eventLastDay;
    private EditText eventName, eventOrganiser;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Uri uriEventImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);

        eventLocation = findViewById(R.id.textViewLocation);
        eventImage = findViewById(R.id.textViewEventImage);
        eventName = findViewById(R.id.editEventName);
        eventOrganiser = findViewById(R.id.editOrganiser);
        eventFirstDay = findViewById(R.id.textViewFirstDay);
        eventLastDay = findViewById(R.id.textViewLastDay);
    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void pickingALocation(View view){

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

    public void pickingAnImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Event Image"),CHOOSE_IMAGE);
    }

    public void pickingFirstDay(View view){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                String date ="Last day: " + dayOfMonth+"/"+monthOfYear+"/"+year;
                eventFirstDay.setText(date);
            }
        },year,month,day);
        datePickerDialog.show();
    }


    public void pickingLastDay(View view){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                String date ="Last day: " + dayOfMonth+"/"+monthOfYear+"/"+year;
                eventLastDay.setText(date);
            }
        },year,month,day);
        datePickerDialog.show();
    }


    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==PLACE_PICKER_REQUEST){
            if(resultCode==RESULT_OK){
                Place place = PlacePicker.getPlace(this,data);
                String locationAddress = String.format("Location address: %s",place.getAddress());
                String locationName = String.format("Location name: %s",place.getName());
                eventLocation.setText(locationName+" | "+locationAddress);
                
                uploadImageToFirebaseStorage();
            }
        }
        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriEventImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriEventImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
       // StorageReference eventImageReference = FirebaseStorage.getInstance().getReference("events/"+eventName+"_"+eventOrganiser+"_");
    }


}
