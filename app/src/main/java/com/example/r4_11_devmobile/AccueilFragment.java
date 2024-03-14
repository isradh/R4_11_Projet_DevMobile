package com.example.r4_11_devmobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class AccueilFragment extends Fragment {
    private View view;

    public AccueilFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_accueil, container, false);

        Button ajtEq= view.findViewById(R.id.ajtEq);
        Button ajtRes= view.findViewById(R.id.ajtRes);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        ajtEq.setVisibility(View.VISIBLE);
        ajtRes.setVisibility(View.VISIBLE);

        ajtEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewEquipmentActivity.class);
                startActivity(intent);
            }
        });



        ajtRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewReservationActivity.class);
                startActivity(intent);
            }

        });
        return view;
    }







}