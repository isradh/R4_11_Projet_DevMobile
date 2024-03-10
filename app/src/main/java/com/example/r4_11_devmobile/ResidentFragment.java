package com.example.r4_11_devmobile;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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


public class ResidentFragment extends Fragment {

    private View view;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_resident, container, false);
        listView = view.findViewById(R.id.ancienneAlertelistView);
        connectUser();
        return view;
    }

    public void connectUser() {
        String url = "http://10.0.2.2/devmobile/actions/recupAllUser.php";
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
        ArrayList<Resident> residents = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String id = jsonObject.getString("idUser");
                String nom = jsonObject.getString("nom");
                String prenom = jsonObject.getString("prenom");
                String etageStr = jsonObject.getString("etage");
                int etage = Integer.parseInt(etageStr);
                residents.add(new Resident(id, nom, prenom, 1, etage));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ResidentAdapter adapter = new ResidentAdapter(getContext(), residents);
        listView.setAdapter(adapter);
    }
}
