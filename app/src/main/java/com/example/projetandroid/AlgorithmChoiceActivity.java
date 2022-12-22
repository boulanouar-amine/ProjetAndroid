package com.example.projetandroid;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetandroid.decisiontree.DecisionTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class AlgorithmChoiceActivity extends AppCompatActivity {

    //Read the data from the csv file and add it to the database
    private List<String> readFeatures() {
        List<String> feautures = new ArrayList<>();
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );
        String line = "";
        try {
            //skip the headers
            feautures = List.of(reader.readLine().split(","));
        } catch (IOException e) {
            Log.wtf("Application", "Error reading data file on line " + line, e);

        }return feautures;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritm_choice);

        DatabaseHandler db = new DatabaseHandler(this);

        Button calculate = findViewById(R.id.calculate);
        RadioGroup radioAlgo = findViewById(R.id.radio_Choice);
        TextView algorithm = findViewById(R.id.algorithm);
        TextView result = findViewById(R.id.result);
        Button viewDecisionTree = findViewById(R.id.viewDecisionTree);


        List<List<String>> data = new ArrayList<>();
        List<String> features = readFeatures();



            calculate.setOnClickListener(view -> {
        try {
            RadioButton selectedAlgo = (RadioButton) findViewById(radioAlgo.getCheckedRadioButtonId());
            String selectedAlgoText = selectedAlgo.getText().toString();

            //getting the last added person in the database from their ROWID instead of sending the whole object from the previous activity
            Personne targetPersonne =  db.getLastAddedPersonne();

            //helps having same name for the results of all the algorithms instead of having to change the name of the result for each algorithm
            String drugResult = "";

            if(selectedAlgoText.equals("Nearest neighbor"))
                 drugResult = Algorithms.knn_Algorithm(targetPersonne, db.getAllPersonnes());

            else if(selectedAlgoText.equals("Naive Bayes"))
                 drugResult  = Algorithms.bayes_Algorithm(targetPersonne, db.getAllPersonnes());

            else if(selectedAlgoText.equals("Decision Tree")) {

                for (Personne p : db.getAllPersonnes()) {
                    data.add(p.toList());
                }


                DecisionTree decisionTree = new DecisionTree(data, features);
                drugResult = decisionTree.predict(targetPersonne.toList());
                viewDecisionTree.setVisibility(Button.VISIBLE);

                System.out.println(decisionTree);

                viewDecisionTree.setOnClickListener(
                        buttonView -> {

                            startActivity(new Intent(AlgorithmChoiceActivity.this, DecisionTreeActivity.class));
                        });

            }
            //setting the drug in the database
            targetPersonne.setDrug(drugResult);
            db.updatePersonne(targetPersonne);

            algorithm.setVisibility(TextView.VISIBLE);
            result.setText(drugResult);
            result.setVisibility(TextView.VISIBLE);

            Toast.makeText(this,
                    "results using " + selectedAlgoText  + " is " + drugResult,
                    Toast.LENGTH_SHORT).show();

        }catch (NullPointerException e){
            Toast.makeText(this, "Please select a choice", Toast.LENGTH_SHORT).show();
            }
        });


    }
}