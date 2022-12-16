package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        Button b1 = (Button) findViewById(R.id.button);

        //Handeling the database on first launch
        DatabaseHandler db = new DatabaseHandler(this);
        readPersonneData(db);
        User admin = new User("admin","admin");
        db.addUser(admin);


        //login button functionality
        b1.setOnClickListener(v -> {
            User loginUser = new User(username.getText().toString(),password.getText().toString());
            if (db.checkUsernamePassword(loginUser))
            {
                Toast.makeText(MainActivity.this, "logged in as " +loginUser.getUsername(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, testDB.class));
            } else {
                Toast.makeText(MainActivity.this, "wrong username or password", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void readPersonneData(DatabaseHandler db){
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
          new InputStreamReader(is, StandardCharsets.UTF_8)
        );
        String line = "";
        try{
            //skip the headers
            reader.readLine();
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

                db.addPesonne(personne);
            }
        }
        catch(IOException e){
            Log.wtf("MainActivity","Error reading data file on line " + line,e);
        }



    }
}

