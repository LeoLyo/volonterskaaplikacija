package com.random.anagnosti.volonterskaaplikacija.welcomePackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.random.anagnosti.volonterskaaplikacija.R;

/**
 * Klasa koja jos nije implementovana. Sluzice prijavljivanju za event jednostavnim unosom koda u odgovarajuce polje i pritiskom na dugme. Na klik ce se otici u bazu, pregledati svi Event-ovi
 * i videti da li postoji aktivan ili buduci event u kome se nalazi email korisnika. Ako postoji, alocirace se korisnik tom eventu, podaci korisnika ce se upisati u odgovarajuce polje za tu
 * osobu u Event-u i Event ce moci biti vidljiv u rezimu rada za Event-ove.
 */
public class JoiningEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinn);
    }
}
