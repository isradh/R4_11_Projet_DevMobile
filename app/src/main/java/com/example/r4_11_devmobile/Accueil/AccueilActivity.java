package com.example.r4_11_devmobile.Accueil;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.r4_11_devmobile.Apropos.AProposFragment;
import com.example.r4_11_devmobile.Equipement.EquipementFragment;
import com.example.r4_11_devmobile.EspaceClient.EspaceClientFragment;
import com.example.r4_11_devmobile.Notification.NotificationFragment;
import com.example.r4_11_devmobile.Preferences.PreferencesFragment;
import com.example.r4_11_devmobile.R;
import com.example.r4_11_devmobile.RegisterLogin.LoginActivity;
import com.example.r4_11_devmobile.Reservation.ReservationFragment;
import com.example.r4_11_devmobile.Resident.ResidentFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
public class AccueilActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);


        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        if (getSupportFragmentManager().findFragmentById(R.id.frameLayout) == null) {

            replaceFragment(new AccueilFragment());
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_equipement) {
                    replaceFragment(new EquipementFragment());
                    toolbar.setTitle("Mes équipements");

                }
                else if(item.getItemId() == R.id.nav_resident){
                    replaceFragment(new ResidentFragment());
                    toolbar.setTitle("Residents");
                }
                else if(item.getItemId() == R.id.nav_espaceclient){
                    replaceFragment(new EspaceClientFragment());
                    toolbar.setTitle("Espace Client");

                } else if (item.getItemId() == R.id.nav_reservation) {
                    replaceFragment(new ReservationFragment());
                    toolbar.setTitle("Mes reservations");
                }else if (item.getItemId() == R.id.nav_accueil){
                    replaceFragment(new AccueilFragment());
                    toolbar.setTitle("Accueil");
                }else if (item.getItemId() == R.id.nav_apropos){
                    replaceFragment(new AProposFragment());
                    toolbar.setTitle("A Propos");
                }else if (item.getItemId() == R.id.nav_preferences){
                    replaceFragment(new PreferencesFragment());
                    toolbar.setTitle("Mes préférences");
                }else if (item.getItemId() == R.id.nav_notification){
                    replaceFragment(new NotificationFragment());
                    toolbar.setTitle("Mes Notifications");
                }else if (item.getItemId() == R.id.nav_deconnection){
                    finish();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
            }






                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });






    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }




}