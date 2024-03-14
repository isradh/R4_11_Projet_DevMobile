package com.example.r4_11_devmobile;

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


public class NotificationFragment extends Fragment {

    private View view;

    private ListView listView;

    private int nbEquipement ;

    ImageView ajoutAlerte;

    ImageView ajoutpetit;



    private String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notification, container, false);

        userId = UserId.getUserId();


        listView = view.findViewById(R.id.listereservation);

        recupNotif(userId);

        return view;
    }


    public void recupNotif(String userId) {
        String url = "http://10.0.2.2/devmobile/actions/recupNotification.php?userId=" + userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        handleResponse(response);

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
        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String mess = jsonObject.getString("message");
                String date = jsonObject.getString("DateNotif");
                messages.add(new Message(mess, date));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        NotificationAdaptateur adapter = new NotificationAdaptateur(getContext(), messages);
        listView.setAdapter(adapter);
        nbEquipement = messages.size();
        TextView hello = view.findViewById(R.id.hello);
        hello.setText("Vous avez" + nbEquipement + "équipements !");


    }

}