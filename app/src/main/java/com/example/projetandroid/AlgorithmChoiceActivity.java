package com.example.projetandroid;

import static com.example.projetandroid.Algorithme.mostFrequent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TreeSet;

public class AlgorithmChoiceActivity extends AppCompatActivity {

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

                TreeSet<Integer> neighrestNeighbours;

                neighrestNeighbours = Algorithme.NearestNeighbour(personne, db.getAllPersonnes());
                Personne neighbour;

                ArrayList<String> neighboursDrugs = new ArrayList<>();
                for (Integer rowid : neighrestNeighbours) {

                   neighbour = db.getPersonneFromRowId(String.valueOf(rowid));
                   neighboursDrugs.add(neighbour.getDrug());
                }
                String mostFrequentDrug = mostFrequent(neighboursDrugs);

                System.out.println(mostFrequentDrug);
                personne.setDrug(mostFrequentDrug);
                db.updatePersonne(personne);

                drugResult = mostFrequentDrug;
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