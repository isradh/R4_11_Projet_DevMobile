package com.example.r4_11_devmobile;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.r4_11_devmobile.bd.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReservationAdaptateur extends ArrayAdapter<Reservation> {

    private DatabaseManager databaseManager;
    private String userId;
    private Reservation reservation;

    public ReservationAdaptateur(Context context, ArrayList<Reservation> reservations) {
        super(context, 0, reservations);
        databaseManager = new DatabaseManager(context);
        userId = UserId.getUserId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        reservation = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_reservation, parent, false);
        }

        TextView textViewEquipement = convertView.findViewById(R.id.NbequipementText);
        TextView textViewDateReservation = convertView.findViewById(R.id.idtext);
        TextView textViewHeureDebut = convertView.findViewById(R.id.NomText);
        TextView textViewHeureFin = convertView.findViewById(R.id.prenomText);

        textViewEquipement.setText(reservation.getEquipement());
        textViewDateReservation.setText(reservation.getDateReservation().toString());
        textViewHeureDebut.setText(reservation.getHeureDebut().toString());
        textViewHeureFin.setText(reservation.getHeureFin().toString());

        CardView cardetage = convertView.findViewById(R.id.cardetage);

        cardetage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirmation de suppression");
                builder.setMessage("Voulez-vous vraiment supprimer cette réservation ?");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        supprimerReservation(reservation.getId());
                    }
                });
                builder.setNegativeButton("Non", null);
                builder.show();
            }
        });

        return convertView;
    }

    public void ApiResponse(JSONObject response) {
        try {
            boolean success = response.getBoolean("success");
            if (success) {
                Toast.makeText(getContext(), "Votre réservation a bien été supprimée", Toast.LENGTH_SHORT).show();
            } else {
                if (response.has("erreur")) {
                    String error = response.getString("erreur");
                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Une erreur inconnue s'est produite", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public void supprimerReservation(int reservationId) {
        String url = "http://10.0.2.2/devmobile/actions/supprimerReservation.php?id=" + reservationId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ApiResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {
                        Toast.makeText(getContext(), erreur.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        databaseManager.queue.add(jsonObjectRequest);
    }
}
