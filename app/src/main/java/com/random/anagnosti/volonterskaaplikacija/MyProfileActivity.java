package com.random.anagnosti.volonterskaaplikacija;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import static android.content.ContentValues.TAG;

public class MyProfileActivity extends AppCompatActivity {




    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView image;

    TextView imeIprezime,adresa,brTelefona,email;

    private void loadUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null) {
            if (user.getPhotoUrl() != null) {
                //String photoUrl = user.getPhotoUrl().toString();
                //  Glide.with(this).load(user.getPhotoUrl().toString()).into(imageView);
            }
            db.collection("Users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        DocumentSnapshot ds = task.getResult();
                        String ime = ds.getString("First name");
                        String prezime = ds.getString("Last name");
                        String imejl = ds.getString("Email");
                        String broj = ds.getString("Phone number");
                        String adr = ds.getString("Address");

                        imeIprezime.setText(ime + " " + prezime);
                        email.setText(imejl);
                        brTelefona.setText(broj);
                        adresa.setText(adr);
                    }
                }
            });



        }
       /* else{
            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
        }*/
    }

    public void addProfileImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select EventDay Image"),101);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        image = findViewById(R.id.addImage);
        imeIprezime = findViewById(R.id.nameSurname);
        adresa = findViewById(R.id.addressa);
        email = findViewById(R.id.emailAbout);
        brTelefona = findViewById(R.id.phoneNumber);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadUserInfo();

    }


   // @Override
/*    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(this,WelcomeActivity.class));

        }*/



   // }
}
