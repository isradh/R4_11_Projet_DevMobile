package com.example.r4_11_devmobile;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class PreferencesFragment extends Fragment {
    ImageView imageview;
    SwitchCompat switchCompat;
    SharedPreferences sharedPreferences = null;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_preferences, container, false);
        imageview = rootView.findViewById(R.id.imageView);
        switchCompat = rootView.findViewById(R.id.switchCompat);
        sharedPreferences = requireActivity().getSharedPreferences("night", 0);

        boolean booleanValue = sharedPreferences.getBoolean("night_mode", true);
        switchCompat.setChecked(booleanValue);

        if (booleanValue) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            imageview.setImageResource(R.drawable.nuit);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            imageview.setImageResource(R.drawable.modejour);
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    imageview.setImageResource(R.drawable.nuit);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    imageview.setImageResource(R.drawable.modejour);
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("night_mode", isChecked);
                editor.apply();
            }
        });

        return rootView;
    }
}
