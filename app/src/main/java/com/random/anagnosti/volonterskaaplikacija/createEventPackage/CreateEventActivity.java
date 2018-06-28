package com.random.anagnosti.volonterskaaplikacija.createEventPackage;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.random.anagnosti.volonterskaaplikacija.R;
import com.random.anagnosti.volonterskaaplikacija.welcomePackage.WelcomeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class CreateEventActivity extends AppCompatActivity implements CreateEventFragmentPage1.OnFragmentInteractionListener,CreateEventFragmentPage2.OnFragmentInteractionListener,CreateEventFragmentPage3.OnFragmentInteractionListener,CreateEventFragmentPage4.OnFragmentInteractionListener,CreateEventFragmentPage5.OnFragmentInteractionListener{

    private static final String TAG = "CreateEventActivity";

    Singleton singleton = Singleton.Instance();
    SectionsPagerAdapter mSectionsPagerAdapter;
    ImageView doneCreatingEventIcon;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);

        TabLayout createEventTabLayout = (TabLayout)findViewById(R.id.createeventtablayout);
        //createEventTabLayout.setupWithViewPager(mViewp);
        //int counter = createEventTabLayout.getTabCount();
        for (int i=0;i<5;i++){
           createEventTabLayout.addTab(createEventTabLayout.newTab().setText("Part "+(i+1)));
        }
        createEventTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager mViewp=(ViewPager) findViewById(R.id.createeventcontainer);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),createEventTabLayout.getTabCount());
        mViewp.setAdapter(mSectionsPagerAdapter);
        mViewp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(createEventTabLayout));
        createEventTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewp.setCurrentItem(tab.getPosition());
                Fragment fragment = ((SectionsPagerAdapter)mViewp.getAdapter()).getFragment(tab.getPosition());
                if(tab.getPosition()==2 && fragment != null){
                    fragment.onResume();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        doneCreatingEventIcon=findViewById(R.id.doneCreatingEventIcon);
        doneCreatingEventIcon.setOnClickListener(new View.OnClickListener() {
            Dialog popUpDialog = new Dialog(CreateEventActivity.this);
            TextView createMessage, eventName;
            Button createButton;
            @Override
            public void onClick(View view) {
                Toast.makeText(CreateEventActivity.this, "People: "+singleton.mEventPeople.size(), Toast.LENGTH_SHORT).show();
                boolean everythingOk=true;
                String notFilledParts="";
                for(int i=0;i<singleton.somethingDoneInEveryPart.length;i++){
                    if(!singleton.somethingDoneInEveryPart[i]){
                        everythingOk=false;
                        notFilledParts=notFilledParts+(i+1)+", ";
                    }

                }
                if(everythingOk) {
                    popUpDialog.setContentView(R.layout.pop_up_dialog_create_event);
                    createMessage = popUpDialog.findViewById(R.id.createeventpopupcreatemessage);
                    eventName = popUpDialog.findViewById(R.id.createeventpopupeventname);
                    createButton = popUpDialog.findViewById(R.id.createeventpopupcreate);
                    String tet = "Are you sure you want to create the following event?";
                    createMessage.setText(tet);
                    eventName.setText(singleton.eventName);
                    createButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            uploadAndCreateEvent();
                            popUpDialog.dismiss();
                        }
                    });
                    Objects.requireNonNull(popUpDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    popUpDialog.show();
                } else{
                    notFilledParts="Parts "+notFilledParts;
                    notFilledParts=notFilledParts.substring(0,notFilledParts.length()-2);
                    notFilledParts+=" have not been filled. Please refer to the mentioned parts and complete the inputs.";
                    Toast.makeText(CreateEventActivity.this, notFilledParts, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~




    @Override
    protected void onDestroy() {
        super.onDestroy();
        singleton.destroyS();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void updateFragments(){
        mSectionsPagerAdapter.updateFragments();
    }

    private void uploadAndCreateEvent(){
        db=FirebaseFirestore.getInstance();

        CollectionReference dbEvent = db.collection("events");
        final Map<String, Object> event = new HashMap<>();
        event.put("event_name", singleton.eventName);
        event.put("organiser_name",singleton.organiserName);
        event.put("description_of_event",singleton.descriptionOfEvent);
        event.put("location_coordinates",singleton.locationCoordinates);
        event.put("location_address",singleton.locationAddress);
        event.put("location_name",singleton.locationName);
        event.put("emails_of_people", singleton.mUsedEmails);

        db.collection("events").add(event).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(final DocumentReference documentReference) {
                Log.d(TAG,"Event successfully added! "+documentReference.getId());

                final String eventId=documentReference.getId();

                //Adding event picture to storage
                StorageReference eventImageRef = FirebaseStorage.getInstance().getReference("eventpics/"+eventId+System.currentTimeMillis()+".jpg");
                if(singleton.uriEventImage!=null){
                    eventImageRef.putFile(singleton.uriEventImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String eventImageUrl = taskSnapshot.getDownloadUrl().toString();
                            DocumentReference thisEvent = db.collection("events").document(eventId);
                            thisEvent.update("event image url",eventImageUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Event picture successfully updated!");

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error updating event picture", e);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(CreateEventActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //Adding days to event

                for(int i=0;i<singleton.mEventDays.size();i++){
                    Map<String,Object> day = new HashMap<>();
                    day.put("date",singleton.mEventDays.get(i).getDate());
                    day.put("time_start",singleton.mEventDays.get(i).getTimeStart());
                    day.put("time_end",singleton.mEventDays.get(i).getTimeEnd());
                    db.collection("events").document(eventId).collection("days").add(day).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG,"Day successfully added into event! "+documentReference.getId());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG,"Error adding day.",e);

                        }
                    });
                }


                //Adding roles to event

                for(int j=0;j<singleton.mEventRoles.size();j++){
                    Map<String,Object> role = new HashMap<>();
                    role.put("title",singleton.mEventRoles.get(j).getName());
                    role.put("description",singleton.mEventRoles.get(j).getDescription());
                    ArrayList<String> subs = new ArrayList<>();
                    if(!singleton.mEventRoles.get(j).getSubordinates().isEmpty()){
                        for(int h=0;h<singleton.mEventRoles.get(j).getSubordinates().size();h++){
                            subs.add(singleton.mEventRoles.get(j).getSubordinates().get(h).getName());
                        }
                    }else{
                        subs.add("None");
                    }
                    role.put("subordinates",subs);
                    db.collection("events").document(eventId).collection("roles").add(role).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG,"Role successfully added into event! "+documentReference.getId());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG,"Error adding role.",e);

                        }
                    });
                }
                    //Adding people to event

                for(int z=0;z<singleton.mEventPeople.size();z++){
                    Map<String,Object> person = new HashMap<>();
                    person.put("boss",singleton.mEventPeople.get(z).getParentOfIndividual().getEmail());
                    person.put("email",singleton.mEventPeople.get(z).getEmail());
                    person.put("role",singleton.mEventPeople.get(z).getRoleOfIndividual().getName());
                    db.collection("events").document(eventId).collection("people").add(person).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG,"Person successfully added into event! "+documentReference.getId());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }

                //Toast.makeText(CreateEventActivity.this, "Successfully added the event! :D", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"Error adding event.",e);
                //Toast.makeText(CreateEventActivity.this, "Error: something went wrong with adding the event. :(", Toast.LENGTH_SHORT).show();
            }
        });

        Toast.makeText(this, "Congratulations! Event has been successfully added!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }


}
