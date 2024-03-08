package com.example.r4_11_devmobile;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResidentActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident);

        listView = findViewById(R.id.ancienneAlertelistView);


        connectUser();
    }

    public void connectUser() {
        String url = "http://10.0.2.2/devmobile/actions/recupAllUser.php";
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
        ArrayList<Resident> residents = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String id = jsonObject.getString("idUser");
                String nom = jsonObject.getString("nom");
                String prenom = jsonObject.getString("prenom");
                String etageStr = jsonObject.getString("etage");
                int etage = Integer.parseInt(etageStr);
                residents.add(new Resident(id, nom, prenom, 1, etage));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ResidentAdapter adapter = new ResidentAdapter(ResidentActivity.this, residents);
        listView.setAdapter(adapter);
    }
}
