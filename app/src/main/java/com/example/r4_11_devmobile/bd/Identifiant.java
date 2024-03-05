package com.example.r4_11_devmobile.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Identifiant extends SQLiteOpenHelper {

    private static final String databaseName = "bdIdentifiant";
    private static final int version = 1;
    public Identifiant(@Nullable Context context) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE identifiant (id integer PRIMARY KEY, email VARCHAR(50), mdp VARCHAR(50), UNIQUE(email))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public boolean insertData(String email,String mdp){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentval = new ContentValues();

        contentval.put("email", email);
        contentval.put("mdp", mdp);

        long result = MyDatabase.insert("identifiants", null, contentval);

        return result != -1;
    }

}
