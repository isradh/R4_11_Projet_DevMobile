package com.example.r4_11_devmobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class EspaceClientModificationActivity extends AppCompatActivity {

    private EditText nomEditText, prenomEditText, etageEditText, superficieEditText, mailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_client_modification);

        // Récupérer les références des champs EditText
        nomEditText = findViewById(R.id.nomSaisie);
        prenomEditText = findViewById(R.id.prenomSaisie);
        etageEditText = findViewById(R.id.etageSaisie);
        superficieEditText = findViewById(R.id.superficieSaisie);
        mailEditText = findViewById(R.id.mailSaisie);

        // Remplir les champs EditText avec les données de l'utilisateur
        fillUserInfo();

        // Configurer le bouton pour enregistrer les modifications
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> updateUserInfo());
    }

    private void fillUserInfo() {
        // Récupérer les informations de l'utilisateur depuis l'intent
        Intent intent = getIntent();
        if (intent != null) {
            nomEditText.setText(intent.getStringExtra("nom"));
            prenomEditText.setText(intent.getStringExtra("prenom"));
            etageEditText.setText(String.valueOf(intent.getIntExtra("etage", 0)));
            superficieEditText.setText(String.valueOf(intent.getIntExtra("superficie", 0)));
            mailEditText.setText(intent.getStringExtra("email"));
        }
    }

    private void updateUserInfo() {
        String nouveauNom = nomEditText.getText().toString().trim();
        String nouveauPrenom = prenomEditText.getText().toString().trim();
        String nouvelEtage = etageEditText.getText().toString().trim();
        String nouvelleSuperficie = superficieEditText.getText().toString().trim();
        String nouveauMail = mailEditText.getText().toString().trim();

        // Vérifier les champs obligatoires
        if (nouveauNom.isEmpty() || nouveauPrenom.isEmpty() || nouvelEtage.isEmpty() || nouvelleSuperficie.isEmpty() || nouveauMail.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show();
        } else {
            sendUpdateRequest(nouveauNom, nouveauPrenom, nouvelEtage, nouvelleSuperficie, nouveauMail);
        }
    }


    // Modifier la fonction sendUpdateRequest() pour afficher un message d'erreur en cas de problème lors de la mise à jour des informations
    private void sendUpdateRequest(String nouveauNom, String nouveauPrenom, String nouvelEtage,
                                   String nouvelleSuperficie, String nouveauMail) {
        // Créer un objet JSON contenant les nouvelles informations de l'utilisateur
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("nom", nouveauNom);
            requestBody.put("prenom", nouveauPrenom);
            requestBody.put("etage", nouvelEtage);
            requestBody.put("superficie", nouvelleSuperficie);
            requestBody.put("email", nouveauMail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Envoyer la requête HTTP au serveur
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://10.0.2.2/devmobile/actions/changeinfo.php", requestBody,
                response -> {
                    // Gérer la réponse du serveur
                    try {
                        Log.d("Server Response", response.toString()); // Log de la réponse serveur
                        boolean success = response.getBoolean("success");
                        if (success) {
                            Toast.makeText(this, "Informations mises à jour avec succès", Toast.LENGTH_SHORT).show();
                            finish(); // Terminer l'activité après la mise à jour réussie
                        } else {
                            String message = response.getString("message");
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Erreur lors de la mise à jour des informations2", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    if (error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        String errorMessage = new String(error.networkResponse.data);
                        Log.e("Volley Error", "HTTP Status Code: " + statusCode);
                        Log.e("Volley Error", "Error Message: " + errorMessage);
                    } else {
                        Log.e("Volley Error", "Unknown error occurred.");
                    }
                    Toast.makeText(this, "Erreur lors de la mise à jour des informations1", Toast.LENGTH_SHORT).show();
                });

        // Ajouter la requête à la file d'attente de Volley pour l'exécution
        Volley.newRequestQueue(this).add(request);
    }



}
