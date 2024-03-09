package com.example.r4_11_devmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EspaceClientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_client);

        TextView ecoCoinsTextView = findViewById(R.id.ecoCoins);
        ecoCoinsTextView.setPaintFlags(ecoCoinsTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView infoTextView = findViewById(R.id.info);
        infoTextView.setPaintFlags(infoTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Button modif = findViewById(R.id.modif);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_habitats) {
            Intent intentHabitats = new Intent(EspaceClientActivity.this, MonHabitatActivity.class);
            startActivity(intentHabitats);
        } else if (itemId == R.id.nav_notif) {
            Intent intentNotif = new Intent(EspaceClientActivity.this, NotificationsActivity.class);
            startActivity(intentNotif);
        } else if (itemId == R.id.nav_preference) {
            Intent intentPref = new Intent(EspaceClientActivity.this, PreferencesActivity.class);
            startActivity(intentPref);
        } else if (itemId == R.id.nav_monhabitat) {
            Intent intentMonHabitat = new Intent(EspaceClientActivity.this, MonHabitatActivity.class);
            startActivity(intentMonHabitat);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleResponse(JSONArray response) {
        try {
            if (response.length() > 0) {
                JSONObject jsonObject = response.getJSONObject(0);
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
