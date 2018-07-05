package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.google.firebase.firestore.GeoPoint;
import com.random.anagnosti.volonterskaaplikacija.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import static android.app.Activity.RESULT_OK;

/**
 * Prva strana to jest fragment Create Event dela aplikacije. Za razliku od prethodne stranice, ovde je svaki TextView clickable i predstavlja link ka necemu drugom:
 * biranje datuma, lokacije i odabir slike iz memorije.
 */
public class CreateEventFragmentPage2 extends Fragment implements Observer{
    private static final String TAG = "CreateEventFragmentPage2";

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


    // TODO: Rename and change types and number of parameters
    public static CreateEventFragmentPage2 newInstance(String param1, String param2) {
        CreateEventFragmentPage2 fragment = new CreateEventFragmentPage2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Promenljive firstDayDateArray, lastDayDateArray i dayOfEventCounter  se resetuju.
     * */
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

    /**
     * Nakon referenciranja svih TextView-ova, ImageView-a i Button-a, pozivaju se listener-i za TextView-ove zaduzene za datum i svaki listener kreira novu MyDatePickerDialog klasu.
     * Na pritisak TextView-a za lokaciju, kreira se PlacePicker builder i poziva se isti kao nov activity. Takodje se salje PLACE_PICKER_REQUEST vrednost u onActivityResult preko resultCode promenljive.
     * Na pritisak TextView-a za Image, setuje se tip novostvorenog intent-a i otvara se prozor za odabir slike iz memorije. Takodje se, kao za lokaciju, salje vrednost CHOOSE_IMAGE_REQUEST
     * u onActivityREsult preko resultCode promenljive.
     * Na pritisak dugmeta za potvrdjivanje svih unosa, proverava se da li su datumi validni (prvi je datum manji od drugog: ne sme datum pocetka Event-a da se desi nakon zavrsavanja istog).
     * Ako nisu, onda se ispisuju odgovarajuci ispisi. koristeci SimpleDateFormat i Date, datumi se formatuju, izracuna se koliko dana traje sam event i vrednosti se ubace u Singleton Event-a.
     */
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
                if(firstSplit.length==1){
                    Toast.makeText(getContext(), "Please select first day of event.", Toast.LENGTH_SHORT).show();
                }else if(lastSplit.length==1){
                    Toast.makeText(getContext(), "Please select last day of event.", Toast.LENGTH_SHORT).show();
                }else {


                    firstDayDateArray = firstSplit[1];
                    lastDayDateArray = lastSplit[1];

                    long diff = -1;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd|MM|yyyy");
                    try {
                        Date dateStart = simpleDateFormat.parse(firstDayDateArray);
                        Date dateEnd = simpleDateFormat.parse(lastDayDateArray);
                        //time is always 00:00:00 so rounding should help to ignore missing hour when going from winter to summer time as well as the extra hour in the other direction
                        diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
                        diff++;
                        dayOfEventCounter = diff;
                        if (dayOfEventCounter < 0) {
                            dayOfEventCounter = -1;
                            Toast.makeText(getActivity(), "Invalid dates: please make sure the last day is after the first day of the event.", Toast.LENGTH_SHORT).show();
                        } else {

                            Singleton singleton = Singleton.Instance();
                            singleton.currentNumberOfDays = dayOfEventCounter;
                            if (singleton.dateStartChanged && singleton.dateEndChanged) {
                                singleton.currentEventDaysChanged = true;
                            }

                            Calendar calS = Calendar.getInstance();
                            calS.setTime(dateStart);

                            Calendar calE = Calendar.getInstance();
                            calE.setTime(dateEnd);

                            while(!calS.after(calE)){
                                singleton.dates.add(calS.getTime());
                                calS.add(Calendar.DATE,1);
                            }
                            Toast.makeText(getActivity(), "Information successfully entered.", Toast.LENGTH_SHORT).show();

                            //singleton.mEventDays.remove(2);
                            //singleton.mEventDays.add(ev);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                Singleton singleton = Singleton.Instance();
                //Toast.makeText(getContext(), singleton.eventName+" | "+singleton.organiserName+" | "+singleton.descriptionOfEvent, Toast.LENGTH_SHORT).show();
                singleton.somethingDoneInEveryPart[1]=true;

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

    /**
     * U odnosu na dobijen requestCode, desava se ili deo za odabir lokacije ili slike. U slucaju lokacije, iz PlacePicker-a se izvuce mesto koje se dalje podeli na adresu, lokaciju i koordinate,
     * pa se sva tri podatka upisu u Singleton, kao i u TextView-ove na ekranu. U slucaju izbora slike, preuzima se slika u obliku bitmape preko Uri linka. I uri link i bitmapa se ubacuju u Singleton.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==PLACE_PICKER_REQUEST){
            if(resultCode==RESULT_OK){
                Singleton singleton = Singleton.Instance();
                Place place = PlacePicker.getPlace(this.getContext(),data);
                String locationAddress = String.format("Location address: %s",place.getAddress());
               //Toast.makeText(this.getContext(), "LOCATIONADDRESS: "+locationAddress, Toast.LENGTH_SHORT).show();
                String locationName = String.format("Location name: %s",place.getName());
               //Toast.makeText(this.getContext(), "LOCATIONNAME: "+locationName, Toast.LENGTH_SHORT).show();
                String combined = locationName+System.getProperty("line.separator")+locationAddress;
                singleton.locationCoordinates=new GeoPoint(place.getLatLng().latitude,place.getLatLng().longitude);
                singleton.locationAddress=place.getAddress().toString();
                singleton.locationName=place.getName().toString();
                createEventLocation.setText(combined);

                //Toast.makeText(this.getContext(),"LOCATIONTESTING: "+ combined, Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == CHOOSE_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uriEventImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(),uriEventImage);
                eventImageView.setImageBitmap(bitmap);
                Singleton singleton = Singleton.Instance();
                singleton.uriEventImage=uriEventImage;

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

    @Override
    public void update(Observable observable, Object o) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
