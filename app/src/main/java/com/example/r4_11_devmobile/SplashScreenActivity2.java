package com.example.r4_11_devmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class SplashScreenActivity2 extends AppCompatActivity {



    private final static int TEMPS = 4500;
    private String Quote1 = "Chaque interrupteur éteint est un pas vers un habitat plus durable et une planète plus verte.";
    private String Quote2 = "En modérant notre utilisation d'électricité, nous éclairons non seulement nos maisons, mais aussi l'avenir de notre planète.";
    private String Quote3 = "La sobriété énergétique dans nos foyers est la clé pour construire un habitat où l'écologie et le confort cohabitent en harmonie.";
    private String Quote4 = "Chaque appareil débranché est un acte de préservation de l'habitat, un petit pas vers un avenir plus durable.";
    private String[] tabQuote = {Quote1, Quote2, Quote3, Quote4};
    private TextView citation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);


        citation = findViewById(R.id.citationText);
        Random random = new Random();
        int nombreAleatoire = random.nextInt(5);
        citation.setText(tabQuote[nombreAleatoire]);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
        new Handler().postDelayed(runnable,TEMPS);


    }



}