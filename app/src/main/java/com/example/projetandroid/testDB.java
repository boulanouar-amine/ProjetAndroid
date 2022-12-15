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
        db.addPesonne(new Personne("fdsfsf","2002",20,"MALE","NORMAL","NORMAL",1,1));
        db.addPesonne(new Personne("vdvd","P@ssword",45,"MALE","LOW","NORMAL",60,14));

        Log.d("Reading: ", "Reading all Personnes..");
        List<Personne> personneList = db.getAllPersonnes();

        for (Personne personne : personneList)
        {
            String log = "Username: " +personne.getUsername()
                    + " ,Password: " + personne.getPassword()
                    + " ,Age: " + personne.getAge()
                    + " ,Genre: " + personne.getGenre()
                    + " ,BloodPressure: " + personne.getBloodPressure()
                    + " ,Cholesterol: " + personne.getCholesterol()
                    + " ,Na: " + personne.getNa()
                    + " ,K: " + personne.getK();
            Log.d("Personne: : ", log);
        }

    }
}