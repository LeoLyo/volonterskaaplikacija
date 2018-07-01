package com.random.anagnosti.volonterskaaplikacija.welcomePackage;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.random.anagnosti.volonterskaaplikacija.R;
import com.random.anagnosti.volonterskaaplikacija.createEventPackage.CreateEventActivity;

import java.io.IOException;
import java.io.InputStream;

public class MyProfileActivity extends AppCompatActivity {
private String TAG ="MyProfileActivityTag";


    public static final int PICK_IMAGE = 1;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView image;
    ImageView imageView;
    RoundImage roundedImage;

    TextView imeIprezime,adresa,brTelefona,email;
     String imejl="imejl";
     String ime="ime";
     String prezime="prezime";
     String broj="brpj";
     String adr="adr";
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
                    if (task.isSuccessful()) {
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
                        });


                    }
       /* else{
            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
        }*/
                }
            })
        ;}
    }

    public void addProfileImage(View view){
       /* Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select EventDay Image"),101);*/

        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE);

        /*Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);*/
    }





    public void editProfileInfo(View view)
    {
        Intent intent = new Intent(this, EditProfileInfo.class);
        startActivity(intent);
    }

    public void emailDialoge(String imejl){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflanter = getLayoutInflater();
        final View dialogView = inflanter.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText etEmail = (EditText) findViewById(R.id.editEmailEdit);
        final Button but = (Button) findViewById(R.id.emailButton);

        dialogBuilder.setTitle("Updating email");
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

        Button btnCamera = (Button)findViewById(R.id.cameraButton);
        imageView = (ImageView)findViewById(R.id.profileImage);

        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.misteryman);
        roundedImage = new RoundImage(bm);
        imageView.setImageDrawable(roundedImage);


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(inte,2);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            roundedImage = new RoundImage(bitmap);
            imageView.setImageDrawable(roundedImage);
        }

        if (requestCode == PICK_IMAGE) {

            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(MyProfileActivity.this.getContentResolver(), selectedImage);
                roundedImage = new RoundImage(bitmap);
                imageView.setImageDrawable(roundedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));*/



           /* Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            roundedImage = new RoundImage(bitmap);
            imageView.setImageDrawable(roundedImage);*/


            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String email = "";
            if (user != null) {
                email = user.getEmail();
            }
            {
                Log.d(TAG, "An unexpected error occurred: the user suddenly does not exist.");
            }
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("userpics/" + user.getUid() + "/" + email + "profilepicture" + ".jpg");
            profileImageRef.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                    DocumentReference thisEvent = db.collection("Users").document(user.getUid());
                    thisEvent.update("profile image url",profileImageUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Profile picture successfully updated!");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating profile picture", e);

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MyProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        // imageView.setImageBitmap(bitmap);
    }
}