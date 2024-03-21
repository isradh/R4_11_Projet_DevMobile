package com.example.r4_11_devmobile.Resident;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.r4_11_devmobile.R;

import java.util.ArrayList;

public class ResidentAdapter extends ArrayAdapter<Resident> {

    public ResidentAdapter(Context context, ArrayList<Resident> residents) {
        super(context, 0, residents);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Resident resident = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_resident, parent, false);
        }

        TextView textViewId = convertView.findViewById(R.id.idtext);
        TextView textViewNom = convertView.findViewById(R.id.NomText);
        TextView textViewPrenom = convertView.findViewById(R.id.prenomText);
        TextView textViewNbEquipement = convertView.findViewById(R.id.NbequipementText);
        TextView textViewEtage = convertView.findViewById(R.id.etage);


        textViewId.setText(resident.getId());
        textViewNom.setText(resident.getNom());
        textViewPrenom.setText(resident.getPrenom());
        textViewNbEquipement.setText(String.valueOf(resident.getNombreEquipement()));
        textViewEtage.setText("Etage : " + String.valueOf(resident.getEtage()));
        return convertView;
    }

}
