package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

public class AlgoritmChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritm_choice);

        DatabaseHandler db = new DatabaseHandler(this);

        Button calculate = (Button) findViewById(R.id.calculate);
        RadioGroup radioAlgo = (RadioGroup) findViewById(R.id.radio_Choice);

        calculate.setOnClickListener(view -> {
        try {
            RadioButton selectedAlgo = (RadioButton) findViewById(radioAlgo.getCheckedRadioButtonId());
            String selectedAlgoText = selectedAlgo.getText().toString();

            //testing the database
            Personne personne =  db.getLastAddedPersonne();

            String drugResult = "";
            if(selectedAlgoText.equals("Nearest neighbor")){

                drugResult = Algorithme.NearestNeighbour(personne, db.getAllPersonnes());

                personne.setDrug(drugResult);
                db.updatePersonne(personne);


            }
            Toast.makeText(this,
                    "results using " + selectedAlgoText  + " is " + drugResult,
                    Toast.LENGTH_SHORT).show();

        }catch (NullPointerException e){
            Toast.makeText(this, "Please select a choice", Toast.LENGTH_SHORT).show();
            }
        });


    }
}