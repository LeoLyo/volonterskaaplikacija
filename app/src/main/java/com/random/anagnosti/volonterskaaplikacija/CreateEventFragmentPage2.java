package com.random.anagnosti.volonterskaaplikacija;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateEventFragmentPage2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateEventFragmentPage2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventFragmentPage2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int PLACE_PICKER_REQUEST = 101;
    private static final int CHOOSE_IMAGE_REQUEST = 102;

    private String firstDayDateArray;
    private String lastDayDateArray;
    private long dayOfEventCounter;

    TextView createEventFirstDay;
    TextView createEventLastDay;
    TextView createEventLocation;
    TextView createEventImageView;
    ImageView eventImageView;
    Button createEventButtonConfirmInputsPage2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;


    private OnFragmentInteractionListener mListener;

    public CreateEventFragmentPage2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventFragmentPage2.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventFragmentPage2 newInstance(String param1, String param2) {
        CreateEventFragmentPage2 fragment = new CreateEventFragmentPage2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firstDayDateArray="";
        lastDayDateArray="";
        dayOfEventCounter=-7;

        sharedpreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_createevent_page2, container, false);

        createEventFirstDay = (TextView) rootView.findViewById(R.id.textViewFirstDay);
        createEventLastDay = (TextView) rootView.findViewById(R.id.textViewLastDay);
        createEventLocation = (TextView) rootView.findViewById(R.id.createEventTextViewLocation);
        createEventImageView = (TextView) rootView.findViewById(R.id.textViewEventImage);
        eventImageView =(ImageView) rootView.findViewById(R.id.imageViewEventImage);
        createEventButtonConfirmInputsPage2 = (Button) rootView.findViewById(R.id.buttonconfirminputspage2);


        createEventFirstDay.setOnClickListener(new MyDatePickerDialog(getContext(),createEventFirstDay,67));
        createEventLastDay.setOnClickListener(new MyDatePickerDialog(getContext(),createEventLastDay, 76));
        createEventLocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent = builder.build(getActivity());
                    //intent.setFlags(0);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);


                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        createEventImageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Choosing an image from storage", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select EventDay Image"),CHOOSE_IMAGE_REQUEST);
            }
        });
        createEventButtonConfirmInputsPage2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                firstDayDateArray= createEventFirstDay.getText().toString();
                lastDayDateArray=createEventLastDay.getText().toString();

                String[] firstSplit = firstDayDateArray.split(":");
                String[] lastSplit = lastDayDateArray.split(":");
                firstDayDateArray=firstSplit[1];
                lastDayDateArray=lastSplit[1];

                long diff = -1;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd|MM|yyyy");
                try {
                    Date dateStart = simpleDateFormat.parse(firstDayDateArray);
                    Date dateEnd = simpleDateFormat.parse(lastDayDateArray);
                    //time is always 00:00:00 so rounding should help to ignore missing hour when going from winter to summer time as well as the extra hour in the other direction
                    diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
                    diff++;
                    dayOfEventCounter=diff;
                    if(dayOfEventCounter<0){
                        dayOfEventCounter=-1;
                        Toast.makeText(getActivity(), "Invalid dates: please make sure the last day is after the first day of the event.", Toast.LENGTH_SHORT).show();
                    }else{

                        Singleton singleton = Singleton.Instance();
                        singleton.currentNumberOfDays=dayOfEventCounter;

                        //singleton.mEventDays.remove(2);
                        //singleton.mEventDays.add(ev);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }



                //Ovo je kod za fragment
/*

//                EventDay ev = new EventDay("15.2", "123", "naziv");
//                singleton.mEventDays.add(ev);
                for(EventDay voja : singleton.mEventDays){
                    voja.getTimeStart();
                    voja.getTimeEnd();
                            //taj textbox.text= voja.getTimeEnd();
                }


               /* Toast.makeText(getActivity(), "StartDate: "+firstDayDateArray, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "EndDate: "+lastDayDateArray, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "EventDay No. of days: "+dayOfEventCounter, Toast.LENGTH_SHORT).show();*/


            }
        });
        return rootView;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==PLACE_PICKER_REQUEST){
            if(resultCode==RESULT_OK){
                Place place = PlacePicker.getPlace(this.getContext(),data);
                String locationAddress = String.format("Location address: %s",place.getAddress());
               //Toast.makeText(this.getContext(), "LOCATIONADDRESS: "+locationAddress, Toast.LENGTH_SHORT).show();
                String locationName = String.format("Location name: %s",place.getName());
               //Toast.makeText(this.getContext(), "LOCATIONNAME: "+locationName, Toast.LENGTH_SHORT).show();
                String combined = locationName+System.getProperty("line.separator")+locationAddress;
                createEventLocation.setText(combined);
                //Toast.makeText(this.getContext(),"LOCATIONTESTING: "+ combined, Toast.LENGTH_SHORT).show();
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


    private void uploadImageToFirebaseStorage() {
        // StorageReference eventImageReference = FirebaseStorage.getInstance().getReference("events/"+eventName+"_"+eventOrganiser+"_");
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
