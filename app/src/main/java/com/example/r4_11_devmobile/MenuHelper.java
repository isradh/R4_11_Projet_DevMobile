package com.example.r4_11_devmobile;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.r4_11_devmobile.AccueilActivity;
import com.example.r4_11_devmobile.EspaceClientActivity;
import com.google.android.material.navigation.NavigationView;

public class MenuHelper {

    public static void initializeMenu(final Context context, NavigationView navigationView, final DrawerLayout drawerLayout, androidx.appcompat.widget.Toolbar toolbar) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_habitats) {
                    Intent intent = new Intent(context, EspaceClientActivity.class);
                    context.startActivity(intent);
                    // Ajoutez d'autres actions de menu ici si nécessaire
                }
                drawerLayout.closeDrawers();
                return true;
            }

        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle((AccueilActivity) context, drawerLayout, toolbar, R.string.navigation_drawer_close, R.string.navigation_drawer_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}
