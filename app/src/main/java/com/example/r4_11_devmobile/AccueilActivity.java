package com.example.r4_11_devmobile;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;


import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;
public class AccueilActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;

    public static final int NAV_HABITATS = R.id.nav_habitats;
    public static final int NAV_NOTIF = R.id.nav_notif;
    public static final int NAV_PREFERENCE = R.id.nav_preference;
    public static final int NAV_MONHABITAT = R.id.nav_monhabitat;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        textView=findViewById(R.id.textView);
        toolbar=findViewById(R.id.toolbar);

        navigationView.bringToFront();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_close,R.string.navigation_drawer_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_habitats);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.nav_habitats) {
                    Intent intent = new Intent(AccueilActivity.this, MonHabitatActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.nav_notif) {
                    Intent i = new Intent(AccueilActivity.this, NotificationsActivity.class);
                    startActivity(i);
                } else if (itemId == R.id.nav_preference) {
                    Intent pref = new Intent(AccueilActivity.this, PreferencesActivity.class);
                    startActivity(pref);
                } else if (itemId == R.id.nav_profil) {
                    Intent intent = new Intent(AccueilActivity.this, EspaceClientActivity.class);
                    startActivity(intent);
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
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == MenuIds.NAV_HABITATS) {
            Intent intent = new Intent(AccueilActivity.this, MonHabitatActivity.class);
            startActivity(intent);
        } else if (itemId == MenuIds.NAV_NOTIF) {
            Intent i = new Intent(AccueilActivity.this, NotificationsActivity.class);
            startActivity(i);
        } else if (itemId == MenuIds.NAV_PREFERENCE) {
            Intent pref = new Intent(AccueilActivity.this, PreferencesActivity.class);
            startActivity(pref);
        } else if (itemId == MenuIds.NAV_MONHABITAT) {
            Intent habitat = new Intent(AccueilActivity.this, MonHabitatActivity.class);
            startActivity(habitat);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }







}