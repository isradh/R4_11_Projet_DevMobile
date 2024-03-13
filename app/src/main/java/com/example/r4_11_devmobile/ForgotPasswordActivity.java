package com.example.r4_11_devmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {


    private static EditText adressMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        adressMail = findViewById(R.id.adressMail);

        TextView textView = findViewById(R.id.back);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        Button page2 = findViewById(R.id.loginBtn);
        page2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), resetCode.class);
                intent.putExtra("email", adressMail.getText().toString());


                startActivity(intent);
                reinitialisation();
            }
        });



    }

    public static String getEmail() {
        return adressMail.getText().toString().trim();
    }


    private void reinitialisation(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2/devmobile/actions/sendResetCode.php";

        Map<String, String>  params = new HashMap<>();
        params.put("email", adressMail.getText().toString());
        JSONObject parametre = new JSONObject(params);


        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, parametre,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ForgotPasswordActivity.this, "Un e-mail vous a été envoyé, vérifiez dans vos spams.", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {
                        Toast.makeText(getApplicationContext(), erreur.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {

        };
        queue.add(postRequest);
    }
}