package com.random.anagnosti.volonterskaaplikacija.navigationPackage;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.random.anagnosti.volonterskaaplikacija.R;

import static android.content.ContentValues.TAG;

public class AboutEventFragment extends Fragment {
    FirebaseFirestore mFirestore;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rView =inflater.inflate(R.layout.fragment_navigation_about_event,container,false);
        mFirestore= FirebaseFirestore.getInstance();

        mFirestore.collection("events").document("1FlSWkk9aD8j29vfnHgz").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d(TAG,"Error: "+e.getMessage());
                }
                    GeoPoint location =(GeoPoint) documentSnapshot.get("location_coordinates");
                    String coordinates = location.getLatitude()+", "+location.getLongitude();
                    //Toast.makeText(AboutEventFragment.this.getContext(), coordinates, Toast.LENGTH_SHORT).show();
                   //String uri = "http://maps.google.com/maps?daddr= " + 12f + "," + 2f + " (" +coordinates+")";
                    String uri = "https://www.google.com/maps/@"+coordinates+",15.00z";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    try
                    {
                        startActivity(intent);
                    }
                    catch(ActivityNotFoundException ex)
                    {
                    try
                    {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(unrestrictedIntent);
                    }
                    catch(ActivityNotFoundException innerEx)
                    {
                        Toast.makeText(AboutEventFragment.this.getContext(), "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return rView;
    }


}
