package com.example.projetandroid;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Application extends android.app.Application {
    private static Application instance;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //Handeling the database on first launch
        DatabaseHandler db = new DatabaseHandler(this);

        readPersonneData(db);
        //i have just one user currently but i will add more later
        User admin = new User("admin","admin");
        db.addUser(admin);
    }

    //Read the data from the csv file and add it to the database
    private void readPersonneData(DatabaseHandler db){
        List<String> feautures ;
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );
        String line = "";
        try{
            //skip the headers
            feautures = List.of(reader.readLine().split(","));

            while((line = reader.readLine()) != null){
                //split by ','
                String[] tokens = line.split(",");
                Personne personne = new Personne();

                personne.setAge(Integer.parseInt(tokens[0]));
                personne.setGenre(tokens[1]);
                personne.setBloodPressure(tokens[2]);
                personne.setCholesterol(tokens[3]);
                personne.setNa(Double.parseDouble(tokens[4]));
                personne.setK(Double.parseDouble(tokens[5]));
                personne.setDrug(tokens[6]);

                db.addPersonne(personne);
            }
        }
        catch(IOException e){
            Log.wtf("Application","Error reading data file on line " + line,e);
        }


    }
}

