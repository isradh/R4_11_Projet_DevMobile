package com.example.r4_11_devmobile.bd;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DatabaseManager {

    private Context context;
    public RequestQueue queue;


    public DatabaseManager(Context context){
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

}
