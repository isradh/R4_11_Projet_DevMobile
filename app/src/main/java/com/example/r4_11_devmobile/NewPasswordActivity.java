package com.example.r4_11_devmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class NewPasswordActivity extends AppCompatActivity {

    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        passwordEditText = findViewById(R.id.mdp);
        confirmPasswordEditText = findViewById(R.id.confirmeMdp);

        TextView btnco = findViewById(R.id.back);
        btnco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button resetPasswordButton = findViewById(R.id.loginBtn);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();


                if (password.equals(confirmPassword)) {

                    changePassword(password);
                } else {
                    Toast.makeText(NewPasswordActivity.this, "Les mots de passe ne correspondent pas.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void changePassword(String newPassword) {

        String email = getIntent().getStringExtra("email");


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("newPassword", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url = "http://10.0.2.2/devmobile/actions/changepassword.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            String message = response.getString("message");
                            if (success) {

                                Toast.makeText(NewPasswordActivity.this, message, Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(NewPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NewPasswordActivity.this, "Erreur de réponse du serveur", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NewPasswordActivity.this, "Erreur de connexion au serveur", Toast.LENGTH_SHORT).show();
                    }
                });


        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
