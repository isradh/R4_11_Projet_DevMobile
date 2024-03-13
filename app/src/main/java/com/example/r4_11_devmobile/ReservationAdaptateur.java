package com.example.r4_11_devmobile;


import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import java.util.ArrayList;

public class ReservationAdaptateur extends ArrayAdapter<Reservation> {

    public ReservationAdaptateur(Context context, ArrayList<Reservation> reservations) {
        super(context, 0, reservations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Reservation reservation = getItem(position);

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

        return convertView;
    }
}
