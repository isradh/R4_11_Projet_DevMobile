package com.example.r4_11_devmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
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

public class
EspaceClientModificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_client_modification);

        TextView ecoCoinsTextView = findViewById(R.id.ecoCoins);
        ecoCoinsTextView.setPaintFlags(ecoCoinsTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView infoTextView = findViewById(R.id.info);
        infoTextView.setPaintFlags(ecoCoinsTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


    }





}