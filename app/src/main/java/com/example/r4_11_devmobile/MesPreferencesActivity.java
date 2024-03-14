package com.example.r4_11_devmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;

public class MesPreferencesActivity extends AppCompatActivity {
    ImageView imageview;
    SwitchCompat switchCompat;
    SharedPreferences sharedPreferences= null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_preferences);
        imageview = findViewById(R.id.imageView);
        switchCompat =findViewById(R.id.switchCompat);
        sharedPreferences = getSharedPreferences("night", 0);
        boolean booleanValue =sharedPreferences.getBoolean("night_mode", true );
        if(booleanValue){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switchCompat.setChecked(true);
            imageview.setImageResource(R.drawable.nuit);

        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    switchCompat.setChecked(true);
                    imageview.setImageResource(R.drawable.modejour);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("night_mode",true);
                    editor.commit();

                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    switchCompat.setChecked(false);
                    imageview.setImageResource(R.drawable.nuit);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("night_mode",false);
                    editor.commit();
                }

            }
        });
    }
}