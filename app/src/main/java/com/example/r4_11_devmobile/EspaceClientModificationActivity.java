package com.example.r4_11_devmobile;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class EspaceClientModificationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_client_modification);

        TextView ecoCoinsTextView = findViewById(R.id.ecoCoins);
        ecoCoinsTextView.setPaintFlags(ecoCoinsTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView infoTextView = findViewById(R.id.info);
        infoTextView.setPaintFlags(ecoCoinsTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_habitats) {
            Intent intentHabitats = new Intent(EspaceClientModificationActivity.this,MonHabitatActivity.class);
            startActivity(intentHabitats);
        } else if (itemId == R.id.nav_notif) {
            Intent intentNotif = new Intent(EspaceClientModificationActivity.this, NotificationsActivity.class);
            startActivity(intentNotif);
        } else if (itemId == R.id.nav_preference) {
            Intent intentPref = new Intent(EspaceClientModificationActivity.this, PreferencesActivity.class);
            startActivity(intentPref);
        } else if (itemId == R.id.nav_monhabitat) {
            Intent intentMonHabitat = new Intent(EspaceClientModificationActivity.this, MonHabitatActivity.class);
            startActivity(intentMonHabitat);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
}

