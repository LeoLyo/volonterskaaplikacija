package com.random.anagnosti.volonterskaaplikacija;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends Activity {

    public static final String EMAIL_KEY = "Email";
    public static final String FIRST_NAME_KEY = "First name";
    public static final String LAST_NAME_KEY = "Last name";
    public static final String ADDRESS_KEY = "Address";
    public static final String PHONE_NUMBER_KEY = "Phone number";
    public static final String PASSWORD_KEY = "Password";

    EditText editTextEmail, editTextFirstName, editTextLastName, editTextAddress, editTextPhoneNumber, editTextPassword, editTextConfirmPassword;
    ProgressBar progressBarRegister;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        progressBarRegister = findViewById(R.id.progressBarRegister);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }


    public void registerUser(View view){
        final String email = editTextEmail.getText().toString().trim();
        final String firstName = editTextFirstName.getText().toString().trim();
        final String lastName = editTextLastName.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();
        final String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(firstName.isEmpty()){
            editTextFirstName.setError("First name is required");
            editTextFirstName.requestFocus();
            return;
        }
        if(lastName.isEmpty()){
            editTextLastName.setError("Last name is required");
            editTextLastName.requestFocus();
            return;
        }

        if(address.isEmpty()){
            editTextAddress.setError("Address is required");
            editTextAddress.requestFocus();
            return;
        }
        if(phoneNumber.isEmpty()){
            editTextPhoneNumber.setError("Phone number is required");
            editTextPhoneNumber.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<7){
            editTextPassword.setError("Minimum length of password should be 7");
            editTextPassword.requestFocus();
            return;
        }
        if(confirmPassword.isEmpty()){
            editTextConfirmPassword.setError("Confirming the password is required");
            editTextConfirmPassword.requestFocus();
            return;
        }
        if(!confirmPassword.matches(password)){
            editTextConfirmPassword.setError("Confirmation of password must match the password");
            editTextConfirmPassword.requestFocus();
            return;
        }

        progressBarRegister.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBarRegister.setVisibility(View.GONE);
                if(task.isSuccessful()){

                    //Inputting user info into database
                    Map<String, Object> user = new HashMap<>();
                    user.put(EMAIL_KEY,email);
                    user.put(FIRST_NAME_KEY,firstName);
                    user.put(LAST_NAME_KEY,lastName);
                    user.put(ADDRESS_KEY,address);
                    user.put(PHONE_NUMBER_KEY,phoneNumber);
                    user.put(PASSWORD_KEY,password);

                    db.collection("Users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG,"Error adding document",e);
                                }
                            });
                    //Informing user that the registration was successful
                    Toast.makeText(getApplicationContext(),"User Registered successfully", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(RegisterActivity.this, WelcomeActivity.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    finish();
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"This email is already registered",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
}
