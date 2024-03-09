package com.example.r4_11_devmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EspaceClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_client);

        TextView ecoCoinsTextView = findViewById(R.id.ecoCoins);
        ecoCoinsTextView.setPaintFlags(ecoCoinsTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView infoTextView = findViewById(R.id.info);
        infoTextView.setPaintFlags(ecoCoinsTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Button modif = findViewById(R.id.modif);

        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EspaceClientModificationActivity.class);
                startActivity(intent);

            }
        });

        String userId = UserId.getUserId();

        connectUser(userId);

    }

    public void connectUser(String userId) {

        String url = "http://10.0.2.2/devmobile/actions/recupInfoUser.php?userId=" + userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        handleResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError erreur) {
                Toast.makeText(getApplicationContext(), erreur.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
    private void handleResponse(JSONArray response) {
        try {
            if (response.length() > 0) {
                JSONObject jsonObject = response.getJSONObject(0); // Accédez au premier objet JSON
                String nom = jsonObject.getString("nom");
                String prenom = jsonObject.getString("prenom");
                String email = jsonObject.getString("email");

                String etageStr = jsonObject.getString("etage");
                int etage = Integer.parseInt(etageStr);
                String superficieStr = jsonObject.getString("superficie");
                int superficie = Integer.parseInt(superficieStr);


                TextView nomTextView = findViewById(R.id.nomSaisie);
                nomTextView.setText(nom);

                TextView prenomTextView = findViewById(R.id.prenomSaisie);
                prenomTextView.setText(prenom);

                TextView emailTextView = findViewById(R.id.mailSaisie);
                emailTextView.setText(email);



                TextView etageTextView = findViewById(R.id.etageSaisie);
                etageTextView.setText(String.valueOf(etage));
                TextView superficieTextView = findViewById(R.id.superficieSaisie);
                superficieTextView.setText(String.valueOf(superficie));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    }


