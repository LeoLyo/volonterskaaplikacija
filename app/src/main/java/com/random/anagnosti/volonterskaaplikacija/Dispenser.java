package com.random.anagnosti.volonterskaaplikacija;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.IOException;

public class Dispenser implements View.OnClickListener{

    private static final String TAG = "Dispenser";

    private Activity localActivity;
    private Context localContext;
    private TextView localTextView;
    private ImageView localImageView;

    private int local_requested_code;

    private static final int PLACE_PICKER_REQUEST = 101;
    private static final int CHOOSE_IMAGE_REQUEST = 102;

    public Dispenser(Activity activity, TextView textView, ImageView imageView, int requested_code){   //Dispenser for finding an image from the phone storage
        this.localActivity = activity;
        this.localTextView = textView;
        this.localImageView = imageView;
        this.local_requested_code = requested_code;
    }
    public Dispenser(Activity activity, TextView textView, Context context, int requested_code){    //Dispenser for opening maps and finding required location
        this.localActivity = activity;
        this.localTextView = textView;
        this.localContext = context;
        this.local_requested_code = requested_code;
    }


    @Override
    public void onClick(View view) {
        if(local_requested_code==PLACE_PICKER_REQUEST){ //This is where we start the location process..
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent intent;
            try {
                intent = builder.build(localActivity);
                intent.setFlags(0);
                localActivity.startActivityForResult(intent, PLACE_PICKER_REQUEST);


            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
        else if(local_requested_code==CHOOSE_IMAGE_REQUEST){ //This is where we start the image finding process..
            Toast.makeText(localActivity, "Choosing an image from storage", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            localActivity.startActivityForResult(Intent.createChooser(intent,"Select Event Image"),CHOOSE_IMAGE_REQUEST);
        }
    }

    private void uploadImageToFirebaseStorage() {
        // StorageReference eventImageReference = FirebaseStorage.getInstance().getReference("events/"+eventName+"_"+eventOrganiser+"_");
    }


}
