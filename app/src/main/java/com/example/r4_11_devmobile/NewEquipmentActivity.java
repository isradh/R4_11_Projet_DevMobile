package com.example.r4_11_devmobile;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NewEquipmentActivity extends AppCompatActivity {

    ImageView imageView;
    Button upload_btn;
    String base64Image = null;
    String nom;
    ProgressBar progressBar;
    Bitmap bitmap;
    String wattage;

    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_equipment);

        imageView = findViewById(R.id.imageView);
        upload_btn = findViewById(R.id.btnUpload);


        Button btnAnnuler = findViewById(R.id.btnAnnuler);

        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewEquipmentActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir retourner en arrière ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode() == Activity.RESULT_OK){
                            Intent data = o.getData();
                            Uri uri = data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                imageView.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }

        });



        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                if (bitmap != null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[]bytes = byteArrayOutputStream.toByteArray();
                    base64Image = Base64.encodeToString(bytes,Base64.DEFAULT);

                    userId = UserId.getUserId();

                    EditText nomEditText = findViewById(R.id.nom);
                    nom = nomEditText.getText().toString();
                    EditText wattageEditText = findViewById(R.id.wattage);
                    wattage = wattageEditText.getText().toString();

                    recupPhoto();


                }else {
                    Toast.makeText(NewEquipmentActivity.this, "Veuillez selectionner une image", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    public void ApiReponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String message = jsonObject.getString("message");
            Toast.makeText(NewEquipmentActivity.this, message, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), AccueilActivity.class);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(NewEquipmentActivity.this, "Erreur lors de l'analyse de la réponse JSON", Toast.LENGTH_SHORT).show();
        }
    }

    public void recupPhoto(){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://10.0.2.2/devmobile/actions/UploadImage.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ApiReponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewEquipmentActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("image", base64Image);
                paramV.put("nom", nom);
                paramV.put("wattage", wattage);
                paramV.put("idUser", userId);

                return paramV;
            }
        };
        queue.add(stringRequest);
    }
}


