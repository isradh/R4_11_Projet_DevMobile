package com.example.r4_11_devmobile.Equipement;

import com.example.r4_11_devmobile.R;
import com.example.r4_11_devmobile.UserId;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EquipementFragment extends Fragment {

    private View view;

    private ListView listView;

    private int nbEquipement ;

    ImageView ajoutAlerte;

    ImageView ajoutpetit;

    private int wattotale;

    private String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_equipement, container, false);

        userId = UserId.getUserId();


        listView = view.findViewById(R.id.ancienneAlertelistView);

        ajoutAlerte = view.findViewById(R.id.ajoutAlerte);
        ajoutpetit = view.findViewById(R.id.ajoutpetit);

        ajoutAlerte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewEquipmentActivity.class);
                startActivity(intent);
            }
        });

        ajoutpetit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewEquipmentActivity.class);
                startActivity(intent);
            }
        });

        connectUser(userId);

        return view;
    }


    public void connectUser(String userId) {
        String url = "http://10.0.2.2/devmobile/actions/recupEquipement.php?userId=" + userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        handleResponse(response);
                        if (nbEquipement > 0) {
                            listView.setVisibility(View.VISIBLE);
                            ajoutAlerte.setVisibility(View.INVISIBLE);
                            TextView nbequipement = view.findViewById(R.id.hello);
                            nbequipement.setText("Vous avez " + nbEquipement + " équipements !");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError erreur) {
                Toast.makeText(getContext(), erreur.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }

    private void handleResponse(JSONArray response) {
        ArrayList<Equipement> equipementList = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String idStr = jsonObject.getString("id");
                int id = Integer.parseInt(idStr);
                String nom = jsonObject.getString("nom");
                String wattageStr = jsonObject.getString("wattage");
                int wattage = Integer.parseInt(wattageStr);
                String imageUrl = jsonObject.getString("path");
                equipementList.add(new Equipement(id, nom, wattage, imageUrl));
                wattotale += wattage;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        EquipmentAdaptateur adapter = new EquipmentAdaptateur(getContext(), equipementList);
        listView.setAdapter(adapter);
        nbEquipement = equipementList.size();
        TextView puissanceGlobal = view.findViewById(R.id.puissanceGlobal);
        puissanceGlobal.setText("Puissance maximale :" + wattotale);
    }


}