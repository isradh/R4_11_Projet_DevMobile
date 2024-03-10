package com.example.r4_11_devmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EquipmentAdaptateur extends ArrayAdapter<Equipement> {


    public EquipmentAdaptateur(Context context, ArrayList<Equipement> equipement) {
        super(context, 0, equipement);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Equipement equipement = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_equipement, parent, false);
        }
        TextView textViewNom = convertView.findViewById(R.id.NomText);
        TextView textViewWattage= convertView.findViewById(R.id.wattageText);

        textViewNom.setText(equipement.getNom());
        textViewWattage.setText(String.valueOf(equipement.getWattage()));

        return convertView;
    }
}
