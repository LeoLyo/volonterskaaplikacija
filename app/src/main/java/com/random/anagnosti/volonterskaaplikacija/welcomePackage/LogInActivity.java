package com.random.anagnosti.volonterskaaplikacija.welcomePackage;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.random.anagnosti.volonterskaaplikacija.R;

/**
 * Klasa koja je zasluzena za logovanje korisnika u aplikaciju.
 */
public class LogInActivity extends Activity {

    FirebaseAuth mAuth;
    EditText editTextEmailLogin, editTextPasswordLogin;
    ProgressBar progressBarLogin;

    /**
     * Referenciranje adekvatnih vizuelnih elemenata.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmailLogin = findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = findViewById(R.id.editTextPasswordLogin);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Metoda za odlazak na deo za registraciju.
     */
    public void registerAction(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Metoda koja proverava da li je email upisan, pravilnog formata, da li je sifra upisana i odgovarajuce velicine. U slucaju da su pravilno upisani podaci, pokusan je signin u bazu
     * navedenim podacima. U slucaju uspesnog loginovanja, vraca se prethodni Activity, to jest WelcomeActivity.
     */
    public void welcomeAction(View view){
        /*String editText = (String) findViewById(R.id.editText);
        String message = editText.toString();*/

        String email = editTextEmailLogin.getText().toString().trim();
        String password = editTextPasswordLogin.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmailLogin.setError("Email is required");
            editTextEmailLogin.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmailLogin.setError("Please enter a valid email");
            editTextEmailLogin.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPasswordLogin.setError("Password is required");
            editTextPasswordLogin.requestFocus();
            return;
        }
        if(password.length()<7){
            editTextPasswordLogin.setError("Minimum length of password should be 7");
            editTextPasswordLogin.requestFocus();
            return;
        }

        progressBarLogin.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBarLogin.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    //Vraca WelcomActivity rezultat
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });







    }
}
