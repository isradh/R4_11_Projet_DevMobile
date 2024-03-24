package com.example.r4_11_devmobile.Equipement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.r4_11_devmobile.R;

import java.util.ArrayList;

public class EquipmentAdaptateur extends ArrayAdapter<Equipement> {


    public EquipmentAdaptateur(Context context, ArrayList<Equipement> equipement) {
        super(context, 0, equipement);
    }


    // Dans votre adaptateur EquipmentAdaptateur
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Equipement equipement = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_equipement, parent, false);
        }

        TextView textid = convertView.findViewById(R.id.idtext);
        TextView textViewNom = convertView.findViewById(R.id.NomText);
        TextView textViewWattage= convertView.findViewById(R.id.wattageText);
        ImageView imageView = convertView.findViewById(R.id.imagequipement);

        textViewNom.setText(equipement.getNom());
        textViewWattage.setText(String.valueOf(equipement.getWattage()));
        textid.setText(String.valueOf(equipement.getId()));

        //String imageUrl = "../../../../../../../../../" + equipement.getImageUrl();

        String imageUrl = "http://10.0.2.2/devmobile/actions/" + equipement.getImageUrl();

        Glide.with(getContext())
                .load(imageUrl)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(imageView);

        return convertView;
    }

}
