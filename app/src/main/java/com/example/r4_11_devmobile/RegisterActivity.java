package com.example.r4_11_devmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.r4_11_devmobile.bd.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    private String nom;

    private String prenom;

    private String mdp;

    private String email;

    private String etage ;
    private String superficie;





    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        databaseManager = new DatabaseManager(getApplicationContext());



        Button loginBtn = findViewById(R.id.loginBtn);
        TextView btncreecompte = findViewById(R.id.textView5);


        EditText nom_ = findViewById(R.id.nom);
        EditText mdp_ = findViewById(R.id.confirmeMdp);
        EditText prenom_ = findViewById(R.id.prenom);
        EditText email_ = findViewById(R.id.mdp);
        EditText etage_ = findViewById(R.id.etage);
        EditText superficie_ = findViewById(R.id.superficie);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 nom = nom_.getText().toString();
                 mdp = mdp_.getText().toString();
                 prenom = prenom_.getText().toString();
                 email = email_.getText().toString();
                 etage = etage_.getText().toString();
                 superficie = superficie_.getText().toString();



                creationCompte();
            }
        });



        btncreecompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



    }

    public void ApiResponse(JSONObject reponse){
        Boolean success = null;
        String error = "";

        try{
            success = reponse.getBoolean("success");

            if(success == true ){
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("username", nom);
                startActivity(intent);
                finish();

            }else {
                error = reponse.getString("erreur");
                Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();

            }

        }catch(JSONException e){
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

        }
    }

    public void creationCompte(){
        String url ="http://10.0.2.2/devmobile/actions/createAccount.php";

        Map<String, String> parametres = new HashMap<>();
        parametres.put("nom", nom);
        parametres.put("prenom", prenom);
        parametres.put("email", email);
        parametres.put("mdp", mdp);
        parametres.put("etage", etage);
        parametres.put("superficie", superficie);

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

}