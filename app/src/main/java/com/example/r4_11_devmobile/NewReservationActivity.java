package com.example.r4_11_devmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.r4_11_devmobile.NewEquipmentActivity;
import com.example.r4_11_devmobile.R;
import com.example.r4_11_devmobile.UserId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewReservationActivity extends AppCompatActivity {


    Spinner listequipement;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvellereservation);

        listequipement = findViewById(R.id.spinnerEquipements);

        String userId = UserId.getUserId();
        connectUser(userId);


        Spinner spinnerHeuredebut = findViewById(R.id.spinner_heuredebut);
        Spinner spinnerHeureFin = findViewById(R.id.spinner_heurefin);


        ArrayList<String> heuresList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            heuresList.add(String.format("%02d", i)); // Ajouter les heures formatées (ex: 01, 02, ..., 23)
        }

        ArrayAdapter<String> heureAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, heuresList);
        heureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerHeuredebut.setAdapter(heureAdapter);
        spinnerHeureFin.setAdapter(heureAdapter);



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
                equipement.add(nom); // Ajouter le nom à la liste des équipements
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Créer un adaptateur simple si vous ne souhaitez pas utiliser ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipement);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Associer l'adaptateur au Spinner
        listequipement.setAdapter(adapter);
    }
}
