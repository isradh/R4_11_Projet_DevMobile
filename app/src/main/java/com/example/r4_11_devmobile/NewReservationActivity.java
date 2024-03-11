package com.example.r4_11_devmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.r4_11_devmobile.bd.DatabaseManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewReservationActivity extends AppCompatActivity {


    Spinner listequipement;
    Spinner spinnerHeuredebut;
    Spinner spinnerHeureFin;

    String equipement;

    private DatabaseManager databaseManager;

    String heureDebut;

    String heureFin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvellereservation);

        databaseManager = new DatabaseManager(getApplicationContext());


        listequipement = findViewById(R.id.spinnerEquipements);
        spinnerHeuredebut = findViewById(R.id.spinner_heuredebut);
        spinnerHeureFin = findViewById(R.id.spinner_heurefin);

        String userId = UserId.getUserId();
        connectUser(userId);

        ArrayList<String> heuresList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            heuresList.add(String.format("%02d", i)); // Ajouter les heures formatées (ex: 01, 02, ..., 23)
        }

        ArrayAdapter<String> heureAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, heuresList);
        heureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerHeuredebut.setAdapter(heureAdapter);
        spinnerHeureFin.setAdapter(heureAdapter);

        Button btnAnnuler = findViewById(R.id.btnAnnuler);
        Button btnajouter = findViewById(R.id.btnajouter);

        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservation();

                if (listequipement.getSelectedItem() == null) {
                    Toast.makeText(NewReservationActivity.this, "Veuillez sélectionner une équipement", Toast.LENGTH_SHORT).show();
                } else if(spinnerHeuredebut.getSelectedItem() == null){
                    Toast.makeText(NewReservationActivity.this, "Veuillez sélectionner une heure de début", Toast.LENGTH_SHORT).show();

                }else if(spinnerHeureFin.getSelectedItem() == null){
                    Toast.makeText(NewReservationActivity.this, "Veuillez sélectionner une heure de fin.", Toast.LENGTH_SHORT).show();

                }else {
                     equipement = listequipement.getSelectedItem().toString();
                     heureDebut = spinnerHeuredebut.getSelectedItem().toString();
                     heureFin= spinnerHeureFin.getSelectedItem().toString();



                }
            }
        });
    }

    public void connectUser(String userId) {
        String url = "http://10.0.2.2/devmobile/actions/recupEquipement.php?userId=" + userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        handleResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError erreur) {
                Toast.makeText(getApplicationContext(), erreur.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(jsonArrayRequest);
    }

    private void handleResponse(JSONArray response) {
        ArrayList<String> equipement = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String nom = jsonObject.getString("nom");
                equipement.add(nom);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipement);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listequipement.setAdapter(adapter);
    }




    public void reservation(){
        String url ="http://10.0.2.2/devmobile/actions/reservation.php";

        Map<String, String> parametres = new HashMap<>();
        parametres.put("equipement", equipement);
        parametres.put("heureDebut", heureDebut);
        parametres.put("heureFin", heureFin);

        JSONObject parametre = new JSONObject(parametres);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametre, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                ApiResponse(response);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError erreur) {
                Toast.makeText(getApplicationContext(), erreur.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        databaseManager.queue.add(jsonObjectRequest);
    }



    public void ApiResponse(JSONObject reponse){
        Boolean success = null;
        String error = "";

        try{
            success = reponse.getBoolean("success");

            if(success == true ){
                Toast.makeText(NewReservationActivity.this, "Ri", Toast.LENGTH_SHORT).show();
            }else {
                error = reponse.getString("erreur");
                Toast.makeText(NewReservationActivity.this, error, Toast.LENGTH_SHORT).show();

            }

        }catch(JSONException e){
            e.printStackTrace();
            Toast.makeText(NewReservationActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

        }
    }

}
