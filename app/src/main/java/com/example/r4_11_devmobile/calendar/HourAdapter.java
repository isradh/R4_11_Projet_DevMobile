package com.example.r4_11_devmobile.calendar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.r4_11_devmobile.R;

import static com.example.r4_11_devmobile.calendar.CalendarUtils.*;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HourAdapter extends ArrayAdapter<HourEvent>
{

    String day;
    String hour;

    String hourTV;
    int total_wattage;
    int max_wattage;
    TextView timeTV;

    private List<LocalTime> loadedTimes = new ArrayList<>();


    View convertView;


    private List<String> loadedDays = new ArrayList<>();



    public HourAdapter(@NonNull Context context, List<HourEvent> hourEvents)
    {
        super(context, 0, hourEvents);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HourEvent event = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hour_cell, parent, false);
        }



        recupConsommationHoraire(convertView, event.time);


        return convertView;
    }


    private void setHour(View convertView, LocalTime time)
    {


    }

    private void setEvents(View convertView, ArrayList<Event> events)
    {

    }

    private void setEvent(TextView textView, Event event)
    {
        textView.setText(event.getName());
        textView.setVisibility(View.VISIBLE);
    }


    private void recupConsommationHoraire(View convertView, LocalTime time) {
        String url = "http://10.0.2.2/devmobile/actions/calendrier.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        handleResponse(response,convertView, time );
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError erreur) {
                Toast.makeText(getContext(), erreur.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }
    private void hideEvent(TextView tv)
    {
        tv.setVisibility(View.INVISIBLE);
    }

    private void handleResponse(JSONArray response, View convertView,LocalTime time) {
        //int couleurFond = Color.GRAY; // Couleur par défaut


        for (int i = 0; i < response.length(); i++) {

            try {

                JSONObject jsonObject = response.getJSONObject(i);
                day = jsonObject.getString("day");
                hour = jsonObject.getString("hour");


                String totalWattageStr = jsonObject.getString("total_wattage");
                String maxWattageStr = jsonObject.getString("max_wattage");

                total_wattage = Integer.parseInt(totalWattageStr);
                max_wattage = Integer.parseInt(maxWattageStr);

                TextView wattageTV = convertView.findViewById(R.id.wattageTV);

                timeTV = convertView.findViewById(R.id.timeTV);
                hourTV = CalendarUtils.formattedShortTime(time);
                timeTV.setText(hourTV);
                String avantLesDeuxPoints = hourTV.substring(0, 2);
               // Toast.makeText(getContext(), avantLesDeuxPoints,Toast.LENGTH_LONG).show();


                if (day.equals(CalendarUtils.selectedDate.toString())) {
                    if (hour.length() == 1) {
                        hour = "0" + hour;
                    }
                    //String[] elements = hourTV.split(":");

                    //Toast.makeText(getContext(), "JSON TIME" + hour ,Toast.LENGTH_LONG).show();

                    // Toast.makeText(getContext(), "APP TIME " + avantLesDeuxPoints ,Toast.LENGTH_LONG).show();

                    int percentage = (total_wattage * 100) / max_wattage;

                    if (hour.equals(avantLesDeuxPoints)) {
                        //Toast.makeText(getContext(), "JSON TIME" + hour + "AUTRE " + avantLesDeuxPoints, Toast.LENGTH_LONG).show();
                        wattageTV.setText( percentage + "%");

                        CardView couleurLayout = convertView.findViewById(R.id.poucentage);


                        if (percentage <= 30) {
                            couleurLayout.setCardBackgroundColor(Color.GREEN);
                            Log.d("HourAdapter", avantLesDeuxPoints + "__ " + i + " Setting color to RED");
                        } else if (percentage >= 30 && percentage <= 70) {
                            couleurLayout.setCardBackgroundColor(Color.rgb(255, 165, 0));

                            Log.d("HourAdapter", avantLesDeuxPoints + "__ " + i + " Setting color to BLUE");
                        } else {
                            couleurLayout.setCardBackgroundColor(Color.RED);
                            Log.d("HourAdapter", avantLesDeuxPoints + "__ " + i+ " Setting color to GRAY");
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

}













