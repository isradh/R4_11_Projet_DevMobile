package com.example.r4_11_devmobile;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewReservationActivity extends AppCompatActivity {


    Spinner listequipement;
    Spinner spinnerHeuredebut;
    Spinner spinnerHeureFin;

    String equipement;
    Calendar calendar;

    ImageView dateButton;


    private DatabaseManager databaseManager;

    String heureDebut;

    String heureFin;
    String dateReservation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvellereservation);

        databaseManager = new DatabaseManager(getApplicationContext());

        dateButton = findViewById(R.id.calendrier);
        listequipement = findViewById(R.id.spinnerEquipements);
        spinnerHeuredebut = findViewById(R.id.spinner_heuredebut);
        spinnerHeureFin = findViewById(R.id.spinner_heurefin);

        calendar = Calendar.getInstance();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewReservationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Traitement de la date sélectionnée
                                dateReservation = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                Toast.makeText(NewReservationActivity.this, "Date sélectionnée : " + dateReservation, Toast.LENGTH_SHORT).show();
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        String userId = UserId.getUserId();
        connectUser(userId);

        ArrayList<String> heuresList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            heuresList.add(String.format("%02d", i));
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

                equipement = listequipement.getSelectedItem().toString();
                heureDebut = spinnerHeuredebut.getSelectedItem().toString() + ":00";
                heureFin= spinnerHeureFin.getSelectedItem().toString() + ":00";

                reservation();

                if (listequipement.getSelectedItem() == null) {
                    Toast.makeText(NewReservationActivity.this, "Veuillez sélectionner une équipement", Toast.LENGTH_SHORT).show();
                } else if(spinnerHeuredebut.getSelectedItem() == null){
                    Toast.makeText(NewReservationActivity.this, "Veuillez sélectionner une heure de début", Toast.LENGTH_SHORT).show();

                }else if(spinnerHeureFin.getSelectedItem() == null){
                    Toast.makeText(NewReservationActivity.this, "Veuillez sélectionner une heure de fin.", Toast.LENGTH_SHORT).show();

                } else {

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
        parametres.put("dateReservation", dateReservation);


        JSONObject parametre = new JSONObject(parametres);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametre, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ApiResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError erreur) {
                Toast.makeText(getApplicationContext(), erreur.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest); // Ajout de la requête à la file d'attente
    }




    public void ApiResponse(JSONObject reponse) {
        Boolean success = null;
        String error = "";

        try {
            success = reponse.getBoolean("success");

            if (reponse.has("show_dialog")) {
                boolean showDialog = reponse.getBoolean("show_dialog");
                if (showDialog) {
                    showReservationConfirmationDialog(new Runnable() {
                        @Override
                        public void run() {
                            // Réponse de l'utilisateur à la boîte de dialogue
                            // Si l'utilisateur approuve, effectuez la réservation
                            reservation();
                        }
                    });
                }
            }

            if (success) {
                Toast.makeText(NewReservationActivity.this, "Réservation réussie.", Toast.LENGTH_SHORT).show();
            } else {
                error = reponse.getString("error");
                if (error != null) {
                    Toast.makeText(NewReservationActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(NewReservationActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }



    private void showReservationConfirmationDialog(final Runnable onConfirmation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewReservationActivity.this);
        builder.setMessage("Êtes-vous sûr de vouloir réserver ce créneau ? Il est presque complet, cela entraînera un malus.");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onConfirmation.run(); // Appel du callback lorsque l'utilisateur appuie sur "Oui"
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Ne rien faire, l'utilisateur a annulé la réservation
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}