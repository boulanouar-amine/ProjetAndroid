package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class testDB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);

        DatabaseHandler db = new DatabaseHandler(this);
        Log.d("Insert: ", "Inserting ..");

        db.addPesonne(new Personne(20,"MALE","NORMAL","NORMAL",1,1,"Drug Y"));

        db.addUser(new User("admin","admin"));
        db.addPesonne(new Personne(55,"MALE","LOW","NORMAL",60,14,"drug D"));

        Log.d("Reading: ", "Reading all Personnes..");
        List<Personne> personneList = db.getAllPersonnes();

        for (Personne personne : personneList)
        {
            String log =
                      "Username: " +personne.getUsername()
                    + " ,Age: " + personne.getAge()
                    + " ,Genre: " + personne.getGenre()
                    + " ,BloodPressure: " + personne.getBloodPressure()
                    + " ,Cholesterol: " + personne.getCholesterol()
                    + " ,Na: " + personne.getNa()
                    + " ,K: " + personne.getK()
                    + ",Drug: " + personne.getDrug();
            Log.d("Personne: : ", log);
        }

    }
}