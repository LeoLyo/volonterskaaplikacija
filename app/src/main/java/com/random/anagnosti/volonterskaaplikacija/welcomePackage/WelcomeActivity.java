package com.random.anagnosti.volonterskaaplikacija.welcomePackage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.random.anagnosti.volonterskaaplikacija.R;
import com.random.anagnosti.volonterskaaplikacija.createEventPackage.CreateEventActivity;
import com.random.anagnosti.volonterskaaplikacija.createEventPackage.Singleton;
import com.random.anagnosti.volonterskaaplikacija.navigationPackage.MainNavigationActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Pocetni ekran koji korisnik vidi kada udje u aplikaciju ulogovan. U slucaju da nije ulogovan, preusmeren je na Login ekran. Na ekranu se nalaze cetiri dugmeta, svako sa svojim
 * preusmeravanjem na odredjen deo aplikacije: jedno za kreiranje eventa, drugo za pregled sopstvenog profila, trece na pregled event-ova i cetvrto za unosenje koda i preuzimanje event-a.
 */
public class WelcomeActivity extends Activity {

    public static final int RC_SIGN_IN = 1;
    private static final String TAG = "WelcomeActivityTag";

    Button tempButton;
    private Dialog popUpDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    /**
     * Nakon uspesnog referenciranja svih vizuelnih elemenata View-a, svako dugme preusmeruje na odredjen deo aplikacije:jedno za kreiranje eventa, drugo za pregled sopstvenog profila,
     * trece na pregled event-ova i cetvrto za unosenje koda i preuzimanje event-a. U slucaju odabira dugmeta za odabir Event-a, prikazuje se popUpDialog koji prikazuje spinner sa listom
     * svih event-ova koje korisnik moze izabrati za odlazak u perspektivu Event-a za odaberen Event.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        firebaseAuth = FirebaseAuth.getInstance();
        initializeAuthListener();

        CardView khakiview = findViewById(R.id.khaki);


        khakiview.setOnClickListener(new View.OnClickListener() {
            Dialog popUpDialog = new Dialog(WelcomeActivity.this);

            TextView asetextview;
            Spinner asespinner;
            Button asebutton;
            @Override
            public void onClick(View view) {

                popUpDialog.setContentView(R.layout.activity_select_event);
                asetextview = popUpDialog.findViewById(R.id.activity_select_event_textview);
                asespinner = popUpDialog.findViewById(R.id.activity_select_event_spinner);
                asebutton = popUpDialog.findViewById(R.id.activity_select_event_button);

                final ArrayList<DocumentSnapshot>spinners= new ArrayList<>();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email="";
                if(user != null){
                    email = user.getEmail();
                }{
                    Log.d(TAG,"An unexpected error occurred: the user suddenly does not exist.");
                }
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                final String finalEmail = email;
                db.collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    ArrayList<String>emailsForEvent=new ArrayList<>();
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<DocumentSnapshot> allEvents = task.getResult().getDocuments();
                            for(int i=0;i<allEvents.size();i++){
                                Map<String,Object> map =allEvents.get(i).getData();
                                for(Map.Entry<String,Object> entry : map.entrySet()){
                                    if(entry.getValue() instanceof ArrayList<?>){
                                        emailsForEvent=(ArrayList<String>)entry.getValue();
                                        for(int z=0;z<emailsForEvent.size();z++){
                                            if(emailsForEvent.get(z).equals(finalEmail)){
                                                spinners.add(allEvents.get(i));
                                            }
                                        }
                                    }

                                }
                            }

                        }else{
                            Log.d(TAG,"Error getting events: " + task.getException().getMessage());
                        }
                    }
                });
                ArrayList<String> convertedSpinners = new ArrayList<>();
                final ArrayList<String>eventIds = new ArrayList<>();
                for(int i=0;i<spinners.size();i++){
                    convertedSpinners.add(spinners.get(i).getString("event_name"));
                    eventIds.add(spinners.get(i).getId());
                }
                convertedSpinners.add("None");
                if(eventIds.isEmpty()){
                    eventIds.add("1FlSWkk9aD8j29vfnHgz");
                }
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,convertedSpinners);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                asespinner.setAdapter(spinnerAdapter);

                asebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String eventId = eventIds.get(asespinner.getSelectedItemPosition());
                        Intent intent = new Intent(getBaseContext(), MainNavigationActivity.class);

                        //ZA TESTIRANJE
                        intent.putExtra("EXTRA_EVENT_ID",eventId);
                        startActivity(intent);
                        popUpDialog.dismiss();

                    }
                });
                Objects.requireNonNull(popUpDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popUpDialog.show();
            }
        });
    }


    /**
     * Pokusaj prelaska u okruzenje za prikaz mape. Prebaceno u AboutEventFragment paketa navigationPackage.
     */
    public void radiNesto(View view){
        Intent intent = new Intent(this, About_Event_Activity.class);
        startActivity(intent);
    }

    /**
     * Prelazak u MyProfile deo aplikacije.
     */
    public void profileAction(View view)
    {
        Intent intent = new Intent(this, MyProfileActivity.class);
        startActivity(intent);
    }

    /**
     * Prelazak u JoinEvent deo aplikacije.
     */
    public void joinEvent(View view)
    {
        Intent intent = new Intent(this, JoiningEventActivity.class);
        startActivity(intent);
    }

    /**
     * Prelazak u CreateEvent deo aplikacije. Singleton se resetuje pri ulasku u ovo okruzenje.
     */
    public void createEventWindow(View view){
        Singleton singleton= Singleton.Instance();
        singleton.destroyS();
        Intent intent = new Intent(this, CreateEventActivity.class);
        startActivity(intent);
    }

    /**
     * Inicijalizuje se authentication listener baze. U slucaju da nije null, dugme postaje logout dugme. Na klik dugmeta, korisnik se izloguje. Ako korisnik nije ulogovan, preusmeruje
     * se na LogInActivity, gde se moze ulogovati u aplikaciju.
     */
    //    LIESTENER ZA LOGINOVANJE
    private void initializeAuthListener(){
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){

//                    TESTIRANJE ZA SIGNOUT SAMO
                    Button bt = findViewById(R.id.showItems);

                    bt.setText(user.getEmail());

                    bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            firebaseAuth.signOut();
                        }
                    });
                }else{
                    Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                    startActivityForResult(intent, 1);
                }
            }
        };
    }

    /**
     * Na resume se dodaje firebase authentication addAuthStateListener.
     */
    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    /**
     * Na pause se uklanja firebase authentication removeAuthStateListener.
     */
    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    /**
     * Na povratak sa Login Activity-ja, ispise se Signed in u slucaju uspesnog SingIn-ovanja, kao i Sign in canceled u slucaju neuspesnog.
     */
    //    OKIDA SE KAD LOGIN VRATI SA LOG IN ACTIVITY
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK)
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}

