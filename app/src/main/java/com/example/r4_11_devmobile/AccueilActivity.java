package com.example.r4_11_devmobile;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
                    toolbar.setTitle("Espace Client");
                }
                else if(item.getItemId() == R.id.nav_resident){
                    replaceFragment(new ResidentFragment());
                    toolbar.setTitle("Resident");
                }
                else if(item.getItemId() == R.id.nav_espaceclient){
                    replaceFragment(new EspaceClientFragment());
                    toolbar.setTitle("Espace Client");

                } else if (item.getItemId() == R.id.nav_profil) {
                   /* Intent intent = new Intent(AccueilActivity.this, EspaceClientActivity.class);
                    startActivity(intent);*/

                }



                // Fermez le tiroir après avoir traité le clic sur l'élément de menu
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });



    }

    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == MenuIds.NAV_HABITATS) {
            replaceFragment(new EspaceClientFragment());
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
    }*/



    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }




}