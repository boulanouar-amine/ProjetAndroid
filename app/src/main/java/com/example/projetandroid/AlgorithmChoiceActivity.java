package com.example.projetandroid;

import static com.example.projetandroid.Algorithm.mostFrequent;

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

            //getting the last added person in the database from their ROWID instead of sending the whole object from the previous activity
            Personne targetPersonne =  db.getLastAddedPersonne();

            //helps having same name for the results of all the algorithms instead of having to change the name of the result for each algorithm
            String drugResult = "";

            if(selectedAlgoText.equals("Nearest neighbor")){

                TreeSet<Integer> nearestNeighbours;

                //getting the nearest neighbours (an array of their ROWID since i use multiple rows as a primary key) of the last added personne
                nearestNeighbours = Algorithm.NearestNeighbour(targetPersonne, db.getAllPersonnes());

                Personne neighbour;

                ArrayList<String> neighboursDrugs = new ArrayList<>();

                //getting the drugs of the 5 nearest neighbours from the database
                //can be changed to get the number of neighbours you want
                //also can show all 5 drugs in a list instead of just the most frequent one
                for (Integer rowid : nearestNeighbours) {

                   neighbour = db.getPersonneFromRowId(String.valueOf(rowid));
                   neighboursDrugs.add(neighbour.getDrug());
                }
                String mostFrequentDrug = mostFrequent(neighboursDrugs);

                //setting the drug in the database
                targetPersonne.setDrug(mostFrequentDrug);
                db.updatePersonne(targetPersonne);

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