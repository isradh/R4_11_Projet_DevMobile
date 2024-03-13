package com.example.r4_11_devmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class EspaceClientFragment extends Fragment {

    private View view;
    private String etageStr;
    private String userId;

    private String email;

    public EspaceClientFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_espace_client, container, false);

        TextView ecoCoinsTextView = view.findViewById(R.id.ecoCoins);
        ecoCoinsTextView.setPaintFlags(ecoCoinsTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView infoTextView = view.findViewById(R.id.info);
        infoTextView.setPaintFlags(infoTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        userId = UserId.getUserId();
        connectUser(userId);

        return view;
    }

    private void connectUser(String userId) {
        String url = "http://10.0.2.2/devmobile/actions/recupInfoUser.php?userId=" + userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        handleResponse(response);
                        setModifButton();
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
        try {
            if (response.length() > 0) {
                JSONObject jsonObject = response.getJSONObject(0);
                String nom = jsonObject.getString("nom");
                String prenom = jsonObject.getString("prenom");

                etageStr = jsonObject.getString("etage");
                String superficieStr = jsonObject.getString("superficie");
                email = jsonObject.getString("email");

                TextView emailTextView = view.findViewById(R.id.emailSaisie);
                emailTextView.setText(email);

                TextView nomTextView = view.findViewById(R.id.nomSaisie);
                nomTextView.setText(nom);

                TextView prenomTextView = view.findViewById(R.id.prenomSaisie);
                prenomTextView.setText(prenom);

                TextView etageTextView = view.findViewById(R.id.etageSaisie);
                etageTextView.setText(etageStr);

                TextView superficieTextView = view.findViewById(R.id.superficieSaisie);
                superficieTextView.setText(superficieStr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setModifButton() {
        Button modif = view.findViewById(R.id.modif);
        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = ((TextView) view.findViewById(R.id.nomSaisie)).getText().toString();
                String prenom = ((TextView) view.findViewById(R.id.prenomSaisie)).getText().toString();
                int etage = Integer.parseInt(etageStr);
                int superficie = Integer.parseInt(((TextView) view.findViewById(R.id.superficieSaisie)).getText().toString());

                Intent intent = new Intent(getActivity(), EspaceClientModificationActivity.class);
                intent.putExtra("nom", nom);
                intent.putExtra("prenom", prenom);
                intent.putExtra("etage", etage);
                intent.putExtra("superficie", superficie);
                intent.putExtra("email",email);
                startActivityForResult(intent, 1); // Ajouter une demande de code pour obtenir un résultat
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                connectUser(userId); // Rafraîchir les données après la modification
            }
        }
    }
}
