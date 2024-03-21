package com.example.r4_11_devmobile.Notification;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.r4_11_devmobile.R;

import java.util.ArrayList;

public class NotificationAdaptateur extends ArrayAdapter<Message> {


    public NotificationAdaptateur(Context context, ArrayList<Message> Message) {
        super(context, 0, Message);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_notification, parent, false);
        }

        TextView textMessage =  convertView.findViewById(R.id.message);
        TextView textViewNom = convertView.findViewById(R.id.dateM);

        textViewNom.setText(message.getDateM());
        textMessage.setText(String.valueOf(message.getMessage()));

        return convertView;
    }
}

