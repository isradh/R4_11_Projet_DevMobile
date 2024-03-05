package com.example.r4_11_devmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.r4_11_devmobile.bd.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;

    private DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseManager = new DatabaseManager(getApplicationContext());

        Button loginBtn = findViewById(R.id.loginBtn);
        TextView btncreecompte = findViewById(R.id.textView5);
        EditText usernameLN = findViewById(R.id.mdp);
        EditText passwordLN = findViewById(R.id.confirmeMdp);
        TextView mdpLost = findViewById(R.id.lost);

        mdpLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), mdpOublieActivity.class);
                startActivity(intent);
            }
        });
        btncreecompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameLN.getText().toString();
                password = passwordLN.getText().toString();
                connectUser();
            }
        });

        btncreecompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
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
                    Intent intent = new Intent(getApplicationContext(), AccueilActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();

                }else {
                    error = reponse.getString("erreur");
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();

                }

            }catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

            }
        }
        public void connectUser(){
            String url ="http://10.0.2.2/devmobile/actions/connectUser.php";

            Map<String, String> parametres = new HashMap<>();
            parametres.put("email", username);
            parametres.put("mdp", password);
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

            databaseManager.queue.add(jsonObjectRequest);
        }

    }



