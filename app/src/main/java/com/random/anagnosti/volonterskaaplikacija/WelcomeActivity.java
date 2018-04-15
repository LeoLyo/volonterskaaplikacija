package com.random.anagnosti.volonterskaaplikacija;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends Activity {

    public static final int RC_SIGN_IN = 1;

    ListView listView;
    String[] peopleList = {"Osoba 1", "Osoba2", "Osoba3", "Osoba4"};

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        listView = findViewById(R.id.listoftasks);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.activity_listview,R.id.textView,peopleList);
        listView.setAdapter(arrayAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        initializeAuthListener();
    }


    public void profileAction(View view)
    {
        Intent intent = new Intent(this, MyProfileActivity.class);
        startActivity(intent);
    }

    public void joinEvent(View view)
    {
        Intent intent = new Intent(this, JoiningEventActivity.class);
        startActivity(intent);
    }


    public void showAction(View view){
        if(listView.getVisibility()==View.GONE) {
            listView.setVisibility(View.VISIBLE);
        }else if(listView.getVisibility()==View.VISIBLE){
            listView.setVisibility(View.GONE);
        }

    }

    public void createEventWindow(View view){
        Intent intent = new Intent(this, CreateEventActivity.class);
        startActivity(intent);
    }

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

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

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

