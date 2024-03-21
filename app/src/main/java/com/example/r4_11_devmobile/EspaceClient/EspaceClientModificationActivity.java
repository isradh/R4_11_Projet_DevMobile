package com.example.r4_11_devmobile.EspaceClient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.r4_11_devmobile.R;

import org.json.JSONException;
import org.json.JSONObject;

public class EspaceClientModificationActivity extends AppCompatActivity {

    private EditText nomEditText, prenomEditText, mdpEditText;

    private TextView emailSaisie, etageEditText, superficieEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_client_modification);

        nomEditText = findViewById(R.id.nomSaisie);
        prenomEditText = findViewById(R.id.prenomSaisie);
        etageEditText = findViewById(R.id.etageSaisie);
        emailSaisie = findViewById(R.id.emailSaisie);
        superficieEditText = findViewById(R.id.superficieSaisie);
        mdpEditText = findViewById(R.id.mdpSaisie);

        fillUserInfo();


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> updateUserInfo());
    }

    private void fillUserInfo() {
        Intent intent = getIntent();
        if (intent != null) {
            nomEditText.setText(intent.getStringExtra("nom"));
            prenomEditText.setText(intent.getStringExtra("prenom"));
            etageEditText.setText(String.valueOf(intent.getIntExtra("etage", 0)));
            emailSaisie.setText(intent.getStringExtra("email"));
            superficieEditText.setText(String.valueOf(intent.getIntExtra("superficie", 0)));
            mdpEditText.setText(intent.getStringExtra("mdp"));


        }
    }

    private void updateUserInfo() {
        String nouveauNom = nomEditText.getText().toString().trim();
        String nouveauPrenom = prenomEditText.getText().toString().trim();
        String nouvelEtage = etageEditText.getText().toString().trim();
        String nouvelleSuperficie = superficieEditText.getText().toString().trim();
        String nouveauMotDePasse = mdpEditText.getText().toString().trim();
        String email = emailSaisie.getText().toString();

        if (nouveauNom.isEmpty() || nouveauPrenom.isEmpty() || nouvelEtage.isEmpty() || nouvelleSuperficie.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show();
        } else {
            sendUpdateRequest(nouveauNom, nouveauPrenom, nouvelEtage, nouvelleSuperficie, nouveauMotDePasse, email);
        }
    }


    private void sendUpdateRequest(String nouveauNom, String nouveauPrenom, String nouvelEtage,
                                   String nouvelleSuperficie, String nouveauMotDePasse, String email) {
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("nom", nouveauNom);
            requestBody.put("prenom", nouveauPrenom);
            requestBody.put("etage", nouvelEtage);
            requestBody.put("superficie", nouvelleSuperficie);
            requestBody.put("mot_de_passe", nouveauMotDePasse);
            requestBody.put("email", email);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://10.0.2.2/devmobile/actions/changeinfo.php", requestBody,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            Toast.makeText(this, "Informations mises à jour avec succès", Toast.LENGTH_SHORT).show();
                            setResult(AppCompatActivity.RESULT_OK);
                            finish();
                        } else {
                            String message = response.getString("message");
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Erreur lors de la mise à jour des informations", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    if (error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        String errorMessage = new String(error.networkResponse.data);
                        Toast.makeText(this, "HTTP Status Code: " + statusCode + "Error Message: " + errorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Unknown error occurred.", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(request);
    }
}
