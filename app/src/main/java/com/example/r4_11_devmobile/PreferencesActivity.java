package com.example.r4_11_devmobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class PreferencesActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView textView;

    // Constantes pour les identifiants des éléments de menu
    private static final int NAV_HABITATS = R.id.nav_habitats;
    private static final int NAV_NOTIF = R.id.nav_notif;
    private static final int NAV_PREFERENCE = R.id.nav_preference;
    private static final int NAV_MONHABITAT = R.id.nav_monhabitat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mespreferences);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        textView = findViewById(R.id.textView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_close, R.string.navigation_drawer_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(NAV_HABITATS);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_habitats) {
                    startActivity(new Intent(PreferencesActivity.this, MonHabitatActivity.class));
                } else if (itemId == R.id.nav_notif) {
                    startActivity(new Intent(PreferencesActivity.this, NotificationsActivity.class));
                } else if (itemId == R.id.nav_preference) {
                    startActivity(new Intent(PreferencesActivity.this, PreferencesActivity.class));
                } else if (itemId == R.id.nav_monhabitat) {
                    startActivity(new Intent(PreferencesActivity.this, MonHabitatActivity.class));
                }else if (itemId == R.id.nav_profil) {
                    Intent intentMonProfil = new Intent(PreferencesActivity.this,EspaceClientActivity.class);
                    startActivity(intentMonProfil);
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
