package com.random.anagnosti.volonterskaaplikacija;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfileInfo extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView image;

    private void updateUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null) {
            if (user.getPhotoUrl() != null) {
                //String photoUrl = user.getPhotoUrl().toString();
                //  Glide.with(this).load(user.getPhotoUrl().toString()).into(imageView);
            }
            db.collection("Users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()) {

                 /*   if (task.isSuccessful()) {
                        DocumentSnapshot ds = task.getResult();
                        ime = ds.getString("First name");
                        prezime = ds.getString("Last name");
                        imejl = ds.getString("Email");
                        broj = ds.getString("Phone number");
                        adr = ds.getString("Address");

                        imeIprezime.setText(ime + " " + prezime);
                        email.setText(imejl);
                        brTelefona.setText(broj);
                        adresa.setText(adr);

                        email.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                emailDialoge(imejl);
                            }
                        });*/


                    }
       /* else{
            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
        }*/
                }
            })
            ;}
    }

    EditText ime,prezime,adresa,brTelefona,email;
    Button submit;

    String surname,addr,num,imejl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_info);
        submit = findViewById(R.id.updateButton);
        ime = findViewById(R.id.editIme);
        prezime = findViewById(R.id.editPrezime);
        adresa = findViewById(R.id.editAdresa);
        email = findViewById(R.id.editEmail);
        brTelefona = findViewById(R.id.editBroj);





        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateF();
            }
        });
    }


    public void updateF()
    {
        final String firstName = ime.getText().toString().trim();
        final String lastName = prezime.getText().toString().trim();
        final String iiimejl = email.getText().toString().trim();
        final String adrr = adresa.getText().toString().trim();
        final String brr = brTelefona.getText().toString().trim();
        FirebaseUser user = mAuth.getCurrentUser();

        DocumentReference contact = db.collection("Users").document(user.getUid());
        if(firstName!=null) {
            contact.update("First name", firstName);
        }
        if(iiimejl!=null) {
            contact.update("Email", iiimejl);
        }
        if(adrr!=null) {
            contact.update("Address", adrr);
        }
        if(brr!=null) {
            contact.update("Phone number", brr);
        }
        contact.update("Last name", lastName)
                .addOnSuccessListener(new OnSuccessListener < Void > () {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditProfileInfo.this, "Updated Successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                });

}}
