package com.example.r4_11_devmobile.Password;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.r4_11_devmobile.R;

import org.json.JSONException;
import org.json.JSONObject;

public class resetCode extends AppCompatActivity {

    private String email;
    private EditText resetCodeInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_code);

        email = getIntent().getStringExtra("email");


        resetCodeInput = findViewById(R.id.resetCodeInput);

        Button btnReset = findViewById(R.id.reset);

        btnReset.setOnClickListener(v -> {

            String resetCode = resetCodeInput.getText().toString();


            verifyResetCode(email, resetCode);
        });
    }

    private void verifyResetCode(String email, String resetCode) {

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("email", email);
            requestBody.put("resetCode", resetCode);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://10.0.2.2/devmobile/actions/verifresetcode.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        String message = response.getString("message");
                        if (success) {

                            Intent intent = new Intent(resetCode.this, NewPasswordActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);

                            finish();
                        } else {


                            Toast.makeText(resetCode.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(resetCode.this, "Erreur de connexion1", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(resetCode.this, "Erreur de connexion2: " + error.getMessage(), Toast.LENGTH_SHORT).show());


        requestQueue.add(jsonObjectRequest);
    }
}