package com.random.anagnosti.volonterskaaplikacija;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class PlaceholderFragment extends Fragment {


    private static final String TAG = "PlaceholderFragment";


    private static final int PLACE_PICKER_REQUEST = 101;
    private static final int CHOOSE_IMAGE_REQUEST = 102;

    //Second page elements
    TextView createEventFirstDay;
    TextView createEventLastDay;
    TextView createEventLocation;
    TextView createEventImageView;
    ImageView eventImageView;


    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        if (getArguments().getInt(ARG_SECTION_NUMBER)==1){
            rootView = inflater.inflate(R.layout.fragment_createevent_page1, container, false);
            EditText eventName =(EditText) rootView.findViewById(R.id.editEventName);
            EditText eventOrganiser =(EditText) rootView.findViewById(R.id.editOrganiser);
            EditText eventDescription =(EditText) rootView.findViewById(R.id.editTextDescriptionOfEvent);

        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
            rootView = inflater.inflate(R.layout.fragment_createevent_page2, container, false);
            createEventFirstDay = (TextView) rootView.findViewById(R.id.textViewFirstDay);
            createEventLastDay = (TextView) rootView.findViewById(R.id.textViewLastDay);
            createEventLocation = (TextView) rootView.findViewById(R.id.textViewLocation);
            createEventImageView = (TextView) rootView.findViewById(R.id.textViewEventImage);
            eventImageView =(ImageView) rootView.findViewById(R.id.imageViewEventImage);

            createEventFirstDay.setOnClickListener(new MyDatePickerDialog(getContext(),createEventFirstDay,"First day: "));
            createEventLastDay.setOnClickListener(new MyDatePickerDialog(getContext(),createEventLastDay, "Last day: "));
            /*createEventLocation.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    Intent intent;
                    try {
                        intent = builder.build((Activity) getActivity().getApplicationContext());
                        intent.setFlags(0);
                        startActivityForResult(intent, PLACE_PICKER_REQUEST);


                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
            });*/
            createEventLocation.setOnClickListener(new Dispenser(getActivity(),createEventLocation,getContext(),101));
            createEventImageView.setOnClickListener(new Dispenser(getActivity(), createEventImageView,eventImageView,102));

            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        }

        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==PLACE_PICKER_REQUEST){
            if(resultCode==RESULT_OK){
                Place place = PlacePicker.getPlace(this.getContext(),data);
                String locationAddress = String.format("Location address: %s",place.getAddress());
                String locationName = String.format("Location name: %s",place.getName());
                String combined = locationName+System.getProperty("line.separator")+locationAddress;
                createEventLocation.setText(combined);
                Toast.makeText(this.getContext(),"1+ "+ combined, Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == CHOOSE_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uriEventImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(),uriEventImage);
                //setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}