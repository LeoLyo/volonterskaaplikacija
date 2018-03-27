package com.random.anagnosti.volonterskaaplikacija;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void registerAction(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void welcomeAction(View view){
        /*String editText = (String) findViewById(R.id.editText);
        String message = editText.toString();*/
        Intent intent2 = new Intent(this, WelcomeActivity.class);
        startActivity(intent2);



    }
}
