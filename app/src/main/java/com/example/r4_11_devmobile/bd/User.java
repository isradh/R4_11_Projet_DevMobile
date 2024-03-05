package com.example.r4_11_devmobile.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class User extends SQLiteOpenHelper {
    private static final String databaseName = "bdUsers";
    private static final int version = 1;
    public User(@Nullable Context context) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id integer PRIMARY KEY, nom VARCHAR(50), prenom VARCHAR(50), adresse VARCHAR(70), code_postal VARCHAR(5), ville VARCHAR(50),FOREIGN KEY (id) REFERENCES users(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public boolean insertData(String nom,String prenom, String adresse, String code_postal, String ville){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentval = new ContentValues();

        String nomC = nom.substring(0, 1).toUpperCase() + nom.substring(1);
        String prenomC = prenom.substring(0, 1).toUpperCase() + prenom.substring(1);

        contentval.put("nom", nomC);
        contentval.put("prenom", prenomC);
        contentval.put("adresse", adresse);
        contentval.put("code_postal", code_postal);
        contentval.put("ville", ville);

        long result = MyDatabase.insert("users", null, contentval);
        return result != -1;
    }

}
