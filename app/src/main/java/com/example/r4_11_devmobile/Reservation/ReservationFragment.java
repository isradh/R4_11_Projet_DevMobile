package com.example.r4_11_devmobile.Reservation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.r4_11_devmobile.R;
import com.example.r4_11_devmobile.UserId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ReservationFragment extends Fragment {

    private View view;
    private ListView listView;
    private ArrayList<Reservation> reservations;

    private int nbReservation ;

    private TextView titre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_reservation, container, false);
        listView = view.findViewById(R.id.listereservation);
        titre = view.findViewById(R.id.hello);


        String userId = UserId.getUserId();
        recupReservation(userId);


        return view;
    }

    public void recupReservation(String userId) {
        String url = "http://10.0.2.2/devmobile/actions/recupAllreservation.php?userId=" + userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        handleResponse(response);

                        if (nbReservation > 0) {
                            TextView nbequipement = view.findViewById(R.id.hello);
                            titre.setText("Vous avez " + nbReservation + " équipements !");
                        }else{
                            titre.setText("Vous n'avez pas encore effectuer de reservation");

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
        reservations = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String equipement = jsonObject.getString("equipement");
                String dateReservation = jsonObject.getString("date_reservation");
                String heureDebut = jsonObject.getString("heure_debut");
                String heureFin = jsonObject.getString("heure_fin");
                String id_ = jsonObject.getString("id");
                int id = Integer.parseInt(id_);


                Reservation reservation = new Reservation(id, equipement, dateReservation, heureDebut, heureFin);
                reservations.add(reservation);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("ReservationFragment", "Nombre de réservations récupérées : " + reservations.size());


        ReservationAdaptateur adapter = new ReservationAdaptateur(getContext(), reservations);
        listView.setAdapter(adapter);
        nbReservation = reservations.size();

        adapter.notifyDataSetChanged();

    }
}
