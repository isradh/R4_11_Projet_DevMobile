package com.example.r4_11_devmobile.Reservation;

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
import com.example.r4_11_devmobile.Accueil.AccueilActivity;
import com.example.r4_11_devmobile.R;
import com.example.r4_11_devmobile.UserId;
import com.example.r4_11_devmobile.bd.DatabaseManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NewReservationActivity extends AppCompatActivity {


    private Spinner listequipement;
    private Spinner spinnerHeuredebut;
    private Spinner spinnerHeureFin;

    private String equipement;
    private Calendar calendar;

    private ImageView dateButton;


    private DatabaseManager databaseManager;

    private String heureDebut;

    private String heureFin;
    private String dateReservation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvellereservation);

        databaseManager = new DatabaseManager(getApplicationContext());

        dateButton = findViewById(R.id.calendrier);
        listequipement = findViewById(R.id.spinnerEquipements);
        spinnerHeuredebut = findViewById(R.id.spinner_heuredebut);
        spinnerHeureFin = findViewById(R.id.spinner_heurefin);

        Button btnAnnuler = findViewById(R.id.btnAnnuler);

        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewReservationActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir retourner en arrière ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

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
                                String formattedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                                dateReservation = formattedDate;
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

        Button btnajouter = findViewById(R.id.btnajouter);



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

        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }




    public void ApiResponse(JSONObject reponse) {
        Boolean success = null;
        String error = "";
        try {
            success = reponse.getBoolean("success");
            if (success) {
                Toast.makeText(NewReservationActivity.this, "Réservation réussie.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AccueilActivity.class);
                startActivity(intent);
                finish();

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




}