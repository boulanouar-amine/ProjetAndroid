package com.example.projetandroid;

import static com.example.projetandroid.Algorithm.mostFrequent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TreeSet;

public class AlgorithmChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritm_choice);

        DatabaseHandler db = new DatabaseHandler(this);

        Button calculate =  findViewById(R.id.calculate);
        RadioGroup radioAlgo =  findViewById(R.id.radio_Choice);

        TextView recommendedDrug = findViewById(R.id.recommendedDrug);
        TextView drugResult = findViewById(R.id.drugResult);

        calculate.setOnClickListener(view -> {
        try {
            RadioButton selectedAlgo = findViewById(radioAlgo.getCheckedRadioButtonId());
            String selectedAlgoText = selectedAlgo.getText().toString();

            //getting the last added person in the database from their ROWID instead of sending the whole object from the previous activity
            Personne targetPersonne =  db.getLastAddedPersonne();

            String drugResultText = "";

            if(selectedAlgoText.equals("Nearest neighbor")){

                TreeSet<Integer> nearestNeighbours;

                //getting the drugs of the 5 nearest neighbours from the database
                nearestNeighbours = Algorithm.NearestNeighbour(targetPersonne, db.getAllPersonnes() ,5);

                Personne neighbour;

                ArrayList<String> neighboursDrugs = new ArrayList<>();


                for (Integer rowid : nearestNeighbours) {

                   neighbour = db.getPersonneFromRowId(String.valueOf(rowid));
                   neighboursDrugs.add(neighbour.getDrug());
                }
                String mostFrequentDrug = mostFrequent(neighboursDrugs);

                //setting the drug in the database
                targetPersonne.setDrug(mostFrequentDrug);
                db.updatePersonne(targetPersonne);

                drugResultText = mostFrequentDrug;
            }else if(selectedAlgoText.equals("Naive Bayes")){

                //getting the drug from the Naive Bayes algorithm
               drugResultText = Algorithm.naiveBayes(targetPersonne, db.getAllPersonnes());
            }

            drugResult.setText(drugResultText);
            recommendedDrug.setVisibility(View.VISIBLE);
            drugResult.setVisibility(View.VISIBLE);


            Toast.makeText(this,
                    "results using " + selectedAlgoText  + " is " + drugResultText,
                    Toast.LENGTH_SHORT).show();

        }catch (NullPointerException e){
            Toast.makeText(this, "Please select a choice", Toast.LENGTH_SHORT).show();
            }
        });


    }
}